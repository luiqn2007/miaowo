package org.miaowo.miaowo.other

import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_miao.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.welcome.IndexFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.ui.processView.IProcessable

/**
 * IMiaoListener
 * Created by lq200 on 2018/3/16.
 */
class MiaoHandler : IMiaoListener {
    private val mCleaningList = mutableListOf<User>()

    override val toolbar: Toolbar get() = Miao.i.toolBar
    override val toolbarImg: ImageView get() = Miao.i.toolBarImg
    override val button: FloatingActionButton get() = Miao.i.fab

    override var decorationVisible: Boolean
        get() = buttonVisible && toolbarVisible
        set(value) {
            buttonVisible = value
            toolbarVisible = value
        }
    override var buttonVisible: Boolean
        get() = button.visibility == View.VISIBLE
        set(value) {
            button.visibility = if (value) View.VISIBLE else View.GONE
        }
    override var toolbarVisible: Boolean
        get() = toolbar.visibility == View.VISIBLE
        set(value) {
            toolbar.visibility = if (value) View.VISIBLE else View.GONE
        }
    override var toolbarImgVisible: Boolean
        get() = toolbarVisible && (Miao.i.toolBarImg.visibility == View.VISIBLE)
        set(value) {
            if (value) toolbarVisible = value
            toolbarImg.visibility = if (value) View.VISIBLE else View.GONE
        }

    override fun login(user: User?, processView: IProcessable?) {
        if (processView != null) Miao.i.bindProcessable(Const.LOGIN, processView)
        // 承担登出作用 user == null || logout
        if (user == null || user.uid == User.logout.uid) {
            API.Profile.logout()
            Miao.i.updateNavigationItems(emptyList())
            IndexFragment.INSTANCE.loadSelf(Miao.i)
            return
        }
        // 登录
        Miao.i.mLoginUser.add(user.username)
        API.Profile.login(user.username, user.password) { process, result ->
            Miao.i.runOnUiThread {
                when (result) {
                    is String -> {
                        Miao.i.process(process, result, Const.LOGIN)
                        if (process < 0) {
                            Miao.i.mLoginResult.add(Pair(System.currentTimeMillis(), false))
                            Miao.i.unbindProcessable(Const.LOGIN)
                        }
                    }
                    is List<*> -> {
                        Miao.i.updateUserProfile()
                        @Suppress("UNCHECKED_CAST")
                        Miao.i.updateNavigationItems(result as List<Category>)
                        Miao.i.mLoginResult.add(Pair(System.currentTimeMillis(), true))
                        Miao.i.unbindProcessable(Const.LOGIN)
                    }
                    is Exception -> {
                        Miao.i.process(result, Const.LOGIN)
                        Miao.i.mLoginResult.add(Pair(System.currentTimeMillis(), false))
                        Miao.i.unbindProcessable(Const.LOGIN)
                    }
                }
            }

            if (API.isLogin && spGet(Const.SP_CLEAN_TOKENS, false)) {
                if (!mCleaningList.contains(API.user)) {
                    mCleaningList.add(API.user)
                    API.Users.getTokens {
                        it.filter { it != API.token.token }.forEach { API.Users.removeToken(it, true, {}) }
                        mCleaningList.remove(API.user)
                    }
                }
            }
        }
    }

    override fun showBackIconOnToolbar() {
        toolbarVisible = true
        Miao.i.mNavigation.actionBarDrawerToggle.isDrawerIndicatorEnabled = false
        Miao.i.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showOptionIconOnToolbar() {
        Miao.i.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        Miao.i.mNavigation.actionBarDrawerToggle.isDrawerIndicatorEnabled = true
    }

    override fun setToolbar(title: CharSequence) {
        setToolbar(title, null)
    }

    override fun setToolbar(title: CharSequence, img: Drawable?) {
        val imgVisible = img != null
        toolbarVisible = true
        toolbarImgVisible = imgVisible
        toolbar.title = title
        showOptionIconOnToolbar()
        if (imgVisible) toolbarImg.setImageDrawable(img)
    }

    override fun addToolbarButton(groupId: Int, id: Int, order: Int, title: String, showAsAction: Int, listener: (item: MenuItem) -> Boolean): MenuItem {
        val menu = toolbar.menu
        val btn = menu.add(groupId, id, order, title)
        btn.setShowAsAction(showAsAction)
        btn.setOnMenuItemClickListener(listener)
        return btn
    }

    override fun addToolbarButton(groupId: Int, id: Int, order: Int, @StringRes title: Int, showAsAction: Int, listener: (item: MenuItem) -> Boolean): MenuItem {
        val menu = toolbar.menu
        val btn = menu.add(groupId, id, order, title)
        btn.setShowAsAction(showAsAction)
        btn.setOnMenuItemClickListener(listener)
        return btn
    }

    override fun removeToolbarButton(id: Int) {
        val menu = toolbar.menu
        menu.removeItem(id)
    }

    override fun resetToolbarButton() {
        val menu = toolbar.menu
        menu.clear()
    }
}