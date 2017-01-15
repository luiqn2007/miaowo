package org.miaowo.miaowo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 提示消息工具
 * Created by luqin on 16-12-29.
 */

public class PopupUtil {
    public static PopupWindow lastPopWindow, thisPopWindow;

    /*
    创建一个新的 PopupWindow
     */
    private static PopupWindow createPopupWindow(Context context, View v, PopupWindowInit init) {
        if (lastPopWindow != null && lastPopWindow.isShowing()) {
            lastPopWindow.dismiss();
        }
        lastPopWindow = thisPopWindow;
        thisPopWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        thisPopWindow.setTouchable(true);
        thisPopWindow.setFocusable(true);
        thisPopWindow.setOutsideTouchable(true);
        thisPopWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        if (init != null) {
            init.init(v, thisPopWindow);
        }
        return thisPopWindow;
    }

    /**
     * 在屏幕中间显示 PopupWindow
     * @param context 所在的Activity
     * @param viewId Layout的id
     * @param init 初始化自定义View
     * @return 创建的 PopupWindow
     */
    public static PopupWindow showPopupWindowInCenter(Activity context, @LayoutRes int viewId, @NonNull PopupWindowInit init, @Nullable PopupWindow.OnDismissListener listener) {
        View v = View.inflate(context, viewId, null);
        createPopupWindow(context, v, init);
        if (listener != null) {
            thisPopWindow.setOnDismissListener(listener);
        }
        thisPopWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return thisPopWindow;
    }

    public static void closePopupWindow() {
        if (thisPopWindow != null && thisPopWindow.isShowing()) {
            thisPopWindow.dismiss();
        }
    }

    /**
     * 初始化View的接口
     */
    public interface PopupWindowInit {
        void init(View v, PopupWindow window);
    }
}
