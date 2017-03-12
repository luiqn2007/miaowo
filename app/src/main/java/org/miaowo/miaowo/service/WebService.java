package org.miaowo.miaowo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WebService extends Service {

    public WebService() {
        // TODO: 用于接收服务器新消息
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
