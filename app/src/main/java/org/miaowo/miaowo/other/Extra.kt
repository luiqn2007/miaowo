package org.miaowo.miaowo.other

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.support.v4.content.res.ResourcesCompat
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.squareup.picasso.Picasso
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.base.extra.loadSelf
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.ImageFragment
import org.miaowo.miaowo.fragment.PostFragment
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.util.FormatUtil
import kotlin.math.min

private val mErrorDrawable by lazy {
    val eIcon = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_error, null)
    val sSize = FormatUtil.screenSize
    val iSize = min(sSize.x, sSize.y) / 4
    eIcon?.setBounds(0, 0, iSize, iSize)
    eIcon
}

private val mPlaceDrawable by lazy {
    val pIcon = ResourcesCompat.getDrawable(App.i.resources, R.drawable.ic_loading, null)
    val sSize = FormatUtil.screenSize
    val iSize = min(sSize.x, sSize.y) / 4
    pIcon?.setBounds(0, 0, iSize, iSize)
    pIcon
}

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

fun TextView.setHTML(html: String?, imageVisibility: Boolean = true) {
    // 不显示图片
    if (!imageVisibility) {
        text = Html.fromHtml(html ?: "")
        return
    }

    // 空内容: 隐藏视图
    if (html?.replace("\n", "")
                    ?.replace("</p>", "")
                    ?.replace("<p>", "")
                    ?.replace(" ", "").isNullOrBlank()) {
        visibility = View.GONE
        return
    }

    visibility = View.VISIBLE
    val rHtml = repair(html)
    RichText.from(rHtml)
//                .autoFix(false)
//                .fix(MyImageFixCallback())
            .singleLoad(false)
            .scaleType(ImageHolder.ScaleType.center_inside)
            .errorImage { _, _, _ -> mErrorDrawable }
            .placeHolder { _, _, _ -> mPlaceDrawable }
            .urlClick {
                val homeUrl = App.i.getString(R.string.url_home_head, "")
                val uri = Uri.parse(it)
                lInfo(uri ?: "null")
                if (it.contains("//")) {
                    // Uri
                    if (it.startsWith(homeUrl)) openMiaoPage(homeUrl, it)
                    else openUri(it)
                } else openMiaoPage(homeUrl, it)
                true
            }
            .imageClick { imageUrls, position ->
                lInfo(imageUrls, position)
                ImageFragment.getInstance(imageUrls[position])
            }
            .into(this)
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

private fun repair(html: String?): String {
    if (html.isNullOrBlank()) return ""
    // 去除无用换行
    if (!html!!.endsWith("\n")) return html
    var ret = html.substring(0, html.length - 2)
    while (ret.endsWith("\n"))
        ret = html.substring(0, html.length - 2)
    // 修复图片地址
    ret = ret.replace("<img src=\"/", "<img src=\"${App.i.getString(R.string.url_home_head, "/")}")

    return ret
}

