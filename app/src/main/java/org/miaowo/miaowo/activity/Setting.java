package org.miaowo.miaowo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.fragment.setting.AppSetting;
import org.miaowo.miaowo.fragment.setting.UserSetting;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.LogUtil;

import butterknife.BindView;

public class Setting extends BaseActivity {
    @BindView(R.id.tab) TabLayout tab;

    private BaseFragment mUserSetting;
    private BaseFragment mAppSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initActivity() {
        TabLayout.Tab user = tab.newTab().setText("用户设置");
        TabLayout.Tab app = tab.newTab().setText("系统设置");

        mUserSetting = UserSetting.newInstance();
        mAppSetting = AppSetting.newInstance();

        loadFragment(R.id.container, mUserSetting);

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.i(tab.getPosition());
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mUserSetting)
                            .commit();
                } else if (tab.getPosition() == 1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mAppSetting)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tab.addTab(user, 0);
        tab.addTab(app, 1);
    }
}
