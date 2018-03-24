package org.miaowo.miaowo

import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_miao.*
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.ImageFragment
import org.miaowo.miaowo.fragment.PostFragment
import org.miaowo.miaowo.fragment.SendFragment
import org.miaowo.miaowo.fragment.UserFragment
import org.miaowo.miaowo.fragment.welcome.*
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.processView.IProcessable

/**
 * IMiaoListener
 * Created by lq200 on 2018/3/16.
 */
class MiaoHandler : IMiaoListener {
    private val mCleaningList = mutableListOf<User>()

    override val toolbar: Toolbar
        get() = Miao.i.toolBar
    override val toolbarImg: ImageView
        get() = Miao.i.toolBarImg
    override val button: FloatingActionButton
        get() = Miao.i.fab

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
            Miao.i.loadFragment(IndexFragment.INSTANCE)
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
                        it.filter { it != API.user.token }.forEach { API.Users.removeToken(it, {}) }
                        mCleaningList.remove(API.user)
                    }
                }
            }
        }
    }

    override fun jump(fg: IMiaoListener.JumpFragment, vararg params: Any?) {
        lInfo("jump To $fg: ${params.joinToString { it.toString() }}")
        when (fg) {
            IMiaoListener.JumpFragment.Login -> Miao.i.loadFragment(LoginFragment.INSTANCE)
            IMiaoListener.JumpFragment.Register -> Miao.i.loadFragment(RegisterFragment.INSTANCE)
            IMiaoListener.JumpFragment.GitHub -> Miao.i.loadFragment(GithubFragment.INSTANCE)
            IMiaoListener.JumpFragment.Forget -> Miao.i.loadFragment(ForgetFragment.INSTANCE)
            IMiaoListener.JumpFragment.User -> {
                if (params.isNotEmpty()) {
                    val p = params[0].toString()
                    if (p.startsWith("[int]"))
                        Miao.i.loadFragment(UserFragment.newInstance(objToInt(p)))
                    else
                        Miao.i.loadFragment(UserFragment.newInstance(params[0].toString()))
                }
            }
            IMiaoListener.JumpFragment.Reply -> if (params.isNotEmpty()) {
                when (params.size) {
                    0 -> return
                    1 ->
                        Miao.i.loadFragment(SendFragment.newInstance(objToInt(params[0])))
                    2 ->
                        Miao.i.loadFragment(SendFragment.newInstance(objToInt(params[0]), params[1].toString()))
                    else ->
                        Miao.i.loadFragment(SendFragment.newInstance(objToInt(params[0]), params[1].toString(), params[2].toString()))
                }
            }
            IMiaoListener.JumpFragment.Topic -> {
                if (params.isNotEmpty())
                    Miao.i.loadFragment(PostFragment.newInstance(objToInt(params[0])))
            }
            IMiaoListener.JumpFragment.Image -> {
                if (params.isNotEmpty())
                    Miao.i.loadFragment(ImageFragment.getInstance(params[0].toString()))
            }
        }
    }

    override fun showBackIconOnToolbar() {
        toolbarVisible = true
        val navigation = Miao.i.mNavigation
        if (navigation.drawerLayout?.isDrawerOpen(GravityCompat.START) == false) {
            navigation.closeDrawer()
            navigation.actionBarDrawerToggle.onDrawerOpened(navigation.drawerLayout)
            toolbar.setNavigationOnClickListener { Miao.i.onBackPressed() }
        }
    }

    override fun showOptionIconOnToolbar() {
        toolbarVisible = true
        val navigation = Miao.i.mNavigation
        val toggle = navigation.actionBarDrawerToggle
        toggle.onDrawerClosed(navigation.drawerLayout)
        toolbar.setNavigationOnClickListener(toggle.toolbarNavigationClickListener)
    }

    override fun setToolbar(title: CharSequence) {
        setToolbar(title, null)
    }

    override fun setToolbar(title: CharSequence, img: Drawable?) {
        val imgVisible = img != null
        toolbarVisible = true
        toolbarImgVisible = imgVisible
        toolbar.title = title
        if (imgVisible) toolbarImg.setImageDrawable(img)
    }

    override fun snackBar(msg: String, duration: Int): Snackbar {
        val coordinatorLayout = Miao.i.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        return Snackbar.make(coordinatorLayout, msg, duration)
    }

    private fun objToInt(obj: Any?): Int {
        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "USELESS_ELVIS")
        val ret = when (obj) {
            null -> -1
            is Int -> obj
            is Integer -> obj.toInt()
            is String -> {
                val rStr = if (obj.startsWith("[int]") && obj.length >= 6)
                    obj.substring(5 until obj.length)
                else obj
                rStr.toInt() ?: -1
            }
            else -> obj.toString().toInt() ?: -1
        }
        lInfo("convert ${obj.toString()} to $ret")
        return ret
    }
}