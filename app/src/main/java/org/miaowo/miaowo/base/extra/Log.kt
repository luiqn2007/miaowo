package org.miaowo.miaowo.base.extra

import android.util.Log
import com.sdsmdg.tastytoast.TastyToast

import okhttp3.Response

/**
 * 封装的 Log 输出类
 * Created by luqin on 17-4-20.
 */

fun lInfo(vararg info: Any, printStack: Boolean = false) = Log.i(createTag(), createMessage(printStack, *info))
fun lError(exception: Throwable?) {
    if (exception == null) {
        Log.e(createTag(), "e: null")
        return
    }
    Log.e(createTag(), "Error-->\n", exception)
}
fun lError(msg: String) = Log.e(createTag(), "\n" + msg)

fun lTODO(msg: String) {
    activity?.toast("尚未实现", TastyToast.WARNING)
    Log.e("TODO: ", msg)
}

private fun createTag() = Thread.currentThread().stackTrace[3].methodName
private fun createMessage(printStack: Boolean, vararg objects: Any): String {
    val sb = StringBuilder()
    if (printStack) {
        sb.append("栈 -->\n")
        Thread.currentThread().stackTrace
                .filter { it.className.startsWith("org.miaowo.miaowo") && it.className != "org.miaowo.miaowo.util.LogUtil" }
                .forEach { sb.append("at ${it.className}(${it.fileName}:${it.lineNumber})\n") }
        sb.append("<--\n")
    }
    if (printStack) sb.append("信息 -->\n")
    for (`object` in objects) sb.append(toString(`object`)).append('\n')
    if (printStack) sb.append("<--")
    return sb.toString()
}
private fun toString(`object`: Any?) = when(`object`) {
    null -> "null"
    is Response ->
        `object`.body()!!.string()
    else -> `object`.toString()
}