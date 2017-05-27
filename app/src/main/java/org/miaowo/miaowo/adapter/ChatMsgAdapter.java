package org.miaowo.miaowo.adapter;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.FormatUtil;

import java.util.ArrayList;

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

public class ChatMsgAdapter extends LMLPageAdapter<ChatMessage> {
    public final static int MY = 1;
    private final static int TO = 2;

    public ChatMsgAdapter() {
        super(new ArrayList<>(), new ViewLoaderCreator<ChatMessage>() {
            private FormatUtil format = FormatUtil.format();

            @Override
            public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater
                        .from(BaseActivity.get).inflate(R.layout.list_chat_message, parent, false));
            }

            @Override
            public void bindView(ChatMessage item, RecyclerView.ViewHolder holder, int type) {
                BaseViewHolder h = (BaseViewHolder) holder;
                boolean my = type == MY;
                ViewCompat.setBackground(h.getView(R.id.tv_msg)
                        , ResourcesCompat.getDrawable(BaseActivity.get.getResources()
                                , my ? R.drawable.bg_rect_red_a100 : R.drawable.bg_rect_deep_purple_300, null));
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) h.getView(R.id.tv_msg).getLayoutParams();
                layoutParams.addRule(my ? RelativeLayout.ALIGN_PARENT_RIGHT : RelativeLayout.ALIGN_PARENT_LEFT);
                h.getView(R.id.tv_msg).setLayoutParams(layoutParams);
                format.parseHtml(item.getContent(), spanned -> h.setText(R.id.tv_msg, spanned));
            }

            @Override
            public int setType(ChatMessage item, int position) {
                return item.getFromuid() == API.loginUser.getUid() ? MY : TO;
            }
        });
    }
}
