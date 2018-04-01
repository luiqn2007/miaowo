package org.miaowo.miaowo.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.interfaces.IPostItem
import org.miaowo.miaowo.other.ViewBindHelper

/**
 * 显示问题的 Adapter
 * Created by lq2007 on 2017/7/22 0022.
 */
class PostAdapter(likeVisible: Boolean, replyVisible: Boolean) : ListAdapter<IPostItem>(
        object : ListAdapter.ViewCreator<IPostItem> {
            override fun createHolder(parent: ViewGroup?, viewType: Int): ListHolder {
                return ListHolder(R.layout.list_post, parent)
            }

            override fun bindView(item: IPostItem?, holder: ListHolder?, type: Int) {
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

                    ViewBindHelper.setVisible(likeCount, likeVisible)
                    ViewBindHelper.setVisible(like, likeVisible)
                    ViewBindHelper.setVisible(reply, replyVisible)

                    when (item) {
                        is Post -> {
                            ViewBindHelper.setUserIcon(head, item.user)
                            ViewBindHelper.setUserName(username, item.user)
                            ViewBindHelper.setTime(time, item.timestamp)
                            ViewBindHelper.setTitle(title, item.topic?.titleRaw)
                            ViewBindHelper.setHTML(content, item.content)
                            ViewBindHelper.setBookMarks(likeCount, item.votes)
                            ViewBindHelper.setFontIcon(like, FontAwesome.Icon.faw_heart)
                            ViewBindHelper.setFontIcon(reply, FontAwesome.Icon.faw_reply)
                        }
                        is Topic -> {
                            ViewBindHelper.setUserIcon(head, item.user)
                            ViewBindHelper.setUserName(username, item.user)
                            ViewBindHelper.setTime(time, item.teaser?.timestamp)
                            ViewBindHelper.setTitle(title, item.titleRaw)
                            ViewBindHelper.setHTML(content, item.teaser?.content)
                            ViewBindHelper.setBookMarks(likeCount, item.postcount)
                            ViewBindHelper.setFontIcon(like, FontAwesome.Icon.faw_heart)
                            ViewBindHelper.setFontIcon(reply, FontAwesome.Icon.faw_reply)
                        }
                    }
                }
            }
        })
