package org.miaowo.miaowo.fragment.setting

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_setting_user.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.base.BaseFragment
import org.miaowo.miaowo.other.PwdShowListener
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.LogUtil

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

class UserSetting : BaseFragment(R.layout.fragment_setting_user) {

    private var mUser = API.loginUser!!

    override fun initView(view: View?) {
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
                LogUtil.TODO(it)
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
                if ("ok" == it) BaseActivity.get?.toast(getString(R.string.user_edit_ok), TastyToast.SUCCESS)
                else BaseActivity.get?.handleError(Exception(it))
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
                    } else BaseActivity.get?.handleError(Exception(it))
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
