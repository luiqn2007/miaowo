package org.miaowo.miaowo.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.firstPosition
import org.miaowo.miaowo.base.extra.inflateId

/**
 * 列表
 * Created by lq200 on 2018/3/29.
 */
abstract class BaseListFragment : Fragment(), SpringView.OnFreshListener {

    private var mScrollPosition = -1
    private var mTouchListener = object : RecyclerView.SimpleOnItemTouchListener() {
        val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                if (e == null) return false
                return try {
                    val child = list.findChildViewUnder(e.x, e.y) as ViewGroup
                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                    val position = list.getChildAdapterPosition(child)
                    onClickListener(childView, position)
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            override fun onLongPress(e: MotionEvent?) {
                if (e == null) return
                try {
                    val child = list.findChildViewUnder(e.x, e.y) as ViewGroup
                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                    val position = list.getChildAdapterPosition(child)
                    onLongListener(childView, position)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val mGestureDetector = GestureDetector(App.i, mGestureListener)

        override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?) = mGestureDetector.onTouchEvent(e)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(inflater, R.layout.fragment_list, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        list.addOnItemTouchListener(mTouchListener)
        setAdapter(list)

        springView.header = DefaultHeader(activity?.applicationContext)
        springView.footer = DefaultFooter(activity?.applicationContext)
        springView.setListener(this)
    }

    override fun onStop() {
        mScrollPosition = list.firstPosition
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        if (mScrollPosition < 0) {
            mScrollPosition = 0
            onRefresh()
        } else list.scrollToPosition(mScrollPosition)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        attach = context
    }

    override fun onDetach() {
        super.onDetach()
        attach = null
    }

    override fun onLoadmore() = loadOver()

    override fun onRefresh() = loadOver()

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

    open fun onClickListener(view: View, position: Int): Boolean = false

    open fun onLongListener(view: View, position: Int) {}

    protected var header: SpringView.DragHander?
        get() = springView.header
        set(value) {
            if (header == null)
                springView.isEnableHeader = false
            else {
                springView.isEnableHeader = true
                springView.header = value
            }
        }

    protected var footer: SpringView.DragHander?
        get() = springView.footer
        set(value) {
            if (footer != null) {
                springView.isEnableFooter = true
                springView.footer
            } else {
                springView.isEnableFooter = false
            }
        }

    protected var attach: Context? = null

    fun loadOver() {
        if (Thread.currentThread() == Looper.getMainLooper().thread) _loadOver()
        else Miao.i.runOnUiThread { _loadOver() }
    }

    @Suppress("FunctionName")
    private fun _loadOver() {
        if (isVisible) {
            loading.hide()
            springView?.onFinishFreshAndLoad()
        }
    }

    abstract fun setAdapter(list: RecyclerView)
}