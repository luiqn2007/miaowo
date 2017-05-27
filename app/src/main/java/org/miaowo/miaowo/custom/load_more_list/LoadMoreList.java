package org.miaowo.miaowo.custom.load_more_list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.miaowo.miaowo.root.BaseViewHolder;

/**
 * 原来用的 PullLoadMoreRecycleView 不能直接滑动到列表指定位置，弃之
 * Created by luqin on 17-1-1.
 */

public class LoadMoreList extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;
    private OnRefreshListener mPushRefresher;
    private OnRefreshListener mPullRefresher;

    private float startY;
    private boolean pullable = false, pushable = false;
    private boolean prePullLoad = false, prePushLoad = false;

    public LoadMoreList(Context context) {
        super(context);
        init(context);
    }
    public LoadMoreList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        addView(mRecyclerView);
    }

    /**
     * 设置下拉监听
     * @param listener 下拉监听
     * @param preLoad 自动更新 当前条目所在位置小于3时自动执行监听
     */
    public void setPullRefresher(OnRefreshListener listener, boolean preLoad) {
        pullable = true;
        mPullRefresher = listener;
        prePullLoad = preLoad;
        setOnRefreshListener(listener);
    }

    /**
     * 设置上滑监听
     * @param listener 上滑监听
     * @param preLoad 自动更新 当前最下方条目所在位置距离底部小于3时自动执行监听
     */
    public void setPushRefresher(OnRefreshListener listener, boolean preLoad) {
        pushable = true;
        prePushLoad = preLoad;
        this.mPushRefresher = listener;
    }

    /**
     * 设置适配器
     * 等同于 RecyclerView.setAdapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取适配器
     * 等同于 RecyclerView.getAdapter
     * @return 适配器
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    /**
     * 设置自定义动画
     * 等同于 RecyclerView.setItemAnimator
     */
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    /**
     * 设置布局管理器
     * 等同于 RecyclerView.setLayoutManager
     * @param layoutManager 布局管理器
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 平滑滚动到某位置
     * 等同于 RecyclerView.smoothScrollToPosition
     * @param position 滚动到位置
     */
    public void scrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    /**
     * 设置加载完成 停止所有加载动画
     */
    public void loadOver() {
        setRefreshing(false);
    }

    /**
     * 开始加载 手动调用下拉监听
     */
    public void load() {
        if (pullable) {
            setRefreshing(true);
            mPullRefresher.onRefresh();
        }
    }

    /**
     * 控制是否允许监听
     * @param pull 下拉监听
     * @param push 上滑监听
     */
    public void loadMoreControl(boolean pull, boolean push) {
        if (push) setPushRefresher(mPushRefresher, prePushLoad);
        if (pull) setPullRefresher(mPullRefresher, prePullLoad);
        else setOnRefreshListener(this::loadOver);

        pullable = pull;
        pushable = push;
    }

    /**
     * 控制是否开启预监听
     * @param pull 下拉监听
     * @param push 上滑监听
     */
    @SuppressWarnings("unused")
    public void preLoadControl(boolean pull, boolean push) {
        prePullLoad = pull;
        prePushLoad = push;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mPushRefresher != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startY = ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float endY = ev.getY();
                    if (startY - endY >= 300 && isPush()) {
                        setRefreshing(true);
                        mPushRefresher.onRefresh();
                        return true;
                    } else if (startY - endY <= -300
                            && pullable && !isRefreshing() && getFirstPosition() <= 3) {
                        setRefreshing(true);
                        mPullRefresher.onRefresh();
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private int getFirstPosition() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if(layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if(layoutManager instanceof StaggeredGridLayoutManager){
            int[] firstPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(firstPositions);
            int itemCount = firstPositions[0];
            for (int value : firstPositions) {
                if (value < itemCount) {
                    itemCount = value;
                }
            }
            return itemCount;
        }
        return -1;
    }
    private int getLastPosition() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if(layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if(layoutManager instanceof StaggeredGridLayoutManager){
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            int itemCount = lastPositions[0];
            for (int value : lastPositions) {
                if (value > itemCount) {
                    itemCount = value;
                }
            }
            return itemCount;
        }
        return -1;
    }

    private boolean isPush() {
        if (!pushable || isRefreshing()) return false;
        int allowPosition = prePushLoad
                ? mRecyclerView.getLayoutManager().getItemCount() - 4
                : mRecyclerView.getLayoutManager().getItemCount() - 1;
        return getLastPosition() >= allowPosition;
    }
}
