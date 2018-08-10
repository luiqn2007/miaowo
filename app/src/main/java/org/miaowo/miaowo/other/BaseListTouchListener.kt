package org.miaowo.miaowo.other

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

open class BaseListTouchListener(val context: Context): RecyclerView.SimpleOnItemTouchListener() {

    companion object {
        fun findView(viewGroup: ViewGroup, x: Int, y: Int, rect: Rect? = null): View {
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

    private var list: RecyclerView? = null
    private val touchGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return try {
                if (list != null && e != null) {
                    val child = list!!.findChildViewUnder(e.x, e.y) as ViewGroup
                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                    val position = list!!.getChildAdapterPosition(child)
                    onClick(childView, position)
                } else false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return try {
                if (list != null && e != null) {
                    val child = list!!.findChildViewUnder(e.x, e.y) as ViewGroup
                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                    val position = list!!.getChildAdapterPosition(child)
                    onDoubleClick(childView, position)
                } else false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        override fun onLongPress(e: MotionEvent?) {
            try {
                if (list != null && e != null) {
                    val child = list!!.findChildViewUnder(e.x, e.y) as ViewGroup
                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                    val position = list!!.getChildAdapterPosition(child)
                    onLongPress(childView, position)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    })

    open fun onClick(view: View?, position: Int): Boolean = false
    open fun onDoubleClick(view: View?, position: Int): Boolean = false
    open fun onLongPress(view: View?, position: Int) {}

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        list = rv
        return touchGestureDetector.onTouchEvent(e)
    }
}