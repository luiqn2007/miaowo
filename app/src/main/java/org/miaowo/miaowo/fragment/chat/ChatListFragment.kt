package org.miaowo.miaowo.fragment.chat

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.adapter.ChatRoomListAdapter
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.loadFragment
import org.miaowo.miaowo.bean.data.ChatRoom
import org.miaowo.miaowo.other.Const

class ChatListFragment : Fragment() {
    companion object {
        fun newInstance(): ChatListFragment {
            val fragment = ChatListFragment()
            val args = Bundle()
            args.putBoolean(Const.FG_POP_ALL, true)
            fragment.arguments = args
            return fragment
        }
    }

    private var mListener: IMiaoListener? = null
    private var mAdapter: ChatRoomListAdapter? = null
    private val mLoader = object : SpringView.OnFreshListener {
        override fun onLoadmore() {

        }

        override fun onRefresh() {
            API.Doc.chatRoom {
                activity?.runOnUiThread {
                    mAdapter?.update(it?.rooms ?: listOf())
                    springView.onFinishFreshAndLoad()
                }
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListener?.setToolbar(getString(R.string.chat))
        mAdapter = ChatRoomListAdapter(App.i)

        list.adapter = mAdapter
        list.layoutManager = LinearLayoutManager(context)
        list.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            var clickItem: ChatRoom? = null

            val listener = object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    return if (clickItem != null) {
                        Miao.i.loadFragment(ChatFragment.newInstance(clickItem!!))
                        true
                    } else false
                }
            }

            val detector = GestureDetector(context, listener)

            override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
                if (rv == null || e == null) return false
                val position = rv.getChildLayoutPosition(rv.findChildViewUnder(e.x, e.y))
                if (position < 0) return false
                clickItem = mAdapter!!.getItem(position)
                return detector.onTouchEvent(e)
            }
        })

        springView.header = DefaultHeader(context)
        springView.setListener(mLoader)
        mLoader.onRefresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener)
            mListener = context
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}