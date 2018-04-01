package org.miaowo.miaowo.fragment.chat

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatRoomListAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.FragmentCall
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.base.extra.loadFragment
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

class ChatListFragment : BaseListFragment() {
    companion object {
        fun newInstance(): ChatListFragment {
            val fragment = ChatListFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
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
                springView.onFinishFreshAndLoad()
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
        var newBtn: MenuItem? = null
        listener?.toolbar?.menu?.run {
            clear()
            newBtn = add(0, 0, 0, "New")
        }

        newBtn?.run {
            icon = IconicsDrawable(context, FontAwesome.Icon.faw_address_card)
            setOnMenuItemClickListener {
                val call = object : FragmentCall("newRoom", this@ChatListFragment) {
                    override fun call(vararg params: Any?) {
                        newRoom(params[0] as User)
                    }
                }
                listener?.jump(IMiaoListener.JumpFragment.UserList, call)
                true
            }
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val clickItem = mAdapter.getItem(position)
        Miao.i.loadFragment(ChatFragment.newInstance(clickItem))
        return true
    }

    private fun newRoom(user: User) {
        lInfo(user)
    }
}