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
    public void show(@IdRes int container, Fragment fragment) {
        if (fragment.isVisible()) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            fragments.stream().forEach(fragment1 -> {
                fragment1.onDetach();
                transaction.hide(fragment1);
            });
        }
        if (!fragment.isAdded()) {
            transaction.add(container, fragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }
}
