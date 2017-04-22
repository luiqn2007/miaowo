package org.miaowo.miaowo.util;

import android.util.Log;

import okhttp3.Response;

/**
 * 封装的 Log 输出类
 * Created by luqin on 17-4-20.
 */

public class LogUtil {

    public static void i(Object... info) {
        Log.i(createTag(), createMessage(info));
    }

    public static void e(String msg) {
        Log.e(createTag(), "\n" + msg);
    }

    public static void e(Exception exception) {
        Log.e(createTag(), "\nError-->\n", exception);
    }

    private static String createTag() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    private static String createMessage(Object... objects) {
        StringBuilder sb = new StringBuilder("栈 -->\n");
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTrace : stackTraces) {
            if (!stackTrace.getClassName().startsWith("org.miaowo.miaowo")) continue;
            if (stackTrace.getClassName().equals("org.miaowo.miaowo.util.LogUtil")) continue;
            sb.append("at ").append(stackTrace.getClassName()).append("(")
                    .append(stackTrace.getFileName()).append(":").append(stackTrace.getLineNumber()).append(")\n");
        }
        sb.append("<--\n信息 -->\n");
        for (Object object : objects) sb.append(toString(object)).append('\n');
        sb.append("<--");
        return sb.toString();
    }

    private static String toString(Object object) {
        try {
            if (object == null) {
                return "null";
            } else if (object instanceof Response) {
                return ((Response) object).body().string();
            } else {
                return object.toString();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
