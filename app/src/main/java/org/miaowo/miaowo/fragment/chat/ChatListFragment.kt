package org.miaowo.miaowo.fragment.chat

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatRoomListAdapter
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.user.UserListFragment
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MiaoListFragment

class ChatListFragment : MiaoListFragment(R.string.chat, true) {
    companion object {
        fun newInstance(): ChatListFragment {
            val fragment = ChatListFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
            args.putString(Const.TAG, "${fragment.javaClass.name}.user.${API.user.uid}")
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = ChatRoomListAdapter()

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onRefresh() {
        API.Doc.chatRoom {
            mAdapter.update(it?.rooms ?: listOf())
            super.onRefresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        miaoListener?.button?.setOnClickListener {
            val fg = UserListFragment.newInstance()
            val call = object : FragmentCall("newRoom", this, fg) {
                override fun call(vararg params: Any?) {
                    newRoom(params[0] as User)
                }
            }
            registerCall(call)
            fg.showSelf(Miao.i, this)
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val clickItem = mAdapter.getItem(position)
        Miao.i.showFragment(ChatFragment.newInstance(clickItem), this)
        return true
    }

    private fun newRoom(user: User) = lInfo(user)
}