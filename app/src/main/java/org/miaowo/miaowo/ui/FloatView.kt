package org.miaowo.miaowo.ui

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.IBinder
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.App
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.util.FormatUtil
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * 悬浮窗
 * Created by luqin on 17-1-21.
 */

class FloatView : LinearLayout {

    private var mEndX = 0f
    private var mEndY = 0f
    private var mStartX = 0f
    private var mStartY = 0f
    private var mChangeX = 0f
    private var mChangeY = 0f
    private val mScreenSize = FormatUtil.screenSize
    private var mGravity = Gravity.CENTER
    private var mManager = App.i.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var mPosition = Point(0, 0)
    private var mToken: IBinder? = null // View.applicationWindowToken
    private var mSlop = ViewConfiguration.get(App.i).scaledTouchSlop
    private var isShowing = false

    var view by Delegates.notNull<View>()
    var title: String? = null
    var positionSave: ((position: Point, gravity: Int) -> Unit) = { _, _ -> }

    constructor(title: String, @LayoutRes layout: Int, viewToken: IBinder) : super(App.i) {
        init()
        reset(title, layout, viewToken)
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

    fun show(gravity: Int, xPos: Int, yPos: Int): FloatView {
        mPosition.set(xPos, yPos)
        mGravity = gravity
        return show()
    }

    fun show(): FloatView {
        if (isShowing) {
            val index = shownWindows.indexOf(this)
            if (index > 0) {
                val bfViews = shownWindows.subList(0, index - 1)
                shownWindows[0] = this
                (0 until index).forEach {
                    shownWindows[it + 1] = bfViews[it]
                }
            }
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
            positionSave(mPosition, mGravity)
        }
        if (clear) {
            findViewById<FrameLayout>(R.id.container).removeAllViews()
            mPosition.set(0, 0)
            mGravity = Gravity.CENTER
            mToken = null
            title = ""
        }
        return this
    }

    fun reset(title: String, @LayoutRes layout: Int, viewToken: IBinder) {
        dismiss(true)
        this.title = title
        mToken = viewToken
        setLayout(layout)
    }

    private fun buildLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams().apply {
            gravity = mGravity
            x = mPosition.x
            y = mPosition.y
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            token = mToken
            // 外部可点击
            tag =   // WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    // 变暗
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND or
                    // 保持常亮
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    // 无视其他修饰物
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                    // 允许屏幕之外
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    // 防止脸误触
                    WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            format = PixelFormat.RGBA_8888
        }
    }

    private fun init() = LayoutInflater.from(context).inflate(R.layout.ui_window_normal, this)

    private fun setLayout(@LayoutRes layout: Int) {
        val container = findViewById<FrameLayout>(R.id.container)
        view = LayoutInflater.from(context).inflate(layout, container)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event.x
                mStartY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                mEndX = event.x
                mEndY = event.y
                mChangeX = mEndX - mStartX
                mChangeY = mEndY - mStartY
                if (mChangeX * mChangeX + mChangeY * mChangeY >= mSlop * mSlop) {
                    val params = layoutParams as WindowManager.LayoutParams
                    val limitXR = mScreenSize.x + params.width / 5 * 4
                    val limitXL = -params.width / 5
                    val limitYB = mScreenSize.y + params.height / 5 * 4
                    val limitYT = -params.height / 5
                    val pX = params.x + mChangeX.toInt()
                    val pY = params.y - mChangeY.toInt()
                    when {
                        pX > limitXR -> params.x = limitXR
                        pX < limitXL -> params.x = limitXL
                        else -> params.x = pX
                    }
                    when {
                        pY > limitYB -> params.y = limitYB
                        pY < limitYT -> params.y = limitYT
                        else -> params.y = pY
                    }
                    mPosition.set(params.x, params.y)
                    mManager.updateViewLayout(this, params)
                }
            }
            MotionEvent.ACTION_UP -> {
                positionSave(mPosition, mGravity)
            }
        }
        return true
    }

    fun defaultBar(): FloatView {
        val holder = ListHolder(this)
        holder.find(R.id.iv_close)?.setOnClickListener { dismiss(false) }
        holder.find(R.id.pb_loading)?.visibility = View.GONE
        holder.find<TextView>(R.id.tv_page)?.text = title ?: ""
        return this
    }

    companion object {
        var shownWindows = ArrayList<FloatView>(5)

        fun setDimAmount(activity: Activity, dimAmount: Float) {
            val window = activity.window
            val lp = window.attributes
            lp.dimAmount = dimAmount
            window.attributes = lp
        }

        fun dismissFirst() = shownWindows.firstOrNull()?.dismiss(true)
    }
}