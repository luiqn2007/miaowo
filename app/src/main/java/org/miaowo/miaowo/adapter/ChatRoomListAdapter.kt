package org.miaowo.miaowo.adapter

import android.view.ViewGroup
import android.widget.TextView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.base.ListHolder

/**
 * 聊天室列表
 * Created by luqin on 17-4-7.
 */

class ChatRoomListAdapter : ListAdapter<ChatRoom>(
        object : ListAdapter.ViewCreator<ChatRoom> {

            override fun createHolder(parent: ViewGroup?, viewType: Int) = ListHolder(R.layout.list_chat, parent)

            override fun bindView(item: ChatRoom?, holder: ListHolder?, type: Int) {
                if (holder != null) {
                    // DataBinding
                    holder.find<TextView>(R.id.username)?.text = item?.lastUser?.username
                }
            }

            override fun setType(item: ChatRoom?, position: Int): Int = 1
        })
