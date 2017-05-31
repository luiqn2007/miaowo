package org.miaowo.miaowo.ui.load_more_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.miaowo.miaowo.other.Const
import java.util.*

/**
 * 列表
 * Created by luqin on 16-12-28.
 */

open class LMLPageAdapter<E>(page: LMLPage<E>, private val mLoader: LMLPageAdapter.ViewLoaderCreator<E>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), LMLAdapter<E> {

    private val mPages = ArrayList<LMLPage<E>>()

    init {
        mPages.add(page)
    }

    constructor(items: List<E>, loader: ViewLoaderCreator<E>) : this(LMLPage(items), loader)
    constructor(loader: ViewLoaderCreator<E>) : this(LMLPage<E>(), loader)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return mLoader.createHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mLoader.bindView(getItem(position), holder, getItemViewType(position))
    }

    override fun getItemCount() = mPages.sumBy { it.items.size }

    private fun getPageStart(pageId: Int): Int {
        var start = 0
        for (page in mPages) {
            if (page.id == pageId)
                return start
            else
                start += page.items.size
        }
        return -1
    }

    private fun getPageUpdateCount(pageId: Int): Int {
        var after = 0
        var find = false
        for (page in mPages) {
            if (page.id == pageId) find = true
            if (find) after += page.items.size
        }
        return after
    }

    override fun getItemViewType(position: Int) = mLoader.setType(getItem(position), position)

    override fun getItem(position: Int): E? {
        if (mPages.size == 0) return null
        var pStart: Int
        var pEnd = 0
        for (page in mPages) {
            pStart = pEnd
            pEnd += page.items.size
            if (position < pEnd) return page.items[position - pStart]
        }
        return null
    }

    override fun update(newItems: List<E>) = update(newItems, Const.NO_ID)

    fun update(newItems: List<E>?, pageId: Int) {
        if (newItems == null || newItems.isEmpty()) return
        val page = getPage(pageId)
        if (page == null) addPage(newItems, pageId)
        else {
            page.items = newItems
            notifyItemRangeChanged(getPageStart(pageId), getPageUpdateCount(pageId))
        }
    }

    override fun append(newItems: List<E>, toHead: Boolean) = append(newItems, toHead, Const.NO_ID)

    fun append(newItems: List<E>?, toHead: Boolean, pageId: Int) {
        if (newItems == null || newItems.isEmpty()) return
        val list = getPage(pageId)?.items ?: mutableListOf()
        if (list is MutableList) list.addAll(if (toHead) 0 else list.size, newItems)
        notifyItemRangeChanged(getPageStart(pageId), getPageUpdateCount(pageId))
    }

    override fun insert(item: E, toHead: Boolean) = insert(item, toHead, Const.NO_ID)

    fun insert(item: E, toHead: Boolean, pageId: Int) {
        val items = getPage(pageId)?.items ?: mutableListOf()
        val position = if (toHead) 0 else items.size
        if (items is MutableList) items.add(position, item)
        notifyItemInserted(getPageStart(pageId) + position)
    }

    @JvmOverloads fun addPage(newItems: List<E>, pageId: Int, toHead: Boolean = false) {
        if (getPage(pageId) != null) {
            update(newItems, pageId)
            return
        }
        mPages.add(if (toHead) 0 else mPages.size, LMLPage(pageId, newItems))
        notifyItemRangeInserted(getPageStart(pageId), newItems.size)
    }

    fun removePage(pageId: Int) {
        val page = getPage(pageId)
        if (page != null) {
            val start = getPageStart(pageId)
            mPages.remove(page)
            if (mPages.isEmpty()) mPages.add(LMLPage(ArrayList<E>()))
            notifyItemRangeRemoved(start, page.items.size)
        }
    }

    fun getPage(pageId: Int): LMLPage<E>? = mPages.firstOrNull { it.id == pageId }

    override fun clear() {
        if (!mPages.isEmpty()) {
            mPages.clear()
            notifyDataSetChanged()
        }
    }

    interface ViewLoaderCreator<in E> {
        fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        fun bindView(item: E?, holder: RecyclerView.ViewHolder, type: Int)
        fun setType(item: E?, position: Int): Int
    }
}
