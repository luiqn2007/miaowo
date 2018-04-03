package org.miaowo.miaowo.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.TopicAdapter
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.loadSelf
import org.miaowo.miaowo.bean.data.Pagination
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

class UnreadFragment : BaseListFragment() {
    companion object {
        fun newInstance(): UnreadFragment {
            val fragment = UnreadFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
            args.putString(Const.TAG, "${fragment.javaClass.name}.user.${API.user.uid}")
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = TopicAdapter(true, false)
    private var mPagination: Pagination? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (attach as? IMiaoListener)?.run {
            decorationVisible = false
            setToolbar(App.i.getString(R.string.unread))
        }
        onRefresh()
    }

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onLoadmore() {
        if (mPagination?.atLast == true) return
        API.Doc.unread(mPagination?.next?.qs) {
            if (it == null) return@unread
            mPagination = it.pagination
            activity?.runOnUiThread {
                mAdapter.append(it.topics, false)
                springView?.onFinishFreshAndLoad()
            }
        }
    }

    override fun onRefresh() {
        API.Doc.unread {
            if (it == null) return@unread
            mPagination = it.pagination
            activity?.runOnUiThread {
                mAdapter.update(it.topics)
                springView?.onFinishFreshAndLoad()
            }
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val item = mAdapter.getItem(position)
        when (view.id) {
            R.id.head -> UserFragment.newInstance(item.user?.username ?: "")
            R.id.like -> API.Topics.follow(item.tid) {
                activity?.runOnUiThread {
                    if (it != Const.RET_OK) activity?.handleError(it)
                    else (view as? ImageView)?.setImageDrawable(IconicsDrawable(App.i, FontAwesome.Icon.faw_heart).color(Color.RED).actionBar())
                }
            }
        }
        PostFragment.newInstance(item.tid).loadSelf(Miao.i)
        return true
    }
}