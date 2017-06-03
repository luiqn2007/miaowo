package org.miaowo.miaowo.fragment.user_detail

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import org.miaowo.miaowo.adapter.UserAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.util.API

class FollowerFragment : BaseListFragment<User>() {

    override fun initView(view: View?) {
        super.initView(view)
        mList?.layoutManager = object : GridLayoutManager(context, 3) {
            override fun canScrollVertically() = false
        }
    }

    override fun setAdapter() = UserAdapter()

    override fun setItems(adapter: LMLAdapter<User>, set: (list: List<User>) -> Unit) {
        API.Doc.follower(arguments[Const.NAME].toString()) { set(it?.users ?: listOf()) }
    }

    companion object {
        fun newInstance(name: String): FollowerFragment {
            val fragment = FollowerFragment()
            val args = Bundle()
            args.putString(Const.NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
}
