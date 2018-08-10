package org.miaowo.miaowo.other

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.res.ResourcesCompat
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.ImageActivity
import org.miaowo.miaowo.activity.PostActivity
import org.miaowo.miaowo.activity.UserActivity
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.util.FormatUtil

fun ImageView.setUserIcon(user: User?) {
    if (!user?.picture.isNullOrBlank()) {
        var rImgRes = user!!.picture
        if (!(rImgRes.startsWith("http") || rImgRes.startsWith("https")))
            rImgRes = "${Const.URL_BASE}$rImgRes"
        Picasso.with(context)
                .load(rImgRes)
                .error(R.drawable.ic_error)
                .transform(CircleTransformation()).fit()
                .into(this)
    } else if (!user?.iconText.isNullOrBlank()) {
        val colorStr = user!!.iconBgColor
        val color =
                if (colorStr.length != 7 && colorStr.length != 9) Color.BLACK
                else Color.parseColor(colorStr)
        setImageDrawable(TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .toUpperCase()
                .endConfig()
                .buildRound(user.iconText, color))
    } else Picasso.with(context).load(R.drawable.def_user).into(this)
}

fun ImageView.setIcon(icon: IIcon) {
    setImageDrawable(IconicsDrawable(context, icon).actionBar())
}

fun TextView.setHTML(html: String?) {
    val config = FormatUtil.HtmlFormatConfig()
            .registerContentReset { text = it }
            .addTagHandler("a") { mark, _ ->
                val clickSpan = object : ClickableSpan() {
                    override fun onClick(widget: View?) {
                        val url = mark.attrs["href"] ?: ""
                        if (url.startsWith("http:") && !url.startsWith(Const.URL_BASE))
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        else
                            openMiaoPage(context, Const.URL_BASE, url)
                    }
                }
                return@addTagHandler arrayOf(clickSpan)
            }
            .addTagHandler("img") { mark, handler ->
                val src = mark.attrs["src"] ?: ""
                var d: Drawable? = null

                if (handler != null) { d = handler.getDrawable(src) }
                if (d == null) {
                    d = ResourcesCompat.getDrawable(context.resources, R.drawable.unknown_image, null)
                    d!!.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
                }

                mark.extra = { it.append("\uFFFC") }

                val imgSpan = ImageSpan(d, src)
                val clickSpan = object : ClickableSpan() {
                    override fun onClick(widget: View?) {
                        Log.i("imageUrlClick", src)
                        context.startActivity(Intent(context, ImageActivity::class.java).putExtra(Const.NAME, FormatUtil.fixImagePath(src)))
                    }
                }
                return@addTagHandler arrayOf(imgSpan, clickSpan)
            }
    movementMethod = LinkMovementMethod.getInstance()
    text = FormatUtil.html(html, textSize, config).trimEnd { it == '\n' }
}

private fun openMiaoPage(context: Context, homeUrl: String, path: String) {
    var subPath = path
    if (path.startsWith(homeUrl))
        subPath = subPath.replace(homeUrl, "")
    while (subPath.startsWith("/"))
        subPath = subPath.substring(1)
    val miaoSub = subPath.split("/")
    when (miaoSub[0]) {
        "post" -> context.startActivity(Intent(context, PostActivity::class.java).putExtra(Const.ID, miaoSub[1].toInt()))
        "uid" -> context.startActivity(Intent(context, UserActivity::class.java)
                .putExtra(Const.TYPE, UserActivity.USER_FROM_ID)
                .putExtra(Const.ID, miaoSub[1].toInt()))
    }
}

