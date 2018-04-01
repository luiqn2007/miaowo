package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 主题详情
 */
class PostFragment : BaseListFragment() {
    private val mAdapter = PostAdapter(true, true)

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onRefresh() {
        if (mTid > 0) {
            API.Doc.topic(mTid) {
                activity?.runOnUiThread {
                    mAdapter.update(it?.posts ?: emptyList())
                    springView.onFinishFreshAndLoad()
                }
            }
        }
    }

    companion object {
        fun newInstance(tid: Int): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            fragment.arguments = args
            return fragment
        }
    }

    private var mTid = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTid = arguments!!.getInt(Const.ID, -1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (attach as? IMiaoListener)?.setToolbar("问题详情")
        footer = null
    }
}
