package org.miaowo.miaowo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.MainActivity
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.other.Const

/**
 * 收件箱
 */
class InboxFragment : Fragment() {

    private var attached: MainActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        attached = context as? MainActivity
    }

    override fun onDetach() {
        attached = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(inflater, R.layout.fragment_blog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attached?.run {
            toolBar.title = getString(R.string.inbox)
            fab.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(): InboxFragment {
            val fragment = InboxFragment()
            val args = Bundle()
            args.putString(Const.TAG, "${fragment.javaClass.name}.user.${API.user.uid}")
            fragment.arguments = args
            return fragment
        }
    }
}
