package org.miaowo.miaowo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.blankj.utilcode.util.FragmentUtils
import kotlinx.android.synthetic.main.activity_setting.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.fragment.setting.AppSetting
import org.miaowo.miaowo.fragment.setting.UserSetting

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        FragmentUtils.add(supportFragmentManager, UserSetting.newInstance(), R.id.container)
                    }
                    1 -> {
                        FragmentUtils.add(supportFragmentManager, AppSetting.newInstance(), R.id.container)
                    }
                }
            }
        })
    }
}
