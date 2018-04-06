package org.miaowo.miaowo.base

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.miaowo.miaowo.R

/**
 * 带 ViewBinding 的 ViewHolder
 * Created by luqin on 18-2-17.
 */
@Suppress("unused")
open class ListHolder(view: View, private val bundleMap: MutableMap<String, Any?>)
    : RecyclerView.ViewHolder(view), MutableMap<String, Any?> by bundleMap {

    private val mCacheViews = SparseArray<View?>()

    constructor(view: View) : this(view, mutableMapOf())

    constructor(@LayoutRes viewId: Int, parent: ViewGroup, context: Context = App.i, attach: Boolean = false) : this(
            LayoutInflater.from(context).inflate(viewId, parent, attach)
    )

    fun find(@IdRes id: Int): View? {
        if (mCacheViews[id] == null)
            mCacheViews.put(id, itemView.findViewById(id))
        return mCacheViews[id]
    }

    inline fun <reified T> find(@IdRes id: Int) = (find(id) as? T)

    operator fun get(@IdRes id: Int) = find(id)

    /**
     * 使用反射，获取 id 名称对应的 View
     *
     * 例:
     * R.id.text => text
     * android:R.id.text => android:text
     */
    fun find(idName: String): View? {
        return try {
            val isSystem = idName.startsWith("android:")
            val rIdName =
                    if (!isSystem) idName
                    else idName.replaceFirst("android:", "")
            val rClass =
                    if (!isSystem) R.id::class.java
                    else android.R.id::class.java
            val field = rClass.getDeclaredField(rIdName)
            if (field != null) {
                field.isAccessible = true
                val id = field.getInt(null)
                this[id]
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}