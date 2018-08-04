package org.miaowo.miaowo.fragment.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ActivityUtils.getTopActivity
import com.blankj.utilcode.util.AppUtils
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_setting_user.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.error
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.other.ActivityCallback
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.PwdShowListener
import org.miaowo.miaowo.other.setHTML
import retrofit2.Call
import retrofit2.Response

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
        et_location.setHTML(API.user.location)
        et_website.setHTML(API.user.website)
        et_birthday.setHTML(API.user.birthday)
        et_signature.setHTML(API.user.signature)

        show_ori.setOnTouchListener(PwdShowListener(et_pwd_ori))
        show_new.setOnTouchListener(PwdShowListener(et_pwd_new))

        if (API.user.isAdmin)
            et_pwd_ori.visibility = View.GONE

        update_pwd.setOnClickListener {
            val pwdNew = et_pwd_new.text.toString()
            val pwdOld = et_pwd_ori.text.toString()
            API.Users.password(pwdOld, pwdNew, API.user.uid).enqueue(object : ActivityCallback<ResponseBody>(getTopActivity()) {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    val obj = JSONObject(response?.body()?.string())
                    if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                        var err = obj.getString("message")
                        if (err.isNullOrBlank()) err = obj.getString("code")
                        if (err.isNullOrBlank()) err = activity.getString(R.string.err_ill)
                        activity.error(err)
                    } else {
                        activity.toast(R.string.success, TastyToast.SUCCESS)
                    }
                }
            })
        }

        update_user.setOnClickListener {
            val user = et_user.text.toString()
            val email = et_email.text.toString()
            val location = et_location.text.toString()
            val website = et_website.text.toString()
            val birthday = et_birthday.text.toString()
            val signature = et_signature.text.toString()
            API.Users.update(user, email, user, website, location, birthday, signature).enqueue(object : ActivityCallback<ResponseBody>(getTopActivity()) {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    val obj = JSONObject(response?.body()?.string())
                    if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                        var err = obj.getString("message")
                        if (err.isNullOrBlank()) err = obj.getString("code")
                        if (err.isNullOrBlank()) err = activity.getString(R.string.err_ill)
                        activity.error(err)
                    } else {
                        activity.toast(R.string.user_edit_ok, TastyToast.SUCCESS)
                    }
                }
            })
        }

        btn_remove.setOnClickListener {
            val builder = AlertDialog.Builder(App.i)
            with(builder) {
                setTitle(R.string.user_remove)
                setMessage(R.string.user_remove_wrong)
                setNegativeButton(R.string.give_up, null)
            }
            builder.setPositiveButton(R.string.delete) { dialog, _ ->
                API.Users.delete().enqueue(object : ActivityCallback<ResponseBody>(getTopActivity()) {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val obj = JSONObject(response?.body()?.string())
                        if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                            var err = obj.getString("message")
                            if (err.isNullOrBlank()) err = obj.getString("code")
                            if (err.isNullOrBlank()) err = activity.getString(R.string.err_ill)
                            activity.error(err)
                        } else {
                            AppUtils.relaunchApp()
                        }
                    }
                })
                dialog.dismiss()
            }
            builder.show()
        }
    }

    companion object {

        fun newInstance(): UserSetting {
            val args = Bundle()
            val fragment = UserSetting()
            args.putString(Const.TAG, "${fragment.javaClass.name}.user.${API.user.uid}")
            fragment.arguments = args
            return fragment
        }
    }
}
