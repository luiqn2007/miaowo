package org.miaowo.miaowo.util

import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager
import android.widget.TextView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import java.text.DateFormat

/**
 * 格式化工具类
 * Created by luqin on 17-1-23.
 */

object FormatUtil {

    val screenSize: Point
        get() {
            val wm = App.i.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val size = Point()
            wm.defaultDisplay.getSize(size)
            return size
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
}
