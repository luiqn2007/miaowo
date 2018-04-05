package org.miaowo.miaowo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.fragment.welcome.GithubFragment
import org.miaowo.miaowo.other.Const

/**
 * 收件箱
 */
class InboxFragment : Fragment() {
    private var mListenerI: IMiaoListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(inflater, R.layout.fragment_github, container)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListenerI?.toolbar?.title = getString(R.string.inbox)
        mListenerI?.button?.visibility = View.GONE
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
