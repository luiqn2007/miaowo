package org.miaowo.miaowo.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MiaoListFragment

/**
 * 通知页
 */
class NotificationFragment : MiaoListFragment(R.string.notification) {
    private val mAdapter = ListAdapter(object : ListAdapter.ViewCreator<Any> {
        override fun createHolder(parent: ViewGroup, viewType: Int) = ListHolder(R.layout.list_notification, parent)

        override fun bindView(item: Any, holder: ListHolder, type: Int) {
            holder.find<TextView>(R.id.notification)?.run {

            }
        }
    })


    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    companion object {
        fun newInstance(): NotificationFragment {
            val fragment = NotificationFragment()
            val args = Bundle()
            args.putString(Const.TAG, "${fragment.javaClass.name}.user.${API.user.uid}")
            fragment.arguments = args
            return fragment
        }
    }
}
