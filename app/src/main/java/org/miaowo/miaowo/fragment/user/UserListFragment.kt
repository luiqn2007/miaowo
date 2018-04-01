package org.miaowo.miaowo.fragment.user

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.adapter.UserListAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.submitAndRemoveCall
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 用户列表
 */
class UserListFragment : BaseListFragment() {
    companion object {
        fun newInstance(callTag: String? = null): UserListFragment {
            val fragment = UserListFragment()
            val args = Bundle()
            if (callTag != null)
                args.putString(Const.TAG, callTag)
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = UserListAdapter()
    private var mCallTag: String? = null

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCallTag = arguments?.getString(Const.TAG)
    }

    override fun onRefresh() {
        API.Doc.user {
            Miao.i.runOnUiThread {
                mAdapter.update(it?.users ?: emptyList())
                springView.onFinishFreshAndLoad()
            }
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val user = mAdapter.getItem(position)
        var handlerOver = false
        if (mCallTag != null)
            handlerOver = submitAndRemoveCall(mCallTag!!, user)
        if (!handlerOver) {
            val listener = attach as? IMiaoListener
            listener?.jump(IMiaoListener.JumpFragment.User, user.username)
        }
        return true
    }
}
