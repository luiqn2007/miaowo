package org.miaowo.miaowo.root;

import android.app.Application;

import com.bugtags.library.Bugtags;

/**
 * 为集成三方框架而设
 * Created by luqin on 16-12-28.
 */

public class MyApplication extends Application {
    // 信息传递
    final public static String EXTRA_ITEM = "extra_item";

    @Override
    public void onCreate() {
        super.onCreate();
        //在这里初始化
//        Bugtags.start("d49a368c1c57f42f2096cfd3222da4e7", this, Bugtags.BTGInvocationEventBubble);
    }
}
