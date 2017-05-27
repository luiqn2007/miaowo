package org.miaowo.miaowo.util;

import android.util.Log;

import org.jsoup.nodes.Element;

import okhttp3.Response;

/**
 * 封装的 Log 输出类
 * Created by luqin on 17-4-20.
 */

public class LogUtil {

    public static void i(Object... info) {
        Log.i(createTag(), createMessage(false, info));
    }

    public static void i(boolean printStack, Object... info) {
        Log.i(createTag(), createMessage(printStack, info));
    }

    public static void e(String msg) {
        Log.e(createTag(), "\n" + msg);
    }

    public static void e(Throwable exception) {
        if (exception == null) {
            Log.e(createTag(), "e: null");
            return;
        }
        Log.e(createTag(), "Error-->\n", exception);
    }

    private static String createTag() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    private static String createMessage(boolean printStack, Object... objects) {
        StringBuilder sb = new StringBuilder();
        if (printStack) {
            sb.append("栈 -->\n");
            StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTrace : stackTraces) {
                if (!stackTrace.getClassName().startsWith("org.miaowo.miaowo")) continue;
                if (stackTrace.getClassName().equals("org.miaowo.miaowo.util.LogUtil")) continue;
                sb.append("at ").append(stackTrace.getClassName()).append("(")
                        .append(stackTrace.getFileName()).append(":").append(stackTrace.getLineNumber()).append(")\n");
            }
            sb.append("<--\n");
        }
        if (printStack) sb.append("信息 -->\n");
        for (Object object : objects) sb.append(toString(object)).append('\n');
        if (printStack) sb.append("<--");
        return sb.toString();
    }

    private static String toString(Object object) {
        try {
            if (object == null) {
                return "null";
            } else if (object instanceof Response) {
                return ((Response) object).body().string();
            } else if (object instanceof Element) {
                return "Element Start-->" +
                        "\ndata\t" + ((Element) object).data() +
                        "\nattrs\t" + ((Element) object).attributes().toString() +
                        "\ntag\t" + ((Element) object).tag() +
                        "\nhasText\t" + ((Element) object).hasText() +
                        "\ntext\t" + ((Element) object).text() +
                        "\nElement end";
            } else {
                return object.toString();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
