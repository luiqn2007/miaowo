package org.miaowo.miaowo.custom.load_more_list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表
 * Created by luqin on 16-12-28.
 */

public class LMLAdapter<E>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<E> mItems;
    private ViewLoaderCreator<E> mLoader;

    public LMLAdapter(@Nullable List<E> items, @NonNull ViewLoaderCreator<E> loader) {
        mItems = items == null ? new ArrayList<>() : items;
        mLoader = loader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mLoader.createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mLoader.bindView(mItems.get(position), holder, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mLoader.setType(mItems.get(position), position);
    }

    public void updateDate(List<E> newItems) {
        if (newItems != null) {
            mItems = newItems;
            notifyDataSetChanged();
        }
    }

    public void appendData(List<E> newItems, boolean toHead) {
        if (newItems == null || newItems.size() == 0) return;
        if (toHead) mItems.addAll(0, newItems);
        else mItems.addAll(newItems);
        notifyDataSetChanged();
    }

    public interface ViewLoaderCreator<E> {
        RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType);
        void bindView(E item, RecyclerView.ViewHolder holder, int type);
        int setType(E item, int position);
    }

    public void clear() {
        if (mItems != null && !mItems.isEmpty()) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }
}
