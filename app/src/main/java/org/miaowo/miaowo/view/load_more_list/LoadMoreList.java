package org.miaowo.miaowo.view.load_more_list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import org.miaowo.miaowo.util.LogUtil;

/**
 * 原来用的 PullLoadMoreRecycleView 不能直接滑动到列表指定位置，弃之
 * Created by luqin on 17-1-1.
 */

public class LoadMoreList extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;
    private OnRefreshListener mPushRefresher;
    private OnRefreshListener mPullRefresher;

    private float startY;
    private Toast loading;

    public LoadMoreList(Context context) {
        super(context);
        init(context, null);
    }
    public LoadMoreList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        addView(mRecyclerView);
    }

    public void setPullRefresher(OnRefreshListener listener) {
        mPullRefresher = listener;
        setOnRefreshListener(listener);
    }
    public void setPushRefresher(OnRefreshListener listener) {
        this.mPushRefresher = listener;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public void loadOver() {
        setRefreshing(false);
    }
    public void load() {
        setRefreshing(true);
        mPullRefresher.onRefresh();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mPushRefresher != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startY = ev.getY();
                    LogUtil.i("start: " + startY);
                    break;
                case MotionEvent.ACTION_UP:
                    float endY = ev.getY();
                    LogUtil.i("end: " + endY);
                    if (startY - endY >= 300 &&isEnd()) {
                        setRefreshing(true);
                        mPushRefresher.onRefresh();
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isEnd() {
        // 获取总大小;
        int itemCount = -1;
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            itemCount = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if(layoutManager instanceof LinearLayoutManager){
            itemCount = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            itemCount = lastPositions[0];
            for (int value : lastPositions) {
                if (value > itemCount) {
                    itemCount = value;
                }
            }
        }
        return itemCount == mRecyclerView.getLayoutManager().getItemCount() - 1;
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
