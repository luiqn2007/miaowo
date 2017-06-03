package org.miaowo.miaowo.ui.load_more_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * 用于创建每一项的视图
 */
interface LMLViewCreator<in E> {
    fun createHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
    fun bindView(item: E?, holder: RecyclerView.ViewHolder?, type: Int)
    fun setType(item: E?, position: Int): Int = 0
}