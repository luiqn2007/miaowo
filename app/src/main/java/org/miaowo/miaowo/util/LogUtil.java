package org.miaowo.miaowo.util;

import android.util.Log;

/**
 * 调试工具类
 * Created by luqin on 17-1-24.
 */

public class LogUtil {
    private static final String TAG = "MiaoLogs";
    
    public static void e(String tag, String errMsg, Exception e) {
        Log.e(TAG, tag + " : " + errMsg, e);
    }
    
    public static void e(String tag, String errMsg) {
        Log.e(TAG, tag + " : " + errMsg);
    }

    public static void i(String tag, Object msg) {
        Log.i(TAG, tag + " : " + msg);
    }

    public static void i(String tag, Object[] msgs) {
        StringBuilder sb = new StringBuilder(tag + " :\n");
        for (Object msg : msgs) {
            sb.append("\n\t");
            sb.append(msg);
        }
        Log.i(TAG, sb.toString());
    }
}
