package org.miaowo.miaowo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.base.extra.inflateId

/**
 * 通知页
 */
class NotificationFragment : Fragment() {

    companion object {

        fun newInstance(): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    var mListenerI: IMiaoListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_list, inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListenerI?.toolbar?.title = getString(R.string.notification)
        list.adapter = ListAdapter(object : ListAdapter.ViewCreator<Any> {
            override fun createHolder(parent: ViewGroup?, viewType: Int) = ListHolder(App.i, R.layout.list_notification, parent)

            override fun bindView(item: Any?, holder: ListHolder?, type: Int) {

            }
        })
    }
}
