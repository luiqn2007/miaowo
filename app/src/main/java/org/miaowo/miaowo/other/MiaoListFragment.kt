package org.miaowo.miaowo.other

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import com.liaoinstan.springview.widget.SpringView
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.base.extra.lInfoStack
import org.miaowo.miaowo.interfaces.IMiaoListener

/**
 * 列表
 * Created by lq200 on 2018/3/29.
 */
abstract class MiaoListFragment(title: String? = null, fabVisible: Boolean = false) : BaseListFragment(), SpringView.OnFreshListener {
    constructor(@StringRes titleId: Int, fabVisible: Boolean = false) : this(App.i.getString(titleId), fabVisible)

    val miaoListener get() = attach as? IMiaoListener
    var title = title
        set(value) {
            if (value != null) miaoListener?.setToolbar(value)
            field = value
        }
    var fabVisible = fabVisible
        set(value) {
            miaoListener?.buttonVisible = fabVisible
            field = fabVisible
        }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        lInfo("listFragment: $fabVisible, $title")
        if (hidden) miaoListener?.buttonVisible = false
        else miaoListener?.apply {
            buttonVisible = fabVisible
            if (title != null) setToolbar(title!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onHiddenChanged(false)
    }
}