package org.miaowo.miaowo.adapter

import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.bean.data.ChatMessage
import org.miaowo.miaowo.databinding.ListChatMessageBinding
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListBindingHolder
import org.miaowo.miaowo.base.ListHolder

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

class ChatMsgAdapter : ListAdapter<ChatMessage>(
        object : ListAdapter.ViewCreator<ChatMessage> {

            override fun createHolder(parent: ViewGroup?, viewType: Int): ListBindingHolder<ListChatMessageBinding> {
                return ListBindingHolder(LayoutInflater
                        .from(App.i).inflate(R.layout.list_chat_message, parent, false))
            }

            override fun bindView(item: ChatMessage?, holder: ListHolder?, type: Int) {
                if (holder is ListBindingHolder<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val cHolder = holder as ListBindingHolder<ListChatMessageBinding>
                    val binder = cHolder.binder
                    val my = type == Const.MY
                    ViewCompat.setBackground(binder.tvMsg, ResourcesCompat.getDrawable(App.i.resources,
                            if (my) R.drawable.bg_rect_red_a100 else R.drawable.bg_rect_deep_purple_300, null))
                    val layoutParams = binder.tvMsg.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.addRule(if (my) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT)
                    cHolder.find(R.id.tv_msg)?.layoutParams = layoutParams
                    binder.message = item
                }
            }

            override fun setType(item: ChatMessage?, position: Int): Int {
                return if (item?.fromuid == API.user.uid) Const.MY else Const.TO
            }
        })
