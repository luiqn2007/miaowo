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
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.Pagination
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MiaoListFragment

class CategoryFragment : MiaoListFragment("No Title", true) {
    companion object {
        const val CALL_TAG = "callTagCategory"

        fun newInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
            args.putString(Const.TAG, fragment.javaClass.name)
            fragment.arguments = args
            return fragment
        }
    }

    private val mAdapter = TopicAdapter(true, false, true, this)
    private var mCategory: Category? = null
        set(value) {
            title = value?.name
            field = value
        }
    private var mPagination: Pagination? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sFg = SendFragment.newInstance(mCategory?.cid ?: -1, CALL_TAG)
        registerCall(object : FragmentCall(CALL_TAG, this, sFg) {
            override fun call(vararg params: Any?) {
                fragmentManager?.beginTransaction()?.apply {
                    remove(sFg)
                    show(this@CategoryFragment)
                }?.commitAllowingStateLoss()
                onRefresh()
            }
        })
        miaoListener?.apply {
            button.setOnClickListener {
                sFg.showSelf(Miao.i)
            }
        }
        mAdapter.clear()
        onRefresh()
        loading.title = mCategory?.name
        loading.description = mCategory?.description
        loading.loadingDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_loading, null)
        loading.show()
    }

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    override fun onLoadmore() {
        if (mPagination?.atLast == true)
            return super.onLoadmore()
        API.Doc.category(mCategory?.cid ?: -1, mPagination?.next?.qs) {
            if (it == null || it.topics.isEmpty()) return@category super.onLoadmore()
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
                        mAdapter.append(topicList, false)
                        super.onLoadmore()
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        API.Doc.category(mCategory?.cid ?: -1, null) {
            if (it == null || it.topics.isEmpty())
                return@category super.onRefresh()
            mPagination = it.pagination
            var added = 0
            val topics = it.topics
            val topicArray = arrayOfNulls<Topic>(topics.size)
            topics.forEachIndexed { position, item ->
                API.Doc.topic(item.tid) {
                    topicArray[position] = it
                    added++

                    lInfo("$added / ${topics.size}")
                    if (added == topics.size) {
                        val topicList = mutableListOf<Topic>()
                        for (i in 0 until topicArray.size) {
                            val topic = topicArray[i]
                            if (topic != null) topicList.add(topic)
                        }
                        mAdapter.update(topicList)
                        super.onRefresh()
                    }
                }
            }
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        val item = mAdapter.getItem(position)
        when (view.id) {
            R.id.head -> UserFragment.newInstance(item.user?.username
                    ?: item.posts.firstOrNull()?.user?.username ?: "").showSelf(Miao.i)
            R.id.like -> API.Topics.follow(item.tid) {
                activity?.runOnUiThread {
                    if (it != Const.RET_OK) activity?.handleError(it)
                    else (view as? ImageView)?.setImageDrawable(IconicsDrawable(App.i, FontAwesome.Icon.faw_heart).color(Color.RED).actionBar())
                }
            }
            else -> PostFragment.newInstance(item.tid).showSelf(Miao.i)
        }
        return true
    }

    fun loadCategory(category: Category) {
        if (category.cid != mCategory?.cid ?: -1) {
            mCategory = category
            if (isVisible) onViewCreated(springView, null)
        }
    }
}