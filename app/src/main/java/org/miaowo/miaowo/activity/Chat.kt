package org.miaowo.miaowo.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_chat.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatPagerAdapter
import org.miaowo.miaowo.bean.data.ChatMessage
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.fragment.chat.ChatListFragment

class Chat : AppCompatActivity(), ChatListFragment.OnChatListener {

    private val mAdapter = ChatPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        container.adapter = mAdapter
        tab.tabMode = TabLayout.MODE_SCROLLABLE
        tab.setupWithViewPager(container)
    }

    override fun toRoom(room: ChatRoom) {
        var position = mAdapter.getPosition(room.roomId)
        if (position <= 0) position = mAdapter.addChat(room)
        mAdapter.notifyDataSetChanged()
        container!!.setCurrentItem(position, true)
    }

    fun sendMessage(message: ChatMessage) {
        val position = mAdapter.getPosition(message.roomId)
        if (position > 0) {
            mAdapter.sendMessage(position, message)
        }
    }
}
