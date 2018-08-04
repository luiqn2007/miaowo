package org.miaowo.miaowo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import kotlinx.android.synthetic.main.activity_chat.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatMsgAdapter
import org.miaowo.miaowo.adapter.ChatRoomAdapter
import org.miaowo.miaowo.data.model.ChatModel
import org.miaowo.miaowo.other.ActivityCallback
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyTextWatcher
import retrofit2.Call
import retrofit2.Response

class ChatActivity : AppCompatActivity() {

    companion object {
        const val OPEN_LIST = -1
        const val OPEN_NEW_CHAT = 0
        const val OPEN_CHAT_ROOM = 1
    }

    private val mRoomAdapter = ChatRoomAdapter()
    private val mChatAdapter = ChatMsgAdapter()
    private lateinit var mRoomModel: ChatModel
    private var mChatRoom: Int? = null
    private var mChatWith: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mRoomModel = ViewModelProviders.of(this)[ChatModel::class.java]

        rooms.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mRoomAdapter
        }

        messages.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = mChatAdapter
        }

        mRoomModel.apply {
            room.observe(this@ChatActivity, Observer {
                if (it != null) {
                    mChatRoom = it.roomId
                    mChatWith = it.users.firstOrNull { it.uid != API.user.uid }?.uid

                    if (mChatRoom == it.roomId) {
                        val lastMsg = mChatAdapter.items.last()
                        val index = it.messages.indexOf(lastMsg)
                        if (index >= 0) {
                            mChatAdapter.append(it.messages.subList(index + 1, it.messages.size))
                        } else {
                            mChatAdapter.update(it.messages)
                        }
                    } else {
                        mChatAdapter.clear()
                        mChatAdapter.update(it.messages)
                    }
                }
                messages.smoothScrollToPosition(mChatAdapter.itemCount - 1)
            })

            rooms.observe(this@ChatActivity, Observer {
                mRoomAdapter.clear()
                mRoomAdapter.update(it?.rooms)
            })
        }

        initView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        mRoomAdapter.notifyDataSetChanged()
        mChatAdapter.notifyDataSetChanged()

        when (intent.getIntExtra(Const.TYPE, OPEN_LIST)) {
            OPEN_CHAT_ROOM -> {
                mRoomModel.loadRoom(intent.getIntExtra(Const.ID, -1))
            }
            OPEN_NEW_CHAT -> {
                mChatWith = intent.getIntExtra(Const.ID, -1)
            }
        }
    }

    private fun initView() {
        send.setOnClickListener {
            val message = input.text.toString()
            val roomId = mChatRoom
            val userId = mChatWith

            if (roomId != null && userId != null) {
                // chat room is existed
                API.Users.chat(userId, message, roomId).enqueue(object : ActivityCallback<ResponseBody>(this) {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        mRoomModel.loadRoom(roomId)
                    }
                })
            }

            if (roomId == null && userId != null) {
                // chat room is not existed
                API.Users.chat(userId, message).enqueue(object : ActivityCallback<ResponseBody>(this) {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val obj = JSONObject(response?.body()?.string())
                        mRoomModel.loadRooms()
                        mRoomModel.loadRoom(obj.getJSONObject("payload").getInt("roomId"))
                    }
                })
            }
        }

        input.addTextChangedListener(object : EmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                send.isEnabled = !s.isNullOrBlank()
            }
        })
    }
}
