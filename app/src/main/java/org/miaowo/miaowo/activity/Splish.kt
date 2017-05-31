package org.miaowo.miaowo.activity

import android.content.Intent
import android.support.v7.app.AlertDialog
import okhttp3.Request
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.service.WebService
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.HttpUtil
import org.miaowo.miaowo.util.SpUtil

/**
 * 这是加载页
 * 没有设置延迟，估计一闪而过
 */
class Splish : BaseActivity(R.layout.fragment_welcome) {

    override fun initActivity() {
        if (SpUtil.getBoolean(Const.SP_FIRST_BOOT, true)) {
            firstInit()
        }
        Thread { checkUpdate() }.run()
        startService(Intent(this, WebService::class.java))
        startActivity(Intent(this, Miao::class.java))
        stopProcess()
        finish()
        overridePendingTransition(0, 0)
    }

    private fun firstInit() {}

    private fun checkUpdate() {
        API.Doc.version {
            if (it != null) {
                if (it.version > App.i.packageManager.getPackageInfo(App.i.packageName, 0).versionCode) {
                    val builder = AlertDialog.Builder(App.i)
                    builder.setTitle(it.versionName)
                    builder.setMessage(getString(R.string.update_msg, it.message))
                    builder.setPositiveButton(R.string.update_start) { dialog, _ ->
                        val request = Request.Builder().url(it.url).build()
                        HttpUtil.post(request) { _, repVersion -> BaseActivity.get?.update(repVersion) }
                        dialog.dismiss()
                    }
                    builder.setNegativeButton(R.string.update_later, null)
                    builder.show()
                }
            }
        }
    }
}
