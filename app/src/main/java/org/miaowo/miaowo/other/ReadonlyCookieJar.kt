package org.miaowo.miaowo.other

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import org.miaowo.miaowo.API

object ReadonlyCookieJar : CookieJar {
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