package org.miaowo.miaowo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import kotlinx.android.synthetic.main.activity_chat.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatMsgAdapter
import org.miaowo.miaowo.adapter.ChatMsgListener
import org.miaowo.miaowo.adapter.ChatRoomAdapter
import org.miaowo.miaowo.adapter.ChatRoomListener
import org.miaowo.miaowo.data.model.ChatModel
import org.miaowo.miaowo.handler.ChatHandler
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyTextWatcher

class ChatActivity : AppCompatActivity() {

    companion object {
        const val OPEN_LIST = -1
        const val OPEN_NEW_CHAT = 0
        const val OPEN_CHAT_ROOM = 1
    }

    private val mRoomAdapter = ChatRoomAdapter()
    private val mChatAdapter = ChatMsgAdapter()
    private val mHandler = ChatHandler(this)
    private var mChatRoom: Int? = null
    private var mChatWith: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mHandler.chatModel = ViewModelProviders.of(this)[ChatModel::class.java]
        mHandler.chatModel?.apply {
            room.observe(this@ChatActivity, Observer { room ->
                if (room != null) {
                    mChatRoom = room.roomId
                    mChatWith = room.users.firstOrNull { it.uid != API.user.uid }?.uid

                    if (mChatRoom == room.roomId) {
                        val lastMsg = mChatAdapter.items.last()
                        val index = room.messages.indexOf(lastMsg)
                        if (index >= 0) {
                            mChatAdapter.append(room.messages.subList(index + 1, room.messages.size))
                        } else {
                            mChatAdapter.update(room.messages)
                        }
                    } else {
                        mChatAdapter.clear()
                        mChatAdapter.update(room.messages)
                    }
                } else {
                    mChatAdapter.clear()
                }
                messages.smoothScrollToPosition(mChatAdapter.itemCount - 1)
            })

            rooms.observe(this@ChatActivity, Observer {
                mRoomAdapter.clear()
                mRoomAdapter.update(it?.rooms)
            })
        }

        rooms.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mRoomAdapter
            addOnItemTouchListener(ChatRoomListener(applicationContext, mRoomAdapter, mHandler))
        }

        messages.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = mChatAdapter
            addOnItemTouchListener(ChatMsgListener(applicationContext, mChatAdapter))
        }

        send.setOnClickListener { mHandler.sendMessage(input.text.toString()) }

        input.addTextChangedListener(object : EmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                send.isEnabled = !s.isNullOrBlank()
            }
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        when (intent.getIntExtra(Const.TYPE, OPEN_LIST)) {
            OPEN_CHAT_ROOM -> mHandler.chatModel?.loadRoom(intent.getIntExtra(Const.ID, -1))
            OPEN_NEW_CHAT -> mChatWith = intent.getIntExtra(Const.ID, -1)
        }
    }
}
