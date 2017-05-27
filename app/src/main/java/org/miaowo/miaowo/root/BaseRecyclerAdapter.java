package org.miaowo.miaowo.root;

import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

abstract public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private SparseIntArray mLayoutIds = new SparseIntArray();
    private List<T> mItems = new ArrayList<>();
    private UnaryOperator<Integer> mTypeChooser;

    public BaseRecyclerAdapter(int layoutId) {
        mLayoutIds.put(0, layoutId);
    }

    public BaseRecyclerAdapter(int[] types, int[] layouts, UnaryOperator<Integer> typeChooser) {
        for (int i = 0; i < types.length; i++) mLayoutIds.put(types[i], layouts[i]);
        mTypeChooser = typeChooser;
    }

    public void update(List<T> items) {
        mItems = items;
        BaseActivity.get.runOnUiThreadIgnoreError(this::notifyDataSetChanged);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(mLayoutIds.get(viewType), parent);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeChooser == null) return 0;
        return mTypeChooser.apply(position);
    }
}
