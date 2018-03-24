package org.miaowo.miaowo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.*
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.*

class CategoryFragment : Fragment() {
    companion object {
        private val mFragments = SparseArray<CategoryFragment>()

        fun newInstance(category: Category): CategoryFragment {
            if (mFragments[category.cid] != null)
                return mFragments[category.cid]

            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt(Const.ID, category.cid)
            args.putBoolean(Const.FG_POP_ALL, true)
            args.putString(Const.NAME, category.name)
            fragment.arguments = args
            return fragment
        }
    }

    private var mId = -1
    private var mName = ""
    private var mTopics = mutableListOf<Topic>()
    private var mListenerI: IMiaoListener? = null
    private val mAdapter = PostAdapter(true, false)
    private var mScrollPosition = -1
    private var mPage = 0
    private var mLoader = object : SpringView.OnFreshListener {
        override fun onLoadmore() {
            API.Doc.category(mId, mPage + 1) {
                activity?.runOnUiThread {
                    if (it != null) mAdapter.append(it.topics, false)
                    mPage++
                    if (springView != null) springView.onFinishFreshAndLoad()
                }
            }
        }

        override fun onRefresh() {
            API.Doc.category(mId, 1) {
                if (it != null) {
                    mTopics.clear()
                    mTopics.addAll(it.topics)
                }
                mPage = 1
                activity?.runOnUiThread {
                    mAdapter.update(mTopics)
                    if (springView != null) springView.onFinishFreshAndLoad()
                }
            }
        }
    }
    private var mTouchListener = object : RecyclerView.SimpleOnItemTouchListener() {
        val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                lInfo("here")
                if (e == null) return false
                try {
                    val child = list.findChildViewUnder(e.x, e.y) as ViewGroup
                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                    lInfo(childView.javaClass)
                    val position = list.getChildAdapterPosition(child)
                    val item = mAdapter.getItem(position) as Topic
                    when (childView.id) {
                        R.id.head ->
                            mListenerI?.jump(IMiaoListener.JumpFragment.User, item.user?.username
                                    ?: "")
                        R.id.like -> API.Topics.follow(item.tid) {
                            activity?.runOnUiThread {
                                if (it != Const.RET_OK)
                                    activity?.handleError(it)
                                else
                                    (childView as ImageView).setImageDrawable(
                                            IconicsDrawable(App.i, FontAwesome.Icon.faw_heart).color(Color.RED).actionBar()
                                    )
                            }
                        }
                    }
                    mListenerI?.jump(IMiaoListener.JumpFragment.Topic, item.tid)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return true
            }
        }
        val mGestureDetector = GestureDetector(App.i, mGestureListener)

        override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
            return mGestureDetector.onTouchEvent(e)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) {
            mListenerI = context
        }
        mFragments.put(mId, this)
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI = null
        mFragments.delete(mId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_list, inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListenerI?.decorationVisible = false
        mListenerI?.setToolbar(mName)

        mId = arguments?.getInt(Const.ID) ?: -1
        mName = arguments?.getString(Const.NAME) ?: ""

        mListenerI?.toolbar?.title = mName

        list.adapter = mAdapter
        list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        list.addOnItemTouchListener(mTouchListener)

        springView.header = DefaultHeader(context)
        springView.footer = DefaultFooter(context)
        springView.setListener(mLoader)
    }

    override fun onStop() {
        mScrollPosition = list.firstPosition
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        if (mScrollPosition < 0) {
            mScrollPosition = 0
            mLoader.onRefresh()
        } else
            list.scrollToPosition(mScrollPosition)
    }

    private fun findView(viewGroup: ViewGroup, x: Int, y: Int, rect: Rect? = null): View {
        val count = viewGroup.childCount
        val viewRect = rect ?: Rect()
        for (i in 0 until count) {
            val child = viewGroup.getChildAt(i)
            child.getGlobalVisibleRect(viewRect)
            if (viewRect.contains(x, y)) {
                return if (child !is ViewGroup) child
                else findView(child, x, y, viewRect)
            }
        }
        return viewGroup
    }
}