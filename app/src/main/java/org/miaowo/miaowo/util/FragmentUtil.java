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
     * @param container 显示Fragment的FrameLayout
     * @param fragment 要显示的Fragment
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
     * @param container
     * @param fragment
     */
    public void showOnly(@IdRes int container, Fragment fragment) {
        hideAll();
        show(container, fragment);
    }

    private void hide(Fragment... fragments) {
        FragmentTransaction transaction = manager.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    /**
     * 隐藏所有Fragment，通常用于显示一个Fragment之前的扫尾工作
     */
    private void hideAll() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                hide(fragment);
            }
        }
    }
}
