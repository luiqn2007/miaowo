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
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.adapter.TopicAdapter
import org.miaowo.miaowo.adapter.UserPagerAdapter
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.data.bean.ChatRooms
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.data.bean.UserPosts
import org.miaowo.miaowo.data.bean.UserTopics
import org.miaowo.miaowo.data.model.UserModel
import org.miaowo.miaowo.other.ActivityHttpCallback
import org.miaowo.miaowo.other.Const
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
            pager.adapter = UserPagerAdapter(this, user)

            tab.apply {
                visibility = View.VISIBLE
                isSmoothScrollingEnabled = true
                setupWithViewPager(pager, true)
                getTabAt(0)?.select()
            }
        }
    }

    private fun focus(view: Button, uid: Int) {
        API.Users.follow(uid).enqueue(object : ActivityHttpCallback<ResponseBody>(this) {
            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                view.setText(R.string.focus_cancel)
                toast(R.string.focus_success, TastyToast.SUCCESS)
                focus.setOnClickListener {
                    unfocus(view, uid)
                }
            }
        })
    }

    private fun unfocus(view: Button, uid: Int) {
        API.Users.unfollow(uid).enqueue(object : ActivityHttpCallback<ResponseBody>(this) {
            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                view.setText(R.string.focus)
                toast(R.string.focus_canceled, TastyToast.SUCCESS)
                focus.setOnClickListener {
                    focus(view, uid)
                }
            }
        })
    }

    private fun chat(uid: Int) {
        API.Docs.chatRoom().enqueue(object : ActivityHttpCallback<ChatRooms>(this) {
            override fun onSucceed(call: Call<ChatRooms>?, response: Response<ChatRooms>) {
                val chatRooms = response.body()
                // 查找会话
                @Suppress("NestedLambdaShadowedImplicitParameter")
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