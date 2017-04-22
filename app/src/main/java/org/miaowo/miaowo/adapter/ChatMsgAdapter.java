package org.miaowo.miaowo.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.custom.load_more_list.LMLAdapter;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseViewHolder;

import java.util.ArrayList;

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

public class ChatMsgAdapter extends LMLAdapter<ChatMessage> {

    public ChatMsgAdapter() {
        super(new ArrayList<>(), new ViewLoaderCreator<ChatMessage>() {
            private State mState;

            private final int MY = 1;
            private final int TO = 2;
            @Override
            public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater
                        .from(BaseActivity.get).inflate(R.layout.list_chat_message, parent, false));
            }

            @Override
            public void bindView(ChatMessage item, RecyclerView.ViewHolder holder, int type) {
                BaseViewHolder h = (BaseViewHolder) holder;
                h.itemView.setBackgroundDrawable(
                        BaseActivity.get.getResources().getDrawable(type == MY
                                ? R.drawable.bg_rect_red_a100
                                : type == TO ? R.drawable.bg_rect_deep_purple_300
                                : R.drawable.bg_rect_blue_200));
                h.setText(R.id.tv_msg, Html.fromHtml(item.getContent()));
            }

            @Override
            public int setType(ChatMessage item, int position) {
                mState = new StateImpl();
                return item.getFromuid() == mState.loginUser().getUid() ? MY : TO;
            }
        });
    }
}
