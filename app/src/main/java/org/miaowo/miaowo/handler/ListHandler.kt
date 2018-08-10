package org.miaowo.miaowo.handler

import android.app.Activity
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.other.BaseListTouchListener

class ListHandler(activity: Activity) {
    private val mActivity = activity

    fun setTitle(title: String, @MenuRes menuRes: Int? = null, menuClickListener: (MenuItem) -> Boolean = {false}) {
        val bar = mActivity.findViewById<Toolbar>(R.id.toolBar)
        bar.apply {
            setTitle(title)
            setOnMenuItemClickListener { menuClickListener.invoke(it) }
        }
    }

    fun setTitle(@StringRes title: Int, @MenuRes menuRes: Int? = null, menuClickListener: (MenuItem) -> Boolean = {false}) {
        setTitle(mActivity.getString(title), menuRes, menuClickListener)
    }

    fun <T> setList(manager: RecyclerView.LayoutManager, adapter: ListAdapter<T>, data: List<T>? = null, click: BaseListTouchListener? = null) {
        val list = mActivity.findViewById<RecyclerView>(R.id.list)
        list.apply {
            this.layoutManager = manager
            this.adapter = adapter
        }
        if (click != null) { list.addOnItemTouchListener(click) }
        if (data != null) { adapter.update(data) }
    }
}