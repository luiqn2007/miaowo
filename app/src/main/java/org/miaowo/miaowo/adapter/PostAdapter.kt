package org.miaowo.miaowo.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.SendActivity
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.data.bean.Post
import org.miaowo.miaowo.other.*
import org.miaowo.miaowo.util.FormatUtil

/**
 * 显示问题的 Adapter
 * Created by lq2007 on 2017/7/22 0022.
 */
class PostAdapter(var likeVisible: Boolean) : ListAdapter<Post>(
        object : ListAdapter.ViewCreator<Post> {
            override fun createHolder(parent: ViewGroup, viewType: Int): ListHolder {
                return ListHolder(R.layout.list_post, parent)
            }

            override fun bindView(item: Post, holder: ListHolder, type: Int) {
                // DataBinding
                val head = holder[R.id.head] as ImageView
                val username = holder[R.id.username] as TextView
                val time = holder[R.id.tv_time] as TextView
                val title = holder[R.id.title] as TextView
                val content = holder[R.id.content] as TextView
                val like = holder[R.id.like] as ImageView
                val likeCount = holder[R.id.like_count] as TextView

                likeCount.visibility = if (likeVisible) View.VISIBLE else View.GONE
                like.visibility = if (likeVisible) View.VISIBLE else View.GONE

                head.setUserIcon(item.user)
                username.text = item.user?.username ?: ""
                time.text = FormatUtil.time(item.timestamp)
                title.setHTML(item.topic?.titleRaw)
                content.setHTML(item.content)
                likeCount.text = item.votes.toString()
                like.setIcon(FontAwesome.Icon.faw_heart)
            }
        })

class PostListener(context: Context, val adapter: PostAdapter): BaseListTouchListener(context) {

    override fun onClick(view: View?, position: Int): Boolean {
        return when (view?.id) {
            R.id.content -> {
                (adapter.getItem(position) as? Post)?.let {
                    if (position == 0)
                        context.startActivity(Intent(context, SendActivity::class.java).apply {
                            putExtra(Const.TYPE, SendActivity.TYPE_REPLY)
                            putExtra(Const.ID, it.tid)
                            putExtra(Const.REPLY, it.user?.username ?: "Unknown User")
                        })
                    else
                        context.startActivity(Intent(context, SendActivity::class.java).apply {
                            putExtra(Const.TYPE, SendActivity.TYPE_REPLY)
                            putExtra(Const.ID, it.tid)
                            putExtra(Const.ID2, it.pid)
                            putExtra(Const.REPLY, it.user?.username ?: "Unknown User")
                        })
                }
                true
            }
            else -> false
        }
    }
}