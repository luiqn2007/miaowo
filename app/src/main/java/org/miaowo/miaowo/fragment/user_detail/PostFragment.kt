package org.miaowo.miaowo.fragment.user_detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import org.miaowo.miaowo.adapter.UserPostAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.util.API

class PostFragment : BaseListFragment<Post>() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mList?.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
    }

    override fun setAdapter() = UserPostAdapter()

    override fun setItems(adapter: LMLAdapter<Post>, set: (list: List<Post>) -> Unit) {
        API.Doc.post(arguments[Const.NAME].toString()) { set(it?.posts ?: listOf()) }
    }

    companion object {
        fun newInstance(name: String): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putString(Const.NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
}
