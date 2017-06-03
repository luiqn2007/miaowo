package org.miaowo.miaowo.util

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.WindowManager
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 格式化工具类
 * Created by luqin on 17-1-23.
 */

object FormatUtil {

    private var mScreenSize: Point? = null
    private var mDateFormater: DateFormat? = null

    fun time(time: Long): String {
        val second = (System.currentTimeMillis() - time) / 1000
        if (second >= 60 * 60 * 24 * 365) {
            return "至少 ${(second / (60 * 60 * 24 * 365)).toInt()} 年"
        } else if (second >= 60 * 60 * 24 * 30) {
            return "至少 ${(second / (60 * 60 * 24 * 30)).toInt()} 个月"
        } else if (second >= 60 * 60 * 24) {
            return "${(second / (60 * 60 * 24)).toInt()} 天"
        } else if (second >= 60 * 60) {
            return "${(second / (60 * 60)).toInt()} 小时"
        } else if (second >= 60) {
            return "${(second / 60)} 分钟"
        } else if (second >= 3) {
            return "$second 秒"
        } else {
            return "片刻"
        }
    }

    fun date(time: Long): String {
        val d = Date(time)
        if (mDateFormater == null)
            mDateFormater = SimpleDateFormat.getDateInstance()
        return mDateFormater!!.format(d)
    }

    fun fillCount(tv: TextView, count: Int) {
        val n = tv.text.length
        if (n == 3) {
            if (count <= 99) {
                tv.text = count.toString()
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.textSize * 3 / 2)
            }
        } else {
            if (count > 99) {
                tv.setText(R.string.more99)
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.textSize / 3 * 2)
            } else {
                tv.text = count.toString()
            }
        }
    }

    fun parseHtml(html: String?) = Html.fromHtml(html ?: "")

    fun parseHtml(html: String?, apply: (spanned: Spanned) -> Unit) {
        val imgs = mutableListOf<String>()
        val htmlRet = Html.fromHtml(html ?: "", { source ->
            imgs.add(source)
            val drawable = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_loading, null)
            val size = screenSize()
            val min = Math.min(size.x, size.y) / 2
            val w = if (drawable?.intrinsicWidth ?: 0 <= 0) min else drawable!!.intrinsicWidth
            val h = if (drawable?.intrinsicHeight ?: 0 <= 0) min else drawable!!.intrinsicHeight
            drawable?.setBounds(0, 0, w, h)
            LogUtil.i("drawable: ${drawable?.bounds}")
            drawable
        }) { _, _, _, _ -> }
        if (!imgs.isEmpty()) Thread { loadImgs(html ?: "", imgs, apply) }.start()
        apply(htmlRet)
    }
    private fun loadImgs(html: String, imgs: List<String>, apply: (spanned: Spanned) -> Unit) {
        val imgRets = mutableMapOf<String, Drawable?>()
        for (img in imgs) {
            try {
                val picImg = if (img.toLowerCase().startsWith("http")) img
                else App.i.getString(R.string.url_home, img)
                LogUtil.i("loadImgs: $picImg")
                imgRets.put(img.toLowerCase(),
                        BitmapDrawable(App.i.resources, Picasso.with(BaseActivity.get).load(picImg).error(R.drawable.ic_error).get()))

            } catch (e: IOException) {
                imgRets.put(img.toLowerCase(),
                        ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_error, null))
                e.printStackTrace()
            } finally {
                BaseActivity.get!!.runOnUiThreadIgnoreError {
                    apply(Html.fromHtml(html, {
                        val drawable = imgRets[it.toLowerCase()]
                        val size = screenSize()
                        val min = Math.min(size.x, size.y)
                        size.set(min / 2, min / 2)
                        val w = if (drawable?.intrinsicWidth ?: 0 <= 0) min else drawable!!.intrinsicWidth
                        val h = if (drawable?.intrinsicHeight ?: 0 <= 0) min else drawable!!.intrinsicHeight
                        drawable?.setBounds(0, 0, w, h)
                        LogUtil.i("drawable-set: ${drawable?.bounds}")
                        drawable
                    }) { _, _, _, _ -> })
                }
            }
        }
    }

    private fun screenSize(): Point {
        if (mScreenSize != null) return mScreenSize!!
        else {
            val wm = App.i.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            mScreenSize = Point()
            wm.defaultDisplay.getSize(mScreenSize)
            return mScreenSize!!
        }
    }
}
