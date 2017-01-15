package org.miaowo.miaowo.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

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
    Context context;

    public ChatImpl(Context context) {
        this.context = context;
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
        return null;
    }
}
