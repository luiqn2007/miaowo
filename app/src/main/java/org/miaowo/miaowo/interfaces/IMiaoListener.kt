package org.miaowo.miaowo.interfaces

import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.ui.processView.IProcessable

/**
 * Miao Activity 的 Fragment 功能接口
 * Created by lq2007 on 2017/10/12 0012.
 */
interface IMiaoListener {
    var decorationVisible: Boolean
    var toolbarVisible: Boolean
    var toolbarImgVisible: Boolean
    var buttonVisible: Boolean

    val toolbar: Toolbar
    val toolbarImg: ImageView
    val button: FloatingActionButton
    fun showBackIconOnToolbar()
    fun showOptionIconOnToolbar()
    fun login(user: User?, processView: IProcessable?)
    fun setToolbar(title: CharSequence)
    fun setToolbar(title: CharSequence, img: Drawable?)
    fun addToolbarButton(groupId: Int, id: Int, order: Int, title: String, showAsAction: Int, listener: (item: MenuItem) -> Boolean): MenuItem
    fun addToolbarButton(groupId: Int, id: Int, order: Int, @StringRes title: Int, showAsAction: Int, listener: (item: MenuItem) -> Boolean): MenuItem
    fun removeToolbarButton(id: Int)
    fun resetToolbarButton()
}
