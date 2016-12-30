package org.miaowo.miaowo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.miaowo.miaowo.C;

/**
 * 用于获取/修改配置信息
 * Created by lq2007 on 16-11-26.
 */

public class SpUtil {
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = loadSp(context);
        sp.edit().putBoolean(key, value).commit();
    }
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = loadSp(context);
        return sp.getBoolean(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = loadSp(context);
        sp.edit().putInt(key, value).commit();
    }
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = loadSp(context);
        return sp.getInt(key, defValue);
    }

    /* ======================================================================== */

    // 用于初始化 SharedPreferences
    private static SharedPreferences loadSp(Context context) {
        return context.getSharedPreferences(C.SpName, Context.MODE_PRIVATE);
    }
}
