package org.miaowo.miaowo.handler

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
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
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyAnimatorListener
import org.miaowo.miaowo.other.template.EmptyCallback
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
    // 登录注册等功能
    fun login(id: Int? = null) {
        val processView = mActivity.findViewById<ProcessButton>(R.id.login)

        val stepIndex = object : EmptyCallback<Index>() {
            override fun onResponse(call: Call<Index>?, response: Response<Index>?) {
                setInputEnable(true)
                processView.setProcess(100f, mActivity.getString(R.string.process_login_over, API.user.username), false)
                MainActivity.CATEGORY_LIST = response?.body()?.categories
                mActivity.startActivity(Intent(mActivity.applicationContext, MainActivity::class.java))
                super.onResponse(call, response)
            }

            override fun onFailure(call: Call<Index>?, t: Throwable?) {
                t?.printStackTrace()
                setInputEnable(true)
                processView.setProcess(100f, t?.message, true)
            }
        }
        val stepUser = object : EmptyCallback<User>() {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                val user = response?.body()
                if (user != null) {
                    API.user = user
                    API.Docs.index().enqueue(stepIndex)
                }
                processView.setProcess(80f, mActivity.getString(R.string.process_category), false)
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                super.onFailure(call, t)
                t?.printStackTrace()
                setInputEnable(true)
                processView.setProcess(60f, t?.message, true)
            }
        }
        val stepToken = object : EmptyCallback<ResponseBody>() {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val obj = JSONObject(response?.body()?.string())
                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                    val newToken = obj.getJSONObject("payload").getString("token")
                    API.token.clear()
                    API.token.add(newToken)
                    API.Docs.user(inputName).enqueue(stepUser)
                } else {
                    processView.setProcess(40f, "无法获取 Token", true)
                }
                processView.setProcess(60f, mActivity.getString(R.string.process_user), false)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                super.onFailure(call, t)
                t?.printStackTrace()
                setInputEnable(true)
                processView.setProcess(40f, t?.message, true)
            }
        }
        val stepSearch = object : EmptyCallback<SearchResult>() {
            override fun onResponse(call: Call<SearchResult>?, response: Response<SearchResult>?) {
                if (response?.body() == null || response.body()!!.matchCount <= 0) {
                    processView.setProcess(0f, "无此用户", true)
                    setInputEnable(true)
                } else {
                    val uid = response.body()!!.users[0].uid
                    API.Users.tokenCreate(inputPwd, uid).enqueue(stepToken)
                }
                processView.setProcess(40f, mActivity.getString(R.string.process_get_token), false)
            }

            override fun onFailure(call: Call<SearchResult>?, t: Throwable?) {
                super.onFailure(call, t)
                t?.printStackTrace()
                setInputEnable(true)
                processView.setProcess(20f, t?.message, true)
            }
        }

        if (id == null) {
            processView.setProcess(20f, "搜索用户", false)
            API.Docs.searchUser(inputName, authorization = "Bearer ${Const.TOKEN_MASTER}", searchUid = 7).enqueue(stepSearch)
            setInputEnable(false)
        } else {
            API.Users.tokenCreate(inputPwd, id).enqueue(stepToken)
        }
    }
    fun register() {
        val processView = mActivity.findViewById<ProcessButton>(R.id.register)

        processView.setProcess(0f, mActivity.getString(R.string.process_reg), false)
        API.Users.create(inputName, inputPwd, inputEmail).enqueue(object : EmptyCallback<ResponseBody>() {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val obj = JSONObject(response?.body()?.string())
                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                    login()
                    processView.setProcess(100f, mActivity.getString(R.string.process_reg_over), false)
                } else {
                    processView.setProcess(100f, obj.getString("code"), true)
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                processView.setProcess(100f, t?.message, true)
            }
        })
    }
    fun forget() {
        val processView = mActivity.findViewById<ProcessButton>(R.id.forget)
        API.Users.password(API.user.password, inputPwd).enqueue(object : EmptyCallback<ResponseBody>() {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                processView.setProcess(100f, t?.message, true)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val msg = response?.body()?.string()
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
        ActivityUtils.getTopActivity().toast("暂未实现", TastyToast.ERROR)
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

            override fun onAnimationStart(animation: Animator?) {
                mButtonEnableTargets.forEach {
                    mActivity.findViewById<View>(it.key).isEnabled = it.value
                }
            }
        })
        mButtonEnableAnimator.duration = 300
    }
    fun playButtonAnimator() {
        mActivity.findViewById<ProcessButton>(R.id.login).setProcess(0f, mActivity.getString(R.string.login), false)
        mActivity.findViewById<ProcessButton>(R.id.forget).setProcess(0f, mActivity.getString(R.string.forget), false)
        mActivity.findViewById<ProcessButton>(R.id.github).setProcess(0f, mActivity.getString(R.string.github), false)
        mActivity.findViewById<ProcessButton>(R.id.register).setProcess(0f, mActivity.getString(R.string.register), false)
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
        if (mButtonEnableAnimator.isRunning)
            mButtonEnableAnimator.cancel()
        mButtonEnableAnimator.start()
    }
}