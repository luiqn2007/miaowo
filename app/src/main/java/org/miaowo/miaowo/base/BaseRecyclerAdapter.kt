package org.miaowo.miaowo.base

import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.ViewGroup
import java.util.function.UnaryOperator

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {
    private val mLayoutIds = SparseIntArray()
    private var mItems: List<T> = mutableListOf()
    private var mTypeChooser: UnaryOperator<Int>? = null

    constructor(layoutId: Int) {
        mLayoutIds.put(0, layoutId)
    }

    constructor(types: IntArray, layouts: IntArray, typeChooser: UnaryOperator<Int>) {
        for (i in types.indices) mLayoutIds.put(types[i], layouts[i])
        mTypeChooser = typeChooser
    }

    fun update(items: List<T>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BaseViewHolder(mLayoutIds.get(viewType), parent)

    override fun getItemCount() = mItems.size

    fun getItem(position: Int) = mItems[position]

    override fun getItemViewType(position: Int) = mTypeChooser?.apply(position) ?: 0
}