package org.miaowo.miaowo.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLViewCreator
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil

class UserPostAdapter : LMLPageAdapter<Post>(object : LMLViewCreator<Post> {
    override fun createHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
            = BaseViewHolder(R.layout.list_user_post, parent)

    override fun bindView(item: Post?, holder: RecyclerView.ViewHolder?, type: Int) {
        if (holder is BaseViewHolder && item != null) {
            holder.setText(R.id.category, item.category?.name)
            holder.setText(R.id.tv_time, FormatUtil.time(item.timestamp))
            FormatUtil.parseHtml(item.content) { holder.setText(R.id.tv_context, it) }
            ImageUtil.setUser(holder.getView(R.id.iv_user) as ImageView, item.user, true)
        }
    }

    override fun setType(item: Post?, position: Int) = 0
})
