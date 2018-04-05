package org.miaowo.miaowo.base.extra

import android.util.Log
import com.sdsmdg.tastytoast.TastyToast

import okhttp3.Response
import org.miaowo.miaowo.Miao

/**
 * 封装的 Log 输出类
 * Created by luqin on 17-4-20.
 */

fun lInfo(vararg info: Any) = Log.i(createTag(), createMessage(false, *info))

fun lInfoStack(vararg info: Any) = Log.i(createTag(), createMessage(true, *info))
fun lError(exception: Throwable?) {
    if (exception == null) {
        Log.e(createTag(), "e: null")
        return
    }
    Log.e(createTag(), "Error-->\n", exception)
}
fun lError(msg: String) = Log.e(createTag(), "\n" + msg)

fun lTODO(msg: String) {
    Miao.i.toast("尚未实现: $msg", TastyToast.WARNING)
    Log.e("TODO: ", msg)
}

private fun createTag() = Thread.currentThread().stackTrace[5].methodName
private fun createMessage(printStack: Boolean, vararg objects: Any): String {
    val sb = StringBuilder()
    var sbStack: StringBuilder? = null
    for (`object` in objects) sb += "${toString(`object`)}\n"

    if (printStack) {
        sbStack = StringBuilder()
        for (i in Thread.currentThread().stackTrace) {
            sbStack += "at ${i.className}(${i.fileName}:${i.lineNumber})\n"
        }
    }

    return if (printStack) "栈 -->\n$sbStack\n<--\n信息 ->\n$sb\n<--" else sb.toString()
}
private fun toString(`object`: Any?) = when(`object`) {
    null -> "null"
    is Response ->
        `object`.body()!!.string()
    is String -> `object`
    else -> `object`.toString()
}