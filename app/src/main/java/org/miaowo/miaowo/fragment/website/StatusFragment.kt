package org.miaowo.miaowo.fragment.website

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.MainActivity
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.other.Const

/**
 * 服务状态
 * Created by lq2007 on 2017/7/22 0022.
 */
class StatusFragment : Fragment() {
    companion object {
        fun newInstance(): StatusFragment {
            val fragment = StatusFragment()
            val args = Bundle()
            args.putString(Const.TAG, fragment.javaClass.name)
            fragment.arguments = args
            return fragment
        }
    }

    private var mAttach: MainActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mAttach = context as? MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        @Suppress("DEPRECATION")
        mAttach = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_service_status, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAttach?.toolBar?.setTitle(R.string.status)
    }
}