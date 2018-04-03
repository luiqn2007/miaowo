package org.miaowo.miaowo.base.extra

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.processView.IProcessable
import java.util.*
import android.app.Fragment as OldFragment


/**
 * Activity 扩展方法
 * Created by luqin on 17-6-22.
 */
@SuppressLint("StaticFieldLeak")
private val hActivityMaps = mutableMapOf<String, MutableMap<Activity?, *>>(
        Pair("ProcessBinding", mutableMapOf<Activity?, MutableMap<String, IProcessable>>()),
        Pair("FragmentLoaded", mutableMapOf<Activity?, Fragment>())
)
@Suppress("UNCHECKED_CAST")
private val hProcessBinding = hActivityMaps["ProcessBinding"] as MutableMap<Activity?, MutableMap<String, IProcessable>>
@Suppress("UNCHECKED_CAST")
private val hLoadedFragment = hActivityMaps["FragmentLoaded"] as MutableMap<Activity?, Fragment>

fun Activity.toast(msg: String, type: Int) = TastyToast.makeText(baseContext, msg, TastyToast.LENGTH_SHORT, type).show()
fun Activity.toast(@StringRes stringId: Int, type: Int) = TastyToast.makeText(baseContext, getString(stringId), TastyToast.LENGTH_SHORT, type).show()
fun Activity.handleError(@StringRes err: Int) = handleError(getString(err))
fun Activity.handleError(err: String, handler: (String) -> Unit = {
    lError(it)
    toast(it, TastyToast.ERROR)
}) {
    handler(err)
}
fun Activity.handleError(err: Throwable?, handler: (Throwable?) -> Unit = {
    if (err != null) {
        it?.printStackTrace()
        toast(it?.message ?: getString(R.string.err_ill), TastyToast.ERROR)
    }
}) {
    handler(err)
}

fun AppCompatActivity.requestPermissions(requestCode: Int, vararg permissions: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val disallowedPermissions = ArrayList<String>()
        for (permission in permissions) {
            val chkResult = PermissionChecker.checkSelfPermission(this, permission)
            if (chkResult != PermissionChecker.PERMISSION_GRANTED) {
                disallowedPermissions.add(permission)
            }
        }
        if (disallowedPermissions.size != 0) {
            requestPermissions(disallowedPermissions.toTypedArray(), requestCode)
        }
    }
}

fun AppCompatActivity.loadFragment(fragment: Fragment?, @IdRes container: Int = R.id.container) {
    hLoadFragment(supportFragmentManager, fragment, fragment?.arguments?.getString(Const.TAG)
            ?: "NO_TAG", container)
}

fun AppCompatActivity.showFragment(showFragment: Fragment?, hideFragment: Fragment, @IdRes container: Int = R.id.container) {
    hShowFragment(supportFragmentManager, showFragment, hideFragment, showFragment?.arguments?.getString(Const.TAG)
            ?: "NO_TAG", container)
}

val Activity.loadedFragment: Fragment? get() = hLoadedFragment[this]

fun Activity.process(msg: String, processKey: String?) {
    if (processKey != null) {
        getProcessable(processKey)?.run {
            processText = msg
            showProcess()
        }
    } else
        toast(msg, TastyToast.INFO)
}

fun Activity.process(msg: Int, processKey: String?) = process(getString(msg), processKey)
fun Activity.process(process: Float = 0f, msg: String? = null, processKey: String?) {
    if (processKey == null) {
        if (msg != null) {
            when {
                process < 0 -> toast("$msg", TastyToast.DEFAULT)
                process >= 100 -> toast("$msg", TastyToast.SUCCESS)
                else -> toast("$msg", TastyToast.INFO)
            }
        }
    } else {
        val fView = getProcessable(processKey)
        val isError = process < 0
        fView?.processText = msg ?: ""
        if (!isError) fView?.process = process
        else fView?.isError = isError
        fView?.showProcess()
    }
}

fun Activity.process(process: Float = 0f, msg: Int, processKey: String?) {
    process(process, getString(msg), processKey)
}

fun Activity.process(err: Throwable?, processKey: String?) {
    err?.printStackTrace()
    val errMsg = err?.message ?: getString(R.string.err_no_err)
    if (processKey == null)
        toast(errMsg, TastyToast.ERROR)
    else {
        getProcessable(processKey)?.run {
            isError = true
            processText = errMsg
            showProcess()
        }
    }
}

fun Activity.bindProcessable(processKey: String, processable: IProcessable) {
    hProcessBinding[this]?.put(processKey, processable)
}

fun Activity.getProcessable(processKey: String) = hProcessBinding[this]?.get(processKey)
fun Activity.unbindProcessable(processKey: String) {
    hProcessBinding[this]?.remove(processKey)
}

val Activity.isKeyboardOpen: Boolean
    get() {
        val contentView = findViewById<ViewGroup>(android.R.id.content)
        val r = Rect()
        contentView.getWindowVisibleDisplayFrame(r)
        return contentView.rootView.height - r.height() >= 200
    }

fun Activity.openKeyboard() {
    if (!isKeyboardOpen) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) view = View(this)
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }
}

fun Activity.hideKeyboard() {
    if (isKeyboardOpen) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) view = View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

inline fun <reified T : Activity> AppCompatActivity.startActivity() = startActivity(Intent(this, T::class.java))

object MyActivityLifecycle : Application.ActivityLifecycleCallbacks {
    override fun onActivityDestroyed(activity: Activity?) {
        hActivityMaps.values.forEach {
            it.filter {
                it.key == activity || it.value == activity
            }.forEach { k, _ -> it.remove(k) }
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        hProcessBinding[activity] = mutableMapOf()
    }

    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
}