package org.miaowo.miaowo.fragment.chat

import android.content.Context
import android.databinding.DataBindingUtil
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
import org.miaowo.miaowo.databinding.FragmentChatBinding
import org.miaowo.miaowo.other.ChatListAnimator
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
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
    private var mTimer = Timer()
    private lateinit var mBinding: FragmentChatBinding
    private var mListener: IMiaoListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener)
            mListener = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRoomId = arguments!!.getInt(Const.ID, -1)
        mUser = arguments!!.getInt(Const.USER, -1)
        mName = arguments!!.getString(Const.NAME, "")

        mListener?.setToolbar("聊天: $mName")

        mBinding.btnSend.setOnClickListener {
            if (mBinding.etMsg.text.toString().isBlank())
                Miao.i.handleError(R.string.err_no_message)
            else {
                API.Users.chat(mUser, mBinding.etMsg.text.toString(), mRoomId) {
                    if (it != "OK") {
                        Miao.i.runOnUiThread {
                            Miao.i.handleError(it)
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

        mTimer.schedule(object : TimerTask() {
            override fun run() {
                if (mRoomId >= 0) {
                    API.Doc.chatMessage(mRoomId) {
                        if (it != null) {
                            val receiveList = it.messages
                            val adapterList = mAdapter.items
                            if (it.messages.isEmpty() || adapterList.last().timestamp >= receiveList.last().timestamp)
                                return@chatMessage
                            if (adapterList.isEmpty())
                                mAdapter.update(receiveList)
                            val existLastTime = adapterList.last().timestamp
                            val appendList = receiveList.filter { it.timestamp > existLastTime }
                            mAdapter.append(appendList)
                        }
                    }
                }
            }
        }, 1000)
    }

    override fun onDestroy() {
        mTimer.cancel()
        super.onDestroy()
    }

    companion object {
        fun newInstance(room: ChatRoom): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putInt(Const.ID, room.roomId)
            args.putInt(Const.USER, room.lastUser?.uid ?: -1)
            args.putString(Const.NAME, room.lastUser?.username)
            fragment.arguments = args
            return fragment
        }
    }
}
