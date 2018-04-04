package org.miaowo.miaowo.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
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
import org.miaowo.miaowo.base.extra.showSelf
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.Pagination
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

class CategoryFragment : BaseListFragment() {
    companion object {
        fun newInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
            args.putString(Const.TAG, fragment.javaClass.name)
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = TopicAdapter(true, false, true)
    private var mCategory: Category? = null
    private var mPagination: Pagination? = null
    private var mViewCreated = springView != null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewCreated = true

        (attach as? IMiaoListener)?.apply {
            setToolbar(mCategory?.name ?: "")
            buttonVisible = true
            button.setOnClickListener {
                SendFragment.newInstance(mCategory?.cid
                        ?: -1).showSelf(Miao.i, this@CategoryFragment)
            }
        }
        mAdapter.clear()
        onRefresh()
        loading.title = mCategory?.name
        loading.description = mCategory?.description
        loading.loadingDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_loading, null)
        loading.show()
        view.postInvalidate()
    }

    override fun onDestroyView() {
        mViewCreated = false
        super.onDestroyView()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        (attach as? IMiaoListener)?.buttonVisible = !hidden
        if (!hidden) {
            (attach as? IMiaoListener)?.setToolbar(mCategory?.name ?: "")
        }
    }

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onLoadmore() {
        if (mPagination?.atLast == true)
            return super.onLoadmore()
        API.Doc.category(mCategory?.cid ?: -1, mPagination?.next?.qs) {
            if (it == null || it.topics.isEmpty())
                return@category loadOverOnUIThread()
            mPagination = it.pagination
            var added = 0
            val topics = it.topics
            val topicArray = arrayOfNulls<Topic>(topics.size)
            topics.forEachIndexed { position, item ->
                API.Doc.topic(item.tid) {
                    topicArray[position] = it
                    added++
                    if (added == topics.size) {
                        val topicList = mutableListOf<Topic>()
                        for (i in 0 until topicArray.size) {
                            val topic = topicArray[i]
                            if (topic != null) topicList.add(topic)
                        }
                        activity?.runOnUiThread {
                            mAdapter.append(topicList, false)
                            super.onLoadmore()
                        }
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        API.Doc.category(mCategory?.cid ?: -1, null) {
            if (it == null || it.topics.isEmpty())
                return@category loadOverOnUIThread()
            mPagination = it.pagination
            var added = 0
            val topics = it.topics
            val topicArray = arrayOfNulls<Topic>(topics.size)
            topics.forEachIndexed { position, item ->
                API.Doc.topic(item.tid) {
                    topicArray[position] = it
                    added++

                    if (added == topics.size) {
                        val topicList = mutableListOf<Topic>()
                        for (i in 0 until topicArray.size) {
                            val topic = topicArray[i]
                            if (topic != null) topicList.add(topic)
                        }
                        activity?.runOnUiThread {
                            mAdapter.update(topicList)
                            super.onRefresh()
                        }
                    }
                }
            }
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val item = mAdapter.getItem(position)
        when (view.id) {
            R.id.head ->
                UserFragment.newInstance(item.user?.username ?: "").showSelf(Miao.i, this)
            R.id.like -> API.Topics.follow(item.tid) {
                activity?.runOnUiThread {
                    if (it != Const.RET_OK) activity?.handleError(it)
                    else (view as? ImageView)?.setImageDrawable(IconicsDrawable(App.i, FontAwesome.Icon.faw_heart).color(Color.RED).actionBar())
                }
            }
        }
        PostFragment.newInstance(item.tid).showSelf(Miao.i, this)
        return true
    }

    fun loadCategory(category: Category) {
        if (category.cid != mCategory?.cid ?: -1) {
            mCategory = category
            if (mViewCreated) onViewCreated(springView, null)
        }
    }
}