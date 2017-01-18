package org.miaowo.miaowo.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.ChatMessage;
import org.miaowo.miaowo.ui.LoadMoreList;
import org.miaowo.miaowo.view.BaseActivity;

import java.util.ArrayList;

/**
 * 提示消息工具
 * Created by luqin on 16-12-29.
 */

public class PopupUtil {
    public static PopupWindow thisPopWindow;

    /*
    创建一个新的 PopupWindow
     */
    private static PopupWindow createPopupWindow(View v, PopupWindowInit init) {
        BaseActivity context = D.getInstance().activeActivity;
        if (context == null) {
            return null;
        }
        closePopupWindow();
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
     * @param viewId Layout的id
     * @param init 初始化自定义View
     * @return 创建的 PopupWindow
     */
    public static PopupWindow showPopupWindowInCenter(@LayoutRes int viewId, @NonNull PopupWindowInit init, @Nullable PopupWindow.OnDismissListener listener) {
        BaseActivity context = D.getInstance().activeActivity;
        if (context == null) {
            return null;
        }
        View v = View.inflate(context, viewId, null);
        createPopupWindow(v, init);
        if (listener != null) {
            thisPopWindow.setOnDismissListener(listener);
        }
        thisPopWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return thisPopWindow;
    }

    public static void closePopupWindow() {
        if (thisPopWindow != null) {
            thisPopWindow.dismiss();
            thisPopWindow = null;
        }
    }

    // 添加俩天消息
    public static PopupWindow addChatMsg(PopupWindow window, ChatMessage msg) {
        RelativeLayout chatView = (RelativeLayout) window.getContentView();
        LoadMoreList list = (LoadMoreList) chatView.findViewById(R.id.list);
        ItemRecyclerAdapter adapter = (ItemRecyclerAdapter) list.getAdapter();
        ArrayList items = adapter.getItems();
        items.add(msg);
        adapter.updateDate(items);
        list.scrollToPosition(items.size() - 1);
        return window;
    }

    /**
     * 初始化View的接口
     */
    public interface PopupWindowInit {
        void init(View v, PopupWindow window);
    }
}
