package org.miaowo.miaowo.set;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.ChatMessage;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.ui.LoadMoreList;
import org.miaowo.miaowo.util.PopupUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 聊天窗口集合
 * Created by luqin on 17-1-1.
 */

public class ChatWindows {

    private ArrayList<User> mChatList;
    private ArrayList<ChatMessage> mMsgList;
    private ItemRecyclerAdapter<ChatMessage> mAdapter;
    private State mState;
    private Chat mChat;
    private D d;

    public ChatWindows() {
        mState = new StateImpl();
        mChat = new ChatImpl();
        d = D.getInstance();
    }

    // 聊天列表
    public PopupWindow showChatList(ArrayList<User> chatList) {
        mChatList = chatList;
        // 判断是否登录
        if (mState.getLocalUser().getId() < 0) return null;
        // 显示对话选择列表
        return PopupUtil.showPopupWindowInCenter(R.layout.window_chat_list,
                new PopupUtil.PopupWindowInit() {
                    @Override
                    public void init(View v, PopupWindow window) {
                        ListView list = (ListView) v.findViewById(R.id.list);
                        final BaseAdapter adapter = new BaseAdapter() {
                            @Override
                            public int getCount() {
                                if (mChatList == null) return 0;
                                else return mChatList.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return mChatList.get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                if (convertView == null) {
                                    ViewHolder holder = new ViewHolder();
                                    convertView = View.inflate(d.activeActivity, R.layout.list_chat, null);
                                    View relView = convertView.findViewById(R.id.include);
                                    holder.iv_user = (ImageView) relView.findViewById(R.id.iv_user);
                                    holder.tv_user = (TextView) relView.findViewById(R.id.tv_user);
                                    convertView.setTag(holder);
                                }
                                ViewHolder holder = (ViewHolder) convertView.getTag();
                                User u = (User) getItem(position);
                                holder.tv_user.setText(u.getName());
                                Picasso.with(d.activeActivity).load(u.getHeadImg())
                                        .transform(new CropCircleTransformation()).fit()
                                        .into(holder.iv_user);
                                return convertView;
                            }
                        };
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                showChatDialog((User) adapter.getItem(position));
                            }
                        });

                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected Void doInBackground(Void... params) {
                                mChatList = mChat.getChatList();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                adapter.notifyDataSetChanged();
                            }
                        }.execute();
                    }
                }, null);
    }

    // 聊天窗口
    public PopupWindow showChatDialog(final User from) {
        // 当前账户
        final User to = mState.getLocalUser();
        if (to.getId() < 0) return null;
        mMsgList = new ArrayList<>();
        D.getInstance().activeChatUser = from;
        return PopupUtil.showPopupWindowInCenter(R.layout.window_chat, new PopupUtil.PopupWindowInit() {
                    @Override
                    public void init(View v, PopupWindow window) {
                        ImageView iv_user = (ImageView) v.findViewById(R.id.include).findViewById(R.id.iv_user);
                        TextView tv_user = (TextView) v.findViewById(R.id.include).findViewById(R.id.tv_user);
                        final LoadMoreList list = (LoadMoreList) v.findViewById(R.id.list);
                        Button btn_send = (Button) v.findViewById(R.id.btn_send);
                        final EditText et_msg = (EditText) v.findViewById(R.id.et_msg);

                        Picasso.with(d.activeActivity).load(from.getHeadImg())
                                .transform(new CropCircleTransformation()).fit()
                                .into(iv_user);
                        tv_user.setText(from.getName());
                        mAdapter = new ItemRecyclerAdapter<>(
                                mMsgList, new ItemRecyclerAdapter.ViewLoader<ChatMessage>() {
                            @Override
                            public ItemRecyclerAdapter.ViewHolder createHolder(ViewGroup parent, int viewType) {
                                return new ItemRecyclerAdapter.ViewHolder(
                                        LayoutInflater.from(d.activeActivity).inflate(R.layout.list_chat_message, parent, false));
                            }

                            @Override
                            public void bindView(ChatMessage item, ItemRecyclerAdapter.ViewHolder holder) {
                                View v = holder.getView();
                                if (to.equals(item.getFrom())) {
                                    v.setBackgroundDrawable(d.activeActivity.getResources().getDrawable(R.drawable.bg_rect_indigo_100));
                                } else {
                                    v.setBackgroundDrawable(d.activeActivity.getResources().getDrawable(R.drawable.bg_rect_red_100));
                                }
                                holder.getTextView(R.id.tv_msg).setText(item.getMessage());
                            }

                            @Override
                            public int setType(ChatMessage item, int position) {
                                return 0;
                            }
                        });
                        list.setPullRefresher(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                mMsgList = mAdapter.getItems();
                                new AsyncTask<ChatMessage, Void, ArrayList<ChatMessage>>() {
                                    @Override
                                    protected ArrayList<ChatMessage> doInBackground(ChatMessage... params) {
                                        return mChat.getBeforeMessage(params[0]);
                                    }

                                    @Override
                                    protected void onPostExecute(ArrayList<ChatMessage> chatMessages) {
                                        chatMessages.addAll(mMsgList);
                                        mMsgList = chatMessages;
                                        mAdapter.updateDate(mMsgList);
                                    }
                                }.execute(mMsgList.size() == 0 
                                        ? new ChatMessage(from, null, null) 
                                        : mMsgList.get(mMsgList.size() - 1));
                                list.loadOver();
                            }
                        });
                        list.setAdapter(mAdapter);
                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AsyncTask<ChatMessage, Void, ChatMessage>() {

                                    @Override
                                    protected ChatMessage doInBackground(ChatMessage... params) {
                                        try {
                                            mChat.sendMessage(params[0]);
                                        } catch (Exception e) {
                                            d.activeActivity.handleError(e);
                                            return null;
                                        }
                                        return params[0];
                                    }

                                    @Override
                                    protected void onPostExecute(ChatMessage c) {
                                        if (c != null) {
                                            et_msg.setText("");
                                            mMsgList.add(c);
                                            mAdapter.notifyDataSetChanged();
                                            list.scrollToPosition(mMsgList.size() - 1);
                                        }
                                    }
                                }.execute(new ChatMessage(to, from, et_msg.getText().toString()));
                            }
                        });
                    }
                },
                new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        d.activeChatUser = null;
                    }
                });
    }

    private class ViewHolder {
        ImageView iv_user;
        TextView tv_user;
    }


}
