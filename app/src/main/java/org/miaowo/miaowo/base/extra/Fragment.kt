package org.miaowo.miaowo.base.extra

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.Const

/**
 * Fragment 扩展方法
 * Created by luqin on 17-6-22.
 */
val hFragmentCallbackList = mutableListOf<FragmentCall>()

fun inflateId(@LayoutRes layoutId: Int, inflater: LayoutInflater?, container: ViewGroup?) = inflateId(inflater, layoutId, container)
fun inflateId(inflater: LayoutInflater?, @LayoutRes layoutId: Int, container: ViewGroup?) = inflater?.inflate(layoutId, container, false)

@SuppressLint("CommitTransaction")
fun Fragment.loadFragment(fragment: Fragment?, @IdRes container: Int = R.id.container) {
    hLoadFragment(childFragmentManager, fragment, null, container)
}

fun Fragment.loadSelf(activity: AppCompatActivity, @IdRes container: Int = R.id.container) = activity.loadFragment(this, container)

fun Fragment.registerCall(callback: FragmentCall) = hFragmentCallbackList.add(callback)

fun Fragment.getCalls(tag: String? = null, from: Fragment? = null): List<FragmentCall> {
    return hFragmentCallbackList.filter {
        it.target == this
                && (tag == null || it.tag == tag)
                && (from == null || it.from == from)
    }
}

fun Fragment.submitCall(tag: String, vararg params: Any?): Boolean {
    val calls = getCalls(tag)
    if (calls.isEmpty()) return false
    for (call in calls) call.call(params)
    return true
}

fun Fragment.removeCall(tag: String?) {
    getCalls(tag).forEach {
        hFragmentCallbackList.remove(it)
    }
}

fun Fragment.submitAndRemoveCall(tag: String, vararg params: Any?): Boolean {
    val hasCalls = submitCall(tag, params)
    if (hasCalls) removeCall(tag)
    return hasCalls
}

object MyFragmentLifeRecycleCallback : FragmentManager.FragmentLifecycleCallbacks()

abstract class FragmentCall(val tag: String, val from: Fragment) {
    var target: Fragment? = null
    abstract fun call(vararg params: Any?)
}

fun hLoadFragment(fragmentManager: FragmentManager, fragment: Fragment?, lastFragment: Fragment?, container: Int): Boolean {
    lError("isAdd: ${fragment?.isAdded ?: false}, isVisible: ${fragment?.isVisible ?: false}")
    if (fragment?.arguments?.getBoolean(Const.FG_POP_ALL) == true) {
        for (i in 0 until fragmentManager.backStackEntryCount) fragmentManager.popBackStack()
    }
    if (fragment != null && !fragment.isVisible) {
        with(fragmentManager.beginTransaction()) {
            if (fragment.isAdded) remove(fragment)
            if (lastFragment?.isAdded == true) remove(lastFragment)
            if (fragment.arguments?.getBoolean(Const.FG_ADD_TO_BACK_STACK, true) == true) addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
            replace(container, fragment)
            commitAllowingStateLoss()
        }
        fragmentManager.executePendingTransactions()
        return true
    }
    return false
}