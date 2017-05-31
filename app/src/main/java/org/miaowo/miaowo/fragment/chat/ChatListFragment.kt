package org.miaowo.miaowo.fragment.chat

import android.content.Context
import android.view.View
import okhttp3.Request
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatRoomListAdapter
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.base.BaseFragment
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.bean.data.ChatRoomList
import org.miaowo.miaowo.ui.load_more_list.LoadMoreList
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.HttpUtil
import java.io.IOException

class ChatListFragment : BaseFragment(LoadMoreList(App.i)) {
    companion object {
        fun newInstance() : ChatListFragment = ChatListFragment()
    }

    private var mListener: OnChatListener? = null
    private var mList: LoadMoreList? = null
    private var mAdapter: ChatRoomListAdapter? = null

    override fun initView(view: View?) {
        mAdapter = ChatRoomListAdapter(context, mListener)
        mList = view as LoadMoreList

        mList?.adapter = mAdapter!!
        mList?.setPullRefresher(false) { loadChatList() }
        mList?.load()
    }

    private fun loadChatList() {
        API.Doc.chatRoom {
            mAdapter?.update(it?.rooms ?: listOf())
            mList?.loadOver()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnChatListener)
            mListener = context as OnChatListener?
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnChatListener {
        fun toRoom(room: ChatRoom)
    }
}