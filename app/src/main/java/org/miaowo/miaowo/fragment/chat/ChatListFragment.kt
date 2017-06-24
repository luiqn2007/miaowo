package org.miaowo.miaowo.fragment.chat

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import org.miaowo.miaowo.adapter.ChatRoomListAdapter
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.ui.load_more_list.LoadMoreList
import org.miaowo.miaowo.util.API

class ChatListFragment : Fragment() {
    companion object {
        fun newInstance() : ChatListFragment = ChatListFragment()
    }

    private var mListener: OnChatListener? = null
    private var mList: LoadMoreList? = null
    private var mAdapter: ChatRoomListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = LoadMoreList(context)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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