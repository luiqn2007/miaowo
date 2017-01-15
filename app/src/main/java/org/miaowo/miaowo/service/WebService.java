package org.miaowo.miaowo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.ChatMessage;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.impl.interfaces.Message;

public class WebService extends Service {
    private static final String TAG = "WebService";

    private Message mMessage;
    private Chat mChat;

    public WebService() {
        mMessage = new MsgImpl();
        mChat = new ChatImpl(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: Service Running");
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(T.BC_CHAT);
        filter.addAction(T.BC_MSG);
        registerReceiver(testReceiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }

    BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case T.BC_CHAT:
                    sendChat(T.getReply((ChatMessage) intent.getParcelableExtra(C.EXTRA_ITEM)));
                    break;
                case T.BC_MSG:
                    sendMsg();
                    break;
            }
        }
    };

    private void sendMsg() {
    }

    private void sendChat(ChatMessage msg) {
        mChat.pushMessage(msg);
        //TODO 发送至系统状态栏
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: Service Stoped!");
        super.onDestroy();
    }
}
