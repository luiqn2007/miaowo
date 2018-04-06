package org.miaowo.miaowo.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.other.setUserIcon

/**
 * 所有用户列表
 * Created by lq200 on 2018/3/29.
 */
class UserListAdapter : ListAdapter<User>(object : ViewCreator<User> {
    override fun createHolder(parent: ViewGroup, viewType: Int) = ListHolder(R.layout.list_user, parent)

    override fun bindView(item: User, holder: ListHolder, type: Int) {
        holder.find<ImageView>(R.id.iv_user)?.setUserIcon(item)
        holder.find<TextView>(R.id.username)?.text = item.username
    }

})