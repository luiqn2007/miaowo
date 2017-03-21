package org.miaowo.miaowo.view.load_more_list;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by luqin on 17-3-22.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    // 代码来自
    // http://www.open-open.com/lib/view/open1455671659714.html
    // Super ViewHolder！
    private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    private View findViewById(int viewId) {
        View view;
        view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    // 重写了下，可以批量设置
    public ViewHolder setOnClickListener(View.OnClickListener listener, int... viewId) {
        for (int i : viewId) {
            View view = findViewById(i);
            view.setOnClickListener(listener);
        }
        return this;
    }

    // 获取自定义视图
    public View getView() {
        return itemView;
    }
}
