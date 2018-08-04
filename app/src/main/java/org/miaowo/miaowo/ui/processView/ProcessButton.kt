package org.miaowo.miaowo.ui.processView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.Button

/**
 * 用于显示有进度的按钮
 * Created by lq2007 on 2017/9/27 0027.
 */
class ProcessButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : Button(context, attrs, defStyleAttr), IProcessable {

    // IProcessable
    private var mMaxProcess = 100f
    private var mMinProcess = 0f
    private var mProcess = 0f
    private var mShowType = IProcessable.ShowType.Both
    private var mProcessType = IProcessable.ProcessType.Percent
    private var mErrorBg = DEF_ERROR_BG
    private var mSuccessfulBg = DEF_SUCCESSFUL_BG
    private var mProcessText: CharSequence = ""
    private var mError = false
    // Button
    private var mIsShowProcess = false
    private var mText: CharSequence = ""
    // Draw
    private val mPaint = Paint()
    private val mProcessWidth: Float
        get() {
            val tWidth = width - paddingLeft - paddingRight
            val p = if (isError) 1f else processPercent
            return tWidth * p
        }
    private var mDrawWidth = 0f
    private var mBaseSpeed = 5f
    private var mAddedSpeed = 0f

    override var maxProcess: Float
        get() = mMaxProcess
        set(value) {
            mMaxProcess = value
            val tProcess: Float
            when {
                value < mMinProcess -> {
                    mMinProcess = value
                    tProcess = value
                }
                value < mProcess -> tProcess = value
                else -> tProcess = mProcess
            }
            process = tProcess
            updateProcess()
        }
    override var minProcess: Float
        get() = mMinProcess
        set(value) {
            mMinProcess = value
            val tProcess: Float
            when {
                value > mMaxProcess -> {
                    mMaxProcess = value
                    tProcess = value
                }
                value > mProcess -> tProcess = value
                else -> tProcess = mProcess
            }
            process = tProcess
            updateProcess()
        }
    override var process: Float
        get() = mProcess
        set(value) {
            mProcess = when {
                value > mMaxProcess -> mMaxProcess
                value < mMinProcess -> mMinProcess
                else -> value
            }
            updateProcess()
        }
    override var processPercent: Float
        get() {
            return if (mMaxProcess == mMinProcess) 0f
            else (mProcess - mMinProcess) / (mMaxProcess - mMinProcess)
        }
        set(value) {
            process = if (maxProcess == minProcess || value >= 1) maxProcess
            else if (value <= 0) minProcess
            else (maxProcess - minProcess) * value
            updateProcess()
        }
    override var showType: IProcessable.ShowType
        get() = mShowType
        set(value) {
            mShowType = value
            updateProcess()
        }
    override var processType: IProcessable.ProcessType
        get() = mProcessType
        set(value) {
            mProcessType = value
            updateProcess()
        }
    override var errorBackground: Drawable
        get() = mErrorBg
        set(value) {
            mErrorBg = value
            updateProcess()
        }
    override var successfulBackground: Drawable
        get() = mSuccessfulBg
        set(value) {
            mSuccessfulBg = value
            updateProcess()
        }
    override var isError: Boolean
        get() = mError
        set(value) {
            if (value != mError) {
                mError = value
            }
            updateProcess()
        }
    override var processText: CharSequence
        get() = mProcessText
        set(value) {
            mProcessText = value
            updateProcess()
        }
    override val showFinish: Boolean
        get() = mDrawWidth >= mProcessWidth

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        mPaint.style = Paint.Style.FILL
    }

    companion object {
        val DEF_ERROR_BG: Drawable = ColorDrawable(Color.RED)
        val DEF_SUCCESSFUL_BG: Drawable = ColorDrawable(Color.BLUE)
    }

    override fun showProcess() {
        if (!mIsShowProcess) {
            mText = text
            mIsShowProcess = true
            updateProcess()
        }
    }

    override fun hideProcess() {
        if (mIsShowProcess) {
            text = mText
            mIsShowProcess = false
            mDrawWidth = 0f
        }
    }

    override fun setProcess(process: Float, text: String?, isError: Boolean) {
        this.process = process
        this.processText = text ?: ""
        this.isError = isError
        updateProcess()
    }

    private fun updateProcess() {
        if (mIsShowProcess) text = processText
        else hideProcess()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (mIsShowProcess && (processPercent > 0 || isError)) {
            val top = paddingTop
            val left = paddingLeft
            val height = height - paddingTop - paddingBottom
            mDrawWidth += (mBaseSpeed + mAddedSpeed)

            if (isError) {
                mErrorBg.setBounds(left, top, mDrawWidth.toInt(), height + top)
                mErrorBg.draw(canvas)
            } else {
                mSuccessfulBg.setBounds(left, top, mDrawWidth.toInt(), height + top)
                mSuccessfulBg.draw(canvas)
            }
        }
        super.onDraw(canvas)

        if (mDrawWidth < mProcessWidth) {
            invalidate()
            mAddedSpeed += mBaseSpeed / 3
        } else mAddedSpeed = 0f
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener {
            if (isError) {
                mError = false
                hideProcess()
            } else if (!mIsShowProcess) {
                l?.onClick(this)
            }
        }
    }
}