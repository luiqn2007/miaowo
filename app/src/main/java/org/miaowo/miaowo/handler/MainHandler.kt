package org.miaowo.miaowo.handler

import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import org.miaowo.miaowo.API
import org.miaowo.miaowo.activity.MainActivity
import org.miaowo.miaowo.activity.WelcomeActivity
import org.miaowo.miaowo.data.bean.User

class MainHandler(val activity: MainActivity) {
    private val mTokenCleanList = mutableMapOf<Int, String>()
    private val mActivity = activity

    fun logout() {
        mTokenCleanList[API.user.uid] = API.token.first()
        API.token.clear()
        API.user = User.logout
        if (!ActivityUtils.isActivityExistsInStack(WelcomeActivity::class.java)) {
            mActivity.startActivity(Intent(mActivity.applicationContext, WelcomeActivity::class.java))
        }
        mActivity.finish()
    }
}