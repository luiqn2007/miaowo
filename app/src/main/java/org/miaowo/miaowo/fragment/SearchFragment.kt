package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.fragment_search.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Detail
import org.miaowo.miaowo.activity.MiaoUser
import org.miaowo.miaowo.base.BaseRecyclerAdapter
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil

class SearchFragment : Fragment() {
    private var isUser = false

    private var mQuestions = listOf<Post>()
    private var mUsers = listOf<User>()
    private val mTitleAdapter = object : BaseRecyclerAdapter<Post>(R.layout.list_answer) {
        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            val question = getItem(position)
            ImageUtil.setUser(holder.getView(R.id.iv_user) as ImageView, question.user!!, true)
            holder.setText(R.id.tv_user, question.user?.username)
            holder.setText(R.id.tv_time, FormatUtil.time(question.timestamp))
            FormatUtil.parseHtml(question.content) { spanned -> holder.setText(R.id.tv_context, spanned) }
            (holder.getView(R.id.tv_context) as TextView).movementMethod = LinkMovementMethod.getInstance()
            holder.setClickListener { Detail.showTitle(question.title?.slug) }
        }
    }
    private val mUserAdapter = object : BaseRecyclerAdapter<User>(R.layout.list_user_h) {
        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            val u = getItem(position)
            holder.setText(R.id.tv_user, u.username)
            ImageUtil.setUser(holder.getView(R.id.iv_user) as ImageView, u, false)
            holder.setClickListener { MiaoUser.showUser(u.username) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_search, inflater, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ib_search.setOnClickListener { search(et_topic.text.toString()) }
        loadSearch()
    }

    private fun loadSearch() {
        if (isUser) loadSearchUser()
        else loadSearchTopic()
        ib_search.setOnClickListener { search(et_topic.text.toString()) }
    }

    private fun loadSearchTopic() {
        ib_search.setImageDrawable(IconicsDrawable(context, FontAwesome.Icon.faw_calculator).actionBar())
        list.adapter = mTitleAdapter
        mTitleAdapter.update(mQuestions)
    }

    private fun loadSearchUser() {
        ib_search.setImageDrawable(IconicsDrawable(context, FontAwesome.Icon.faw_user).actionBar())
        list.adapter = mUserAdapter
        mUserAdapter.update(mUsers)
    }

    fun search(key: String) {
        ib_search.isEnabled = false
        if (isUser) {
            API.Doc.searchUser(key) {
                ib_search.isEnabled = true
                mUsers = it?.users ?: mutableListOf()
            }
        } else {
            API.Doc.searchTopic(key) {
                ib_search.isEnabled = true
                mQuestions = it?.posts ?: mutableListOf()
            }
        }
        loadSearch()

        ib_search.setImageDrawable(IconicsDrawable(context, FontAwesome.Icon.faw_ban).actionBar())
    }

    companion object {

        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
