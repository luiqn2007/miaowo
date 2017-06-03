package org.miaowo.miaowo.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.Session
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLViewCreator
import org.miaowo.miaowo.util.FormatUtil

class UserInfoAdapter : LMLPageAdapter<Session>(object : LMLViewCreator<Session> {
    override fun createHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
            = BaseViewHolder(R.layout.list_info, parent)

    override fun bindView(item: Session?, holder: RecyclerView.ViewHolder?, type: Int) {
        if (item != null && holder is BaseViewHolder) {
            holder.run {
                setText(R.id.tv_msg, "${item.browser} ${item.version} on ${item.platform}")
                setText(R.id.tv_time, FormatUtil.time(item.datetime ?: 0))
                setText(R.id.tv_ip, "IP地址: ${item.ip}")
            }
        }
    }

    override fun setType(item: Session?, position: Int) = 0

})
