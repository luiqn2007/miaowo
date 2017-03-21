package org.miaowo.miaowo.view.load_more_list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 列表
 * Created by luqin on 16-12-28.
 */

public class ItemRecyclerAdapter<E>
        extends RecyclerView.Adapter<ViewHolder> {

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

    public List<E> getItems() {
        return mItems;
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
