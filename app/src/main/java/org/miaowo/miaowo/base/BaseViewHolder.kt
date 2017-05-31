package org.miaowo.miaowo.base

import android.graphics.drawable.Drawable
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * ViewHolder
 * Created by luqin on 17-4-22.
 */

class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mChildViews = SparseArray<View>()

    @JvmOverloads
    constructor(viewId: Int, parent: ViewGroup, attach: Boolean = false) : this(LayoutInflater.from(App.i).inflate(viewId, parent, attach))

    /**
     * 获得子 View
     * @param id ViewId
     * @return View
     */
    fun getView(@IdRes id: Int): View? {
        var view: View? = mChildViews.get(id, null)
        if (view == null) {
            view = itemView.findViewById(id)
            mChildViews.put(id, view)
        }
        return view
    }

    /**
     * 为子 View 设置文本
     * 但该 View 要求:
     * 必须可以被通过 id 找到
     * 拥有一个叫 setText 的方法
     * 接受参数(按优先级): CharSequence*1
     * @param id ViewId
     * @param text 文本
     */
    fun setText(@IdRes id: Int, text: CharSequence?) {
        val view = getView(id)
        if (view != null && view is TextView) view.text = text?:""
    }

    /**
     * 为子 View 设置文本
     * 但该 View 要求:
     * 必须可以被通过 id 找到
     * 拥有一个叫 setText 的方法
     * 接受参数(按优先级): int*1 或 CharSequence*1
     * @param id ViewId
     * @param stringId 文本 id
     */
    fun setText(@IdRes id: Int, @StringRes stringId: Int) {
        val view = getView(id)
        if (view != null && view is TextView) view.setText(stringId)
    }

    /**
     * 为子 View 设置图片
     * 但该 View 要求:
     * 必须可以被通过 id 找到
     * 拥有一个叫 setImageDrawable 的方法
     * 接受参数(按优先级): Drawable*1
     * @param id ViewId
     * @param drawable 图片
     */
    fun setDrawable(@IdRes id: Int, drawable: Drawable) {
        val view = getView(id)
        if (view != null && view is ImageView) view.setImageDrawable(drawable)
    }

    /**
     * 为子 View 设置点击监听
     * @param id ViewId
     * @param listener 监听方法
     */
    fun setClickListener(@IdRes id: Int, listener: (view: View) -> Unit) =
            getView(id)?.setOnClickListener { listener(it) }

    /**
     * 为该 View 设置点击监听
     * @param listener 监听方法
     */
    fun setClickListener(listener: (view: View) -> Unit) = view.setOnClickListener(listener)

    /**
     * 获取绑定 View
     * @return 绑定 View
     */
    val view: View = itemView
}
