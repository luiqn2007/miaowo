package org.miaowo.miaowo.fragment.user_detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import org.miaowo.miaowo.adapter.UserInfoAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.Session
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.util.API

class InfoFragment : BaseListFragment<Session>() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mList?.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
    }

    override fun setAdapter() = UserInfoAdapter()

    override fun setItems(adapter: LMLAdapter<Session>, set: (list: List<Session>) -> Unit) {
        API.Doc.info(arguments[Const.NAME].toString()) { set(it?.sessions ?: listOf()) }
    }

    companion object {
        fun newInstance(name: String): InfoFragment {
            val fragment = InfoFragment()
            val args = Bundle()
            args.putString(Const.NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
}
