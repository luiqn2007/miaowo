package org.miaowo.miaowo.activity

import android.content.Intent
import android.support.v7.app.AlertDialog
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.fragment.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.ChatButton
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.SpUtil
import kotlin.properties.Delegates

class Miao : BaseActivity(R.layout.activity_miao), MiaoFragment.OnFragmentInteractionListener {

    companion object {
        var fg_miao by Delegates.notNull<MiaoFragment>()
    }

    // 视图
    private val fg_square by lazyOf(SquareFragment.newInstance())
    private val fg_search by lazyOf(SearchFragment.newInstance())
    private val fg_topic by lazyOf(TopicFragment.newInstance())
    private val fg_unread by lazyOf(UnreadFragment.newInstance())
    private val fg_user by lazyOf(UserFragment.newInstance())

    override fun initActivity() {
        // 绑定Fragment
        fg_miao = MiaoFragment.newInstance()
        loadFragment(show = fg_miao)
        // 显示对话框
        if (SpUtil.getBoolean(Const.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder(this)
                    .setTitle("人人都有\"萌\"的一面")
                    .setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%")
                    .setNegativeButton("我最萌(•‾̑⌣‾̑•)✧˖°") { dialog, _ -> dialog.dismiss() }
                    .show()
            SpUtil.putBoolean(Const.SP_FIRST_BOOT, false)
        }
    }

    override fun onChooserClick(position: Int) {
        when (position) {
            0 -> loadFragment(show = fg_square)
            1 -> loadFragment(show = fg_unread)
            2 -> loadFragment(show = fg_topic)
            3 -> loadFragment(show = fg_user)
            4 -> loadFragment(show = fg_search)
            5 -> startActivity(Intent(this, Setting::class.java))
            6 -> {
                API.Login.logout()
                fg_miao.prepareLogin()
                fg_miao.processController?.stopProcess()
                ChatButton.hide()
            }
        }
    }

    override fun onBackPressed() {
        if (!fg_miao.isVisible) loadFragment(show = fg_miao)
        else if (API.loginUser != null) API.Login.logout()
        else finish()
    }
}
