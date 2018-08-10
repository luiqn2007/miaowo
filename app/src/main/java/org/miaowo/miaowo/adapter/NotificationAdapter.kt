package org.miaowo.miaowo.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.PostActivity
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.data.bean.Notification
import org.miaowo.miaowo.other.BaseListTouchListener
import org.miaowo.miaowo.other.CircleTransformation
import org.miaowo.miaowo.other.Const
import java.util.*

class NotificationAdapter(val context: Context): ListAdapter<Notification>(object : ViewCreator<Notification> {
    override fun createHolder(parent: ViewGroup, viewType: Int): ListHolder {
        return ListHolder(R.layout.list_topic, parent, context)
    }

    override fun bindView(item: Notification, holder: ListHolder, type: Int) {
        holder[R.id.select]?.background = ColorDrawable(ResourcesCompat.getColor(context.resources, Const.COLORS[Random().nextInt(Const.COLORS.size)], null))
        (holder[R.id.content] as TextView).text = item.topicTitle
        var rImgRes = item.image
        if (rImgRes != null) {
            if (!(rImgRes.startsWith("http") || rImgRes.startsWith("https")))
                rImgRes = "${Const.URL_BASE}$rImgRes"
            Picasso.with(context)
                    .load(rImgRes)
                    .error(R.drawable.ic_error)
                    .transform(CircleTransformation()).fit()
                    .into(holder[R.id.icon] as ImageView)
        } else {
            (holder[R.id.icon] as ImageView).setImageResource(R.drawable.ic_notifications)
        }
    }
})

class NotificationListener(context: Context, val adapter: NotificationAdapter): BaseListTouchListener(context) {
    override fun onClick(view: View?, position: Int): Boolean {
        context.startActivity(Intent(context, PostActivity::class.java).putExtra(Const.ID, adapter.getItem(position).tid))
        return true
    }
}