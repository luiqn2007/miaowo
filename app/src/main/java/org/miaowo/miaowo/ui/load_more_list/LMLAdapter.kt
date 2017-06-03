package org.miaowo.miaowo.ui.load_more_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class LMLAdapter<E>(val creator: LMLViewCreator<E>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return creator.createHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        creator.bindView(getItem(position), holder, getItemViewType(position))
    }

    override abstract fun getItemCount(): Int
    abstract fun getItem(position: Int): E?
    abstract fun update(newItems: List<E>)
    abstract fun append(newItems: List<E>, toHead: Boolean)
    abstract fun insert(item: E, toHead: Boolean)
    abstract fun clear()
    abstract fun load(back: Boolean = false, list: List<E>)
}
