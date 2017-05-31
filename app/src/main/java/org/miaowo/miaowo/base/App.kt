package org.miaowo.miaowo.base

import android.app.Application
import org.miaowo.miaowo.util.LogUtil
import kotlin.properties.Delegates

/**
 *
 * Created by luqin on 16-12-28.
 */

class App : Application() {

    companion object {
        var i by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        i = this
        LogUtil.i("喵～～～ \n"
                + "凯神团队创建了好奇喵，后来团队解散，好奇喵服务器关停\n"
                + "幸好在 GC 前，小久等风纪组重新组织，系统菌重新创建网页版好奇喵，名为 喵窝\n"
                + "\n向凯神团队，好奇喵风纪组等每一位为维护好奇喵健康发展的喵们致敬\n"
                + "\n全体起立，敬礼\n")
    }
}
