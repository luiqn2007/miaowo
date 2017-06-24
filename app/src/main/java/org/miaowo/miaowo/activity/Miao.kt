package org.miaowo.miaowo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import okhttp3.Request
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.fragment.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.ChatButton
import org.miaowo.miaowo.util.*
import kotlin.properties.Delegates

class Miao : AppCompatActivity(), MiaoFragment.OnFragmentInteractionListener {

    companion object {
        var fg_miao by Delegates.notNull<MiaoFragment>()
    }

    // 视图
    private val fg_square by lazyOf(SquareFragment.newInstance())
    private val fg_search by lazyOf(SearchFragment.newInstance())
    private val fg_topic by lazyOf(TopicFragment.newInstance())
    private val fg_unread by lazyOf(UnreadFragment.newInstance())
    private val fg_user by lazyOf(UserFragment.newInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_miao)
        // 绑定Fragment
        fg_miao = MiaoFragment.newInstance()
        loadFragment(show = fg_miao)
        // 显示对话框
        if (spGet(Const.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder(this)
                    .setTitle("人人都有\"萌\"的一面")
                    .setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%")
                    .setNegativeButton("我最萌(•‾̑⌣‾̑•)✧˖°") { dialog, _ -> dialog.dismiss() }
                    .show()
            spPut(Const.SP_FIRST_BOOT, false)
        }
        // 更新
        checkUpdate()
    }

    private fun checkUpdate() {
        API.Doc.version {
            if (it != null) {
                val versionCode = App.i.packageManager.getPackageInfo(App.i.packageName, 0).versionCode
                lInfo("Version: $versionCode to ${it.version}")
                if (it.version > versionCode) {
                    val builder = AlertDialog.Builder(activity!!)
                    builder.setTitle(it.versionName)
                    builder.setMessage(getString(R.string.update_msg, it.message))
                    builder.setPositiveButton(R.string.update_start) { dialog, _ ->
                        val request = Request.Builder().url(it.url).build()
                        HttpUtil.post(request, onUI = false) { _, repVersion -> App.i.update(repVersion) }
                        dialog.dismiss()
                    }
                    builder.setNegativeButton(R.string.update_later, null)
                    builder.show()
                }
            } else {
                lInfo("Version: Null")
            }
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
