package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Detail
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.ImageUtil
import java.util.*

class UserFragment : BaseListFragment<User>() {

    override fun initView(view: View?) {
        super.initView(view)
        mList?.layoutManager = GridLayoutManager(context, 3)
    }

    override fun setAdapter() = LMLPageAdapter(ArrayList<User>(), object : LMLPageAdapter.ViewLoaderCreator<User> {
        override fun createHolder(parent: ViewGroup, viewType: Int) =
                BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.list_user, parent, false))

        override fun bindView(item: User?, holder: RecyclerView.ViewHolder, type: Int) {
            if (item != null) {
                holder.itemView.setOnClickListener { Detail.showUser(item.username) }
                val iv_user = holder.itemView.findViewById(R.id.iv_user) as ImageView
                iv_user.contentDescription = "用户：${item.username}"
                ImageUtil.setUser(iv_user, item, true)
                (holder.itemView.findViewById(R.id.tv_user) as TextView).text = item.username
            }
        }

        override fun setType(item: User?, position: Int) = 0
    })

    override fun setItems(page: Int, set: (list: List<User>) -> Unit) {
        API.Doc.user {
            set(it?.users ?: mutableListOf())
        }
    }

    companion object {
        fun newInstance(): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            args.putString(Const.URL, App.i.getString(R.string.url_users))
            fragment.arguments = args
            return fragment
        }
    }
}
