package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 主题详情
 */
class PostFragment : BaseListFragment() {
    companion object {
        const val CALL_TAG = "send"

        fun newInstance(tid: Int): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            args.putString(Const.TAG, "${fragment.javaClass.name}.id.$tid")
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = PostAdapter(true, true)

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onRefresh() {
        if (mTid > 0) {
            API.Doc.topic(mTid) {
                activity?.runOnUiThread {
                    mAdapter.update(it?.posts ?: emptyList())
                    super.onRefresh()
                }
            }
        } else super.loadOver()
    }

    private var mTid = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTid = arguments!!.getInt(Const.ID, -1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (attach as? IMiaoListener)?.setToolbar("问题详情")
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        (mAdapter.getItem(position) as? Post)?.also {
            registerCall(object : FragmentCall("send", this@PostFragment) {
                override fun call(vararg params: Any?) {
                    lInfo("send_call: $params")
                    onRefresh()
                }
            })
            if (position == 0)
                SendFragment.newInstance(it.tid, it.user?.username
                        ?: "Unknown User", CALL_TAG).showSelf(Miao.i, this)
            else
                SendFragment.newInstance(it.tid, it.pid, it.user?.username
                        ?: "Unknown User", CALL_TAG).showSelf(Miao.i, this)
        }

        return super.onClickListener(view, position)
    }
}
