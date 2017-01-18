package org.miaowo.miaowo.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.ChatMessage;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.set.Exceptions;

import java.util.ArrayList;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {
    private static final String TAG = "ChatImpl";

    private Context context;

    public ChatImpl() {
        this.context = D.getInstance().activeActivity;
    }

    @Override
    public ArrayList<User> getChatList() {
        return T.createUsers(7);
    }

    @Override
    public void sendMessage(ChatMessage msg) throws Exception {
        if (msg == null || TextUtils.isEmpty(msg.getMessage())) throw Exceptions.E_WRONG_CHAT_MSG;

        Intent intent = new Intent(T.BC_CHAT);
        intent.putExtra(C.EXTRA_ITEM, msg);
        Log.i(TAG, "sendMessage: " + context);
        context.sendBroadcast(intent);
    }

    @Override
    public void pushMessage(ChatMessage msg) {
        Intent intent = new Intent(C.BC_CHAT);
        intent.putExtra(C.EXTRA_ITEM, msg);
        context.sendBroadcast(intent);
    }

    @Override
    public ArrayList<ChatMessage> getBeforeMessage(ChatMessage msg) {
        ArrayList<ChatMessage> beforeMsg = new ArrayList<>();
        User from = msg.getFrom();
        User to = msg.getTo();
        String before = "有获取：";
        if (to == null) {
            to = (new StateImpl()).getLocalUser();
            before = "无获取：";
        }

        for (int i = 0; i < 5; i++) {
            beforeMsg.add(new ChatMessage(from, to, before + i));
            beforeMsg.add(new ChatMessage(to, from, "答复" + i));
        }
        return beforeMsg;
    }
}
