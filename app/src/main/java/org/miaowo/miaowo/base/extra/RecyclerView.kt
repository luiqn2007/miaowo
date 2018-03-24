@file:Suppress("unused")

package org.miaowo.miaowo.base.extra

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * RecyclerView 相关
 * Created by lq200 on 2018/2/13.
 */

val hRecyclerViewPositionGetter = mutableMapOf<Class<*>?, RecyclerViewPositionGetter>()

inline fun <reified T : RecyclerView.LayoutManager> RecyclerView.registerPositionGetter(getter: RecyclerViewPositionGetter) {
    hRecyclerViewPositionGetter[T::class.java] = getter
}

val RecyclerView.firstPosition: Int
    get() {
        return when {
            layoutManager::class.java in hRecyclerViewPositionGetter ->
                hRecyclerViewPositionGetter[layoutManager.javaClass]!!.firstPosition(this, layoutManager)
            layoutManager is LinearLayoutManager ->
                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            layoutManager is StaggeredGridLayoutManager ->
                (layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null).min()
                        ?: RecyclerView.NO_POSITION
            else -> hRecyclerViewPositionGetter[layoutManager.javaClass]?.firstPosition(this, layoutManager)
                    ?: RecyclerView.NO_POSITION
        }
    }
val RecyclerView.lastPosition: Int
    get() {
        return when {
            layoutManager::class.java in hRecyclerViewPositionGetter ->
                hRecyclerViewPositionGetter[layoutManager.javaClass]!!.lastPosition(this, layoutManager)
            layoutManager is LinearLayoutManager ->
                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            layoutManager is StaggeredGridLayoutManager ->
                (layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null).min()
                        ?: RecyclerView.NO_POSITION
            else -> hRecyclerViewPositionGetter[layoutManager.javaClass]?.lastPosition(this, layoutManager)
                    ?: RecyclerView.NO_POSITION
        }
    }

interface RecyclerViewPositionGetter {
    fun firstPosition(rv: RecyclerView?, lm: RecyclerView.LayoutManager?): Int
    fun lastPosition(rv: RecyclerView?, lm: RecyclerView.LayoutManager?): Int
}