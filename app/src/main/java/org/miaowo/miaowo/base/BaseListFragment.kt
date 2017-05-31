package org.miaowo.miaowo.base

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.R
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LoadMoreList

/**
 * 加载的 Fragment
 * Created by luqin on 17-5-4.
 */

abstract class BaseListFragment<ITEM> : BaseFragment(LoadMoreList(App.i)) {

    protected var mAdapter: LMLPageAdapter<ITEM>? = null
    protected var mList: LoadMoreList? = null
    private var mPage = 1

    override fun initView(view: View?) {
        mList = view as LoadMoreList
        mAdapter = setAdapter()

        mList?.adapter = mAdapter!!
        mList?.layoutManager = LinearLayoutManager(context)
        mList?.setPullRefresher(true) { load(true) }
        mList?.setPushRefresher(true) { load() }
        mList?.load()
    }

    private fun load(back: Boolean = false) {
        if (back) {
            if (mPage > 1) mPage--
        }
        else mPage++
        setItems(mPage) {
            mList?.loadOver()

            if (it.isEmpty()) {
                if (!back) mPage--
                TastyToast.makeText(context, getString(R.string.err_page_end), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show()
            } else if (back) {
                if (mPage == 1) mAdapter?.clear()
                mAdapter?.addPage(it, mPage, true)
                mAdapter?.removePage(mPage + 2)
            } else {
                mAdapter?.addPage(it, mPage)
                mAdapter?.removePage(mPage - 2)
            }
        }
    }

    abstract fun setAdapter(): LMLPageAdapter<ITEM>
    abstract fun setItems(page: Int, set: (list: List<ITEM>) -> Unit)
}
