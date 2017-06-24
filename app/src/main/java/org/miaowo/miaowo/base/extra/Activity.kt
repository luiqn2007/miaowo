package org.miaowo.miaowo.base.extra

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.R
import java.util.ArrayList

/**
 * Activity 扩展方法
 * Created by luqin on 17-6-22.
 */
var _ShownActivity: Activity? = null
val activity
    get() = _ShownActivity

fun Activity.toast(msg: String, type: Int) {
    TastyToast.makeText(baseContext, msg, TastyToast.LENGTH_SHORT, type).show()
}
fun Activity.toast(@StringRes stringId: Int, type: Int) {
    TastyToast.makeText(baseContext, getString(stringId), TastyToast.LENGTH_SHORT, type).show()
}
fun Activity.handleError(err: Throwable?, handler: (Throwable?) -> Unit = {
    if (err != null) {
        it?.printStackTrace()
        toast(it?.message ?: getString(R.string.err_ill), TastyToast.ERROR)
    }
}) {
    handler(err)
}
fun Activity.handleError(@StringRes err: Int) {
    handleError(Exception(getString(err)))
}

fun Activity.uiThread(run: () -> Unit) { this.runOnUiThread(run) }
fun Activity.requestPermissions(vararg permissions: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val disallowedPermissions = ArrayList<String>()
        for (permission in permissions) {
            val chkResult = PermissionChecker.checkSelfPermission(this, permission)
            if (chkResult != PermissionChecker.PERMISSION_GRANTED) {
                disallowedPermissions.add(permission)
            }
        }
        if (disallowedPermissions.size != 0) {
            requestPermissions(disallowedPermissions.toTypedArray(), 0)
        }
    }
}

fun Activity.setProcess(process: Int, message: String) {
    _Fragments[this]?.get(0)?.processController?.setProcess(process, message)
}
fun Activity.stopProcess() {
    _Fragments[this]?.get(0)?.processController?.stopProcess()
}
fun Activity.processError(e: Exception) {
    _Fragments[this]?.get(0)?.processController?.processError(e)
}

fun AppCompatActivity.loadFragment(@IdRes container: Int = R.id.container, show: Fragment?) {
    val manager = this.supportFragmentManager
    if (_Fragments[this]?.isEmpty() ?: true) {
        manager.beginTransaction().add(container, show).show(show).commit()
    } else {
        manager.beginTransaction()
                .replace(container, show)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
                .addToBackStack(null)
                .commit()
    }
}

object MyActivityLifecycle : Application.ActivityLifecycleCallbacks {
    override fun onActivityResumed(activity: Activity?) {
        if (activity != _ShownActivity) _ShownActivity = activity
    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (activity is AppCompatActivity) _Fragments.remove(activity)
        if (activity == _ShownActivity) _ShownActivity = null
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (activity is AppCompatActivity)
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycle(activity), true)
    }

    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityStopped(activity: Activity?) {}
}