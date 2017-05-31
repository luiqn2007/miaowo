package org.miaowo.miaowo.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.function.BiFunction
import kotlin.properties.Delegates

abstract class BaseFragment() : Fragment() {

    private var layout by Delegates.notNull<BiFunction<LayoutInflater?, ViewGroup?, View?>>()

    constructor(@LayoutRes layoutId: Int) : this() {
        layout = BiFunction { inflater, container ->
            return@BiFunction inflater?.inflate(layoutId, container, false)
        }
    }

    constructor(view: View?) : this() {
        layout = BiFunction { _, _ ->
            return@BiFunction view
        }
    }

    var processController: ProcessController? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            layout.apply(inflater, container)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }


    /**
     * 在此处初始化页面
     * @param view 根视图
     */
    abstract fun initView(view: View?)

    interface ProcessController {
        fun setProcess(process: Int, message: String)
        fun processError(e: Exception) = BaseActivity.get?.handleError(e)
        fun stopProcess()
    }
}


