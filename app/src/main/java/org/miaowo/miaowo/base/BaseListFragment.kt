package org.miaowo.miaowo.base

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LoadMoreList

/**
 * 加载的 Fragment
 * Created by luqin on 17-5-4.
 */

abstract class BaseListFragment<ITEM> : BaseFragment(LoadMoreList(App.i)) {

    protected var mAdapter: LMLAdapter<ITEM>? = null
    protected var mList: LoadMoreList? = null

    override fun initView(view: View?) {
        mList = view as LoadMoreList
        mAdapter = setAdapter()

        mList?.adapter = mAdapter!!
        mList?.layoutManager = LinearLayoutManager(context)
        mList?.setPullRefresher(true) { load(true) }
        mList?.setPushRefresher(true) { load() }
        mList?.load()
    }

    protected fun load(back: Boolean = false) {
        mList?.loadOver()
        setItems(mAdapter!!) {
            mAdapter?.load(back, it)
        }
//        TastyToast.makeText(context, getString(R.string.err_page_end), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show()
    }

    abstract fun setAdapter(): LMLPageAdapter<ITEM>
    abstract fun setItems(adapter: LMLAdapter<ITEM>, set: (list: List<ITEM>) -> Unit)
}
