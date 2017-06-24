package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_topic.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Detail
import org.miaowo.miaowo.base.*
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.bean.data.Title
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil

class TopicFragment : Fragment() {
    private var mMenu: PopupMenu? = null
    /* ================================================================ */
    private var mTopics: List<Topic>? = null
    private var mAdapter = object : BaseRecyclerAdapter<Title>(R.layout.list_question_title) {
        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            val item = getItem(position)
            val u = item.user
            ImageUtil.setUser(holder.getView(R.id.iv_user) as ImageView, u, true)
            holder.setText(R.id.tv_user, u?.username)
            holder.setText(R.id.tv_time, FormatUtil.time(item.lastposttime))
            FormatUtil.parseHtml(item.title) { spanned -> holder.setText(R.id.tv_page, spanned) }
            holder.setClickListener { Detail.showTitle(item.slug) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_topic, inflater, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        loadTags()
        mMenu = PopupMenu(context, ib_search)
        ib_search.setOnClickListener { mMenu?.show() }
        list.adapter = mAdapter
        list.layoutManager = LinearLayoutManager(context)
    }

    private fun loadTags(): Boolean {
        val menu = mMenu?.menu
        tv_topic.setText(R.string.data_loading)
        menu?.clear()
        menu?.add(R.string.data_loading)
        mMenu?.setOnMenuItemClickListener { false }

        API.Doc.topic({ _, _ ->
            tv_topic.setText(R.string.err_load)
            mMenu?.menu?.clear()
            mMenu?.menu?.add(R.string.reload)
            mMenu?.setOnMenuItemClickListener { loadTags() }
        }) {
            mTopics = it?.tags ?: mutableListOf()
            tv_topic.setText(R.string.topic_choose)
            val subMenu = mMenu?.menu
            subMenu?.clear()
            mTopics?.forEach { subMenu?.add(it.value) }
            subMenu?.addSubMenu(R.string.other)?.add(R.string.reload)
            mMenu?.setOnMenuItemClickListener { openTag(it) }
        }
        return true
    }

    private fun openTag(item: MenuItem): Boolean {
        if (item.subMenu != null) return loadTags()
        val title = item.title.toString()
        tv_topic.text = getString(R.string.data_loading_detail, title)
        API.Doc.topic(title, onErr = { _, e ->
            tv_topic.setText(R.string.err_load)
            activity?.handleError(e)
        }) {
            tv_topic.text = title
            mAdapter.update(it?.topics ?: mutableListOf())
        }
        return true
    }

    companion object {
        fun newInstance(): TopicFragment {
            val fragment = TopicFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
