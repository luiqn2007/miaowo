package org.miaowo.miaowo.util

import okhttp3.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.lInfo
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.collections.List
import kotlin.collections.mutableMapOf

/**
 * 对 OKHttp 的二次封装
 * 用于网络信息的获取和处理
 * Created by luqin on 17-3-5.
 */

object HttpUtil {
    val client = OkHttpClient.Builder()
            .cookieJar(MyCookieJar)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()!!

    fun clearCookies(): HttpUtil {
        MyCookieJar.clear()
        return this
    }

    private object MyCookieJar : CookieJar {
        private val mCookies = mutableMapOf<String, List<Cookie>>()
        private var canWrite = true

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            if (canWrite && !API.isLogin) {
                mCookies[url.host()] = cookies
                canWrite = false
            }
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = mCookies[url.host()]
            return cookies ?: ArrayList()
        }

        fun clear() {
            mCookies.clear()
            canWrite = true
        }
    }

    class LinearCall {
        private val mRequests = mutableListOf<(lastResult: Any?) -> Request>()
        private val mCallbacks = mutableListOf<LinearCallback>()
        private var mLastResult: Any? = null
        private var mInterruptWhileError: Boolean = false
        private var mIndex = 0

        /**
         * 是否在依次调用错误时停止调用
         * 默认 false 一次调用失败后会传入调用失败结果 null
         */
        var interruptWhileError: Boolean
            get() = mInterruptWhileError
            set(value) {
                mInterruptWhileError = value
            }

        var defaultErrorHandler: ((call: Call?, e: Exception?) -> Unit)? = null

        fun next(callback: LinearCallback, requestBuilder: (lastResult: Any?) -> Request): LinearCall {
            mRequests.add(requestBuilder)
            if (callback.linearCall == null)
                callback.linearCall = this
            mCallbacks.add(callback)
            return this
        }

        fun call() {
            mRequests.firstOrNull()?.invoke(mLastResult)?.call(mCallbacks.first())
        }

        fun callNext(result: Any?) {
            mIndex++
            if (mIndex >= mRequests.size) return
            mLastResult = result
            mRequests[mIndex].invoke(mLastResult).call(mCallbacks[mIndex])
        }

        val lastResult = mLastResult
    }

    abstract class MyCallback : Callback {
        abstract fun onError(call: Call?, e: Exception?)
        abstract fun onResult(call: Call?, response: Response?)

        override fun onFailure(call: Call?, e: IOException?) {
            onError(call, e)
        }

        final override fun onResponse(call: Call?, response: Response?) {
            try {
                onResult(call, response)
            } catch (e: Exception) {
                onError(call, e)
            }
        }
    }

    abstract class LinearCallback : MyCallback() {
        var linearCall: LinearCall? = null

        override fun onError(call: Call?, e: Exception?) {
            linearCall?.defaultErrorHandler?.invoke(call, e)
        }

        open fun beforeResult(call: Call?) {}

        abstract fun onResult(call: Call?, response: Response?, lastResult: Any?): Any?

        final override fun onFailure(call: Call?, e: IOException?) {
            onError(call, e)
            if (linearCall != null && linearCall!!.interruptWhileError)
                linearCall!!.callNext(null)
        }

        final override fun onResult(call: Call?, response: Response?) {
            if (linearCall == null) throw Exception("Please set linearCall")
            val ret = onResult(call, response, linearCall!!.lastResult)
            linearCall!!.callNext(ret)
        }

        fun setToDefaultErrorHandler() {
            linearCall?.defaultErrorHandler = this::onError
        }
    }
}

fun Request.call(callback: Callback): Call {
    lInfo("call: ${url()}")
    val rCall = HttpUtil.client.newCall(this)
    (callback as? HttpUtil.LinearCallback)?.beforeResult(rCall)
    rCall.enqueue(callback)
    return rCall
}

fun Request.call(callback: (call: Call?, response: Response?) -> Unit) {
    call(object : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            Miao.i.runOnUiThread { Miao.i.handleError(e) }
        }

        override fun onResponse(call: Call?, response: Response?) {
            callback(call, response)
        }
    })
}

fun Request.callLinear(interruptWhileException: Boolean, callback: HttpUtil.LinearCallback): HttpUtil.LinearCall {
    val linearCall = HttpUtil.LinearCall()
    linearCall.interruptWhileError = interruptWhileException
    linearCall.next(callback) { this }
    callback.setToDefaultErrorHandler()
    return linearCall
}

