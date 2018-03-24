package org.miaowo.miaowo.base.extra

/**
 * 对所有类通用
 * Created by lq200 on 2018/3/25.
 */
fun Any.calcHashCode(vararg args: Any?): Int {
    var hashCode = 0
    args.forEach {
        hashCode += when (it) {
            null -> 0
            is Float -> it.toBits()
            is Double -> {
                val bits = it.toBits()
                (bits xor (bits ushr 32)).toInt()
            }
            is Number -> it.toInt()
            is Char -> it.toInt()
            else -> it.hashCode()
        }
    }
    return hashCode
}