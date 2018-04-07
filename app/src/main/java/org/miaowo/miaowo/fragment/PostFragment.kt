package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.base.extra.FragmentCall
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.base.extra.registerCall
import org.miaowo.miaowo.base.extra.showSelf
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MiaoListFragment

/**
 * 主题详情
 */
class PostFragment : MiaoListFragment(R.string.question_detail) {
    companion object {
        const val CALL_TAG = "sendCall"

        fun newInstance(tid: Int): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            args.putString(Const.TAG, "${fragment.javaClass.name}.id.$tid")
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = PostAdapter(true, true, this)

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

    override fun onClickListener(view: View, position: Int): Boolean {
        if (view.id == R.id.reply) (mAdapter.getItem(position) as? Post)?.also {
            val sFg =
                    if (position == 0) SendFragment.newInstance(it.tid, it.user?.username
                            ?: "Unknown User", CALL_TAG)
                    else SendFragment.newInstance(it.tid, it.pid, it.user?.username
                            ?: "Unknown User", CALL_TAG)
            registerCall(object : FragmentCall(CALL_TAG, this@PostFragment, sFg) {
                override fun call(vararg params: Any?) {
                    lInfo("send_call: $params")
                    fragmentManager?.beginTransaction()?.apply {
                        remove(sFg)
                        show(this@PostFragment)
                    }?.commitAllowingStateLoss()
                    onRefresh()
                }
            })
            sFg.showSelf(Miao.i)
            return true
        }

        return super.onClickListener(view, position)
    }
}
