package org.miaowo.miaowo.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.squareup.picasso.Picasso
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Detail
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.other.CircleTransformation

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

object ImageUtil {
    data class TextIconConfig(val bgColor: Int, val textColor: Int)

    /**
     * 填充用户头像框
     * 附带设置点击查看详情
     * @param iv 头像 ImageView
     * @param user 填充用户
     */
    fun setUser(iv: ImageView, user: User?, clickable: Boolean) {
        if (user != null) {
            fillUserImage(iv, user)
            if (clickable) {
                if (user.uid <= 0) iv.setOnClickListener { BaseActivity.get?.handleError(R.string.err_not_login) }
                else iv.setOnClickListener { Detail.showUser(user.username) }
            }
        }
    }

    private fun fillUserImage(iv: ImageView, user: User) {
        if (user.picture.isEmpty() && !user.iconText.isEmpty())
            iv.setImageDrawable(textIcon(user.iconText,
                    TextIconConfig(colorFromUser(user.iconBgColor), Color.WHITE)))
        else fill(iv, App.i.getString(R.string.url_home, user.picture), null)
    }

    /**
     * 填充图片框
     * @param iv 图片框
     * @param imgRes 默认，地址，或转换为图片的文字
     */
    fun fill(iv: ImageView, imgRes: String, config: TextIconConfig?) {
        if (config == null) {
            val creator = when (imgRes) {
                "default" -> Picasso.with(App.i).load(R.drawable.def_user)
                else -> Picasso.with(App.i).load(imgRes)
            }
            BaseActivity.get?.runOnUiThreadIgnoreError {
                creator.transform(CircleTransformation()).fit().into(iv)
            }
        } else {
            BaseActivity.get?.runOnUiThreadIgnoreError {
                iv.setImageDrawable(textIcon(imgRes, config))
            }
        }
    }

    private fun textIcon(text: String, config: TextIconConfig?): Drawable? =
            if (!text.isEmpty() && config != null) {
                TextDrawable.builder()
                        .beginConfig()
                        .textColor(config.textColor)
                        .toUpperCase()
                        .endConfig()
                        .buildRound(text, config.bgColor)
            } else null

    private fun colorFromUser(color: String) =
            if (TextUtils.isEmpty(color) || color.length < 6) -1
            else Color.rgb(Integer.parseInt(color.substring(color.length - 6, color.length - 4), 16),
                    Integer.parseInt(color.substring(color.length - 4, color.length - 2), 16),
                    Integer.parseInt(color.substring(color.length - 2, color.length), 16))
}
