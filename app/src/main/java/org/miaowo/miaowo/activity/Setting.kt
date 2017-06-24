package org.miaowo.miaowo.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.loadFragment
import org.miaowo.miaowo.fragment.setting.AppSetting
import org.miaowo.miaowo.fragment.setting.UserSetting

class Setting : AppCompatActivity() {

    private var mUserSetting = UserSetting.newInstance()
    private var mAppSetting = AppSetting.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        loadFragment(R.id.container, mUserSetting)
        tab.run {
            addTab(tab.newTab().setText(R.string.setting_user), 0)
            addTab(tab.newTab().setText(R.string.setting_sys), 1)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> supportFragmentManager.beginTransaction()
                                .replace(R.id.container, mUserSetting).commit()
                        1 -> supportFragmentManager.beginTransaction()
                                .replace(R.id.container, mAppSetting).commit()
                    }
                }
            })
        }
    }
}
