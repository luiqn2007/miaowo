package org.miaowo.miaowo.fragment

import android.os.Bundle
import okhttp3.Response
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.TitleListAdapter
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.Title
import org.miaowo.miaowo.bean.data.TitleList
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.API

class UnreadFragment : BaseListFragment<Title>() {

    override fun setAdapter() = TitleListAdapter()

    override fun setItems(page: Int, set: (list: List<Title>) -> Unit) {
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
