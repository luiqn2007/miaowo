package org.miaowo.miaowo.util

import android.content.Context
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.other.Const

/**
 * 用于获取/修改配置信息
 * Created by lq2007 on 16-11-26.
 */

object SpUtil {
    private val sp = App.i.getSharedPreferences(Const.SP_DEFAULT, Context.MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean) = sp.edit().putBoolean(key, value).apply()
    fun getBoolean(key: String, defValue: Boolean) = sp.getBoolean(key, defValue)
    fun putInt(key: String, value: Int) = sp.edit().putInt(key, value).apply()
    fun getInt(key: String, defValue: Int) = sp.getInt(key, defValue)
    fun putString(key: String, value: String) = sp.edit().putString(key, value).apply()
    fun getString(key: String, defValue: String) = sp.getString(key, defValue) ?: defValue
}
