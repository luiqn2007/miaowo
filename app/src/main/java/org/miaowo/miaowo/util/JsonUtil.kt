package org.miaowo.miaowo.util

import org.json.JSONObject

/**
 * 生成 JavaBean 类
 * Created by luqin on 17-3-10.
 */

object JsonUtil {
    private val JSON_PAIR = arrayOf(Pair("JSON.parse('", "\')"),
            Pair("<script id=\"ajaxify-data\" type=\"application/json\">", "</script>"))

    fun getJson(response: String?, onErr: (err: Exception) -> MutableList<String>? = { it.printStackTrace();null }) =
            try {
                val json = mutableListOf<String>()
                response?.lines()?.forEach {
                    val line: String = it
                    JSON_PAIR.forEach {
                        val s = line.indexOf(it.first)
                        val e = line.lastIndexOf(it.second)
                        if (s >= 0 && e >= 0) json.add(line.substring(s + it.first.length, e).trim())
                    }
                };json
            } catch (e: Exception) {
                onErr(e)
            }

    fun getCsrf(response: String?) =
            getJson(response)
                    ?.map { JSONObject(it) }
                    ?.firstOrNull { it.has("csrf_token") }
                    ?.getString("csrf_token") ?: ""

    fun getToken(response: String?, onErr: (err: Exception) -> String? = { it.printStackTrace();null }) =
            try {
                val apiRet = response?: ""
                val jsonObject = JSONObject(apiRet)
                if ("ok" == jsonObject.getString("code").toLowerCase())
                    jsonObject.getJSONObject("payload").getString("token")
                else throw Exception(jsonObject.getString("message"))
            } catch (e: Exception) {
                onErr(e)
            }
}
