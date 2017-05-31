package org.miaowo.miaowo.util

import com.google.gson.GsonBuilder
import okhttp3.Response
import org.json.JSONObject
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.bean.config.VersionMessage
import org.miaowo.miaowo.other.Const

/**
 * 生成 JavaBean 类
 * Created by luqin on 17-3-10.
 */

object JsonUtil {
    private val gson = GsonBuilder()
            .serializeNulls()
            .create()

    private fun getJson(response: Response, onErr: (err: Exception) -> MutableList<String>? = { Const.defErr<MutableList<String>>(it) }) =
            Const.tryGet(onErr) {
                val json = mutableListOf<String>()
                response.body()?.string()?.lines()?.forEach {
                    val line: String = it
                    Const.JSON_PAIR.forEach {
                        val s = line.indexOf(it.first)
                        val e = line.lastIndexOf(it.second)
                        if (s >= 0 && e >= 0) {
                            json.add(line.substring(s + it.first.length, e).trim())
                        }
                    }
                }
                json
            }

    fun getCsrf(response: Response, onErr: (err: Exception) -> String? = { Const.defErr<String>(it) }) =
            Const.tryGet(onErr) {
                getJson(response)?.map { JSONObject(it) }
                        ?.firstOrNull { it.has("csrf_token") }?.getString("csrf_token") ?: ""
            }

    fun getToken(response: Response, onErr: (err: Exception) -> String? = { Const.defErr<String>(it) }) =
            Const.tryGet(onErr) {
                val apiRet = response.body()?.string() ?: ""
                val jsonObject = JSONObject(apiRet)
                if ("ok" == jsonObject.getString("code").toLowerCase())
                    jsonObject.getJSONObject("payload").getString("token")
                else ""
            }
}
