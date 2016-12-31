package org.miaowo.miaowo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 提示消息工具
 * Created by luqin on 16-12-29.
 */

public class PopupUtil {
    /**
     * 存一份打开的弹窗引用副本，用于控制只能显示一个弹窗
     */
    private static PopupWindow nowWindow = null;

    /*
    创建一个新的 PopupWindow
     */
    private static PopupWindow getPopupWindow(Context context, View v, PopupWindowInit init) {
        PopupWindow window = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        if (init != null) {
            init.init(v, window);
        }
        return window;
    }

    /**
     * 当前显示窗口的 Tag，用于判断
     */
    private static String windowTag;

    /**
     * 在屏幕中间显示 PopupWindow
     * @param context 所在的Activity
     * @param tag 标签，附着于 View，用于判断弹窗类型
     * @param viewId Layout的id
     * @param init 初始化自定义View
     * @return 创建的 PopupWindow
     */
    public static PopupWindow showPopupWindowInCenter(Activity context, String tag, @LayoutRes int viewId, PopupWindowInit init) {
        // 关闭上一个窗口
        closePopupWindow();
        View v = View.inflate(context, viewId, null);
        // 显示新窗口
        windowTag = tag;
        nowWindow = getPopupWindow(context, v, init);
        nowWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return nowWindow;
    }


    /**
     * 用于关闭当前弹窗
     * @return 返回被关闭的弹窗的 Tag
     */
    public static String closePopupWindow() {
        String tag = windowTag;
        if (nowWindow != null) {
            nowWindow.dismiss();
            nowWindow = null;
            windowTag = null;
        }
        return tag;
    }

    /**
     * 初始化View的接口
     */
    public interface PopupWindowInit {
        void init(View v, PopupWindow window);
    }
}
