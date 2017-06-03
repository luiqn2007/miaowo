package org.miaowo.miaowo.fragment.user_detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import org.miaowo.miaowo.adapter.UserTopicAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.util.API

class TopicFragment : BaseListFragment<Topic>() {

    override fun initView(view: View?) {
        super.initView(view)
        mList?.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
    }

    override fun setAdapter() = UserTopicAdapter()

    override fun setItems(adapter: LMLAdapter<Topic>, set: (list: List<Topic>) -> Unit) {
        API.Doc.topic(arguments[Const.NAME].toString()) {
            set(it?.topics ?: mutableListOf())
        }
    }

    companion object {
        fun newInstance(name: String): TopicFragment {
            val fragment = TopicFragment()
            val args = Bundle()
            args.putString(Const.NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
}
