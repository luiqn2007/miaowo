package org.miaowo.miaowo.base.extra

import android.content.Context
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.other.Const

/**
 * 用于获取/修改配置信息
 * Created by lq2007 on 16-11-26.
 */
val sp get() = org.miaowo.miaowo.base.App.Companion.i.getSharedPreferences(org.miaowo.miaowo.other.Const.SP_DEFAULT, android.content.Context.MODE_PRIVATE)!!
fun spGet(key: String, defValue: Boolean) = org.miaowo.miaowo.base.extra.sp.getBoolean(key, defValue)
fun spGet(key: String, defValue: String) = org.miaowo.miaowo.base.extra.sp.getString(key, defValue) ?: defValue
fun spGet(key: String, defValue: Int) = org.miaowo.miaowo.base.extra.sp.getInt(key, defValue)
fun spPut(key: String, value: Boolean) = org.miaowo.miaowo.base.extra.sp.edit().putBoolean(key, value).apply()
fun spPut(key: String, value: String) = org.miaowo.miaowo.base.extra.sp.edit().putString(key, value).apply()
fun spPut(key: String, value: Int) = org.miaowo.miaowo.base.extra.sp.edit().putInt(key, value).apply()
