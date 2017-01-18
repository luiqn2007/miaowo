package org.miaowo.miaowo;

import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.view.BaseActivity;

/**
 * D == Data
 * 用于存放程序运行时一些即时数据
 * Created by lq2007 on 16-12-7.
 */
public class D {
    private static D instance = null;
    private D () {}
    public static D getInstance() {
        if (instance == null) {
            instance = new D();
        }
        return instance;
    }

    public User activeChatUser = null;
    public BaseActivity activeActivity = null;
}
