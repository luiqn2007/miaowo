package org.miaowo.miaowo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.databinding.ListChatBinding
import org.miaowo.miaowo.base.ListBindingHolder
import org.miaowo.miaowo.base.ListHolder

/**
 * 聊天室列表
 * Created by luqin on 17-4-7.
 */

class ChatRoomListAdapter(context: Context) : ListAdapter<ChatRoom>(
        object : ListAdapter.ViewCreator<ChatRoom> {

            override fun createHolder(parent: ViewGroup?, viewType: Int): ListBindingHolder<ListChatBinding> =
                    ListBindingHolder(LayoutInflater.from(context).inflate(R.layout.list_chat, parent, false))

            override fun bindView(item: ChatRoom?, holder: ListHolder?, type: Int) {
                @Suppress("UNCHECKED_CAST")
                if (holder is ListBindingHolder<*>) {
                    val rHolder = holder as ListBindingHolder<ListChatBinding>
                    rHolder.binder.user = item?.lastUser
                }
            }

            override fun setType(item: ChatRoom?, position: Int): Int = 1
        })
