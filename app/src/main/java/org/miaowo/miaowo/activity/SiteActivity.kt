package org.miaowo.miaowo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.base.extra.toast

class SiteActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toast("暂未完成", TastyToast.ERROR)
        finish()
    }
}