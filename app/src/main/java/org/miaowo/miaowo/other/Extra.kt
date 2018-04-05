package org.miaowo.miaowo.other

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.base.extra.loadSelf
import org.miaowo.miaowo.base.extra.showSelf
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.ImageFragment
import org.miaowo.miaowo.fragment.PostFragment
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.Html
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min

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

fun ImageView.setUserIcon(user: User?) {
    if (user == null) Picasso.with(context).load(R.drawable.def_user).into(this)
    else if (!TextUtils.isEmpty(user.picture)) {
        var rImgRes = user.picture
        if (!(rImgRes.startsWith("http") || rImgRes.startsWith("https")))
            rImgRes = context.getString(R.string.url_home_head, rImgRes)
        Picasso.with(context)
                .load(rImgRes)
                .error(R.drawable.ic_error)
                .transform(CircleTransformation()).fit()
                .into(this)
    } else if (!user.iconText.isEmpty()) {
        val colorStr = user.iconBgColor
        val color =
                if (colorStr.length != 7 && colorStr.length != 9) Color.BLACK
                else Color.parseColor(colorStr)

        val text = user.iconText
        val drawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .toUpperCase()
                .endConfig()
                .buildRound(text, color)
        setImageDrawable(drawable)
    } else Picasso.with(context).load(R.drawable.def_user).into(this)
}

fun ImageView.setUserBackground(coverUrl: String?) {
    if (coverUrl.isNullOrBlank()) Picasso.with(context).load(R.drawable.def_user).fit().into(this)
    else Picasso.with(context).load(context.getString(R.string.url_home_head, coverUrl)).error(R.drawable.def_user).fit().into(this)
}

fun ImageView.setIcon(icon: IIcon) {
    setImageDrawable(IconicsDrawable(context, icon).actionBar())
}

fun TextView.setHTML(html: String?, imageVisibility: Boolean = true, hideFragment: Fragment? = null) {
    if (html.isNullOrBlank()) return
    // 不显示图片
    if (!imageVisibility) {
        text = Html.fromHtml(html ?: "", Html.FROM_HTML_MODE_LEGACY, context.applicationContext)
        return
    }

    val tagHandler = Html.TagHandler { _, tag, output, _ ->
        when (tag.toLowerCase()) {
            "img" -> {
                val len = output.length
                val span = output.getSpans(len - 1, len, ImageSpan::class.java)
                val url = span.firstOrNull()?.source
                if (url != null) {
                    output.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View?) {
                            val fg = ImageFragment.getInstance(fixImagePath(url))
                            if (hideFragment == null) fg.loadSelf(Miao.i)
                            else fg.showSelf(Miao.i, hideFragment)
                        }
                    }, len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
    }
    val imgGetterExist = Html.ImageGetter {
        val d = HtmlImageDownloader[fixImagePath(it)]
        if (d == null) hErrorDrawable
        else {
            val sz = FormatUtil.screenSize
            val height = d.intrinsicHeight
            val width = d.intrinsicWidth
            val k = 3f / 5f
            if (height != 0 && width != 0) {
                val rK = max(width / (sz.x * k), height / (sz.y * k))
                if (rK <= 1) d.setBounds(0, 0, width, height)
                else d.setBounds(0, 0, (width / rK).toInt(), (height / rK).toInt())
            } else {
                d.setBounds(0, 0, (sz.x * k).toInt(), (sz.y * k).toInt())
            }
            d
        }
    }
    val imgGetter = Html.ImageGetter {
        lInfo("newTask: ${fixImagePath(it)}")
        HtmlImageDownloader.newTask(fixImagePath(it)) {
            Miao.i.runOnUiThread {
                this.text = Html.fromHtml(html
                        ?: "", Html.FROM_HTML_MODE_LEGACY, imgGetterExist, tagHandler, context.applicationContext)
            }
        }
        hPlaceDrawable
    }
    this.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, imgGetter, tagHandler, context.applicationContext)
}

private fun openMiaoPage(homeUrl: String, path: String) {
    lInfo(path)
    var subPath = path
    if (path.startsWith(homeUrl))
        subPath = subPath.replace(homeUrl, "")
    while (subPath.startsWith("/"))
        subPath = subPath.substring(1)
    val miaoSub = subPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    when (miaoSub[0]) {
        "post" -> PostFragment.newInstance(miaoSub[1].toInt()).loadSelf(Miao.i)
        "uid" -> UserFragment.newInstance(miaoSub[1].toInt()).loadSelf(Miao.i)
        else -> lInfo(miaoSub)
    }
}

private fun openUri(url: String) {
    val uri = Uri.parse(url)
    val i = Intent(Intent.ACTION_VIEW, uri)
    Miao.i.startActivity(i)
}

private fun fixImagePath(url: String): String {
    return if (url.startsWith("http")) url
    else App.i.applicationContext.getString(R.string.url_home_head, url)
}

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
                val bmp = Picasso.with(context).load(url).error(hErrorDrawable).get()
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

