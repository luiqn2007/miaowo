package org.miaowo.miaowo.fragment.chat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_chat.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.ChatMsgAdapter
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.bean.data.ChatMessage
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.other.ChatListAnimator
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.base.extra.lTODO
import java.io.IOException

/**
 * 聊天内容
 * Created by luqin on 17-4-23.
 */

class ChatFragment : Fragment() {
    private var mAdapter = ChatMsgAdapter()
    var room: ChatRoom? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_chat, inflater, container)
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        list.adapter = mAdapter
        list.itemAnimator = ChatListAnimator()

        API.Doc.chatMessage(room?.roomId ?: Const.NO_ID) {
            try {
                mAdapter.update(it?.messages ?: listOf())
                list.scrollToPosition(mAdapter.itemCount - 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        btn_send.setOnClickListener {
            val message = et_msg.text.toString()
            if (TextUtils.isEmpty(message)) {
                activity?.toast(R.string.err_msg_empty, TastyToast.ERROR)
            } else {
                API.Use.sendChat(room!!.lastUser!!.uid, room!!.roomId, message) { lTODO(it) }
                et_msg.setText("")
            }
        }
    }

    fun newMessage(message: ChatMessage) {
        mAdapter.insert(message, false)
        list.scrollToPosition(mAdapter.itemCount - 1)
    }

    companion object {
        fun newInstance(room: ChatRoom): ChatFragment {
            val fragment = ChatFragment()
            fragment.room = room
            return fragment
        }
    }
}
