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
    hLoadFragment(childFragmentManager, fragment, fragment?.arguments?.getString(Const.TAG)
            ?: "NO_TAG", container)
}

fun Fragment.loadSelf(activity: AppCompatActivity, @IdRes container: Int = R.id.container) = activity.loadFragment(this, container)
fun Fragment.showSelf(activity: AppCompatActivity, @IdRes container: Int = R.id.container) = activity.showFragment(this, container)

fun Fragment.registerCall(callback: FragmentCall) {
    lInfo("registerCall: ${callback.from} -> ${callback.target}, tag = ${callback.tag}")
    hFragmentCallbackList.add(callback)
}

fun Fragment.getCalls(tag: String? = null, from: Fragment? = null): List<FragmentCall> {
    lInfo("getCall: $from -> $this, tag = $tag")
    return hFragmentCallbackList.filter {
        it.target == this
                && (tag == null || it.tag == tag)
                && (from == null || it.from == from)
    }
}

fun Fragment.submitCall(tag: String, vararg params: Any?): Boolean {
    val calls = getCalls(tag)
    lInfo(calls)
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

abstract class FragmentCall(val tag: String, val from: Fragment, val target: Fragment) {
    abstract fun call(vararg params: Any?)
}

fun hLoadFragment(fragmentManager: FragmentManager, fragment: Fragment?, tag: String, container: Int): Boolean {
    if (fragment?.arguments?.getBoolean(Const.FG_POP_ALL) == true) {
        for (i in 0 until fragmentManager.backStackEntryCount) fragmentManager.popBackStackImmediate()
    }
    if (fragment != null && !fragment.isVisible) {
        val isAdded = (fragment.isAdded) || (fragmentManager.findFragmentByTag(fragment.tag) != null)
        fragmentManager.beginTransaction().apply {
            // animator
            if (!isAdded) {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
            }
            // show
            if (fragment.arguments?.getBoolean(Const.FG_ADD_TO_BACK_STACK, true) != false)
                addToBackStack(null)
            replace(container, fragment, tag)
        }.commitAllowingStateLoss()

        if (!isAdded)
            fragmentManager.executePendingTransactions()
        return true
    }
    return false
}

fun hShowFragment(fragmentManager: FragmentManager, showFragment: Fragment?, tag: String, container: Int): Boolean {
    if (showFragment != null && !showFragment.isVisible) {
        val hideFragment = fragmentManager.fragments.filter { it.isVisible }
        val isAdded = (showFragment.isAdded) || (fragmentManager.findFragmentByTag(showFragment.tag) != null)
        fragmentManager.beginTransaction().apply {
            // animator
            if (!isAdded) {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
            }
            // show
            if (showFragment.arguments?.getBoolean(Const.FG_ADD_TO_BACK_STACK, true) != false)
                addToBackStack(null)
            if (!isAdded) add(container, showFragment, tag)
            hideFragment.forEach { hide(it) }
            show(showFragment)
        }.commitAllowingStateLoss()

        if (!isAdded)
            fragmentManager.executePendingTransactions()
        return true
    }
    return false
}