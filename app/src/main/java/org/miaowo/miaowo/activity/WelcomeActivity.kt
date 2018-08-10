package org.miaowo.miaowo.activity

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_welcome.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.data.config.VersionMessage
import org.miaowo.miaowo.handler.WelcomeHandler
import org.miaowo.miaowo.other.ActivityHttpCallback
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyTextWatcher
import retrofit2.Call
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

    private val mExitTouch = arrayOf(-1L, -1L)
    private val mHandler = WelcomeHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initInput()
        initButton()
        mHandler.initAnimator()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // 首次启动
        if (App.SP.getBoolean(Const.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder(this)
                    .setTitle("人人都有\"萌\"的一面")
                    .setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%")
                    .setNegativeButton("我最萌(•‾̑⌣‾̑•)✧˖°") { dialog, _ -> dialog.dismiss() }
                    .show()
            App.SP.put(Const.SP_FIRST_BOOT, false)
        }
        // 更新
        API.Docs.version().enqueue(object : ActivityHttpCallback<VersionMessage>(this) {
            override fun onSucceed(call: Call<VersionMessage>?, response: Response<VersionMessage>) {
                val msg = response.body()
                if (msg != null && msg.version > packageManager.getPackageInfo(App.i.packageName, 0).versionCode) {
                    with(AlertDialog.Builder(App.i)) {
                        setTitle(msg.versionName)
                        setMessage(getString(R.string.update_msg, msg.message))
                        setPositiveButton(R.string.update_start) { dialog, _ ->
                            App.i.update(msg.url)
                            dialog.dismiss()
                        }
                        setNegativeButton(R.string.update_later, null)
                    }.show()
                }
            }
        })
    }

    override fun onBackPressed() {
        when {
            // 小键盘
            KeyboardUtils.isSoftInputVisible(this) -> KeyboardUtils.hideSoftInput(this)
            // Pop Fragment
            supportFragmentManager.isStateSaved -> FragmentUtils.pop(supportFragmentManager)
            else -> {
                mExitTouch[0] = SystemClock.currentThreadTimeMillis()
                if (mExitTouch[1] < 0 || mExitTouch[0] - mExitTouch[1] > 500) {
                    mExitTouch[1] = mExitTouch[0]
                    toast(R.string.exit_touch, TastyToast.DEFAULT)
                } else AppUtils.exitApp()
            }
        }
    }

    private fun initInput() {
        user.editText?.addTextChangedListener(object : EmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                mHandler.inputName = s?.toString() ?: ""
                mHandler.playButtonAnimator()
            }
        })
        pwd.editText?.addTextChangedListener(object : EmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                mHandler.inputPwd = s?.toString() ?: ""
                mHandler.playButtonAnimator()
            }
        })
        email.editText?.addTextChangedListener(object : EmptyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                mHandler.inputEmail = s?.toString() ?: ""
                mHandler.playButtonAnimator()
            }
        })
        save.setOnCheckedChangeListener { _, checked ->
            App.SP.put(Const.SP_SAVE, checked)
        }
        save.isChecked = App.SP.getBoolean(Const.SP_SAVE, false)

        val savedUser = App.SP.getString(Const.SP_USER, "")
        val savedPwd = App.SP.getString(Const.SP_PWD, "")
        user.editText?.setText(savedUser)
        pwd.editText?.setText(savedPwd)
        mHandler.inputName = savedUser
        mHandler.inputPwd = savedPwd
        mHandler.playButtonAnimator()
    }

    private fun initButton() {
        login.setOnClickListener {
            App.SP.run {
                if (save.isChecked) {
                    put(Const.SP_USER, mHandler.inputName)
                    put(Const.SP_PWD, mHandler.inputPwd)
                }
            }
            mHandler.login()
        }

        register.setOnClickListener {
            App.SP.run {
                if (save.isChecked) {
                    put(Const.SP_USER, mHandler.inputName)
                    put(Const.SP_PWD, mHandler.inputPwd)
                }
            }
            mHandler.register()
        }

        forget.setOnClickListener { mHandler.forget() }

        github.setOnClickListener { mHandler.github() }
    }
}