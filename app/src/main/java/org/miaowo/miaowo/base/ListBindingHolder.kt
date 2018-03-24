package org.miaowo.miaowo.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup

/**
 * 带 ViewBinding 的 ViewHolder
 * Created by luqin on 18-2-17.
 */

open class ListBindingHolder<T : ViewDataBinding> : ListHolder {
    private lateinit var mBinder: T

    constructor(view: View) : super(view)
    constructor(context: Context, @LayoutRes viewId: Int, parent: ViewGroup?, attach: Boolean = false) :
            super(context, viewId, parent, attach)

    init {
        bind()
    }

    private fun bind() {
        mBinder = DataBindingUtil.bind(itemView)
    }

    fun bind(index: Int, obj: Any) = mBinder.setVariable(index, obj)

    fun bind(obj: Any) = bind(0, obj)

    fun bind(vararg objects: Any) {
        objects.forEachIndexed { index, obj ->
            bind(index, obj)
        }
    }

    val binder = mBinder
}
