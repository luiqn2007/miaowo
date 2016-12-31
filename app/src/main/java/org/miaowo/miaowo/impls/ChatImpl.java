package org.miaowo.miaowo.impls;

import org.miaowo.miaowo.T;
import org.miaowo.miaowo.beans.ChatMessage;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.interfaces.Chat;

import java.util.ArrayList;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {

    @Override
    public ArrayList<User> getChatList() {
        return T.createUsers(7);
    }

    @Override
    public void sendMessage(ChatMessage msg) throws Exception {
        pushMessage(new ChatMessage(msg.getTo(), msg.getFrom(), "刚刚消息的回复"));
    }

    @Override
    public void pushMessage(ChatMessage msg) {
        // 暂时设想，这是一个广播接收器接受了服务器消息，经解析发现是聊天消息，然后调用传入聊天消息
        // 此处，有两件事：更新UI && 发送一个后后台通知--->发送一个广播
    }

    @Override
    public ArrayList<ChatMessage> getBeforeMessage(ChatMessage msg) {
        return null;
    }
}
