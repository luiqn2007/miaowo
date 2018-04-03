package org.miaowo.miaowo.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.setHTML
import org.miaowo.miaowo.other.setIcon
import org.miaowo.miaowo.other.setUserIcon
import org.miaowo.miaowo.util.FormatUtil

/**
 * 显示问题的 Adapter
 * Created by lq2007 on 2017/7/22 0022.
 */
class TopicAdapter(likeVisible: Boolean, replyVisible: Boolean, bodyControl: Boolean = false) : ListAdapter<Topic>(
        object : ListAdapter.ViewCreator<Topic> {
            override fun createHolder(parent: ViewGroup?, viewType: Int): ListHolder {
                return ListHolder(R.layout.list_post, parent)
            }

            override fun bindView(item: Topic?, holder: ListHolder?, type: Int) {
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

                    if (item == null || item.posts.isEmpty()) return

                    title.setHTML(item.titleRaw, false)
                    likeCount.text = item.postcount.toString()
                    like.setIcon(FontAwesome.Icon.faw_heart)
                    reply.setIcon(FontAwesome.Icon.faw_reply)

                    if (bodyControl) {
                        head.setUserIcon(item.posts[0].user)
                        username.text = item.posts[0].user?.username ?: ""

                        val bodyHide = spGet(Const.SP_HIDE_BODY, false)
                        val bodyType = spGet(Const.SP_SHOW_TYPE, Const.CBODY_LAST)

                        content.visibility = if (bodyHide) View.GONE else View.VISIBLE

                        if (item.posts.isEmpty()) return
                        val post = when (bodyType) {
                            Const.CBODY_CONTENT -> item.posts.firstOrNull()
                            Const.CBODY_FIRST -> item.posts[1]
                            Const.CBODY_LAST -> item.posts.lastOrNull()
                            else -> null
                        }
                        time.text = FormatUtil.time(post?.timestamp)
                        content.setHTML(post?.content)
                    } else {
                        if (item.teaser == null) {
                            val post = item.posts.firstOrNull { it.pid == item.teaserPid }
                            time.text = FormatUtil.time(post?.timestamp)
                            content.setHTML(post?.content)
                            head.setUserIcon(item.posts[0].user)
                            username.text = item.posts[0].user?.username ?: ""
                        } else {
                            time.text = FormatUtil.time(item.teaser.timestamp)
                            content.setHTML(item.teaser.content)
                            head.setUserIcon(item.user)
                            username.text = item.user?.username ?: ""
                        }
                    }
                }
            }
        })
