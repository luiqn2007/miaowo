package org.miaowo.miaowo.set;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
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
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapters.ItemRecycleAdapter;
import org.miaowo.miaowo.beans.ChatMessage;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.ChatImpl;
import org.miaowo.miaowo.impls.StateImpl;
import org.miaowo.miaowo.impls.interfaces.Chat;
import org.miaowo.miaowo.impls.interfaces.State;
import org.miaowo.miaowo.utils.PopupUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 聊天窗口集合
 * Created by luqin on 17-1-1.
 */

public class ChatWindows {
    private static final String TAG = "ChatWindows";

    private ArrayList<User> mChatList;
    private ArrayList<ChatMessage> mMsgList;
    private Activity context;
    private State mState;
    private Chat mChat;

    public ChatWindows(Activity context) {
        this.context = context;

        mState = new StateImpl();
        mChat = new ChatImpl();
    }

    // 聊天列表
    public PopupWindow showChatList(ArrayList<User> chatList) {
        mChatList = chatList;
        // 判断是否登录
        if (mState.getLocalUser().getId() == -1) return null;
        // 显示对话选择列表
        return PopupUtil.showPopupWindowInCenter(context, C.PW_CHAT_LIST, R.layout.window_chat_list, new PopupUtil.PopupWindowInit() {
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
                            convertView = View.inflate(context, R.layout.list_chat, null);
                            View relView = convertView.findViewById(R.id.include);
                            holder.iv_user = (ImageView) relView.findViewById(R.id.iv_user);
                            holder.tv_user = (TextView) relView.findViewById(R.id.tv_user);
                            convertView.setTag(holder);
                        }
                        ViewHolder holder = (ViewHolder) convertView.getTag();
                        User u = (User) getItem(position);
                        holder.tv_user.setText(u.getName());
                        Picasso.with(context).load(u.getHeadImg())
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
                        mChatList = new ChatImpl().getChatList();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        adapter.notifyDataSetChanged();
                    }
                }.execute();
            }
        });
    }


    // 聊天窗口
    PopupWindow showChatDialog(final User u) {
        // 当前账户
        final User lU = mState.getLocalUser();
        if (u == null || lU.getId() == -1) {
            return null;
        }
        mMsgList = new ArrayList<>();
        return PopupUtil.showPopupWindowInCenter(context, C.PW_CHAT, R.layout.window_chat, new PopupUtil.PopupWindowInit() {
            @Override
            public void init(View v, PopupWindow window) {
                ImageView iv_user = (ImageView) v.findViewById(R.id.include).findViewById(R.id.iv_user);
                TextView tv_user = (TextView) v.findViewById(R.id.include).findViewById(R.id.tv_user);
                final PullLoadMoreRecyclerView list = (PullLoadMoreRecyclerView) v.findViewById(R.id.list);
                Button btn_send = (Button) v.findViewById(R.id.btn_send);
                final EditText et_msg = (EditText) v.findViewById(R.id.et_msg);

                Picasso.with(context).load(u.getHeadImg())
                        .transform(new CropCircleTransformation()).fit()
                        .into(iv_user);
                tv_user.setText(u.getName());
                final ItemRecycleAdapter<ChatMessage> adapter = new ItemRecycleAdapter<>(
                        mMsgList, new ItemRecycleAdapter.ViewLoader<ChatMessage>() {
                    @Override
                    public ItemRecycleAdapter.ViewHolder createHolder(ViewGroup parent, int viewType) {
                        return new ItemRecycleAdapter.ViewHolder(
                                LayoutInflater.from(context).inflate(R.layout.list_chat_message, parent, false));
                    }

                    @Override
                    public void bindView(ChatMessage item, ItemRecycleAdapter.ViewHolder holder) {
                        View v = holder.getView();
                        if (lU.equals(item.getFrom())) {
                            v.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_rect_indigo_100));
                        } else {
                            v.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_rect_red_100));
                        }
                        holder.getTextView(R.id.tv_msg).setText(item.getMessage());
                    }
                }
                );
                list.setLinearLayout();
                list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        // TODO: 刷新
                        list.setPullLoadMoreCompleted();
                    }

                    @Override
                    public void onLoadMore() {
                        list.setPullLoadMoreCompleted();
                    }
                });
                // 关闭上拉
                list.setPushRefreshEnable(false);
                list.setAdapter(adapter);
                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AsyncTask<ChatMessage, Void, ChatMessage>() {

                            @Override
                            protected ChatMessage doInBackground(ChatMessage... params) {
                                mChat.pushMessage(params[0]);
                                return params[0];
                            }

                            @Override
                            protected void onPostExecute(ChatMessage c) {
                                et_msg.setText("");
                                mMsgList.add(c);
                                Log.i(TAG, "onPostExecute: " + c.getMessage());
                                adapter.notifyDataSetChanged();
                            }
                        }.execute(new ChatMessage(lU, u, et_msg.getText().toString()));
                    }
                });
            }
        });
    }

    private class ViewHolder {
        ImageView iv_user;
        TextView tv_user;
    }
}
