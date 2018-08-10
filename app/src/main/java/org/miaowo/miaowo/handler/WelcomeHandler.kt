package org.miaowo.miaowo.handler

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.sdsmdg.tastytoast.TastyToast
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.MainActivity
import org.miaowo.miaowo.activity.WelcomeActivity
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.data.bean.Index
import org.miaowo.miaowo.data.bean.SearchResult
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.other.BaseHttpCallback
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyAnimatorListener
import org.miaowo.miaowo.ui.processView.ProcessButton
import retrofit2.Call
import retrofit2.Response

class WelcomeHandler(activity: WelcomeActivity) {
    private val mButtonAlphaBegins = mutableMapOf<Int, Float>()
    private val mButtonEnableTargets = mutableMapOf<Int, Boolean>()
    private val mButtonEnableAnimator = ValueAnimator.ofFloat(0f, 1f)
    private val mActivity = activity
    var inputPwd = ""
    var inputName = ""
    var inputEmail = ""
    var loginUid: Int? = null
    // 登录注册等功能
    fun login() {
        val processView = mActivity.findViewById<ProcessButton>(R.id.login)
        KeyboardUtils.hideSoftInput(mActivity)

        val stepIndex = object : BaseHttpCallback<Index>() {
            override fun onSucceed(call: Call<Index>?, response: Response<Index>) {
                setInputEnable(true)
                processView.setProcess(100f, mActivity.getString(R.string.process_login_over, API.user.username), false)
                MainActivity.CATEGORY_LIST = response.body()?.categories
                mActivity.startActivity(Intent(mActivity.applicationContext, MainActivity::class.java))
                mActivity.finish()
            }

            override fun onFailure(call: Call<Index>?, t: Throwable?, errMsg: Any?) {
                val error = when(errMsg) {
                    is String -> errMsg
                    is JSONObject -> errMsg.getString("message") ?: errMsg.getString("code")
                    else -> {
                        t?.printStackTrace()
                        t?.message
                    }
                }
                setInputEnable(true)
                processView.setProcess(100f, error, true)
            }
        }
        val stepUser = object : BaseHttpCallback<User>() {
            override fun onSucceed(call: Call<User>?, response: Response<User>) {
                val user = response.body()
                if (user != null) {
                    API.user = user
                    API.Docs.index().enqueue(stepIndex)
                }
                processView.setProcess(80f, mActivity.getString(R.string.process_category), false)
            }

            override fun onFailure(call: Call<User>?, t: Throwable?, errMsg: Any?) {
                val error = when(errMsg) {
                    is String -> errMsg
                    is JSONObject -> errMsg.getString("message") ?: errMsg.getString("code")
                    else -> {
                        t?.printStackTrace()
                        t?.message
                    }
                }
                setInputEnable(true)
                processView.setProcess(60f, error, true)
            }
        }
        val stepToken = object : BaseHttpCallback<ResponseBody>() {
            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                val obj = JSONObject(response.body()?.string())
                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                    val newToken = obj.getJSONObject("payload").getString("token")
                    API.token.clear()
                    API.token.add(newToken)
                    API.Docs.user(inputName.replace(" ", "-")).enqueue(stepUser)
                } else {
                    processView.setProcess(40f, "无法获取 Token", true)
                }
                processView.setProcess(60f, mActivity.getString(R.string.process_user), false)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?, errMsg: Any?) {
                val error = when(errMsg) {
                    is String -> errMsg
                    is JSONObject -> errMsg.getString("message") ?: errMsg.getString("code")
                    else -> {
                        t?.printStackTrace()
                        t?.message
                    }
                }
                setInputEnable(true)
                processView.setProcess(40f, error, true)
            }
        }
        val stepSearch = object : BaseHttpCallback<SearchResult>() {
            override fun onSucceed(call: Call<SearchResult>?, response: Response<SearchResult>) {
                if (response.body()?.matchCount ?: -1 <= 0) {
                    processView.setProcess(0f, "无此用户", true)
                    setInputEnable(true)
                } else {
                    val uid = response.body()?.users?.firstOrNull()?.uid ?: -1
                    API.Users.tokenCreate(inputPwd, uid).enqueue(stepToken)
                }
                processView.setProcess(40f, mActivity.getString(R.string.process_get_token), false)
            }

            override fun onFailure(call: Call<SearchResult>?, t: Throwable?, errMsg: Any?) {
                val error = when(errMsg) {
                    is String -> errMsg
                    is JSONObject -> errMsg.getString("message") ?: errMsg.getString("code")
                    else -> {
                        t?.printStackTrace()
                        t?.message
                    }
                }
                setInputEnable(true)
                processView.setProcess(20f, error, true)
            }
        }

        val uid = loginUid
        loginUid = null
        if (uid == null) {
            processView.setProcess(20f, "搜索用户", false)
            API.Docs.searchUserMaster(inputName).enqueue(stepSearch)
            setInputEnable(false)
        } else {
            API.Users.tokenCreate(inputPwd, uid).enqueue(stepToken)
        }
    }
    fun register() {
        val processView = mActivity.findViewById<ProcessButton>(R.id.register)
        KeyboardUtils.hideSoftInput(mActivity)

        processView.setProcess(0f, mActivity.getString(R.string.process_reg), false)
        API.Users.create(inputName, inputPwd, inputEmail).enqueue(object : BaseHttpCallback<ResponseBody>() {
            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                val obj = JSONObject(response.body()?.string())
                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                    loginUid = obj.getJSONObject("payload").getInt("uid")
                    login()
                    processView.setProcess(100f, mActivity.getString(R.string.process_reg_over), false)
                } else {
                    processView.setProcess(100f, obj.getString("code"), true)
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?, errMsg: Any?) {
                val error = when(errMsg) {
                    is String -> errMsg
                    is JSONObject -> errMsg.getString("message") ?: errMsg.getString("code")
                    else -> {
                        t?.printStackTrace()
                        t?.message
                    }
                }
                processView.setProcess(100f, error, true)
            }
        })
    }
    fun forget() {
        val processView = mActivity.findViewById<ProcessButton>(R.id.forget)
        KeyboardUtils.hideSoftInput(mActivity)
        API.Users.password(API.user.password, inputPwd).enqueue(object : BaseHttpCallback<ResponseBody>() {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?, errMsg: Any?) {
                val error = when(errMsg) {
                    is String -> errMsg
                    is JSONObject -> errMsg.getString("message")
                    else -> {
                        t?.printStackTrace()
                        t?.message
                    }
                }
                processView.setProcess(100f, error, true)
            }

            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                val msg = response.body()?.string()
                if (msg == null) {
                    processView.setProcess(100f, mActivity.getString(R.string.err_ill), true)
                } else {
                    if (msg.startsWith("[[") && msg.endsWith("]]")) {
                        processView.setProcess(100f, msg.substring(2, msg.length - 2), true)
                    } else {
                        val obj = JSONObject(msg)
                        if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                            processView.setProcess(100f, mActivity.getString(R.string.pwd_reset_success), false)
                        } else {
                            processView.setProcess(100f, obj.getString("code"), true)
                        }
                    }
                }
            }
        })
    }
    fun github() {
        val processView = mActivity.findViewById<ProcessButton>(R.id.github)
        KeyboardUtils.hideSoftInput(mActivity)
        mActivity.toast("暂未实现", TastyToast.ERROR)
    }
    private fun setInputEnable(isEnable: Boolean) {
        mActivity.findViewById<View>(R.id.user).isEnabled = isEnable
        mActivity.findViewById<View>(R.id.pwd).isEnabled = isEnable
        mActivity.findViewById<View>(R.id.email).isEnabled = isEnable
        mActivity.findViewById<View>(R.id.save).isEnabled = isEnable
    }
    // 动画相关
    fun initAnimator() {
        mButtonEnableAnimator.addUpdateListener {
            mButtonEnableTargets.forEach { kv ->
                val id = kv.key
                val target = kv.value
                val btn = mActivity.findViewById<View>(id)!!

                if (!mButtonAlphaBegins.containsKey(id))
                    mButtonAlphaBegins[id] = btn.alpha
                val alpha = mButtonAlphaBegins[id]!!

                if (target) {
                    btn.alpha = alpha + (1f - alpha) * (it.animatedValue as Float)
                } else {
                    btn.alpha = alpha - (alpha - 0.3f) * (it.animatedValue as Float)
                }
            }
        }
        mButtonEnableAnimator.addListener(object : EmptyAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                mButtonAlphaBegins.clear()
            }
        })
        mButtonEnableAnimator.duration = 300
    }
    fun playButtonAnimator() {
        mActivity.findViewById<ProcessButton>(R.id.login).hideProcess()
        mActivity.findViewById<ProcessButton>(R.id.forget).hideProcess()
        mActivity.findViewById<ProcessButton>(R.id.github).hideProcess()
        mActivity.findViewById<ProcessButton>(R.id.register).hideProcess()
        if (inputName.isNotBlank() && inputPwd.isNotBlank()) {
            // +login, +github
            mButtonEnableTargets[R.id.login] = true
            mButtonEnableTargets[R.id.github] = true
            if (inputEmail.isNotBlank()) {
                // +register, +forget
                mButtonEnableTargets[R.id.forget] = true
                mButtonEnableTargets[R.id.register] = true
            } else {
                // -register, -forget
                mButtonEnableTargets[R.id.forget] = false
                mButtonEnableTargets[R.id.register] = false
            }
        } else {
            // -login, -register, -github
            mButtonEnableTargets[R.id.login] = false
            mButtonEnableTargets[R.id.github] = false
            mButtonEnableTargets[R.id.forget] = inputEmail.isNotBlank()
            mButtonEnableTargets[R.id.register] = false
        }
        mButtonEnableTargets.forEach {
            mActivity.findViewById<View>(it.key).isEnabled = it.value
        }
        if (mButtonEnableAnimator.isRunning)
            mButtonEnableAnimator.cancel()
        mButtonEnableAnimator.start()
    }
}