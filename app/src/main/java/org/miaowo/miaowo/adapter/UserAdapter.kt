package org.miaowo.miaowo.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.MiaoUser
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLViewCreator
import org.miaowo.miaowo.util.ImageUtil

class UserAdapter : LMLPageAdapter<User>(object : LMLViewCreator<User> {
    override fun createHolder(parent: ViewGroup?, viewType: Int) =
            BaseViewHolder(R.layout.list_user, parent)

    override fun bindView(item: User?, holder: RecyclerView.ViewHolder?, type: Int) {
        if (item != null && holder is BaseViewHolder) {
            holder.itemView.setOnClickListener { MiaoUser.showUser(item.username) }
            val iv_user = holder.getView(R.id.iv_user) as ImageView
            iv_user.contentDescription = "用户：${item.username}"
            ImageUtil.setUser(iv_user, item, true)
            holder.setText(R.id.tv_user, item.username)
        }
    }

    override fun setType(item: User?, position: Int) = 0
})