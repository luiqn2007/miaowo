@file:Suppress("unused")

package org.miaowo.miaowo.base.extra

import kotlin.text.StringBuilder

/**
 * StringBuilder 相关
 * Created by lq200 on 2018/2/17.
 */
@Suppress("unused")
operator fun StringBuilder.plusAssign(a: Any) {
    append(a)!!
}

infix fun StringBuilder.append(a: Any): StringBuilder {
    append(a)
    return this
}

operator fun String.times(i: Int): StringBuilder {
    val sb: StringBuilder
    when {
        i > 0 -> {
            sb = StringBuilder(this)
            for (t in 0 until i) sb += this
        }
        i < 0 -> {
            sb = StringBuilder("-")
            sb += (this * (-i))
        }
        else -> sb = StringBuilder()
    }
    return sb
}

operator fun String.minus(i: Int): String {
    if (i > length) return ""
    return substring(0..length - i)
}

operator fun String.minus(c: String): String {
    if (c.isEmpty()) return this
    val i = lastIndexOf(c)
    if (i < 0) return this
    val count = length - i
    if (c.length < count)
        replaceRange(i, length, c)
    return replaceRange(i, i + c.length, c)
}