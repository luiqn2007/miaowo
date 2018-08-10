package org.miaowo.miaowo.adapter

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.ActivityUtils
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.data.bean.UserPosts
import org.miaowo.miaowo.data.bean.UserTopics
import org.miaowo.miaowo.other.ActivityHttpCallback
import retrofit2.Call
import retrofit2.Response

class UserPagerAdapter(val context: Context, val user: User): PagerAdapter() {
    private val cView = mutableListOf<View>()
    private val cTitle = listOf(R.string.title, R.string.reply, R.string.user_detail)

    override fun isViewFromObject(iView: View, `object`: Any) = iView == `object`

    override fun getCount() = 3

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (cView.size == 0) {
            // 0 主题
            val uReply = RecyclerView(context)
            val uReplyAdapter = TopicAdapter(false, false)
            uReply.adapter = uReplyAdapter
            uReply.layoutManager = LinearLayoutManager(context)
            API.Docs.userTopics(user.username.replace(" ", "-")).enqueue(object : ActivityHttpCallback<UserTopics>(ActivityUtils.getTopActivity()) {
                override fun onSucceed(call: Call<UserTopics>?, response: Response<UserTopics>) {
                    uReplyAdapter.update(response.body()?.topics ?: emptyList())
                }
            })
            // 1 回复
            val uTopic = RecyclerView(context)
            val uTopicAdapter = PostAdapter(false)
            uTopic.adapter = uTopicAdapter
            uTopic.layoutManager = LinearLayoutManager(context)
            API.Docs.post(user.username.replace(" ", "-")).enqueue(object : ActivityHttpCallback<UserPosts>(ActivityUtils.getTopActivity()) {
                override fun onSucceed(call: Call<UserPosts>?, response: Response<UserPosts>) {
                    uTopicAdapter.update(response.body()?.posts ?: emptyList())
                }
            })
            // 2 资料
            val uDetail = ImageView(context)
            uDetail.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_error, null))
            cView.add(0, uReply)
            cView.add(1, uTopic)
            cView.add(2, uDetail)
        }
        container.addView(cView[position])
        return cView[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (cView[position] == `object`) container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int) = context.getString(cTitle[position])!!
}