package org.miaowo.miaowo.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class ListAdapter<E>(private val mCreator: ViewCreator<E>) : RecyclerView.Adapter<ListHolder>() {
    private var mRAddedSize = 0
    private var mRAddedPosition = 0
    private var mLastAddedSize = 0
    private var mLastAddedPosition = 0
    private var mItems = mutableListOf<E>()

    /**
     * 列表中的条目数最大值，当条目数多于该值时自动从 0 开始移除条目
     * >0   使用该值
     * =0   保留最近一次 append / insert 的结果
     * <0   不自动移除
     */
    var maxCount = -1

    val items = mItems

    override fun getItemCount() = mItems.size

    fun getPosition(item: E) = mItems.indexOf(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = mCreator.createHolder(parent, viewType)

    override fun onBindViewHolder(holder: ListHolder, pPosition: Int) = mCreator.bindView(getItem(pPosition), holder, getItemViewType(pPosition))

    override fun getItemViewType(position: Int) = mCreator.setType(getItem(position), position)

    fun getItem(position: Int) = mItems[position]

    fun update(newItems: List<E>?) {
        if (newItems == null) return
        mItems.clear()
        mItems = newItems as? MutableList ?: newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun append(newItems: List<E>?) {
        if (newItems == null) return
        prepareList()
        mRAddedSize = mLastAddedSize
        mRAddedPosition = mLastAddedPosition
        mLastAddedSize = newItems.size
        mLastAddedPosition = mItems.size
        mItems.addAll(mLastAddedPosition, newItems)
        notifyItemRangeInserted(mLastAddedPosition, newItems.size)
    }

    fun append(newItem: E) {
        prepareList()
        mItems.add(newItem)
        notifyItemInserted(mItems.size - 1)
    }

    fun insert(newItem: E, position: Int) {
        prepareList()
        val rPosition: Int = when {
            position < 0 -> 0
            position > itemCount -> itemCount
            else -> position
        }

        mItems.add(rPosition, newItem)
        notifyItemInserted(rPosition)
    }

    fun remove(position: Int) {
        prepareList()
        val rPosition: Int = when {
            position < 0 -> 0
            position > itemCount -> itemCount
            else -> position
        }

        mItems.removeAt(rPosition)
        notifyItemRemoved(rPosition)
    }

    fun clear() {
        if (mItems.isNotEmpty()) mItems.clear()
        notifyDataSetChanged()
    }

    private fun prepareList() {
        when {
            maxCount < 0 -> return
            maxCount == 0 -> mItems
                    .filterIndexed { index, _ -> index >= mRAddedPosition && index < mRAddedSize + mRAddedSize }
                    .forEach { mItems.remove(it) }
            mItems.size > maxCount -> {
                val removeCount = mItems.size - maxCount
                mItems.removeAll(mItems.subList(0, removeCount))
            }
        }
    }

    interface ViewCreator<in E> {
        fun createHolder(parent: ViewGroup, viewType: Int): ListHolder
        fun bindView(item: E, holder: ListHolder, type: Int)
        fun setType(item: E, position: Int): Int = 0
    }
}
