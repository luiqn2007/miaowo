package org.miaowo.miaowo.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLViewCreator
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil

class UserTopicAdapter : LMLPageAdapter<Topic>(object : LMLViewCreator<Topic> {
    override fun createHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
        BaseViewHolder(R.layout.list_user_topic, parent)

    override fun bindView(item: Topic?, holder: RecyclerView.ViewHolder?, type: Int) {
        if (holder is BaseViewHolder) {
            ImageUtil.setUser(holder.getView(R.id.iv_user) as ImageView, item?.user, true)
            holder.setText(R.id.tv_context, item?.titleRaw)
            holder.setText(R.id.category, item?.category?.name)
            holder.setText(R.id.time, FormatUtil.time(item?.lastposttime ?: item?.timestamp ?: 0))
            holder.setText(R.id.tv_post, item?.user?.username)
            holder.setText(R.id.tv_postcount, item?.postcount?.toString())
            holder.setText(R.id.tv_view, item?.viewcount?.toString())
        }
    }

    override fun setType(item: Topic?, position: Int) = 0
})
