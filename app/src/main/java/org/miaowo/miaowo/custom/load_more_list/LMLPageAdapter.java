package org.miaowo.miaowo.custom.load_more_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表
 * Created by luqin on 16-12-28.
 */

public class LMLPageAdapter<E>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements LMLAdapter<E>{

    final static int NO_ID = -1;

    private List<LMLPage<E>> mPages;
    private ViewLoaderCreator<E> mLoader;

    public LMLPageAdapter(LMLPage<E> page, @NonNull ViewLoaderCreator<E> loader) {
        mPages = new ArrayList<>();
        mPages.add(page);
        mLoader = loader;
    }
    public LMLPageAdapter(List<E> items, @NonNull ViewLoaderCreator<E> loader) {
        this(new LMLPage<>(items), loader);
    }
    public LMLPageAdapter(@NonNull ViewLoaderCreator<E> loader) {
        this(new LMLPage<>(), loader);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mLoader.createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mLoader.bindView(getItem(position), holder, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (LMLPage<E> item : mPages) {
            count += item.items.size();
        }
        return count;
    }
    private int getPageStart(int pageId) {
        int start = 0;
        for (LMLPage<E> page : mPages) {
            if (page.id == pageId) return start;
            else start += page.items.size();
        }
        return -1;
    }
    private int getPageUpdateCount(int pageId) {
        int after = 0;
        boolean find = false;
        for (LMLPage<E> page : mPages) {
            if (page.id == pageId) find = true;
            if (find) after += page.items.size();
        }
        return after;
    }

    @Override
    public int getItemViewType(int position) {
        return mLoader.setType(getItem(position), position);
    }

    public E getItem(int position) {
        if (mPages.size() == 0) return null;
        int pStart, pEnd = 0;
        for (LMLPage<E> page : mPages) {
            pStart = pEnd;
            pEnd += page.items.size();
            if (position < pEnd) return page.items.get(position - pStart);
        }
        return null;
    }
    public void update(List<E> newItems) {
        update(newItems, NO_ID);
    }
    public void update(List<E> newItems, int pageId) {
        if (newItems == null || newItems.isEmpty()) return;
        LMLPage<E> page = getPage(pageId);
        if (page == null) addPage(newItems, pageId);
        else {
            page.items = newItems;
            notifyItemRangeChanged(getPageStart(pageId), getPageUpdateCount(pageId));
        }
    }
    public void append(List<E> newItems, boolean toHead) {
        append(newItems, toHead, NO_ID);
    }
    public void append(List<E> newItems, boolean toHead, int pageId) {
        if (newItems == null || newItems.size() == 0) return;
        LMLPage<E> page = getPage(pageId);
        if (page == null) {
            addPage(newItems, pageId);
        } else {
            page.items.addAll(toHead ? 0 : page.items.size(), newItems);
        }
        notifyItemRangeChanged(getPageStart(pageId), getPageUpdateCount(pageId));
    }
    public void insert(E item, boolean toHead) {
        insert(item, toHead, NO_ID);
    }
    public void insert(E item, boolean toHead, int pageId) {
        LMLPage<E> page = getPage(pageId);
        if (page != null) {
            int position = toHead ? 0 : page.items.size();
            page.items.add(position, item);
            notifyItemInserted(getPageStart(pageId) + position);
        }
    }
    public void addPage(List<E> newItems, int pageId) {
        addPage(newItems, pageId, false);
    }
    public void addPage(List<E> newItems, int pageId, boolean toHead) {
        if (getPage(pageId) != null) {
            update(newItems, pageId);
            return;
        }
        mPages.add(toHead ? 0 : mPages.size(), new LMLPage<>(pageId, newItems));
        notifyItemRangeInserted(getPageStart(pageId), newItems.size());
    }
    public void removePage(int pageId) {
        LMLPage<E> page = getPage(pageId);
        if (page != null) {
            int start = getPageStart(pageId);
            mPages.remove(page);
            if (mPages.isEmpty()) mPages.add(new LMLPage<>(new ArrayList<>()));
            notifyItemRangeRemoved(start, page.items.size());
        }
    }
    public LMLPage<E> getPage(int pageId) {
        for (LMLPage<E> page : mPages) {
            if (page.id == pageId) {
                return page;
            }
        }
        return null;
    }
    public void clear() {
        if (mPages != null && !mPages.isEmpty()) {
            mPages.clear();
            notifyDataSetChanged();
        }
    }
    public boolean isIndex() {
        return mPages.get(0).index;
    }

    public interface ViewLoaderCreator<E> {
        RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType);
        void bindView(E item, RecyclerView.ViewHolder holder, int type);
        int setType(E item, int position);
    }

}
