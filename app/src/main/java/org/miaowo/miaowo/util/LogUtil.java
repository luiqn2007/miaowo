package org.miaowo.miaowo.util;

import android.util.Log;

/**
 * 调试工具类
 * Created by luqin on 17-1-24.
 */

public class LogUtil {
    private static final String TAG = "MiaoLogs";
    
    public static void e(String errMsg, Exception e) {
        Log.e(TAG, "Error : " + errMsg, e);
    }
    
    public static void e(String errMsg) {
        Log.e(TAG, "Error : " + errMsg);
    }

    public static void i(Object msg) {
        Log.i(TAG, msg == null ? "null" : msg.toString());
    }

    public static void i(Object[] msgs) {
        if (msgs == null) {
            Log.i(TAG, "null");
            return;
        }
        StringBuilder sb = new StringBuilder("Information :\n");
        if (msgs.length == 0) {
            sb.append("Empty!");
        } else {
            for (Object msg : msgs) {
                sb.append("\n\t");
                sb.append(msg);
            }
        }
        Log.i(TAG, sb.toString());
    }
}
