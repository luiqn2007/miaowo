package org.miaowo.miaowo;

import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.fragment.ListFragment;
import org.miaowo.miaowo.root.view.BaseActivity;

/**
 * D == Data
 * 用于存放程序运行时一些即时数据
 * Created by lq2007 on 16-12-7.
 */
public class D {
    private static D instance = null;
    private D () {
        guest = new User(-1, "流浪喵", "欢迎来到喵窝");
        thisUser = guest;
    }
    public static D getInstance() {
        if (instance == null) {
            instance = new D();
        }
        return instance;
    }

    public BaseActivity activeActivity = null;
    public ListFragment shownFragment = null;
    public User thisUser;
    public User guest;
}