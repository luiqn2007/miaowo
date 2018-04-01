package org.miaowo.miaowo.fragment.welcome

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_login.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.hideKeyboard
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.base.extra.spPut
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 登录
 */
class LoginFragment : Fragment() {
    private var mListenerI: IMiaoListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_login, inflater, container)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
        mListenerI?.buttonVisible = false
        mListenerI?.setToolbar(getString(R.string.login_toolbar))
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isVisible) {
            outState.putString(Const.USER, user.editText?.text.toString())
            outState.putString(Const.PWD, pwd.editText?.text.toString())
            outState.putBoolean(Const.SAVE, save.isChecked)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sUser = savedInstanceState?.getString(Const.USER)
        val spUser = spGet(Const.SP_USER, "")
        val sPwd = savedInstanceState?.getString(Const.PWD)
        val spPwd = spGet(Const.SP_PWD, "")
        save.isChecked = spGet(Const.SP_SAVE, false)
                || savedInstanceState?.getBoolean(Const.SAVE) ?: false
        user.editText!!.setText(sUser ?: spUser, TextView.BufferType.EDITABLE)
        user.editText!!.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER))
                pwd.editText!!.requestFocus()
            true
        }
        pwd.editText!!.setText(sPwd ?: spPwd, TextView.BufferType.EDITABLE)
        pwd.editText!!.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER))
                login.callOnClick()
            true
        }
        login.setOnClickListener {
            spPut(Const.SP_SAVE, save.isChecked)
            activity?.hideKeyboard()
            if (save.isChecked) {
                spPut(Const.SP_USER, user.editText!!.text.toString())
                spPut(Const.SP_PWD, pwd.editText!!.text.toString())
            }
            mListenerI?.login(User(username = user.editText!!.text.toString(), password = pwd.editText!!.text.toString(), uid = Int.MAX_VALUE), login)
        }
        register.setOnClickListener { mListenerI?.showBackIconOnToolbar() }
//        register.setOnClickListener { mListenerI?.jump(IMiaoListener.JumpFragment.Register) }
        github.setOnClickListener { mListenerI?.jump(IMiaoListener.JumpFragment.GitHub) }
//        forget.setOnClickListener { mListenerI?.jump(IMiaoListener.JumpFragment.Forget) }
        forget.setOnClickListener { mListenerI?.showOptionIconOnToolbar() }
    }

    companion object {
        private var sInstance: LoginFragment? = null
        val INSTANCE = sInstance ?: newInstance()

        fun newInstance(): LoginFragment {
            if (sInstance == null) {
                val fragment = LoginFragment()
                val args = Bundle()
                args.putBoolean(Const.FG_ADD_TO_BACK_STACK, false)
                fragment.arguments = args
                sInstance = fragment
            }
            return sInstance!!
        }
    }
}
