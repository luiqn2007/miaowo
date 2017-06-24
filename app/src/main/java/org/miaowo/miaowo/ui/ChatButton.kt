package org.miaowo.miaowo.ui

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Chat
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.activity
import org.miaowo.miaowo.base.extra.uiThread
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.base.extra.spPut
import java.util.*
import kotlin.properties.Delegates

/**
 * 悬浮窗
 * Created by luqin on 17-1-21.
 */

class ChatButton : View {

    private val mPosition = Point()
    private val mScreenSize = Point()
    private val mStartTouch = PointF()
    private val mEndTouch = PointF()
    private val mChange = PointF()
    private var mPaintFill = Paint()
    private var mManager: WindowManager? = null
    private var mLogo: Drawable? = null
    private var mSize by Delegates.notNull<Float>()
    private var mMoved = false
    private var mMoveBack: Timer? = null

    private fun init() {
        mManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mManager?.defaultDisplay?.getSize(mScreenSize)
        mSize = (Math.max(mScreenSize.x, mScreenSize.y) / 32 * 3).toFloat()
        mPosition.set(spGet("btn_x", mScreenSize.x / 2), spGet("btn_y", mScreenSize.y / 2))
        mPaintFill.style = Paint.Style.FILL
        val color = ResourcesCompat.getColor(resources, R.color.md_deep_purple_400, null)
        mPaintFill.color = Color.argb(100, Color.red(color), Color.green(color), Color.blue(color))
        mLogo = ResourcesCompat.getDrawable(resources, R.drawable.chat, null)
    }

    constructor(context: Context) : super(context) { init() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    fun show() {
        val params = buildLayoutParams()
        try {
            mManager!!.addView(this, params)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dismiss() {
        try { mManager?.removeViewImmediate(this) }
        catch (e: Exception) { e.printStackTrace() }
    }

    private fun buildLayoutParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.RGBA_8888)
        params.gravity = Gravity.START or Gravity.TOP
        params.x = this.mPosition.x
        params.y = this.mPosition.y
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        return params
    }

    private fun move(dx: Int, dy: Int) {
        val params = layoutParams as WindowManager.LayoutParams
        params.x += dx
        params.y += dy
        mPosition.set(params.x, params.y)
        mManager!!.updateViewLayout(this, params)
    }

    private fun clickAction(x: Int, y: Int, moved: Boolean) {
        var aX = x
        var aY = y
        val xTo: Int
        val yTo: Int
        if (!moved) {
            val finalY = aY
            if (aX < 0 || aX > mScreenSize.x - mSize) {
                if (aX < 0) {
                    xTo = 0
                    mMoveBack?.cancel()
                    mMoveBack = Timer()
                    mMoveBack?.schedule(object : TimerTask() {
                        override fun run() {
                            activity?.uiThread { clickAction(-1, finalY, true) }
                            mMoveBack = null
                        }
                    }, 600)
                } else {
                    xTo = (mScreenSize.x - mSize).toInt()
                    mMoveBack?.cancel()
                    mMoveBack = Timer()
                    mMoveBack?.schedule(object : TimerTask() {
                        override fun run() {
                            activity?.uiThread { clickAction((mScreenSize.x - mSize + 1).toInt(), finalY, true) }
                            mMoveBack = null
                        }
                    }, 600)
                }
            } else {
                context.startActivity(Intent(context, Chat::class.java))
                return
            }
        } else {
            xTo = if (aX < 0) (-mSize * 2 / 3).toInt()
            else if (aX > mScreenSize.x - mSize) (mScreenSize.x - mSize / 3).toInt() else aX
        }
        yTo = if (aY < 0) 1
        else if (aY > mScreenSize.y - mSize) (mScreenSize.y - mSize).toInt() else aY
        var dx = if (aX > xTo) -1 else if (aX < xTo) 1 else 0
        var dy = if (aY > yTo) -1 else if (aY < yTo) 1 else 0
        while (aX != xTo || aY != yTo) {
            move(dx, dy)
            aX += dx
            aY += dy
            dx = if (aX > xTo) -1 else if (aX < xTo) 1 else 0
            dy = if (aY > yTo) -1 else if (aY < yTo) 1 else 0
        }
        mPosition.x = xTo
        mPosition.y = yTo
        spPut("btn_x", mPosition.x)
        spPut("btn_y", mPosition.y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mSize = measuredWidth.toFloat()
        canvas.drawCircle(mSize / 2, mSize / 2, Math.min(mSize, mSize) / 2, mPaintFill)
        mLogo?.setBounds((mSize / 4).toInt(), (mSize / 4).toInt(), (mSize * 3 / 4).toInt(), (mSize * 3 / 4).toInt())
        mLogo?.draw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY)
            View.MeasureSpec.getSize(widthMeasureSpec)
        else
            mSize.toInt(),
                if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY)
                    View.MeasureSpec.getSize(heightMeasureSpec)
                else
                    mSize.toInt())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartTouch.set(event.x, event.y)
                mMoved = false
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                mEndTouch.set(event.x, event.y)
                mChange.set(mEndTouch.x - mStartTouch.x, mEndTouch.y - mStartTouch.y)
                if (Math.sqrt(Math.pow(mChange.x.toDouble(), 2.0) + Math.pow(mChange.y.toDouble(), 2.0)) >= 3) {
                    move(mChange.x.toInt(), mChange.y.toInt())
                    mMoved = true
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                clickAction(mPosition.x, mPosition.y, mMoved)
                return true
            }
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        activity?.onKeyDown(keyCode, event)
        return true
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        activity?.onKeyUp(keyCode, event)
        return true
    }

    companion object {
        private var chat: ChatButton? = null

        fun show() {
            if (chat == null) {
                chat = ChatButton(App.i)
            }
            chat?.show()
        }

        fun hide() = chat?.dismiss()
    }
}
