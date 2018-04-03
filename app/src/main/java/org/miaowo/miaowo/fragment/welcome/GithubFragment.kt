package org.miaowo.miaowo.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 使用 Github 登录
 */
class GithubFragment : Fragment() {
    private var mListenerI: IMiaoListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_github, inflater, container)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
        mListenerI?.buttonVisible = false
        mListenerI?.setToolbar(getString(R.string.github))
        mListenerI?.showBackIconOnToolbar()
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI?.showOptionIconOnToolbar()
        mListenerI = null
    }

    companion object {
        private var sInstance: GithubFragment? = null
        val INSTANCE = sInstance ?: newInstance()

        fun newInstance(): GithubFragment {
            val fragment = GithubFragment()
            val args = Bundle()
            args.putString(Const.TAG, fragment.javaClass.name)
            args.putBoolean(Const.FG_ADD_TO_BACK_STACK, false)
            fragment.arguments = args
            return fragment
        }
    }
}
