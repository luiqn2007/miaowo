package org.miaowo.miaowo.ui

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.PointF
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout

import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.base.BaseViewHolder

import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * 悬浮窗
 * Created by luqin on 17-1-21.
 */

class FloatView : LinearLayout {

    private val mStartTouch = PointF()
    private val mEndTouch = PointF()
    private val mChange = PointF()
    private var mPosition = Point(0, 0)
    private var mGravity = Gravity.CENTER
    private var mManager = BaseActivity.get!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var isShowing = false

    var view by Delegates.notNull<View>()
    var title: String? = null

    constructor(title: String, @LayoutRes layout: Int) : super(App.i) {
        this.title = title
        init()
        setLayout(layout)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun show(gravity: Int, position: Point): FloatView {
        mPosition = position
        mGravity = gravity
        return show()
    }

    fun show(): FloatView {
        if (isShowing) {
            return this
        }
        val params = buildLayoutParams()
        shownWindows.add(this)
        isShowing = true
        mManager.addView(this, params)
        return this
    }

    fun dismiss(clear: Boolean): FloatView {
        if (isShowing) {
            mManager.removeView(this)
            shownWindows.remove(this)
            isShowing = false
            if (clear) {
                mPosition.set(0, 0)
                mGravity = Gravity.CENTER
                title = ""
            }
        }
        return this
    }

    private fun buildLayoutParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.RGBA_8888)
        params.gravity = mGravity
        params.x = this.mPosition.x
        params.y = this.mPosition.y
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        return params
    }

    private fun init() = LayoutInflater.from(context).inflate(R.layout.ui_window_normal, this)

    private fun setLayout(@LayoutRes layout: Int) {
        val container = findViewById(R.id.container) as FrameLayout
        view = LayoutInflater.from(context).inflate(layout, container)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mStartTouch.set(event.x, event.y)
            MotionEvent.ACTION_MOVE -> {
                mEndTouch.set(event.x, event.y)
                mChange.set(mEndTouch.x - mStartTouch.x, mEndTouch.y - mStartTouch.y)
                if (Math.sqrt(Math.pow(mChange.x.toDouble(), 2.0) + Math.pow(mChange.y.toDouble(), 2.0)) >= 20) {
                    val params = layoutParams as WindowManager.LayoutParams
                    params.x += mChange.x.toInt()
                    params.y += mChange.y.toInt()
                    mPosition.set(params.x, params.y)
                    mManager.updateViewLayout(this, params)
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    fun defaultBar(): FloatView {
        val holder = BaseViewHolder(this)
        holder.setClickListener(R.id.iv_close) { dismiss(false) }
        holder.setText(R.id.tv_page, title ?: "")
        holder.getView(R.id.pb_loading)?.visibility = View.GONE
        return this
    }

    companion object {
        var shownWindows = ArrayList<FloatView>()
    }
}
