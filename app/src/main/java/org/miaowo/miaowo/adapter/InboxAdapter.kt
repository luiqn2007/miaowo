package org.miaowo.miaowo.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.PostActivity
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.data.bean.Topic
import org.miaowo.miaowo.other.BaseListTouchListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.setUserIcon

class InboxAdapter(val context: Context): ListAdapter<Topic>(object : ViewCreator<Topic> {
    override fun createHolder(parent: ViewGroup, viewType: Int) = ListHolder(R.layout.list_topic, parent, context)

    override fun bindView(item: Topic, holder: ListHolder, type: Int) {
        (holder[R.id.icon] as ImageView).setUserIcon(item.user)
        (holder[R.id.content] as TextView).text = item.title
    }
})

class InboxListener(context: Context, val adapter: InboxAdapter): BaseListTouchListener(context) {
    override fun onClick(view: View?, position: Int): Boolean {
        context.startActivity(Intent(context, PostActivity::class.java).putExtra(Const.ID, adapter.getItem(position).tid))
        return true
    }
}