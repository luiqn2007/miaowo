package org.miaowo.miaowo.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_user.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.adapter.TopicAdapter
import org.miaowo.miaowo.base.extra.error
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.data.bean.*
import org.miaowo.miaowo.data.model.UserModel
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

class UserActivity: AppCompatActivity() {

    companion object {
        const val USER_FROM_LOGIN = 0
        const val USER_FROM_ID = 1
        const val USER_FROM_NAME = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolBar)

        val user = when (intent.getIntExtra(Const.TYPE, USER_FROM_LOGIN)) {
            USER_FROM_ID -> intent.getIntExtra(Const.ID, API.user.uid)
            USER_FROM_NAME -> intent.getStringExtra(Const.NAME) ?: API.user.username
            else -> null
        }

        ViewModelProviders.of(this)[UserModel::class.java][user].observe(this, Observer {
            initUserDetail(it)
        })
    }

    private fun initUserDetail(user: User?) {
        if (user != null) {
            toolBar.title = user.username

            if (user.uid == API.user.uid) {
                focus.visibility = View.GONE
                chat.visibility = View.GONE
            } else {
                if (user.isFollowing) {
                    focus.setText(R.string.focus_cancel)
                    focus.setOnClickListener {
                        unfocus(focus, user.uid)
                    }
                } else {
                    focus.setOnClickListener {
                        focus(focus, user.uid)
                    }
                }

                chat.setOnClickListener {
                    chat(user.uid)
                }
            }

            error.visibility = View.GONE
            pager.apply {
                adapter = object : PagerAdapter() {
                    val cView = mutableListOf<View>()
                    val cTitle = listOf(R.string.title, R.string.reply, R.string.user_detail)

                    override fun isViewFromObject(iView: View, `object`: Any) = iView == `object`

                    override fun getCount() = 3

                    override fun instantiateItem(container: ViewGroup, position: Int): Any {
                        if (cView.size == 0) {
                            // 0 主题
                            val uReply = RecyclerView(context)
                            val uReplyAdapter = TopicAdapter(false, false)
                            uReply.adapter = uReplyAdapter
                            uReply.layoutManager = LinearLayoutManager(context)
                            API.Docs.userTopics(user.username).enqueue(object : EmptyCallback<UserTopics>() {
                                override fun onResponse(call: Call<UserTopics>?, response: Response<UserTopics>?) {
                                    uReplyAdapter.update(response?.body()?.topics ?: emptyList())
                                }
                            })
                            // 1 回复
                            val uTopic = RecyclerView(context)
                            val uTopicAdapter = PostAdapter(false)
                            uTopic.adapter = uTopicAdapter
                            uTopic.layoutManager = LinearLayoutManager(context)
                            API.Docs.post(user.username).enqueue(object : EmptyCallback<UserPosts>() {
                                override fun onResponse(call: Call<UserPosts>?, response: Response<UserPosts>?) {
                                    uTopicAdapter.update(response?.body()?.posts ?: emptyList())
                                }
                            })
                            // 2 资料
                            val uDetail = ImageView(context)
                            uDetail.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_error, null))
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

                    override fun getPageTitle(position: Int) = getString(cTitle[position])
                }
            }

            tab.apply {
                visibility = View.VISIBLE
                isSmoothScrollingEnabled = true
                setupWithViewPager(pager, true)
                getTabAt(0)?.select()
            }
        }
    }

    private fun focus(view: Button, uid: Int) {
        API.Users.follow(uid).enqueue(object : EmptyCallback<ResponseBody>() {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val obj = JSONObject(response?.body()?.string())
                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                    var err = obj.getString("message")
                    if (err.isNullOrBlank()) err = obj.getString("code")
                    if (err.isNullOrBlank()) err = getString(R.string.err_ill)
                    error(err)
                } else {
                    view.setText(R.string.focus_cancel)
                    toast(R.string.focus_success, TastyToast.SUCCESS)
                    focus.setOnClickListener {
                        unfocus(view, uid)
                    }
                }
            }
        })
    }

    private fun unfocus(view: Button, uid: Int) {
        API.Users.unfollow(uid).enqueue(object : EmptyCallback<ResponseBody>() {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val obj = JSONObject(response?.body()?.string())
                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                    var err = obj.getString("message")
                    if (err.isNullOrBlank()) err = obj.getString("code")
                    if (err.isNullOrBlank()) err = getString(R.string.err_ill)
                    error(err)
                } else {
                    view.setText(R.string.focus)
                    toast(R.string.focus_canceled, TastyToast.SUCCESS)
                    focus.setOnClickListener {
                        focus(view, uid)
                    }
                }
            }
        })
    }

    private fun chat(uid: Int) {
        API.Docs.chatRoom().enqueue(object : EmptyCallback<ChatRooms>() {
            override fun onResponse(call: Call<ChatRooms>?, response: Response<ChatRooms>?) {
                val chatRooms = response?.body()
                // 查找会话
                val rooms = chatRooms?.rooms?.filter { it.users.any { it.uid == uid } }

                if (rooms == null || rooms.isEmpty()) {
                    // 空：直接打开新会话
                    startActivity(Intent(this@UserActivity, ChatActivity::class.java)
                            .putExtra(Const.TYPE, ChatActivity.OPEN_NEW_CHAT)
                            .putExtra(Const.ID, uid))
                } else if (rooms.size == 1) {
                    // 只有一项: 打开该聊天室
                    startActivity(Intent(this@UserActivity, ChatActivity::class.java)
                            .putExtra(Const.TYPE, ChatActivity.OPEN_CHAT_ROOM)
                            .putExtra(Const.ID, rooms[0].roomId))
                } else {
                    // 有多项: 打开聊天选择
                    val names = rooms.map { it.roomName ?: "id: ${it.roomId}" }.toTypedArray()
                    AlertDialog.Builder(App.i).setItems(names) { dialog, which ->
                        startActivity(Intent(this@UserActivity, ChatActivity::class.java)
                                .putExtra(Const.TYPE, ChatActivity.OPEN_CHAT_ROOM)
                                .putExtra(Const.ID, rooms[which].roomId))
                        dialog.dismiss()
                    }
                }
            }
        })
    }
}