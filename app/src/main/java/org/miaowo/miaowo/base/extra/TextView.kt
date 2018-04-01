package org.miaowo.miaowo.base.extra

import android.widget.TextView
import org.miaowo.miaowo.other.ViewBindHelper

/**
 * 文本编辑器
 * Created by lq2007 on 2017/8/19 0019.
 */
@Suppress("DEPRECATION")
fun TextView.setHtml(html: String?) {
    ViewBindHelper.setTitle(this, html)
}

@Suppress("DEPRECATION")
fun TextView.setHtmlWithImage(html: String?) {
    ViewBindHelper.setHTML(this, html)
}