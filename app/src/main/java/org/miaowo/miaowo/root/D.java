package org.miaowo.miaowo.root;

import org.miaowo.miaowo.view.activity.Miao;
import org.miaowo.miaowo.bean.data.User;

/**
 * D == Data
 * 用于存放程序运行时一些即时数据
 * Created by lq2007 on 16-12-7.
 */
public class D {
    private static D instance = null;
    private D () {
        guest = new User(-1, "流浪喵", "欢迎来到喵窝", "", "default");
        thisUser = guest;
    }
    public static D getInstance() {
        if (instance == null) {
            instance = new D();
        }
        return instance;
    }

    public Miao miaoActivity = null;
    public BaseActivity activeActivity = null;
    public ListFragment shownFragment = null;
    public User thisUser;
    public User guest;
}
