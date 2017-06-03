package org.miaowo.miaowo.adapter

import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.ChatMessage
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLViewCreator
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

class ChatMsgAdapter : LMLPageAdapter<ChatMessage>(object : LMLViewCreator<ChatMessage> {

    override fun createHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(LayoutInflater
                .from(App.i).inflate(R.layout.list_chat_message, parent, false))
    }

    override fun bindView(item: ChatMessage?, holder: RecyclerView.ViewHolder?, type: Int) {
        val h = holder as BaseViewHolder
        val my = type == Const.MY
        ViewCompat.setBackground(h.getView(R.id.tv_msg), ResourcesCompat.getDrawable(App.i.resources, if (my) R.drawable.bg_rect_red_a100 else R.drawable.bg_rect_deep_purple_300, null))
        val layoutParams = h.getView(R.id.tv_msg)?.layoutParams as RelativeLayout.LayoutParams
        layoutParams.addRule(if (my) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT)
        h.getView(R.id.tv_msg)?.layoutParams = layoutParams
        FormatUtil.parseHtml(item?.content ?: "") { spanned -> h.setText(R.id.tv_msg, spanned) }
    }

    override fun setType(item: ChatMessage?, position: Int): Int {
        return if (item?.fromuid == API.loginUser?.uid) Const.MY else Const.TO
    }
})
