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
    /**
     * 显示一个Fragment
     * @param manager FragmentManager
     * @param layoutId 显示Fragment的FrameLayout
     * @param fragment 要显示的Fragment
     */
    public static void showFragment(FragmentManager manager, @IdRes int layoutId, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(layoutId, fragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    private static void hideFragment(FragmentManager manager, Fragment... fragments) {
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
     * @param manager 要隐藏的FragmentManager
     */
    public static void hideAllFragment(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                hideFragment(manager, fragment);
            }
        }
    }
}
