package org.miaowo.miaowo.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.miaowo.miaowo.C;

/**
 * 用于获取/修改配置信息
 * Created by lq2007 on 16-11-26.
 */

public class SpUtil {

    /**
     * 向配置文件传入 Boolean 参数
     * @param context 上下文
     * @param key 参数键名
     * @param value 参数值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        loadSp(context).edit().putBoolean(key, value).apply();
    }

    /**
     * 从配置文件读取 Boolean 参数
     * @param context 上下文
     * @param key 参数键名
     * @param defValue 当找不到时默认返回的值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = loadSp(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 向配置文件传入 整形 参数
     * @param context 上下文
     * @param key 参数键名
     * @param value 参数值
     */
    public static void putInt(Context context, String key, int value) {
        loadSp(context).edit().putInt(key, value).apply();
    }

    /**
     * 从配置文件读取 整形 参数
     * @param context 上下文
     * @param key 参数键名
     * @param defValue 当找不到时默认返回的值
     */
    public static int getInt(Context context, String key, int defValue) {
        return loadSp(context).getInt(key, defValue);
    }

    /* ======================================================================== */

    // 用于初始化 SharedPreferences
    private static SharedPreferences loadSp(Context context) {
        return context.getSharedPreferences(C.SpName, Context.MODE_PRIVATE);
    }
}
