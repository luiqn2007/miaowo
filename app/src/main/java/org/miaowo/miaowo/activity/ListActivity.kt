package org.miaowo.miaowo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.*
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.data.bean.Category
import org.miaowo.miaowo.data.bean.Notifications
import org.miaowo.miaowo.handler.ListHandler
import org.miaowo.miaowo.other.ActivityHttpCallback
import org.miaowo.miaowo.other.Const
import retrofit2.Call
import retrofit2.Response
import kotlin.math.min

class ListActivity: AppCompatActivity() {

    companion object {
        const val TYPE_FEEDBACK = 0
        const val TYPE_NOTIFICATION = 1
        const val TYPE_INBOX = 2
        const val TYPE_STATUS = 3
        const val TYPE_BLOG = 4
    }

    private val mHandler = ListHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        when (intent.getIntExtra(Const.TYPE, 0)) {
            TYPE_FEEDBACK -> loadFeedback()
            TYPE_INBOX -> loadInbox()
            TYPE_NOTIFICATION -> loadNotification()
            TYPE_BLOG -> loadBlog()
            TYPE_STATUS -> loadStatus()
        }
    }

    private fun loadFeedback() {
        mHandler.setTitle(R.string.feedback)
        val arrTitle = App.i.resources.getStringArray(R.array.feedback_title)
        val arrValue = App.i.resources.getStringArray(R.array.feedback_value)
        val listArr = mutableListOf<Array<String>>()
        for (i in 0 until min(arrTitle.size, arrValue.size)) {
            listArr.add(arrayOf(arrTitle[i], arrValue[i]))
        }
        mHandler.setList(LinearLayoutManager(applicationContext), FeedbackAdapter(applicationContext), listArr, FeedbackListener(applicationContext))
    }

    private fun loadNotification() {
        mHandler.setTitle(R.string.notification)
        val adapter = NotificationAdapter(applicationContext)
        mHandler.setList(LinearLayoutManager(applicationContext), adapter, null, NotificationListener(applicationContext, adapter))
        API.Docs.notification().enqueue(object : ActivityHttpCallback<Notifications>(this) {
            override fun onSucceed(call: Call<Notifications>?, response: Response<Notifications>) {
                adapter.update(response.body()?.notifications)
            }
        })
    }

    private fun loadInbox() {
        mHandler.setTitle(R.string.inbox)
        val adapter = InboxAdapter(application)
        mHandler.setList(LinearLayoutManager(applicationContext), adapter, null, InboxListener(applicationContext, adapter))
        API.Docs.unread().enqueue(object : ActivityHttpCallback<Category>(this) {
            override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                adapter.update(response.body()?.topics)
            }
        })
    }

    private fun loadBlog() {
        mHandler.setTitle(R.string.blog)
        // TODO: Blog
        toast("暂未完成", TastyToast.ERROR)
        finish()
    }

    private fun loadStatus() {
        mHandler.setTitle(R.string.status)
        // TODO: 状态
        toast("暂未完成", TastyToast.ERROR)
        finish()
    }
}