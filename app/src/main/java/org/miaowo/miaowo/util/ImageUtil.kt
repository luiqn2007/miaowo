package org.miaowo.miaowo.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import com.amulyakhare.textdrawable.TextDrawable
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.bean.config.TextIconConfig

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

object ImageUtil {

    fun textIcon(config: TextIconConfig?, text: String? = null): Drawable? =
            if (!text.isNullOrBlank() && config != null) {
                TextDrawable.builder()
                        .beginConfig()
                        .textColor(config.textColor)
                        .toUpperCase()
                        .endConfig()
                        .buildRound(text, config.bgColor)
            } else if (config?.icon != null) {
                TextDrawable.builder()
                        .beginConfig()
                        .textColor(config.textColor)
                        .useFont(config.icon.typeface.getTypeface(App.i))
                        .toUpperCase()
                        .endConfig()
                        .buildRound(config.icon.character.toString(), config.bgColor)
            } else null

    fun colorFromUser(color: String) =
            if (TextUtils.isEmpty(color) || color.length < 6) -1
            else Color.rgb(Integer.parseInt(color.substring(color.length - 6, color.length - 4), 16),
                    Integer.parseInt(color.substring(color.length - 4, color.length - 2), 16),
                    Integer.parseInt(color.substring(color.length - 2, color.length), 16))
}
