package org.miaowo.miaowo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IntDef;

public class RestartService extends Service {
    public RestartService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SystemClock.sleep(200);
        Intent restart = getPackageManager().getLaunchIntentForPackage(getPackageName());
        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(restart);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
