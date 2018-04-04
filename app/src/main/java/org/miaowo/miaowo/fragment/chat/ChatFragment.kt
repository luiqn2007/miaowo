package org.miaowo.miaowo.fragment.chat

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_chat.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatMsgAdapter
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.other.ChatListAnimator
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.interfaces.IMiaoListener
import java.util.*

/**
 * 聊天内容
 * Created by luqin on 17-4-23.
 */

class ChatFragment : Fragment() {
    private val mAdapter = ChatMsgAdapter()
    private var mRoomId = -1
    private var mUser = -1
    private var mName = ""
    private var mTimer: Timer? = null
    private var mListener: IMiaoListener? = null
    private var mViewShown: Boolean? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener)
            mListener = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(inflater, R.layout.fragment_chat, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRoomId = arguments!!.getInt(Const.ID, -1)
        mUser = arguments!!.getInt(Const.USER, -1)
        mName = arguments!!.getString(Const.NAME, "")

        mListener?.setToolbar("聊天: $mName")

        btn_send.setOnClickListener {
            if (et_msg.text.toString().isBlank())
                Miao.i.handleError(R.string.err_no_message)
            else {
                API.Users.chat(mUser, et_msg.text.toString(), mRoomId) {
                    Miao.i.runOnUiThread {
                        if (it != "OK") {
                            Miao.i.handleError(it)
                        } else {
                            list.scrollToPosition(mAdapter.itemCount)
                        }
                    }
                }
            }
        }

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = mAdapter
        list.itemAnimator = ChatListAnimator()

        if (mRoomId >= 0) {
            API.Doc.chatMessage(mRoomId) {
                if (it != null) {
                    activity?.runOnUiThread {
                        mAdapter.update(it.messages)
                        list.scrollToPosition(mAdapter.itemCount)
                    }
                }
            }
        }

        mViewShown = true

        mTimer = Timer()
        mTimer?.schedule(object : TimerTask() {
            override fun run() {
                if (mRoomId >= 0) {
                    API.Doc.chatMessage(mRoomId) {
                        try {
                            if (it != null) {
                                val receiveList = it.messages
                                val adapterList = mAdapter.items
                                if (adapterList.isEmpty())
                                    mAdapter.update(receiveList)
                                if (it.messages.isEmpty()
                                        || adapterList.last().timestamp >= receiveList.last().timestamp)
                                    return@chatMessage
                                val existLastTime = adapterList.last().timestamp
                                val appendList = receiveList.filter { it.timestamp > existLastTime }
                                mAdapter.append(appendList)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            mTimer?.cancel()
                            mTimer = null
                        }
                    }
                }
            }
        }, 1000)
    }

    override fun onDestroy() {
        mTimer?.cancel()
        mTimer = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(room: ChatRoom): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putInt(Const.ID, room.roomId)
            args.putInt(Const.USER, room.lastUser?.uid ?: -1)
            args.putString(Const.NAME, room.lastUser?.username)
            args.putString(Const.TAG, "${fragment.javaClass.name}.room.${room.roomId}")
            fragment.arguments = args
            return fragment
        }
    }
}
