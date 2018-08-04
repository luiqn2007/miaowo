package org.miaowo.miaowo.base.extra

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Fragment 扩展方法
 * Created by luqin on 17-6-22.
 */
fun inflateId(@LayoutRes layoutId: Int, inflater: LayoutInflater?, container: ViewGroup?) = inflateId(inflater, layoutId, container)
fun inflateId(inflater: LayoutInflater?, @LayoutRes layoutId: Int, container: ViewGroup?) = inflater?.inflate(layoutId, container, false)