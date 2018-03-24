package org.miaowo.miaowo.fragment.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_setting_user.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.PwdShowListener

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

class UserSetting : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_setting_user, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        et_email.setText(API.user.email)
        et_user.setText(API.user.username)
        et_location.setHtml(API.user.location)
        et_website.setHtml(API.user.website)
        et_birthday.setHtml(API.user.birthday)
        et_signature.setHtmlWithImage(API.user.signature)

        show_ori.setOnTouchListener(PwdShowListener(et_pwd_ori))
        show_new.setOnTouchListener(PwdShowListener(et_pwd_new))

        if (API.user.isAdmin)
            et_pwd_ori.visibility = View.GONE

        update_pwd.setOnClickListener {
            val pwdNew = et_pwd_new.text.toString()
            val pwdOld = et_pwd_ori.text.toString()
            API.Users.password(API.user.uid, pwdOld, pwdNew) {
                (activity as? IMiaoListener)?.login(null, null)
            }
        }

        update_user.setOnClickListener {
            val user = et_user.text.toString()
            val email = et_email.text.toString()
            val location = et_location.text.toString()
            val website = et_website.text.toString()
            val birthday = et_birthday.text.toString()
            val signature = et_signature.text.toString()
            API.Users.update(user, email, user, website, location, birthday, signature) {
                if (it == Const.RET_OK) activity?.toast(getString(R.string.user_edit_ok), TastyToast.SUCCESS)
                else activity?.handleError(Exception(it))
            }
        }

        btn_remove.setOnClickListener {
            val builder = AlertDialog.Builder(App.i)
            with(builder) {
                setTitle(R.string.user_remove)
                setMessage(R.string.user_remove_wrong)
                setNegativeButton(R.string.give_up, null)
            }
            builder.setPositiveButton(R.string.delete) { dialog, _ ->
                API.Users.delete {
                    if (it == Const.RET_OK) {
                        API.Profile.logout()
                        activity?.finish()
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
