package org.miaowo.miaowo.root;

import android.app.Application;
import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AlertDialog;

import org.miaowo.miaowo.service.RestartService;
import org.miaowo.miaowo.util.LogUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 为集成三方框架而设
 * Created by luqin on 16-12-28.
 */

public class BaseApp extends Application {
    // 信息传递
    final public static String EXTRA_ITEM = "extra_item";
    final public static String ERR = "err";
    final public static String ERR_LOG = "err_log";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("喵～～～ \n"
                + "凯神团队创建了好奇喵，后来团队解散，好奇喵服务器关停\n"
                + "幸好在 GC 前，小久等风纪组重新组织，系统菌重新创建网页版好奇喵，名为 喵窝\n"
                + "\n向凯神团队，好奇喵风纪组等每一位为维护好奇喵健康发展的喵们致敬\n"
                + "\n全体起立，敬礼\n");

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            new Thread(() -> saveErr(t, e)).run();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("啊, App崩溃啦");
            builder.setMessage("是否重启App？");
            builder.setPositiveButton("重启", (dialog, which) -> {
                Intent intent = new Intent(this, RestartService.class);
                startService(intent);
                dialog.dismiss();
                Process.killProcess(Process.myPid());
            });
            builder.setNegativeButton("算啦", (dialog, which) -> {
                dialog.dismiss();
                Process.killProcess(Process.myPid());
            });
        });
    }

    private void saveErr(Thread t, Throwable e) {
        SpUtil sp = SpUtil.defaultSp();
        File errFile = new File(getFilesDir(), "err" + System.currentTimeMillis());
        try {
            if (!errFile.exists()) {
                if (!errFile.createNewFile()) {
                    return;
                }
            }
            PrintStream errStream = new PrintStream(errFile);
            errStream.println(t.getName());
            e.printStackTrace(errStream);
            errStream.flush();
            errStream.close();
            sp.putBoolean(ERR, true);
            sp.putString(ERR_LOG, errFile.getAbsolutePath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
