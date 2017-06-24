package org.miaowo.miaowo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_title.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseRecyclerAdapter
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil

class Detail : AppCompatActivity() {
    companion object {
        fun showTitle(slug: String?) =
                App.i.startActivity(Intent(App.i, Detail::class.java).putExtra(Const.SLUG, slug ?: ""))
    }

    private val mAdapter = object : BaseRecyclerAdapter<Post>(
            arrayOf(0, 1), arrayOf(R.layout.list_question, R.layout.list_question),
            { if (it == 0) 0 else 1 }) {
        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            if (position != 0)
                (holder.getView(R.id.rl_item) as CardView)
                        .setCardBackgroundColor(ResourcesCompat
                                .getColor(this@Detail.resources, R.color.md_lime_A400, null))
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
                    intent.putExtra(Const.ID, item.tid)
                    intent.putExtra(Const.NAME, item.user?.username ?: "")
                    startActivityForResult(intent, 0)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = mAdapter
        load()
    }

    private fun load() {
        val mSlug = intent.getStringExtra(Const.SLUG)
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
