package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import org.miaowo.miaowo.adapter.UserAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.util.API

class UserFragment : BaseListFragment<User>() {

    override fun initView(view: View?) {
        super.initView(view)
        mList?.layoutManager = GridLayoutManager(context, 3)
    }

    override fun setAdapter() = UserAdapter()

    override fun setItems(adapter: LMLAdapter<User>, set: (list: List<User>) -> Unit) {
        API.Doc.user {
            set(it?.users ?: listOf())
        }
    }

    companion object {
        fun newInstance(): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
