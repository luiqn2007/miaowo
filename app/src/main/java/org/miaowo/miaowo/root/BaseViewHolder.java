package org.miaowo.miaowo.root;

import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.util.ReflectUtil;

/**
 * ViewHolder
 * Created by luqin on 17-4-22.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mChildViews;
    private ReflectUtil reflect;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mChildViews = new SparseArray<>();
        reflect = ReflectUtil.utils();
    }

    /**
     * 获得子 View
     * @param id ViewId
     * @return View
     */
    public View getView(@IdRes int id) {
        View view = mChildViews.get(id, null);
        if (view == null) {
            view = itemView.findViewById(id);
            mChildViews.put(id, view);
        }
        return view;
    }

    /**
     * 为子 View 设置文本
     * 但该 View 要求:
     *  必须可以被通过 id 找到
     *  拥有一个叫 setText 的方法
     *  接受参数(按优先级): CharSequence*1
     * @param id ViewId
     * @param text 文本
     */
    public void setText(@IdRes int id, CharSequence text) {
        View view = getView(id);
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            } else {
                try {
                    reflect.invoke(view, "setText", new CharSequence[]{text});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 为子 View 设置文本
     * 但该 View 要求:
     *  必须可以被通过 id 找到
     *  拥有一个叫 setText 的方法
     *  接受参数(按优先级): int*1 或 CharSequence*1
     * @param id ViewId
     * @param stringId 文本 id
     */
    public void setText(@IdRes int id, @StringRes int stringId) {
        View view = getView(id);
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setText(stringId);
            } else {
                try {
                    reflect.invoke(view, "setText", new Object[]{stringId});
                } catch (Exception e) {
                    setText(id, view.getResources().getText(stringId));
                }
            }
        }
    }

    /**
     * 为子 View 设置图片
     * 但该 View 要求:
     *  必须可以被通过 id 找到
     *  拥有一个叫 setImageDrawable 的方法
     *  接受参数(按优先级): Drawable*1
     * @param id ViewId
     * @param drawable 图片
     */
    public void setDrawable(@IdRes int id, Drawable drawable) {
        View view = getView(id);
        if (view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(drawable);
            } else {
                try {
                    reflect.invoke(view, "setImageDrawable", new Drawable[]{drawable});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 为子 View 设置点击监听
     * @param id ViewId
     * @param listener 监听方法
     */
    public void setClickListener(@IdRes int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
    }

    /**
     * 获取绑定 View
     * @return 绑定 View
     */
    public View getView() {
        return itemView;
    }
}
