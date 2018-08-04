package org.miaowo.miaowo.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.template.EmptyAnimatorListener
import kotlin.math.min

class LoadingView : View {

    private var _title: String? = null
    private var _titleSize: Float = 120f
    private var _titleColor: Int = Color.BLACK
    private var _description: String? = null
    private var _descriptionSize: Float = 100f
    private var _descriptionColor: Int = Color.BLACK
    private var _loadingDrawable: Drawable? = null
    private var _totalDuring: Long = 500

    private var titlePaint: TextPaint? = null
    private var titleWidth: Float = 0f
    private var titleHeight: Float = 0f
    private var descriptionPaint: TextPaint? = null
    private var descriptionWidth: Float = 0f
    private var descriptionHeight: Float = 0f

    private var animPlay = ValueAnimator.ofFloat(1f, 0f)
    private var animStop = ValueAnimator.ofFloat(1f, 0f)
    private var animShow = ValueAnimator.ofFloat(0f, 1f)

    var title: String?
        get() = _title
        set(value) {
            _title = value
            invalidateTextPaintAndMeasurements()
        }

    var titleSize: Float
        get() = _titleSize
        set(value) {
            _titleSize = value
            invalidateTextPaintAndMeasurements()
        }

    var titleColor: Int
        get() = _titleColor
        set(value) {
            _titleColor = value
            invalidateTextPaintAndMeasurements()
        }

    var description: String?
        get() = _description
        set(value) {
            _description = value
            invalidateTextPaintAndMeasurements()
        }

    var descriptionSize: Float
        get() = _descriptionSize
        set(value) {
            _descriptionSize = value
            invalidateTextPaintAndMeasurements()
        }

    var descriptionColor: Int
        get() = _descriptionColor
        set(value) {
            _descriptionColor = value
            invalidateTextPaintAndMeasurements()
        }

    var loadingDrawable: Drawable?
        get() = _loadingDrawable
        set(value) {
            _loadingDrawable = value
            invalidateTextPaintAndMeasurements()
        }

    var totalDuring: Long
        get() = _totalDuring
        set(value) {
            _totalDuring = value
            invalidateTextPaintAndMeasurements()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        loadAttributes(attrs, defStyle)
        // Init paint and animator
        initPaint()
        initAnimator()
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()

    }

    private fun loadAttributes(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyle, 0)
        _title = a.getString(R.styleable.LoadingView_title)
        _titleSize = a.getDimension(R.styleable.LoadingView_titleSize, 24f)
        _titleColor = a.getColor(R.styleable.LoadingView_titleColor, Color.BLACK)
        _description = a.getString(R.styleable.LoadingView_description)
        _descriptionSize = a.getDimension(R.styleable.LoadingView_descriptionSize, 20f)
        _descriptionColor = a.getColor(R.styleable.LoadingView_descriptionColor, Color.BLACK)
        _loadingDrawable = a.getDrawable(R.styleable.LoadingView_loadingDrawable)
        _totalDuring = a.getInt(R.styleable.LoadingView_totalDuring, 500).toLong()
        a.recycle()
    }

    private fun initPaint() {
        titlePaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }
        descriptionPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }
    }

    private fun initAnimator() {
        animPlay.let {
            it.duration = _totalDuring
            it.repeatCount = ValueAnimator.INFINITE
            it.repeatMode = ValueAnimator.REVERSE
            it.addUpdateListener {
                alpha = it.animatedValue as Float
            }
        }

        animStop.let {
            it.addUpdateListener {
                alpha = it.animatedValue as Float
            }
            it.addListener(object : EmptyAnimatorListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    visibility = View.GONE
                }
            })
        }

        animShow.let {
            it.addUpdateListener {
                alpha = it.animatedValue as Float
            }
            it.addListener(object : EmptyAnimatorListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    animPlay.start()
                }
            })
        }
    }

    private fun invalidateTextPaintAndMeasurements() {
        titlePaint?.let {
            if (_title == null) {
                titleWidth = 0f
                titleHeight = 0f
                return@let
            }
            it.textSize = _titleSize
            it.color = _titleColor
            titleWidth = it.measureText(_title)
            titleHeight = it.fontMetrics.bottom
        }
        descriptionPaint?.let {
            if (_description == null) {
                descriptionWidth = 0f
                descriptionHeight = 0f
                return@let
            }
            it.textSize = _descriptionSize
            it.color = _descriptionColor
            descriptionWidth = it.measureText(_description)
            descriptionHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        val loadingSize = if (_loadingDrawable == null) 0 else min(contentHeight, contentWidth) / 2

        val emptyHeight = (contentHeight - loadingSize - titleHeight.toInt() - descriptionHeight.toInt()) / 8
        val totalHeight = if (emptyHeight > 0)
            emptyHeight * 2 + loadingSize + titleHeight.toInt() + descriptionHeight.toInt()
        else contentHeight

        _loadingDrawable?.let {
            val left = paddingLeft + (contentWidth - loadingSize) / 2
            val top = paddingTop + (contentHeight - totalHeight) / 2
            it.setBounds(left, top, left + loadingSize, top + loadingSize)
            it.draw(canvas)
        }

        canvas.drawText(_title,
                paddingLeft + (contentWidth - titleWidth) / 2,
                paddingTop + (contentHeight - totalHeight) / 2 + loadingSize + emptyHeight + titleHeight,
                titlePaint)

        canvas.drawText(_description,
                paddingLeft + (contentWidth - descriptionWidth) / 2,
                paddingTop + (contentHeight - totalHeight) / 2 + loadingSize + emptyHeight + titleHeight + descriptionHeight,
                descriptionPaint)
    }

    fun show() {
        visibility = View.VISIBLE
        if (animPlay.isRunning || animShow.isRunning)
            return
        animShow.let {
            it.setFloatValues(alpha, 1f)
            it.duration = (_totalDuring * (1 - alpha)).toLong()
            it.start()
        }
    }

    fun stop() {
        if (animShow.isRunning)
            animShow.end()
        if (animPlay.isRunning)
            animPlay.end()
        if (animStop.isRunning)
            animStop.end()
    }

    fun hide() {
        if (alpha == 1f) return
        animStop.let {
            it.setFloatValues(alpha, 0f)
            it.duration = (_totalDuring * alpha).toLong()
            it.start()
        }
    }
}
