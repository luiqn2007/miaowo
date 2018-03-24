package org.miaowo.miaowo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API

/**
 * 主题详情
 */
class PostFragment : Fragment() {

    companion object {
        fun newInstance(tid: Int): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            fragment.arguments = args
            return fragment
        }
    }

    private var mListenerI: IMiaoListener? = null
    private var mTid = -1
    private val mAdapter = PostAdapter(true, true)
    private val mLoader = object : SpringView.OnFreshListener {
        override fun onLoadmore() {

        }

        override fun onRefresh() {
            if (mTid > 0) {
                API.Doc.topic(mTid) {
                    activity?.runOnUiThread {
                        mAdapter.update(it?.posts ?: emptyList())
                        springView.onFinishFreshAndLoad()
                    }
                }
            }
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
        mListenerI?.showBackIconOnToolbar()
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI?.showOptionIconOnToolbar()
        mListenerI = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_list, inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListenerI?.setToolbar("问题详情")

        mTid = arguments!!.getInt(Const.ID, -1)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = mAdapter

        springView.setListener(mLoader)
        springView.header = DefaultHeader(context)
        springView.footer = DefaultFooter(context)

        mLoader.onRefresh()
    }
}
