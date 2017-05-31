package org.miaowo.miaowo.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.bean.data.ChatMessage
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.fragment.chat.ChatFragment
import org.miaowo.miaowo.fragment.chat.ChatListFragment

/**
 * ViewPager 的 Adapter
 * Created by luqin on 17-4-23.
 */

class ChatPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mFragments = mutableListOf<ChatFragment>()
    private val mListFragment = ChatListFragment.newInstance()
    
    override fun getItem(position: Int) = when(position) {
        0 -> mListFragment
        else -> mFragments[position - 1]
    }

    override fun getCount() = mFragments.size + 1

    fun addChat(room: ChatRoom): Int {
        val fragment = ChatFragment.newInstance(room)
        mFragments.add(fragment)
        return mFragments.indexOf(fragment) + 1
    }

    fun getPosition(roomId: Int) = mFragments.firstOrNull { roomId == it.room?.roomId }
            ?.let { mFragments.indexOf(it) + 1 } ?: -1

    override fun getPageTitle(position: Int): CharSequence {
        val fragment = getItem(position)
        when(fragment) {
            is ChatListFragment -> return App.i.getString(R.string.chat_all)
            is ChatFragment -> return fragment.room?.lastUser?.username ?: App.i.getString(R.string.err_chat_user)
            else -> return App.i.getString(R.string.err_chat_user)
        }
    }

    fun sendMessage(position: Int, message: ChatMessage) {
        val fragment = getItem(position)
        if (fragment is ChatFragment) fragment.newMessage(message)
    }
}
