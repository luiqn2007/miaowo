package org.miaowo.miaowo.util

import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.text.*
import android.view.WindowManager
import com.blankj.utilcode.util.ActivityUtils
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.Const
import org.xml.sax.XMLReader
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min


/**
 * 格式化工具类
 * Created by luqin on 17-1-23.
 */

object FormatUtil {
    private var mCachedScreenSize: Point? = null
    private var mCachedTextSize = mutableMapOf<Int, Point>()
    private val mTextPaint by lazy {
        TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            density = App.i.resources.displayMetrics.density
        }
    }

    private val hErrorDrawable = {
        val eIcon = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_error, null)
        val sSize = FormatUtil.screenSize
        val iSize = min(sSize.x, sSize.y) / 4
        eIcon?.setBounds(0, 0, iSize, iSize)
        eIcon
    }.invoke()

    private val hPlaceDrawable = {
        val pIcon = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_loading, null)
        val sSize = FormatUtil.screenSize
        val iSize = min(sSize.x, sSize.y) / 4
        pIcon?.setBounds(0, 0, iSize, iSize)
        pIcon
    }.invoke()

    val screenSize: Point
        get() {
            if (mCachedScreenSize == null)
                loadScreenSize()
            return mCachedScreenSize!!
        }

    fun textSize(textSize: Float): Point {
        val intSize = textSize.toInt()
        if (!mCachedTextSize.containsKey(intSize)) {
            val size = Point()
            val bound = Rect()
            mTextPaint.textSize = textSize
            mTextPaint.measureText("t")
            mTextPaint.getTextBounds("t", 0, 1, bound)
            size.set(bound.right - bound.left, bound.bottom - bound.top)
            mCachedTextSize[intSize] = size
        }
        return mCachedTextSize[intSize]!!
    }

    fun time(time: Long?): String {
        if (time == null || time < 0) {
            return "无效时间"
        }
        val second = (System.currentTimeMillis() - time) / 1000
        return when {
            second >= 60 * 60 * 24 * 365 -> "至少 ${(second / (60 * 60 * 24 * 365)).toInt()} 年"
            second >= 60 * 60 * 24 * 30 -> "至少 ${(second / (60 * 60 * 24 * 30)).toInt()} 个月"
            second >= 60 * 60 * 24 -> "${(second / (60 * 60 * 24)).toInt()} 天"
            second >= 60 * 60 -> "${(second / (60 * 60)).toInt()} 小时"
            second >= 60 -> "${(second / 60)} 分钟"
            second >= 3 -> "$second 秒"
            else -> "片刻"
        }
    }

    fun html(html: String?, textSize: Float, config: HtmlFormatConfig): SpannableStringBuilder {
        val imgGetterExist = Html.ImageGetter {
            resizeImage(HtmlImageDownloader[it], textSize) ?: hErrorDrawable
        }
        var tagHandler: Html.TagHandler? = null
        val imgGetter = Html.ImageGetter {
            HtmlImageDownloader.newTask(it) {
                ActivityUtils.getTopActivity().runOnUiThread {
                    config.resetHandler(Html.fromHtml(html ?: "", Html.FROM_HTML_MODE_LEGACY, imgGetterExist, tagHandler, App.i))
                }
            }
            hPlaceDrawable
        }
        tagHandler = CustomTagHandler(config, imgGetter)
        val rHtml = CustomTagHandler.renameTag(html ?: "", *config.getTagArray())
        return Html.fromHtml(rHtml, Html.FROM_HTML_MODE_LEGACY, imgGetter, tagHandler, App.i) as SpannableStringBuilder
    }

    private fun loadScreenSize() {
        val wm = App.i.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mCachedScreenSize = Point()
        wm.defaultDisplay.getSize(mCachedScreenSize)
    }

    private fun resizeImage(drawable: Drawable?, charSize: Float): Drawable? {
        if (drawable == null) return null

        val sSize = FormatUtil.screenSize
        val cSize = FormatUtil.textSize(charSize)
        val maxSize = min(sSize.x, sSize.y) * 3 / 5
        val minSize = max(cSize.x, cSize.y)

        val dHeight = drawable.intrinsicHeight
        val dWidth = drawable.intrinsicWidth

        if (dWidth <= 0 || dHeight <= 0)
            drawable.setBounds(0, 0, maxSize, maxSize)
        else if (dWidth < minSize && dHeight < minSize) {
            val eW = minSize / dWidth
            val eH = minSize / dHeight
            val e = max(eW, eH)
            drawable.setBounds(0, 0, dWidth * e, dHeight * e)
        } else if (dWidth > maxSize || dHeight > maxSize) {
            val eW = dWidth / maxSize
            val eH = dHeight / maxSize
            val e = max(eW, eH)
            drawable.setBounds(0, 0, dWidth / e, dHeight / e)
        }

        return drawable
    }

    class HtmlFormatConfig {
        private val tagHandlerMap = mutableMapOf<String, (spanMark: SpanMark, imgHandler: Html.ImageGetter?) -> Array<Any>>()
        private val tagList = mutableListOf<String>()
        @Suppress("UNREACHABLE_CODE")
        private var _resetHandler = { _: Spanned ->
            throw Exception("No Reset Handler")
            Unit
        }

        val resetHandler get() = _resetHandler

        fun addTagHandler(tag: String, handler: (spanMark: SpanMark, imgHandler: Html.ImageGetter?) -> Array<Any>): HtmlFormatConfig {
            tagList.add(tag)
            tagHandlerMap["${CustomTagHandler.CUSTOM_TAG}$tag"] = handler
            return this
        }

        fun getTagHandler(tag: String) = tagHandlerMap["${CustomTagHandler.CUSTOM_TAG}$tag"]

        fun getTagArray() = tagList.toTypedArray()

        fun registerContentReset(reset: (content: Spanned) -> Unit): HtmlFormatConfig {
            _resetHandler = reset
            return this
        }
    }

    class CustomTagHandler(private val config: HtmlFormatConfig, private val imgHandler: Html.ImageGetter?): Html.TagHandler {
        companion object {
            const val CUSTOM_TAG = "CUSTOM_TAG_"

            fun fixTag(tag: String?): String {
                return if (tag == null) return ""
                else if (!tag.startsWith(CUSTOM_TAG)) tag
                else tag.replaceFirst(CUSTOM_TAG, "")
            }

            fun renameTag(html: String, vararg tags: String): String {
                var r = html
                tags.forEach {
                    r = r
                            .replace("<$it ", "<${CustomTagHandler.CUSTOM_TAG}$it ", true)
                            .replace("<$it>", "<${CustomTagHandler.CUSTOM_TAG}$it>", true)
                            .replace("<$it\n", "<${CustomTagHandler.CUSTOM_TAG}$it\n", true)

                            .replace("</$it ", "</${CustomTagHandler.CUSTOM_TAG}$it ", true)
                            .replace("</$it>", "</${CustomTagHandler.CUSTOM_TAG}$it>", true)
                            .replace("</$it\n", "</${CustomTagHandler.CUSTOM_TAG}$it\n", true)

                            .replace("<$it/>", "<${CustomTagHandler.CUSTOM_TAG}$it/>", true)
                }
                return r
            }
        }

        private val mAttributeMap = mutableMapOf<String, String>()
        private val mMarkStartMap = mutableMapOf<String, Int>()

        override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {
            val rTag = fixTag(tag)
            if (rTag.isBlank() || output == null || xmlReader == null || !config.getTagArray().contains(rTag)) return
            if (opening) handleStart(rTag, output, xmlReader)
            else handleEnd(rTag, output)
        }

        private fun handleStart(tag: String, output: Editable, xmlReader: XMLReader) {
            mAttributeMap.clear()
            buildAttribute(xmlReader)
            val spanMask = SpanMark(tag, output.toString(), mAttributeMap.toMap())
            val len = output.length
            output.setSpan(spanMask, len, len, Spannable.SPAN_MARK_MARK)
            mMarkStartMap[tag] = len
        }

        private fun handleEnd(tag: String, output: Editable) {
            val len = output.length
            val mark = output.getSpans(mMarkStartMap[tag] ?: len, len, SpanMark::class.java).lastOrNull() ?: return
            val start = output.getSpanStart(mark)
            output.removeSpan(mark)
            val spanList = config.getTagHandler(tag)?.invoke(mark, imgHandler)
            mark.extra(output)
            val newLen = output.length
            spanList?.forEach {
                output.setSpan(it, start, newLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        private fun buildAttribute(xmlReader: XMLReader) {
            try {
                val elementField = xmlReader.javaClass.getDeclaredField("theNewElement")
                elementField.isAccessible = true
                val element = elementField.get(xmlReader)
                val attrsField = element.javaClass.getDeclaredField("theAtts")
                attrsField.isAccessible = true
                val attrs = attrsField.get(element)
                val dataField = attrs.javaClass.getDeclaredField("data")
                dataField.isAccessible = true
                val data = dataField.get(attrs) as Array<*>
                val lengthField = attrs.javaClass.getDeclaredField("length")
                lengthField.isAccessible = true
                val len = lengthField.get(attrs) as Int
                for (i in 0 until len) {
                    mAttributeMap[data[i * 5 + 1].toString()] = data[i * 5 + 4].toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    data class SpanMark(val tag: String, val value: String, val attrs: Map<String, String>) {
        var extra: (text: Editable) -> Unit = {}
    }

    @Suppress("unused")
    object HtmlImageDownloader : MutableMap<String, Drawable?> by mutableMapOf() {

        private val sTaskMap = mutableMapOf<String, () -> Unit>()
        private val threadPool = ThreadPoolExecutor(2, 10,
                5000, TimeUnit.MILLISECONDS,
                ArrayBlockingQueue<Runnable>(20),
                ThreadFactory {
                    val count = AtomicInteger(1)
                    return@ThreadFactory Thread(it, "ImageDownloadThread-${count.getAndIncrement()}")
                }).apply {
            allowCoreThreadTimeOut(true)
        }

        fun newTask(url: String, apply: () -> Unit) {
            sTaskMap[url] = {
                try {
                    val context = App.i.applicationContext
                    val bmp = Picasso.with(context).load(fixImagePath(url)).error(hErrorDrawable).get()
                    this[url] = BitmapDrawable(context.resources, bmp)
                    apply.invoke()
                } catch (e: Exception) {
                    this[url] = hErrorDrawable
                } finally {
                    if (sTaskMap.containsKey(url))
                        sTaskMap.remove(url)
                }
            }
            threadPool.execute {
                sTaskMap[url]?.invoke()
            }
        }

        fun removeTask(url: String) = threadPool.remove(sTaskMap[url])

        fun clearTask() = sTaskMap.values.forEach { threadPool.remove(it) }

        fun clearCache() = this.clear()
    }

    fun fixImagePath(url: String): String {
        return if (url.startsWith("http")) url
        else "${Const.URL_BASE}$url"
    }
}
