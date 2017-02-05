package org.miaowo.miaowo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.impl.AnswersImpl;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.QuestionsImpl;
import org.miaowo.miaowo.impl.interfaces.Answers;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.impl.interfaces.Questions;
import org.miaowo.miaowo.receiver.MessageReceiver;
import org.miaowo.miaowo.root.MyApp;

public class WebService extends Service {
    // 广播
    final public static String BC_CHAT = "org.miaowo.chat";
    final public static String BC_MSG = "org.miaowo.msg";

    final private static String T_BC_CHAT = "org.miaowo.t.newchat";
    final private static String T_BC_MSG = "org.miaowo.t.newmsg";

    private MessageReceiver mMsgReceiver;
    private Answers mAnswers;
    private Questions mQuestions;
    private Chat mChat;

    public WebService() {
        mAnswers = new AnswersImpl();
        mQuestions = new QuestionsImpl();
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
        tFilter.addAction(T_BC_CHAT);
        tFilter.addAction(T_BC_MSG);
        registerReceiver(testReceiver, tFilter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BC_MSG);
        filter.addAction(BC_CHAT);
        registerReceiver(mMsgReceiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }

    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Object msg = intent.getParcelableExtra(MyApp.EXTRA_ITEM) instanceof ChatMessage;
            if (msg instanceof ChatMessage) {
                sendChat((ChatMessage) msg);
            } else if (msg instanceof Question) {
                sendQuestion((Question) msg);
            } else if (msg instanceof Answer) {
                sendAnswer((Answer) msg);
            }
        }
    };

    private void sendAnswer(Answer answer) {
        mAnswers.pushAnswer(answer);
    }

    private void sendQuestion(Question question) {
        mQuestions.pushQuestion(question);
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
