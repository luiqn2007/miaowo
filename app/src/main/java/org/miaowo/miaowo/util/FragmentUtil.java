package org.miaowo.miaowo.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * 有关 Fragment 的工具类
 * Created by luqin on 17-1-17.
 */

public class FragmentUtil {

    private FragmentUtil(FragmentManager manager){ this.manager = manager; }
    public static FragmentUtil manager(FragmentManager manager) {
        return new FragmentUtil(manager);
    }

    private FragmentManager manager;

    /**
     * 显示一个Fragment
     * @param container 显示容器
     * @param fragment 要显示的 Fragment
     */
    private void show(@IdRes int container, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(container, fragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    /**
     * 关闭所有打开的 Fragment, 仅显示一页
     * @param container 显示容器
     * @param fragment 要显示的 Fragment
     */
    public void showOnly(@IdRes int container, Fragment fragment) {
        hide(manager.getFragments());
        show(container, fragment);
    }

    /*
     * 隐藏 Fragment
     */
    private void hide(List<Fragment> fragments) {
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }
}
