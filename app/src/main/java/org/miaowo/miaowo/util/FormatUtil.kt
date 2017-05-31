package org.miaowo.miaowo.util

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.IdRes
import android.support.v4.content.res.ResourcesCompat
import android.text.Html
import android.text.Layout
import android.text.Spanned
import android.text.style.AlignmentSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import java.io.IOException
import java.util.*

/**
 * 格式化工具类
 * Created by luqin on 17-1-23.
 */

object FormatUtil {

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

    fun fillCount(v: View, @IdRes viewId: Int, count: Int) {
        val tv = v.findViewById(viewId) as TextView
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

    fun parseHtml(html: String?, apply: (spanned: Spanned) -> Unit) {
        val imgs = ArrayList<String>()
        val htmlRet = Html.fromHtml(html ?: "", { source ->
            imgs.add(source)
            ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_loading, null)
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
//                BaseActivity.get!!.runOnUiThreadIgnoreError {
//                    apply(Html.fromHtml(
//                            html,
//                            { imgRets[it.toLowerCase()] },
//                            { _, _, output, _ ->
//                                output.setSpan(
//                                        { Layout.Alignment.ALIGN_NORMAL } as AlignmentSpan,
//                                        0,
//                                        output.length,
//                                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE) }))
//                }
            }
        }
    }
}
