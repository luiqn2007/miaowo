package org.miaowo.miaowo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class WebService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return WebBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    class WebBinder: Binder() {
        // TODO: 用于接收服务器新消息
    }
}
