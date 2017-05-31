package org.miaowo.miaowo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Detail
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.Title
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil
import java.util.*

/**
 * 问题列表的适配器,
 * Created by luqin on 17-3-15.
 */

class TitleListAdapter : LMLPageAdapter<Title>(ArrayList<Title>(), object : LMLPageAdapter.ViewLoaderCreator<Title> {
    override fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(
                LayoutInflater.from(App.i).inflate(R.layout.list_question_title, parent, false)
        )
    }

    override fun bindView(item: Title?, holder: RecyclerView.ViewHolder, type: Int) {
        val vh = holder as BaseViewHolder
        if (item != null) {
            val u = item.user
            ImageUtil.setUser(vh.getView(R.id.iv_user) as ImageView, u!!, true)
            vh.setText(R.id.tv_user, u.username)
            vh.setText(R.id.tv_time, FormatUtil.time(item.lastposttime))
            FormatUtil.parseHtml(item.title ?: "") { spanned -> vh.setText(R.id.tv_page, spanned) }
            vh.setClickListener { Detail.showTitle(item.slug) }
        }
    }

    override fun setType(item: Title?, position: Int) = 0
})
