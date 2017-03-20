package org.miaowo.miaowo.set.windows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.web.ChatMessage;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.FloatView;
import org.miaowo.miaowo.view.LoadMoreList;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天窗口集合
 * Created by luqin on 17-1-1.
 */

public class ChatWindows {

    private List<User> mChatList;
    private State mState;
    private Chat mChat;
    private BaseActivity mContext;

    private ChatWindows(BaseActivity context) {
        mContext = context;
        mState = new StateImpl(context);
        mChat = new ChatImpl(context);
    }
    public static ChatWindows windows(BaseActivity context) { return new ChatWindows(context); }

    // 聊天列表
    public FloatView showChatList(ArrayList<User> chatList) {
        mChatList = chatList;
        // 判断是否登录
        if (!mState.isLogin()) return null;
        // 显示对话选择列表
        FloatView view = new FloatView(mContext, "聊天列表", R.layout.window_normal_list);
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
                    convertView = View.inflate(mContext, R.layout.list_chat, null);
                    holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
                    holder.tv_user = (TextView) convertView.findViewById(R.id.tv_user);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();
                User u = (User) getItem(position);
                holder.tv_user.setText(u.getUsername());
                ImageUtil.utils(mContext).setUser(holder.iv_user, u, true);
                return convertView;
            }
        };
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view1, position, id) -> showChatDialog((User) adapter.getItem(position)));

        return view.defaultBar().show();
    }

    // 聊天窗口
    FloatView showChatDialog(final User from) {
        // 自己
        if (from.equals(mState.loginedUser())) {
            return null;
        }
        // 已登录
        if (!mState.isLogin()) return null;
        User to = mState.loginedUser();
        // 未显示
        FloatView view = FloatView.searchByTag(from);
        if (view != null) return view;
        // 更新列表
        mChat.loadList();

        view = new FloatView(mContext, "和 " + from.getUsername() + " 聊天",R.layout.window_chat);
        view.setTag(from);
        View v = view.getView();

        ImageView iv_user = (ImageView) v.findViewById(R.id.include).findViewById(R.id.iv_user);
        TextView tv_user = (TextView) v.findViewById(R.id.include).findViewById(R.id.tv_user);
        final LoadMoreList list = (LoadMoreList) v.findViewById(R.id.list);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        final EditText et_msg = (EditText) v.findViewById(R.id.et_msg);
        et_msg.setText(Long.toString(System.currentTimeMillis()));

        ImageUtil.utils(mContext).setUser(iv_user, from, true);
        tv_user.setText(from.getUsername());
        list.setPullRefresher(() -> mChat.loadBefore());
        list.setAdapter(new ItemRecyclerAdapter<>(
                new ArrayList<>(), new ItemRecyclerAdapter.ViewLoader<ChatMessage>() {
            @Override
            public ItemRecyclerAdapter.ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new ItemRecyclerAdapter.ViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.list_chat_message, parent, false));
            }

            @Override
            public void bindView(ChatMessage item, ItemRecyclerAdapter.ViewHolder holder) {
                View v = holder.getView();
                if (to.equals(item.getFrom())) {
                    v.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_rect_indigo_100));
                } else {
                    v.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_rect_red_100));
                }
                holder.getTextView(R.id.tv_msg).setText(item.getMessage());
            }

            @Override
            public int setType(ChatMessage item, int position) {
                return 0;
            }
        }));
        btn_send.setOnClickListener(v1 ->
                mChat.sendMessage(new ChatMessage(-1, System.currentTimeMillis(), from, to, et_msg.getText().toString())));
        return view.defaultBar().show();
    }

    private class ViewHolder {
        ImageView iv_user;
        TextView tv_user;
    }

    public static boolean addChatMsg(final ChatMessage msg) {
        FloatView view = FloatView.searchByTag(msg.getFrom());
        if (view == null) {
            return false;
        }

        View v = view.getView();
        LoadMoreList list = (LoadMoreList) v.findViewById(R.id.list);
        ItemRecyclerAdapter<ChatMessage> adapter = (ItemRecyclerAdapter<ChatMessage>) list.getAdapter();
        List<ChatMessage> items = adapter.getItems();
        items.add(msg);
        adapter.updateDate(items);
        list.scrollToPosition(items.size() - 1);
        return true;
    }
}
