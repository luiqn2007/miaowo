package org.miaowo.miaowo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.data.bean.ChatRoom
import org.miaowo.miaowo.handler.ChatHandler
import org.miaowo.miaowo.other.BaseListTouchListener
import org.miaowo.miaowo.other.setUserIcon

/**
 * 聊天室列表
 * Created by luqin on 17-4-7.
 */

class ChatRoomAdapter: ListAdapter<ChatRoom>(
        object : ListAdapter.ViewCreator<ChatRoom> {

            override fun createHolder(parent: ViewGroup, viewType: Int) = ListHolder(R.layout.list_chat, parent)

            override fun bindView(item: ChatRoom, holder: ListHolder, type: Int) {
                holder.find<ImageView>(R.id.icon)?.setUserIcon(item.users.firstOrNull { it.uid != API.user.uid })
            }

            override fun setType(item: ChatRoom, position: Int): Int = 0
        })

class ChatRoomListener(context: Context, val adapter: ChatRoomAdapter, val handler: ChatHandler): BaseListTouchListener(context) {
    override fun onClick(view: View?, position: Int): Boolean {
        val room = adapter.getItem(position)
        handler.changeRoom(room.roomId, room.users.firstOrNull { it.uid != API.user.uid }?.uid)
        return true
    }
}