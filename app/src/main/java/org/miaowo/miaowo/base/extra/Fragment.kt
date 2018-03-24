package org.miaowo.miaowo.base.extra

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.ViewGroup
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.Const

/**
 * Fragment 扩展方法
 * Created by luqin on 17-6-22.
 */
fun inflateId(@LayoutRes layoutId: Int, inflater: LayoutInflater?, container: ViewGroup?)
        = inflater?.inflate(layoutId, container, false)

@SuppressLint("CommitTransaction")
fun Fragment.loadFragment(fragment: Fragment?, @IdRes container: Int = R.id.container, clearBackStack: Boolean = false) {
    lError("isAdd: ${fragment?.isAdded ?: false}, isVisible: ${fragment?.isVisible ?: false}")
    if (clearBackStack) for (i in 0 until childFragmentManager.backStackEntryCount) childFragmentManager.popBackStack()
    if (fragment != null && !fragment.isVisible) {
        with(childFragmentManager.beginTransaction()) {
            if (!fragment.isAdded) add(container, fragment)
            if (fragment.arguments?.getBoolean(Const.FG_TO_BACKSTACK, true) == true) addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
            replace(container, fragment)
            commitAllowingStateLoss()
        }
        childFragmentManager.executePendingTransactions()
    }
}

object MyFragmentLifeRecycleCallback : FragmentManager.FragmentLifecycleCallbacks()