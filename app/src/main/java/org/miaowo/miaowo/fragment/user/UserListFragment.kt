package org.miaowo.miaowo.fragment.user

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.UserListAdapter
import org.miaowo.miaowo.base.extra.showSelf
import org.miaowo.miaowo.base.extra.submitAndRemoveCall
import org.miaowo.miaowo.bean.data.Pagination
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MiaoListFragment

/**
 * 用户列表
 */
class UserListFragment : MiaoListFragment(R.string.user_list) {
    companion object {
        fun newInstance(callTag: String? = null): UserListFragment {
            val fragment = UserListFragment()
            val args = Bundle()
            if (callTag != null)
                args.putString(Const.TAG, callTag)
            args.putString(Const.TAG, "${fragment.javaClass.name}.tag.$callTag")
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = UserListAdapter()
    private var mCallTag: String? = null
    private var mPagination: Pagination? = null

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCallTag = arguments?.getString(Const.TAG)
    }

    override fun onRefresh() {
        API.Doc.users {
            if (it == null) return@users super.onRefresh()
            mPagination = it.pagination
            mAdapter.update(it.users)
            super.onRefresh()
        }
    }

    override fun onLoadmore() {
        if (mPagination?.atLast == true)
            return super.onLoadmore()
        API.Doc.users(mPagination?.next?.qs) {
            if (it == null) return@users
            mPagination = it.pagination
            mAdapter.append(it.users)
            super.onLoadmore()
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val user = mAdapter.getItem(position)
        var handlerOver = false
        if (mCallTag != null) handlerOver = submitAndRemoveCall(mCallTag!!, user)
        if (!handlerOver) UserFragment.newInstance(user.username).showSelf(Miao.i)
        return true
    }
}
