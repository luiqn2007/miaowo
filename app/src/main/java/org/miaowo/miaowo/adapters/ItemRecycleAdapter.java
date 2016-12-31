package org.miaowo.miaowo.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 列表
 * Created by luqin on 16-12-28.
 */

public class ItemRecycleAdapter<E> extends RecyclerView.Adapter<ItemRecycleAdapter.ViewHolder> {
    private static final String TAG = "ItemRecycleAdapter";

    private ArrayList<E> mItems;
    private ViewLoader<E> mLoader;

    public ItemRecycleAdapter(@Nullable ArrayList<E> items, @NonNull ViewLoader<E> loader) {
        mItems = items;
        mLoader = loader;
    }

    public interface ViewLoader<E> {
        ViewHolder createHolder(ViewGroup parent, int viewType);
        void bindView(E item, ViewHolder holder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mLoader.createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mLoader.bindView(mItems.get(position), holder);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    public void updateDate(@NonNull ArrayList<E> newItems) {
        mItems = newItems;
        Log.i(TAG, "updateDate: New Items: " + newItems.size());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 代码来自
        // http://www.open-open.com/lib/view/open1455671659714.html
        // Super ViewHolder！
        private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        private <T extends View> T findViewById(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getView(int viewId) {
            return findViewById(viewId);
        }

        public TextView getTextView(int viewId) {
            return (TextView) getView(viewId);
        }

        public Button getButton(int viewId) {
            return (Button) getView(viewId);
        }

        public ImageView getImageView(int viewId) {
            return (ImageView) getView(viewId);
        }

        public EditText getEditText(int viewId) {
            return (EditText) getView(viewId);
        }

        public ViewHolder setText(int viewId, String value) {
            TextView view = findViewById(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setBackground(int viewId, int resId) {
            View view = findViewById(viewId);
            view.setBackgroundResource(resId);
            return this;
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

}
