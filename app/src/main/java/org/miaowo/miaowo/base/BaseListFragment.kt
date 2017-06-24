package org.miaowo.miaowo.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.ui.load_more_list.LMLPageAdapter
import org.miaowo.miaowo.ui.load_more_list.LoadMoreList

/**
 * 加载的 Fragment
 * Created by luqin on 17-5-4.
 */

abstract class BaseListFragment<ITEM> : Fragment() {

    protected var mAdapter: LMLAdapter<ITEM>? = null
    protected var mList: LoadMoreList? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LoadMoreList(App.i)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
