package org.miaowo.miaowo.fragment

import android.app.AlertDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.internal.SnackbarContentLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.databinding.FragmentUserDetailBinding
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.bean.data.ChatRooms
import org.miaowo.miaowo.bean.data.User

/**
 * 个人资料
 */
class UserFragment : Fragment() {

    companion object {
        fun newInstance(name: String = API.user.username): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            args.putString(Const.NAME, name)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(uid: Int): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            args.putInt(Const.ID, uid)
            fragment.arguments = args
            return fragment
        }
    }

    val name: String get() = arguments!!.getString(Const.NAME, API.user.username)
    val uid: Int get() = arguments!!.getInt(Const.ID, -1)

    var mListenerI: IMiaoListener? = null
    lateinit var binding: FragmentUserDetailBinding

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI?.showOptionIconOnToolbar()
        mListenerI = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListenerI?.showBackIconOnToolbar()
        val tUid = uid
        val callback: (user: User?) -> Unit = { gUser ->
            activity?.runOnUiThread {
                binding.setUser(gUser)
                mListenerI?.setToolbar(gUser?.username ?: getString(R.string.err_chat_user))
                if (gUser?.uid == API.user.uid) {
                    focus.isEnabled = false
                    chat.isEnabled = false
                } else if (gUser != null) {
                    // 关注
                    focus.setOnClickListener {
                        if (!gUser.isFollowing) {
                            API.Users.follow(gUser.uid) {
                                activity?.runOnUiThread {
                                    if (it == "ok") activity?.toast(R.string.focus_success, TastyToast.SUCCESS)
                                    else activity?.handleError(it)
                                }
                            }
                        } else {
                            API.Users.unFollow(gUser.uid) {
                                activity?.runOnUiThread {
                                    if (it == "ok") activity?.toast(R.string.focus_canceled, TastyToast.SUCCESS)
                                    else activity?.handleError(it)
                                }
                            }
                        }
                    }
                    // 聊天(会话)
                    chat.setOnClickListener {
                        API.Doc.chatRoom {
                            activity?.runOnUiThread { chatRoomSelect(gUser, it) }
                        }
                    }
                }
                tab.setupWithViewPager(pager, true)
                tab.isSmoothScrollingEnabled = true
                pager.adapter = object : PagerAdapter() {
                    val cView = mutableListOf<View>()
                    val cTitle = listOf(R.string.title, R.string.reply, R.string.user_detail)

                    override fun isViewFromObject(iView: View, `object`: Any) = iView == `object`

                    override fun getCount() = 3

                    override fun instantiateItem(container: ViewGroup, position: Int): Any {
                        if (cView.size == 0) {
                            // 0 主题
                            val uReply = RecyclerView(context)
                            val uReplyAdapter = PostAdapter(false, false)
                            uReply.adapter = uReplyAdapter
                            uReply.layoutManager = LinearLayoutManager(context)
                            API.Doc.topic(name.toLowerCase()) {
                                activity?.runOnUiThread {
                                    uReplyAdapter.update(it?.topics ?: emptyList())
                                }
                            }
                            // 1 回复
                            val uTopic = RecyclerView(context)
                            val uTopicAdapter = PostAdapter(false, false)
                            uTopic.adapter = uTopicAdapter
                            uTopic.layoutManager = LinearLayoutManager(context)
                            API.Doc.post(name.toLowerCase()) {
                                activity?.runOnUiThread {
                                    uTopicAdapter.update(it?.posts ?: listOf())
                                }
                            }
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
                pager.setOnTouchListener { _, event ->
                    val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
                        var mDistanceX = 0
                        val mSlop = ViewConfiguration.get(App.i).scaledTouchSlop
                        val limit = pager.measuredWidth / 2
                        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                            if (mSlop > distanceX)
                                return false
                            mDistanceX = distanceX.toInt()
                            tab.smoothScrollTo(mDistanceX, 0)
                            return true
                        }

                        override fun onSingleTapUp(e: MotionEvent?): Boolean {
                            if (mDistanceX == 0) return false
                            when {
                                mDistanceX > limit -> tab.arrowScroll(View.FOCUS_RIGHT)
                                mDistanceX < -limit -> tab.arrowScroll(View.FOCUS_LEFT)
                                else -> tab.scrollTo(0, 0)
                            }
                            return true
                        }
                    }
                    val mGestureDetector = GestureDetector(App.i, mGestureListener)
                    return@setOnTouchListener mGestureDetector.onTouchEvent(event)
                }
            }
        }
        if (tUid < 0) API.Doc.user(name, callback)
        else API.Doc.user(tUid, callback)
    }

    private fun chatRoomSelect(user: User, receive: ChatRooms?) {
        // 查找会话
        val rooms = receive?.rooms?.filter { it.users.any { it.uid == user.uid } }

        // 空：直接打开新会话
        if (rooms == null || rooms.isEmpty()) {
            sendAMessage(user)
            return
        }

        // 选择会话
        val roomNames = rooms
                .map { it.roomName ?: "id: ${it.roomId}" }
                .toMutableList()
        roomNames.add("新窗口")

        val arr = roomNames.toTypedArray()
        AlertDialog.Builder(App.i).setItems(arr, { dialog, which ->
            val roomId =
                    if (which == arr.size - 1) null
                    else rooms.firstOrNull {
                        it.roomName == arr[which]
                    }?.roomId
            sendAMessage(user, roomId)
            dialog.dismiss()
        })
    }

    private fun sendAMessage(user: User, roomId: Int? = null) {
        mListenerI?.snackBar("Choose", Snackbar.LENGTH_LONG)?.apply {
            // 将 mMessageText 更换为 EditText, 原本为 TextView
            val contentLayout = (view as SnackbarContentLayout).getChildAt(0) as SnackbarContentLayout
            val text = contentLayout.javaClass.getField("mMessageView")
            text.isAccessible = true
            val et = EditText(context)
            et.hint = "你想对 Ta 说什么？"
            et.maxLines = 5
            text.set(contentLayout, et)

            setAction(R.string.send) {
                API.Users.chat(user.uid, et.text.toString(), roomId) {
                    if (it != Const.RET_OK)
                        Miao.i.runOnUiThread { activity?.handleError(it) }
                    else
                        dismiss()
                }
            }
        }?.show()
    }
}
