package org.miaowo.miaowo.base

import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.miaowo.miaowo.Miao

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

    fun update(newItems: List<E>) {
        mItems.clear()
        mItems = newItems as? MutableList ?: newItems.toMutableList()
        runOnUiThread {
            notifyDataSetChanged()
        }
    }

    fun append(newItems: List<E>, toHead: Boolean = false) {
        prepareList()
        mRAddedSize = mLastAddedSize
        mRAddedPosition = mLastAddedPosition
        mLastAddedSize = newItems.size
        mLastAddedPosition = if (toHead) 0 else mItems.size
        mItems.addAll(mLastAddedPosition, newItems)
        runOnUiThread {
            notifyItemRangeInserted(mLastAddedPosition, newItems.size)
        }
    }

    fun insert(item: E, toHead: Boolean) {
        prepareList()
        mRAddedSize = mLastAddedSize
        mRAddedPosition = mLastAddedPosition
        mLastAddedSize = 1
        mLastAddedPosition = if (toHead) 0 else mItems.size
        mItems.add(mLastAddedPosition, item)
        runOnUiThread {
            notifyItemInserted(mLastAddedPosition)
        }
    }

    fun clear() {
        if (mItems.isNotEmpty())
            mItems.clear()
        runOnUiThread {
            notifyDataSetChanged()
        }
    }

    fun runOnUiThread(run: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            run()
        } else {
            Miao.i.runOnUiThread {
                run()
            }
        }
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
        fun createHolder(parent: ViewGroup?, viewType: Int): ListHolder
        fun bindView(item: E?, holder: ListHolder?, type: Int)
        fun setType(item: E?, position: Int): Int = 0
    }
}
