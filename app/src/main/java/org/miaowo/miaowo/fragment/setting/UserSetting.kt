package org.miaowo.miaowo.fragment.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_setting_user.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.other.PwdShowListener
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.base.extra.lTODO

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

class UserSetting : Fragment() {

    private var mUser = API.loginUser!!

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_setting_user, inflater, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        et_email.setText(mUser.email)
        et_user.setText(mUser.username)
        FormatUtil.parseHtml(mUser.location) { et_location.setText(it) }
        FormatUtil.parseHtml(mUser.website) { et_website.setText(it) }
        FormatUtil.parseHtml(mUser.birthday) { et_birthday.setText(it) }
        FormatUtil.parseHtml(mUser.signature) { et_signature.setText(it) }

        show_ori.setOnTouchListener(PwdShowListener(et_pwd_ori))
        show_new.setOnTouchListener(PwdShowListener(et_pwd_new))

        update_pwd.setOnClickListener {
            val pwdNew = et_pwd_new.text.toString()
            val pwdOld = et_pwd_ori.text.toString()
            API.Use.changePwd(pwdOld, pwdNew) {
                lTODO(it)
            }
        }

        update_user.setOnClickListener {
            val user = et_user.text.toString()
            val email = et_email.text.toString()
            val location = et_location.text.toString()
            val website = et_website.text.toString()
            val birthday = et_birthday.text.toString()
            val signature = et_signature.text.toString()
            API.Use.updateUser(user, email, user, website, location, birthday, signature) {
                if ("ok" == it) activity?.toast(getString(R.string.user_edit_ok), TastyToast.SUCCESS)
                else activity?.handleError(Exception(it))
            }
        }

        btn_remove.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder) {
                setTitle(R.string.user_remove)
                setMessage(R.string.user_remove_wrong)
                setNegativeButton(R.string.give_up, null)
            }
            builder.setPositiveButton(R.string.delete) { dialog, _ ->
                API.Use.removeUser {
                    if ("ok" ==it) {
                        API.Login.logout()
                        activity.finish()
                    } else activity?.handleError(Exception(it))
                }
                dialog.dismiss()
            }
            builder.show()
        }
    }


    companion object {

        fun newInstance(): UserSetting {
            val args = Bundle()
            val fragment = UserSetting()
            fragment.arguments = args
            return fragment
        }
    }
}
