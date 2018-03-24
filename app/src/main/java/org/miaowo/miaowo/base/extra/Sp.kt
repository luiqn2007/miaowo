package org.miaowo.miaowo.base.extra

import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.other.Const
import android.content.Context

/**
 * 用于获取/修改配置信息
 * Created by lq2007 on 16-11-26.
 */
val sp get() = App.i.getSharedPreferences(Const.SP_DEFAULT, Context.MODE_PRIVATE)!!

fun spGet(key: String, defValue: Boolean) = sp.getBoolean(key, defValue)
fun spGet(key: String, defValue: String) = sp.getString(key, defValue) ?: defValue
fun spGet(key: String, defValue: Int) = sp.getInt(key, defValue)
fun spPut(key: String, value: Boolean) = sp.edit().putBoolean(key, value).apply()
fun spPut(key: String, value: String) = sp.edit().putString(key, value).apply()
fun spPut(key: String, value: Int) = sp.edit().putInt(key, value).apply()
