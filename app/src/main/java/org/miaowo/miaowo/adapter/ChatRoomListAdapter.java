package org.miaowo.miaowo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.fragment.chat.ChatListFragment;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.ArrayList;

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

public class ChatRoomListAdapter extends LMLPageAdapter<ChatRoom> {

    public ChatRoomListAdapter(Context context, ChatListFragment.OnChatListener listener) {
        super(new ArrayList<>(), new ViewLoaderCreator<ChatRoom>() {
            private ImageUtil image = ImageUtil.utils();
            @Override
            public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.list_chat, parent, false));
            }

            @Override
            public void bindView(ChatRoom item, RecyclerView.ViewHolder holder, int type) {
                BaseViewHolder h = (BaseViewHolder) holder;
                h.getView().setOnClickListener(v -> listener.toRoom(item));
                image.setUser((ImageView) h.getView(R.id.iv_user), item.getLastUser(), false);
                h.setText(R.id.tv_user, item.getLastUser().getUsername());
            }

            @Override
            public int setType(ChatRoom item, int position) {
                return 1;
            }
        });
    }
}
