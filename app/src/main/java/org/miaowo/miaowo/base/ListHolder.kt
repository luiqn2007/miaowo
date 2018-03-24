package org.miaowo.miaowo.base

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 带 ViewBinding 的 ViewHolder
 * Created by luqin on 18-2-17.
 */

open class ListHolder : RecyclerView.ViewHolder {
    private val mCacheViews = SparseArray<View?>()

    constructor(view: View) : super(view)
    constructor(context: Context, @LayoutRes viewId: Int, parent: ViewGroup?, attach: Boolean = false) : super(
            LayoutInflater.from(context).inflate(viewId, parent, attach)
    )

    fun find(@IdRes id: Int): View? {
        if (mCacheViews[id] == null)
            mCacheViews.put(id, view.findViewById(id))
        return mCacheViews[id]
    }

    inline fun <reified T> find(@IdRes id: Int) = if (find(id) is T) find(id) as T? else null

    val view: View = itemView

    var message: Any? = null
    var message2: Any? = null
}
