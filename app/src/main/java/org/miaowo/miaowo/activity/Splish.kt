package org.miaowo.miaowo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.miaowo.miaowo.R
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.service.WebService
import org.miaowo.miaowo.base.extra.spGet

/**
 * 这是加载页
 * 没有设置延迟，估计一闪而过
 */
class Splish : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_welcome)
        if (spGet(Const.SP_FIRST_BOOT, true)) {
            firstInit()
        }
        startService(Intent(this, WebService::class.java))
        startActivity(Intent(this, Miao::class.java))
        finish()
        overridePendingTransition(0, 0)
    }

    private fun firstInit() {}
}
