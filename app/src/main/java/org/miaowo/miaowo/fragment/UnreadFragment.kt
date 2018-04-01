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
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

class UnreadFragment : BaseListFragment() {
    companion object {
        fun newInstance(): UnreadFragment {
            val fragment = UnreadFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = PostAdapter(true, false)
    private var mPage = 0
    private var mViewCreated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewCreated = true
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
        API.Doc.unread(mPage) {
            activity?.runOnUiThread {
                if (it != null) mAdapter.append(it.topics, false)
                mPage++
                springView?.onFinishFreshAndLoad()
            }
        }
    }

    override fun onRefresh() {
        API.Doc.unread {
            val topics = it?.topics ?: emptyList()
            mPage = 1
            activity?.runOnUiThread {
                mAdapter.update(topics)
                springView?.onFinishFreshAndLoad()
            }
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val listener = attach as? IMiaoListener
        val item = mAdapter.getItem(position) as Topic
        when (view.id) {
            R.id.head ->
                listener?.jump(IMiaoListener.JumpFragment.User, item.user?.username ?: "")
            R.id.like -> API.Topics.follow(item.tid) {
                activity?.runOnUiThread {
                    if (it != Const.RET_OK) activity?.handleError(it)
                    else (view as? ImageView)?.setImageDrawable(IconicsDrawable(App.i, FontAwesome.Icon.faw_heart).color(Color.RED).actionBar())
                }
            }
        }
        listener?.jump(IMiaoListener.JumpFragment.Topic, item.tid)
        return true
    }
}