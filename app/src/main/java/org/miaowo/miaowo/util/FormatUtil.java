package org.miaowo.miaowo.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * 格式化工具类
 * Created by luqin on 17-1-23.
 */

public class FormatUtil {
    private FormatUtil() {}
    public static FormatUtil format() { return new FormatUtil(); }

    public String time(long time) {
        time = System.currentTimeMillis() - time;
        long second = time / 1000;
        if (second >= 60 * 60 * 24 * 365) {
            int n = (int) (second / (60 * 60 * 24 * 365));
            return "至少" + n + "年";
        } else if (second >= 60 * 60 * 24 * 30) {
            int n = (int) (second / (60 * 60 * 24 * 30));
            return "至少" + n + "个月";
        } else if (second >= 60 * 60 * 24) {
            int n = (int) (second / (60 * 60 * 24));
            return n + "天";
        } else if (second >= 60 * 60) {
            int n = (int) (second / (60 * 60));
            return n + "小时";
        } else if (second >= 60) {
            return (second / 60) + "分钟";
        } else if (second >= 3) {
            return second + "秒";
        } else {
            return "片刻";
        }
    }

    public Spanned parseHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        else return Html.fromHtml(html);
    }

    public String toHtml(Spanned text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.toHtml(text, Html.FROM_HTML_OPTION_USE_CSS_COLORS);
        else return Html.toHtml(text);
    }
}
