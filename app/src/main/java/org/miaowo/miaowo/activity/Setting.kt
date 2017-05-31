package org.miaowo.miaowo.activity

import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_setting.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.fragment.setting.AppSetting
import org.miaowo.miaowo.fragment.setting.UserSetting

class Setting : BaseActivity(R.layout.activity_setting) {

    private var mUserSetting = UserSetting.newInstance()
    private var mAppSetting = AppSetting.newInstance()

    override fun initActivity() {
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
                                .replace(R.id.container, mUserSetting)
                                .commit()
                        1 -> supportFragmentManager.beginTransaction()
                                .replace(R.id.container, mAppSetting)
                                .commit()
                    }
                }
            })
        }
    }
}
