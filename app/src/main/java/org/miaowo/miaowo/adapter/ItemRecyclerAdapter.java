package org.miaowo.miaowo.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 列表
 * Created by luqin on 16-12-28.
 */

public class ItemRecyclerAdapter<E>
        extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    final public static int SORT_NEW = 1;
    final public static int SORT_HOT= 2;

    private List<E> mItems;
    private ViewLoader<E> mLoader;
    private DataSort<E> mSort;

    public ItemRecyclerAdapter(@Nullable List<E> items, @NonNull ViewLoader<E> loader) {
        mItems = items;
        mLoader = loader;
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

    @Override
    public int getItemViewType(int position) {
        return mLoader.setType(mItems.get(position), position);
    }

    public void updateDate(List<E> newItems) {
        mItems = newItems;
        notifyDataSetChanged();
    }

    public void setSort(DataSort<E> sortType) {
        mSort = sortType;
    }

    public DataSort<E> getSort() {
        return mSort;
    }

    public void sortBy(int sort) {
        if (mSort == null) {
            return;
        }
        switch (sort) {
            case SORT_HOT:
                mItems.sort((o1, o2) -> mSort.sortByHot(o1, o2));
                break;
            case SORT_NEW:
                mItems.sort((o1, o2) -> mSort.sortByNew(o1, o2));
                break;
            default:
                return;
        }
        notifyDataSetChanged();
    }

    public List<E> getItems() {
        return mItems;
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
    public interface ViewLoader<E> {
        ViewHolder createHolder(ViewGroup parent, int viewType);
        void bindView(E item, ViewHolder holder);
        int setType(E item, int position);
    }
    public interface DataSort<E> {
        int sortByHot(E o1, E o2);
        int sortByNew(E o1, E o2);
    }
}
