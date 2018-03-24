package org.miaowo.miaowo.adapter

import android.view.View
import android.view.ViewGroup
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.lTODO
import org.miaowo.miaowo.bean.data.Post
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.databinding.ListPostBinding
import org.miaowo.miaowo.fragment.SendFragment
import org.miaowo.miaowo.fragment.UserFragment
import org.miaowo.miaowo.interfaces.IPostItem
import org.miaowo.miaowo.base.ListBindingHolder
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.base.extra.loadFragment

/**
 * 显示问题的 Adapter
 * Created by lq2007 on 2017/7/22 0022.
 */
class PostAdapter(likeVisible: Boolean, replyVisible: Boolean) : ListAdapter<IPostItem>(
        object : ListAdapter.ViewCreator<IPostItem> {
            override fun createHolder(parent: ViewGroup?, viewType: Int): ListBindingHolder<ListPostBinding> {
                return ListBindingHolder(App.i, R.layout.list_post, parent)
            }

            override fun bindView(item: IPostItem?, holder: ListHolder?, type: Int) {
                if (holder is ListBindingHolder<*>) {
                    (holder.binder as? ListPostBinding)?.run {
                        this.likeVisible = likeVisible
                        this.replyVisible = replyVisible
                        when (item) {
                            is Post -> bindPostToListPost(item, this)
                            is Topic -> bindTopicToListPost(item, this)
                        }
                    }
                }
            }

            fun bindPostToListPost(item: Post, binder: ListPostBinding) {
                binder.post = item
//                when (5) {
//                    R.id.head -> Miao.i.loadFragment(UserFragment.newInstance(item.user?.username
//                            ?: ""))
//                    R.id.like -> lTODO("喜欢")
//                    R.id.reply -> Miao.i.loadFragment(SendFragment.newInstance(item.tid, item.pid, item.user?.username
//                            ?: ""))
//                }
            }

            fun bindTopicToListPost(item: Topic, binder: ListPostBinding) {
                if (binder.post?.tid == item.tid) return
                if (item.teaser != null)
                    bindPostToListPost(Post(item), binder)
                else API.Doc.topic(item.tid) {
                    if (it?.posts?.isNotEmpty() == true)
                        bindPostToListPost(it.posts[0], binder)
                }
            }
        })
