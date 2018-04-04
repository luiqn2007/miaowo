package org.miaowo.miaowo.fragment.chat

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatRoomListAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.user.UserListFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

class ChatListFragment : BaseListFragment() {
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
            activity?.runOnUiThread {
                mAdapter.update(it?.rooms ?: listOf())
                super.onRefresh()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = attach as? IMiaoListener
        listener?.setToolbar(getString(R.string.chat))
        listener?.buttonVisible = true

        listener?.button?.setOnClickListener {
            val call = object : FragmentCall("newRoom", this) {
                override fun call(vararg params: Any?) {
                    newRoom(params[0] as User)
                }
            }
            registerCall(call)
            UserListFragment.newInstance().showSelf(Miao.i, this)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        (attach as? IMiaoListener)?.buttonVisible = !hidden
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val clickItem = mAdapter.getItem(position)
        Miao.i.showFragment(ChatFragment.newInstance(clickItem), this)
        return true
    }

    private fun newRoom(user: User) {
        lInfo(user)
    }
}