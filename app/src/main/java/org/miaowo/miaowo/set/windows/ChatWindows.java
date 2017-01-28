package org.miaowo.miaowo.set.windows;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.ui.FloatView;
import org.miaowo.miaowo.ui.LoadMoreList;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;

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
    public FloatView showChatList(ArrayList<User> chatList) {
        mChatList = chatList;
        // 判断是否登录
        if (mState.getLocalUser().getId() < 0) return null;
        // 显示对话选择列表
        FloatView view = new FloatView(R.layout.window_normal_list);
        View v = view.getView();

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
                ImageUtil.fillImage(holder.iv_user, u);
                return convertView;
            }
        };
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view1, position, id) -> showChatDialog((User) adapter.getItem(position)));

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                User[] users = mChat.getChatList();
                mChatList = new ArrayList<>();
                Collections.addAll(mChatList, users);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        }.execute();

        return view.defaultCloseButton().show();
    }

    // 聊天窗口
    FloatView showChatDialog(final User from) {
        // 已登录
        final User to = mState.getLocalUser();
        if (to.getId() < 0) return null;
        // 未显示
        FloatView view = FloatView.isShowing(from);
        if (FloatView.isShowing(from) != null) {
            LogUtil.i("windowSearch", view.toString());
            return view;
        }

        mMsgList = new ArrayList<>();
        view = new FloatView(R.layout.window_chat);
        view.setTag(from);
        View v = view.getView();

        ImageView iv_user = (ImageView) v.findViewById(R.id.include).findViewById(R.id.iv_user);
        TextView tv_user = (TextView) v.findViewById(R.id.include).findViewById(R.id.tv_user);
        final LoadMoreList list = (LoadMoreList) v.findViewById(R.id.list);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        final EditText et_msg = (EditText) v.findViewById(R.id.et_msg);

        ImageUtil.fillImage(iv_user, from);
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
        list.setPullRefresher(() -> {
            mMsgList = mAdapter.getItems();
            new AsyncTask<ChatMessage, Void, ArrayList<ChatMessage>>() {
                @Override
                protected ArrayList<ChatMessage> doInBackground(ChatMessage... params) {
                    ArrayList<ChatMessage> chatMessages = new ArrayList<>();
                    ChatMessage[] beforeMessage = mChat.getBeforeMessage(params[0]);
                    Collections.addAll(chatMessages, beforeMessage);
                    return chatMessages;
                }

                @Override
                protected void onPostExecute(ArrayList<ChatMessage> chatMessages) {
                    chatMessages.addAll(mMsgList);
                    mMsgList = chatMessages;
                    mAdapter.updateDate(mMsgList);
                }
            }.execute(mMsgList.size() == 0
                    ? new ChatMessage(-1, System.currentTimeMillis(), from, D.getInstance().thisUser, "")
                    : mMsgList.get(mMsgList.size() - 1));
            list.loadOver();
        });
        list.setAdapter(mAdapter);
        btn_send.setOnClickListener(v1 -> new AsyncTask<ChatMessage, Void, ChatMessage>() {

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
        }.execute(new ChatMessage(-1, System.currentTimeMillis(), from, to, et_msg.getText().toString())));

        return view.defaultCloseButton().show();
    }

    private class ViewHolder {
        ImageView iv_user;
        TextView tv_user;
    }

    public static boolean addChatMsg(final ChatMessage msg) {
        FloatView view = FloatView.isShowing(msg.getFrom());
        if (view == null) {
            return false;
        }

        View v = view.getView();
        LoadMoreList list = (LoadMoreList) v.findViewById(R.id.list);
        ItemRecyclerAdapter<ChatMessage> adapter = (ItemRecyclerAdapter<ChatMessage>) list.getAdapter();
        ArrayList<ChatMessage> items = adapter.getItems();
        items.add(msg);
        adapter.updateDate(items);
        list.scrollToPosition(items.size() - 1);
        return true;
    }
}
