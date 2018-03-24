package org.miaowo.miaowo.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_register.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.extra.*

/**
 * 注册
 */
class RegisterFragment : Fragment() {
    private var mListenerI: IMiaoListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_register, inflater, container)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
        mListenerI?.buttonVisible = false
        mListenerI?.setToolbar(getText(R.string.register_toolbar))
        mListenerI?.showBackIconOnToolbar()
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI?.showOptionIconOnToolbar()
        mListenerI = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login.setOnClickListener {
            mListenerI?.showOptionIconOnToolbar()
            mListenerI?.jump(IMiaoListener.JumpFragment.Login)
        }
        submit.setOnClickListener {
            val iPwd = pwd.text.toString()
            val cPwd = check.text.toString()
            if (iPwd == cPwd) {
                API.Profile.register(user.text.toString(), iPwd, mail.text.toString()) { process, result ->
                    when (result) {
                        is String -> {
                            if (result.toUpperCase() == "OK")
                                mListenerI?.login(API.user, submit)
                            else
                                Miao.i.process(process, result, Const.LOGIN)
                        }
                        is Exception -> {
                            Miao.i.process(result, Const.LOGIN)
                            activity?.unbindProcessable(Const.LOGIN)
                        }
                    }
                }
            } else activity?.handleError(R.string.err_check_password)
        }

        mail.setText(savedInstanceState?.getString(Const.EMAIL), TextView.BufferType.EDITABLE)
        user.setText(savedInstanceState?.getString(Const.USER), TextView.BufferType.EDITABLE)
        pwd.setText(savedInstanceState?.getString(Const.PWD), TextView.BufferType.EDITABLE)
        check.setText(savedInstanceState?.getString(Const.CHECK), TextView.BufferType.EDITABLE)
        activity?.hideKeyboard()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Const.EMAIL, mail.text.toString())
        outState.putString(Const.USER, user.text.toString())
        outState.putString(Const.PWD, pwd.text.toString())
        outState.putString(Const.CHECK, check.text.toString())
    }

    companion object {
        private var sInstance: RegisterFragment? = null
        val INSTANCE = sInstance ?: newInstance()

        fun newInstance(): RegisterFragment {
            if (sInstance == null) {
                val fragment = RegisterFragment()
                val args = Bundle()
                args.putBoolean(Const.FG_TO_BACKSTACK, false)
                fragment.arguments = args
                sInstance = fragment
            }
            return sInstance!!
        }
    }
}
