package org.miaowo.miaowo.activity

import android.content.Intent
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_title.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.base.BaseRecyclerAdapter
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.FloatView
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil

class Detail : BaseActivity(R.layout.activity_title) {
    companion object {
        fun showTitle(slug: String?) =
                App.i.startActivity(Intent(App.i, Detail::class.java).putExtra(Const.TITLE, slug ?: ""))

        fun showUser(username: String) {
            val window = FloatView("", R.layout.window_user)
            val v = window.view

            ImageUtil.fill(v.findViewById(R.id.iv_user) as ImageView, Const.DEF, null)
            (v.findViewById(R.id.tv_user) as TextView).text = username
            (v.findViewById(R.id.tv_email) as TextView).text = App.i.getString(R.string.data_loading)
            (v.findViewById(R.id.tv_regist_time) as TextView).text = App.i.getString(R.string.data_loading)
            FormatUtil.run {
                fillCount(v, R.id.tv_ask, 0)
                fillCount(v, R.id.tv_scan, 0)
                fillCount(v, R.id.tv_like, 0)
                fillCount(v, R.id.tv_focus, 0)
            }

            API.Doc.user(username) {
                val user = it
                if (user != null) {
                    window.title = user.username
                    ImageUtil.setUser(v.findViewById(R.id.iv_user) as ImageView, user, false)
                    (v.findViewById(R.id.tv_user) as TextView).text = username
                    (v.findViewById(R.id.tv_email) as TextView).text = user.email
                    (v.findViewById(R.id.tv_regist_time) as TextView).text = FormatUtil.time(user.joindate)
                    FormatUtil.fillCount(v, R.id.tv_ask, user.postcount)
                    FormatUtil.fillCount(v, R.id.tv_scan, user.profileviews)
                    FormatUtil.fillCount(v, R.id.tv_like, user.followerCount)
                    FormatUtil.fillCount(v, R.id.tv_focus, user.followingCount)
                    val focus = v.findViewById(R.id.btn_focus) as Button
                    focus.text = if (user.isIsFollowing) App.i.getText(R.string.focus_cancel) else App.i.getText(R.string.focus)
                    focus.setOnClickListener {
                        API.Use.focus(user.uid) {
                            when (it) {
                                "ok" -> {
                                    BaseActivity.get?.toast(R.string.focus_success, TastyToast.SUCCESS)
                                    user.followerCount = user.followerCount + 1
                                    FormatUtil.fillCount(v, R.id.tv_like, user.followerCount)
                                    focus.text = App.i.getText(R.string.focus_cancel)
                                }
                                "already-following" -> {
                                    API.Use.unfocus(user.uid) {
                                        BaseActivity.get?.toast(R.string.focus_canceled, TastyToast.SUCCESS)
                                        user.followerCount = user.followerCount - 1
                                        FormatUtil.fillCount(v, R.id.tv_like, user.followerCount)
                                        focus.text = App.i.getText(R.string.focus)
                                    }
                                }
                                "you-cant-follow-yourself" -> {
                                    BaseActivity.get?.handleError(R.string.err_focus_self)
                                }
                                else -> {
                                    BaseActivity.get?.handleError(R.string.err_no_err)
                                }
                            }
                        }
                    }
                } else {
                    (v.findViewById(R.id.tv_regist_time) as TextView).text = App.i.getString(R.string.err_load)
                    (v.findViewById(R.id.tv_email) as TextView).text = App.i.getString(R.string.err_load)
                    (v.findViewById(R.id.tv_regist_time) as TextView).text = App.i.getString(R.string.err_load)
                    v.findViewById(R.id.btn_focus).setOnClickListener(null)
                }
            }

            window.defaultBar().show()
        }
    }

    private val mAdapter = object : BaseRecyclerAdapter<Post>(R.layout.list_question) {
        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            if (position != 0)
                (holder.getView(R.id.rl_item) as CardView).setCardBackgroundColor(ResourcesCompat.getColor(this@Detail.resources, R.color.md_lime_A400, null))
            val item = getItem(position)
            val u = item.user
            if (u != null) {
                ImageUtil.setUser(holder.getView(R.id.iv_user) as ImageView, u, true)
                holder.setText(R.id.tv_user, u.username)
                holder.setText(R.id.tv_time, FormatUtil.time(item.timestamp))
                FormatUtil.parseHtml(item.content ?: "") {
                    spanned ->
                    holder.setText(R.id.tv_page, spanned)
                }
                holder.setClickListener(R.id.tv_reply) {
                    val intent = Intent(this@Detail, Add::class.java)
                    intent.putExtra(Const.TAG, -1)
                    intent.putExtra(Const.ID, item.tid)
                    intent.putExtra(Const.NAME, item.user?.username ?: "")
                    startActivityForResult(intent, 0)
                }
            }
        }
    }

    override fun initActivity() {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = mAdapter
        load()
    }

    private fun load() {
        val mSlug = intent.getStringExtra(Const.TITLE)
        API.Doc.question(mSlug) {
            mAdapter.update(it?.posts ?: listOf())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val content = "${data.getStringExtra(Const.TITLE)}\n${data.getStringExtra(Const.CONTENT)}"
            val tid = data.getIntExtra(Const.TAG, -1)
            API.Use.sendTopic(tid, content) { load() }
        }
    }
}
