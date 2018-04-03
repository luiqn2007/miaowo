package org.miaowo.miaowo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.other.setHTML
import org.miaowo.miaowo.other.setIcon
import org.miaowo.miaowo.other.setUserIcon
import org.miaowo.miaowo.util.FormatUtil

/**
 * 显示问题的 Adapter
 * Created by lq2007 on 2017/7/22 0022.
 */
class PostAdapter(likeVisible: Boolean, replyVisible: Boolean) : ListAdapter<Post>(
        object : ListAdapter.ViewCreator<Post> {
            override fun createHolder(parent: ViewGroup?, viewType: Int): ListHolder {
                return ListHolder(R.layout.list_post, parent)
            }

            override fun bindView(item: Post?, holder: ListHolder?, type: Int) {
                if (holder != null) {
                    // DataBinding
                    val head = holder[R.id.head] as ImageView
                    val username = holder[R.id.username] as TextView
                    val time = holder[R.id.tv_time] as TextView
                    val title = holder[R.id.title] as TextView
                    val content = holder[R.id.content] as TextView
                    val like = holder[R.id.like] as ImageView
                    val likeCount = holder[R.id.like_count] as TextView
                    val reply = holder[R.id.reply] as ImageView

                    likeCount.visibility = if (likeVisible) View.VISIBLE else View.GONE
                    like.visibility = if (likeVisible) View.VISIBLE else View.GONE
                    reply.visibility = if (replyVisible) View.VISIBLE else View.GONE

                    if (item == null) return

                    head.setUserIcon(item.user)
                    username.text = item.user?.username ?: ""
                    time.text = FormatUtil.time(item.timestamp)
                    title.setHTML(item.topic?.titleRaw, false)
                    content.setHTML(item.content)
                    likeCount.text = item.votes.toString()
                    like.setIcon(FontAwesome.Icon.faw_heart)
                    reply.setIcon(FontAwesome.Icon.faw_reply)
                }
            }
        })
