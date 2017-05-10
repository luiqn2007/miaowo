package org.miaowo.miaowo.root;

import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {
    private List<T> mList;

    public BaseListAdapter(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List<T> list) {
        mList = list;
        BaseActivity.get.runOnUiThreadIgnoreError(this::notifyDataSetChanged);
    }
}
