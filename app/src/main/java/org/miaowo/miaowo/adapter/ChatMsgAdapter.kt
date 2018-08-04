package org.miaowo.miaowo.adapter

import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.App
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.data.bean.ChatMessage
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.setHTML
import org.miaowo.miaowo.other.setUserIcon

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

class ChatMsgAdapter : ListAdapter<ChatMessage>(
        object : ListAdapter.ViewCreator<ChatMessage> {

            override fun createHolder(parent: ViewGroup, viewType: Int) = ListHolder(R.layout.list_chat_message, parent)

            override fun bindView(item: ChatMessage, holder: ListHolder, type: Int) {
                val my = type == Const.MY
                ViewCompat.setBackground(holder[R.id.tv_msg], ResourcesCompat.getDrawable(App.i.resources,
                        if (my) R.drawable.bg_rect_red_a100 else R.drawable.bg_rect_deep_purple_300, null))
                val layoutParams = holder[R.id.tv_msg]?.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(if (my) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT)
                holder.find(R.id.tv_msg)?.layoutParams = layoutParams
                holder.find<ImageView>(R.id.iv_user)?.setUserIcon(item.fromUser)
                holder.find<TextView>(R.id.tv_msg)?.setHTML(item.content)
            }

            override fun setType(item: ChatMessage, position: Int): Int {
                return if (item.fromuid == API.user.uid) Const.MY else Const.TO
            }
        })
