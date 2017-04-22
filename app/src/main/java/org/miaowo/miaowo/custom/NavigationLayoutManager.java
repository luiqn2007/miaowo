package org.miaowo.miaowo.custom;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于导航的 RecyclerView 的 LayoutManager
 * Created by luqin on 17-4-13.
 */

public class NavigationLayoutManager extends RecyclerView.LayoutManager {
    /*
    * 要实现自定义LayoutManager,首先实现generateDefaultLayoutParams()方法给child添加默认的LayoutParams，
    * 在onLayoutChildren这个方法里面首先detach掉界面上的view缓存到scrap里面，然后重新进行布局，
    * 调用getViewForPosition取出缓存的view，添加到RecyclerView里面并测量，接着把它的边界信息设置为我们所想要的样子，
    * 通过layoutDecorated()方法把需要显示的子view布局到界面上。
    *
    * 如果需要滑动，把canScrollVertically()和canScrollHorizontally()按需返回true，
    * 重写scrollVerticallyBy()或者scrollHorizontallyBy()方法，这两个方法需要返回真实的偏移距离，
    * 返回的dx或者dy可能并不是真实的移动距离，因为当滑动到边缘的时候真实移动距离可能就不是dx或者dy，
    * 所以在这个时候需要判断处理，移动就调用offsetChildrenHorizontal()或者offsetChildrenVertical()实现，
    * 然后重新布局到界面上就可以实现。
    */
    private SparseArrayCompat<Rect> mItemFrames;
    private Point mSize;
    private Point mOffset;

    public NavigationLayoutManager() {
        mSize = new Point();
        mOffset = new Point();
        mItemFrames = new SparseArrayCompat<>();
    }

    // 给RecyclerView的子View创建一个默认的LayoutParams
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    // 放置子view的位置
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) return;
        detachAndScrapAttachedViews(recycler);

        Point offset = new Point(0, 0);
        for (int i = 0; i < getItemCount(); i++) {
            // 视图
            View view = recycler.getViewForPosition(i);
            addView(view);
            // 测量
            measureChildWithMargins(view, 0, 0);
            int height = getDecoratedMeasuredHeight(view);
            int width = getDecoratedMeasuredWidth(view);
            // 位置
            Rect rect = mItemFrames.get(i);
            if (rect == null) {
                rect = new Rect();
                mItemFrames.put(i, rect);
            }
            rect.set(offset.x, offset.y, offset.x + width, offset.y + height);
            offset.y += height;
            offset.x += width;
        }
        mSize.set(Math.max(offset.x, getHorizontalSpace()), Math.max(offset.y, getVerticalSpace()));
        // 回收 & 显示
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Rect displayFrame = new Rect(mOffset.x, mOffset.y,
                mOffset.x + getHorizontalSpace(), mOffset.y + getVerticalSpace());

        // 缓存
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            childFrame.set(getDecoratedLeft(view), getDecoratedTop(view)
                    , getDecoratedRight(view), getDecoratedBottom(view));
            if (!Rect.intersects(displayFrame, childFrame)) {
                removeAndRecycleView(view, recycler);
            }
        }

        // 显示
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayFrame, mItemFrames.get(i))) {
                View view = recycler.getViewForPosition(i);
                measureChildWithMargins(view, 0, 0);
                Rect rect = mItemFrames.get(i);
                layoutDecorated(view,
                        rect.left - mOffset.x, rect.top - mOffset.y,
                        rect.right - mOffset.x, rect.bottom - mOffset.y);
            }
        }
    }

    // 能否滚动
    @Override
    public boolean canScrollHorizontally() {return true;}
    @Override
    public boolean canScrollVertically() {return true;}

    // 滚动时 d代表每次的增长值 返回值是真实移动的距离
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mOffset.x + dx < 0) {
            dx = -mOffset.x;
        } else if (mOffset.x + dx > mSize.x - getHorizontalSpace()) {
            dx = mSize.x - getHorizontalSpace() - mOffset.x;
        }

        offsetChildrenHorizontal(-dx);
        mOffset.x += dx;
        return dx;
    }
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mOffset.y + dy < 0) {
            dy = -mOffset.y;
        } else if (mOffset.y + dy > mSize.y - getHorizontalSpace()) {
            dy = mSize.y - getHorizontalSpace() - mOffset.y;
        }

        offsetChildrenHorizontal(-dy);
        mOffset.y += dy;
        return dy;
    }

    //获取控件的竖直高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    //获取控件的水平宽度
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
