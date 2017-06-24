package org.miaowo.miaowo.base.extra

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Fragment 扩展方法
 * Created by luqin on 17-6-22.
// */

val _Fragments = mutableMapOf<AppCompatActivity, MutableList<Fragment>>()
val _FragmentProcessControllers = mutableMapOf<Fragment, ProcessController?>()
var Fragment.processController
    set(value) {
        _FragmentProcessControllers.put(this, value)
    }
    get() = _FragmentProcessControllers[this]

fun inflateId(@LayoutRes layoutId: Int, inflater: LayoutInflater?, container: ViewGroup?)
        = inflater?.inflate(layoutId, container, false)

interface ProcessController {
    fun setProcess(process: Int, message: String)
    fun processError(e: Exception) = activity?.handleError(e)
    fun stopProcess()
}
class MyFragmentLifecycle(val activity: AppCompatActivity) : FragmentManager.FragmentLifecycleCallbacks() {
    init {
        _Fragments.put(activity, mutableListOf())
    }

    override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
        if (f != null) _Fragments[activity]?.add(f)
    }

    override fun onFragmentDetached(fm: FragmentManager?, f: Fragment?) {
        if (f != null) _Fragments[activity]?.remove(f)
    }
}