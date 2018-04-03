package org.miaowo.miaowo.interfaces

import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
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
    fun snackBar(msg: String, duration: Int): Snackbar
}
