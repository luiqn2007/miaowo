package org.miaowo.miaowo.fragment.chat

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
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
    private var mCheckThread: Thread? = null
    private var mListener: IMiaoListener? = null

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
                        if (it != "OK") Miao.i.handleError(it)
                        else list.scrollToPosition(mAdapter.itemCount)
                    }
                }
            }
        }

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = mAdapter
        list.itemAnimator = ChatListAnimator()

        if (mRoomId >= 0) {
            checkMsgUpdate()

            mCheckThread = Thread {
                var isFinish = true
                try {
                    while (true) {
                        while (!isFinish) {
                        }
                        isFinish = false
                        Thread.sleep(5000)
                        checkMsgUpdate {
                            isFinish = true
                        }
                    }
                } catch (e: InterruptedException) {
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            mCheckThread?.start()
        } else fragmentManager?.popBackStackImmediate()
    }

    override fun onDestroy() {
        mCheckThread?.interrupt()
        mCheckThread = null
        super.onDestroy()
    }

    private fun checkMsgUpdate(run: (() -> Unit)? = null) {
        API.Doc.chatMessage(mRoomId) {
            if (isVisible) {
                val receiveList = it?.messages ?: emptyList()
                // 未成功获取数据
                if (receiveList.isEmpty()) return@chatMessage
                // 成功获取数据
                val existLastTime = mAdapter.items.lastOrNull()?.timestamp ?: -1
                val appendList = receiveList.filter { it.timestamp > existLastTime }
                if (appendList.isNotEmpty() && isVisible) mAdapter.append(appendList)
            }
            run?.invoke()
        }
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
