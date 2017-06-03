package org.miaowo.miaowo.fragment

import android.os.Bundle
import org.miaowo.miaowo.adapter.TitleListAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.Title
import org.miaowo.miaowo.ui.load_more_list.LMLAdapter
import org.miaowo.miaowo.util.API

class UnreadFragment : BaseListFragment<Title>() {

    override fun setAdapter() = TitleListAdapter()

    override fun setItems(adapter: LMLAdapter<Title>, set: (list: List<Title>) -> Unit) {
        API.Doc.unread {
            set(it?.topics ?: mutableListOf())
        }
    }

    companion object {
        fun newInstance(): UnreadFragment {
            val args = Bundle()
            val fragment = UnreadFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
