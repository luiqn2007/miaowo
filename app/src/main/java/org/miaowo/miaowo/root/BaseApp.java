package org.miaowo.miaowo.root;

import android.app.Application;

import org.miaowo.miaowo.util.LogUtil;

/**
 * 为集成三方框架而设
 * Created by luqin on 16-12-28.
 */

public class BaseApp extends Application {
    // 信息传递
    final public static String EXTRA_ITEM = "extra_item";
    final public static String FILE_PROVIDER_URI = "org.miaowo.miaowo.fileProvider";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("喵～～～ \n" +
                "凯神团队创建了好奇喵，后来团队解散，好奇喵服务器关停\n" +
                "幸好在 GC 前，小久等风纪组重新组织，系统菌重新创建网页版好奇喵，名为 喵窝\n" +
                "\n向凯神团队，好奇喵风纪组，系统菌等每一位为维护好奇喵健康发展的喵们致敬\n" +
                "\n全体起立，敬礼\n");
    }
}
