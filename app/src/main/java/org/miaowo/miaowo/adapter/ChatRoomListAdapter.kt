package org.miaowo.miaowo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.fragment.chat.ChatListFragment
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLViewCreator
import org.miaowo.miaowo.util.ImageUtil

/**
 * 聊天信息
 * Created by luqin on 17-4-7.
 */

class ChatRoomListAdapter(context: Context, listener: ChatListFragment.OnChatListener?) : LMLPageAdapter<ChatRoom>(object : LMLViewCreator<ChatRoom> {
    override fun createHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.list_chat, parent, false))

    override fun bindView(item: ChatRoom?, holder: RecyclerView.ViewHolder?, type: Int) {
        val h = holder as BaseViewHolder
        if (item != null) {
            h.view.setOnClickListener { listener?.toRoom(item) }
            ImageUtil.setUser(h.getView(R.id.iv_user) as ImageView, item.lastUser!!, false)
            h.setText(R.id.tv_user, item.lastUser?.username)
        }
    }

    override fun setType(item: ChatRoom?, position: Int): Int = 1
})
