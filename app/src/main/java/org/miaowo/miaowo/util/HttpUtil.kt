package org.miaowo.miaowo.util

import okhttp3.*
import org.miaowo.miaowo.base.BaseActivity
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 对 OKHttp 的二次封装
 * 用于网络信息的获取和处理
 * Created by luqin on 17-3-5.
 */

object HttpUtil {
    private val client = OkHttpClient.Builder()
            .cookieJar(MyCookieJar)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

    fun clearCookies(): HttpUtil {
        MyCookieJar.clear()
        return this
    }

    @JvmOverloads fun post(request: Request, error: (call: Call, e: IOException) -> Unit = { _, e -> BaseActivity.get?.handleError(e) }, callback: (call: Call, response: Response) -> Unit): Call {
        val call = client.newCall(request)
        LogUtil.i("url: ${request.url()}")
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                BaseActivity.get?.runOnUiThreadIgnoreError { error(call, e) }
            }
            override fun onResponse(call: Call, response: Response) {
                BaseActivity.get?.runOnUiThreadIgnoreError { callback(call, response) }
            }
        })
        return call
    }

    private object MyCookieJar : CookieJar {
        private val mCookies = mutableMapOf<String, List<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            mCookies.put(url.host(), cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = mCookies[url.host()]
            return cookies ?: ArrayList<Cookie>()
        }

        fun clear() = mCookies.clear()
    }
}
