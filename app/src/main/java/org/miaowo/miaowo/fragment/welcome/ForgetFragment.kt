package org.miaowo.miaowo.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_forget.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.hideKeyboard
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 忘记密码
 */
class ForgetFragment : Fragment() {
    private var mListenerI: IMiaoListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_forget, inflater, container)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
        mListenerI?.decorationVisible = true
        mListenerI?.setToolbar(getString(R.string.forget))
        mListenerI?.showBackIconOnToolbar()
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI?.showOptionIconOnToolbar()
        mListenerI = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mail.setText(savedInstanceState?.getString(Const.EMAIL), TextView.BufferType.EDITABLE)
        submit.setOnClickListener {
            API.Profile.resetPassword(mail.text.toString()) {
                activity?.runOnUiThread {
                    if (it != Const.RET_OK) activity?.handleError(it)
                    else activity?.toast(R.string.pwd_reset_success, TastyToast.SUCCESS)
                    activity?.hideKeyboard()
                }
            }
        }
        mListenerI?.button?.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Const.EMAIL, mail.text.toString())
    }

    companion object {
        private var sInstance: ForgetFragment? = null
        val INSTANCE: ForgetFragment = sInstance ?: newInstance()

        fun newInstance(): ForgetFragment {
            if (sInstance == null) {
                val fragment = ForgetFragment()
                val args = Bundle()
                args.putString(Const.TAG, fragment.javaClass.name)
                fragment.arguments = args
                sInstance = fragment
            }
            return sInstance!!
        }
    }
}
