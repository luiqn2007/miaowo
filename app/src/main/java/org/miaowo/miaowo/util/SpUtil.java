package org.miaowo.miaowo.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Set;

/**
 * 用于获取/修改配置信息
 * Created by lq2007 on 16-11-26.
 */

public class SpUtil {
    private SharedPreferences sp;

    private SpUtil(Context context, String spName) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }
    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    /* ======================================================================== */

    // 用于初始化 SharedPreferences
    public static SpUtil defaultSp(Context context) {
        return new SpUtil(context, "miaowo");
    }
}
