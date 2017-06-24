package org.miaowo.miaowo.base

import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {
    private val mLayoutIds = SparseIntArray()
    private var mItems: List<T> = mutableListOf()
    private var mTypeChooser: (position: Int) -> Int = {0}

    constructor(layoutId: Int) {
        mLayoutIds.put(0, layoutId)
    }

    constructor(types: Array<Int>, layouts: Array<Int>, typeChooser: (position: Int) -> Int) {
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

    override fun getItemViewType(position: Int) = mTypeChooser.invoke(position)
}
