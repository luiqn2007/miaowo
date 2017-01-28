package org.miaowo.miaowo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.impl.AnswersImpl;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.interfaces.Answers;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.receiver.MessageReceiver;

public class WebService extends Service {

    private MessageReceiver mMsgReceiver;
    private Answers mAnswers;
    private Chat mChat;

    public WebService() {
        mAnswers = new AnswersImpl();
        mChat = new ChatImpl();
        mMsgReceiver = new MessageReceiver(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter tFilter = new IntentFilter();
        tFilter.addAction(T.BC_CHAT);
        tFilter.addAction(T.BC_MSG);
        registerReceiver(testReceiver, tFilter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(C.BC_MSG);
        filter.addAction(C.BC_CHAT);
        registerReceiver(mMsgReceiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }

    BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Object msg = intent.getParcelableExtra(C.EXTRA_ITEM) instanceof ChatMessage;
            if (msg instanceof ChatMessage) {
            } else if (msg instanceof Question) {
            } else if (msg instanceof Answer) {
            }
        }
    };

    private void sendMsg(Answer answer) {
        try {
            mAnswers.sendAnswer(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendChat(ChatMessage msg) {
        mChat.pushMessage(msg);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mMsgReceiver);
        unregisterReceiver(testReceiver);
        super.onDestroy();
    }
}
