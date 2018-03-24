package org.miaowo.miaowo.base.extra

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.text.Html
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.R
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.util.FormatUtil
import java.io.IOException

/**
 * 文本编辑器
 * Created by lq2007 on 2017/8/19 0019.
 */
@Suppress("DEPRECATION")
fun TextView.setHtml(html: String?) {
    text = Html.fromHtml(html ?: "")!!
}

@Suppress("DEPRECATION")
fun TextView.setHtmlWithImage(html: String?) {
    val imgs = mutableListOf<String>()
    val size = FormatUtil.screenSize
    val min = Math.min(size.x, size.y)
    // 初次解析：图片将显示为方框
    val htmlRet = Html.fromHtml(html ?: "", { source ->
        imgs.add(source)
        val drawable = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_loading, null)
        val w = if (drawable?.intrinsicWidth ?: 0 <= 0) min else Math.min(min, drawable!!.intrinsicWidth)
        val h = if (drawable?.intrinsicHeight ?: 0 <= 0) min else Math.min(min, drawable!!.intrinsicWidth)
        drawable?.setBounds(0, 0, w, h)
        drawable
    }) { _, _, _, _ -> }
    text = htmlRet
    // 二次解析：下载图片并设置
    if (!imgs.isEmpty()) Thread {
        val imgRets = mutableMapOf<String, Drawable?>()
        for (img in imgs) {
            try {
                val picImg = if (img.toLowerCase().startsWith("http")) img
                else App.i.getString(R.string.url_home_head, img)
                imgRets[img.toLowerCase()] = BitmapDrawable(App.i.resources, Picasso.with(App.i).load(picImg).error(R.drawable.ic_error).get())
            } catch (e: IOException) {
                imgRets[img.toLowerCase()] = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_error, null)
                e.printStackTrace()
            } finally {
                Miao.i.runOnUiThread {
                    text = Html.fromHtml(html, {
                        val drawable = imgRets[it.toLowerCase()]
                        val w = if (drawable?.intrinsicWidth ?: 0 <= 0) min else drawable!!.intrinsicWidth
                        val h = if (drawable?.intrinsicHeight ?: 0 <= 0) min else drawable!!.intrinsicHeight
                        drawable?.setBounds(0, 0, w, h)
                        drawable
                    }) { _, _, _, _ -> }
                }
            }
        }
    }.start()
}