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

    private static PopupWindow getPopupWindow(Context context, @LayoutRes int viewId, PopupWindowInit init) {
        View v = View.inflate(context, viewId, null);
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

    public static PopupWindow showPopupWindowInCenter(Activity context, @LayoutRes int viewId, PopupWindowInit init) {
        PopupWindow window = getPopupWindow(context, viewId, init);
        window.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return window;
    }

    public interface PopupWindowInit {
        void init(View v, PopupWindow window);
    }
}
