package org.miaowo.miaowo.ui.load_more_list

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 原来用的 PullLoadMoreRecycleView 不能直接滑动到列表指定位置，弃之
 * Created by luqin on 17-1-1.
 */

class LoadMoreList : SwipeRefreshLayout {
    private var mRecyclerView = RecyclerView(context)
    private var mPushRefresher: SwipeRefreshLayout.OnRefreshListener? = null
    private var mPullRefresher: SwipeRefreshLayout.OnRefreshListener? = null

    private var startY: Float = 0.toFloat()
    private var pullable = false
    private var pushable = false
    private var prePullLoad = false
    private var prePushLoad = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        mRecyclerView = RecyclerView(context)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        addView(mRecyclerView)
    }

    /**
     * 设置下拉监听
     * @param listener 下拉监听
     * @param preLoad 自动更新 当前条目所在位置小于3时自动执行监听
     */
    fun setPullRefresher(preLoad: Boolean, listener: () -> Unit = {}) {
        pullable = true
        mPullRefresher = OnRefreshListener {
            pullable = false
            listener()
        }
        prePullLoad = preLoad
        setOnRefreshListener {
            pullable = false
            listener()
        }
    }

    /**
     * 设置上滑监听
     * @param listener 上滑监听
     * @param preLoad 自动更新 当前最下方条目所在位置距离底部小于3时自动执行监听
     */
    fun setPushRefresher(preLoad: Boolean, listener: () -> Unit) {
        pushable = true
        prePushLoad = preLoad
        mPushRefresher = OnRefreshListener {
            pushable = false
            listener()
        }
    }

    /**
     * 适配器
     * 等同于 RecyclerView.adapter
     */
    var adapter: RecyclerView.Adapter<*>
        get() = mRecyclerView.adapter
        set(adapter) { mRecyclerView.adapter = adapter }

    /**
     * 设置自定义动画
     * 等同于 RecyclerView.setItemAnimator
     */
    var itemAnimator: RecyclerView.ItemAnimator
        get() = mRecyclerView.itemAnimator
        set(value) { mRecyclerView.itemAnimator = value }

    /**
     * 设置布局管理器
     * 等同于 RecyclerView.setLayoutManager
     */
    var layoutManager: RecyclerView.LayoutManager
        get() = mRecyclerView.layoutManager
        set(value) { mRecyclerView.layoutManager = value }

    /**
     * 平滑滚动到某位置
     * 等同于 RecyclerView.smoothScrollToPosition
     * @param position 滚动到位置
     */
    fun scrollToPosition(position: Int) {
        mRecyclerView.smoothScrollToPosition(position)
    }

    /**
     * 设置加载完成 停止所有加载动画
     */
    fun loadOver() {
        pullable = mPullRefresher != null
        pushable = mPushRefresher != null
        isRefreshing = false
    }

    /**
     * 开始加载 手动调用下拉监听
     */
    fun load() {
        if (pullable) {
            isRefreshing = true
            mPullRefresher?.onRefresh()
        }
    }

    /**
     * 控制是否开启预监听
     * @param pull 下拉监听
     * @param push 上滑监听
     */
    fun preLoadControl(pull: Boolean, push: Boolean) {
        prePullLoad = pull
        prePushLoad = push
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (mPushRefresher != null) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> startY = ev.y
                MotionEvent.ACTION_UP -> {
                    val endY = ev.y
                    if (startY - endY >= 300 && isPush) {
                        isRefreshing = true
                        mPushRefresher?.onRefresh()
                        return true
                    } else if (startY - endY <= -300
                            && pullable && !isRefreshing && firstPosition <= 3) {
                        isRefreshing = true
                        mPullRefresher?.onRefresh()
                        return true
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private val firstPosition: Int
        get() {
            val layoutManager = mRecyclerView.layoutManager
            if (layoutManager is GridLayoutManager) {
                return layoutManager.findFirstVisibleItemPosition()
            } else if (layoutManager is LinearLayoutManager) {
                return layoutManager.findFirstVisibleItemPosition()
            } else if (layoutManager is StaggeredGridLayoutManager) {
                val firstPositions = IntArray(layoutManager.spanCount)
                layoutManager.findFirstVisibleItemPositions(firstPositions)
                val itemCount = firstPositions.min() ?: firstPositions[0]
                return itemCount
            }
            return -1
        }
    private val lastPosition: Int
        get() {
            val layoutManager = mRecyclerView.layoutManager
            if (layoutManager is GridLayoutManager) {
                return layoutManager.findLastVisibleItemPosition()
            } else if (layoutManager is LinearLayoutManager) {
                return layoutManager.findLastVisibleItemPosition()
            } else if (layoutManager is StaggeredGridLayoutManager) {
                val lastPositions = IntArray(layoutManager.spanCount)
                layoutManager.findLastVisibleItemPositions(lastPositions)
                val itemCount = lastPositions.max() ?: lastPositions[0]
                return itemCount
            }
            return -1
        }

    private val isPush: Boolean
        get() {
            if (!pushable || isRefreshing) return false
            val allowPosition = if (prePushLoad)
                mRecyclerView.layoutManager.itemCount - 4
            else
                mRecyclerView.layoutManager.itemCount - 1
            return lastPosition >= allowPosition
        }
}
