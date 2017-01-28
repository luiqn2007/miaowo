package org.miaowo.miaowo.impl.interfaces;

import android.support.annotation.WorkerThread;

import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.User;

import java.util.ArrayList;

/**
 * 聊天的有关方法
 * Created by luqin on 16-12-31.
 */

public interface Chat {

    /**
     * 获取所有已打开聊天用户列表
     * @return 聊天用户列表
     */
    User[] getChatList();

    /**
     * 向服务器发送一条聊天消息
     * @param msg 消息
     * @throws Exception 发送失败的信息
     */
    void sendMessage(ChatMessage msg) throws Exception;

    /**
     * 将接受的一条聊天消息发送给用户
     * 此方由APP内部相关方法触发
     * @param msg 消息
     */
    void pushMessage(ChatMessage msg);

    /**
     * 获取以前的消息
     * @param msg 当前列表中最早的一条消息
     * @return 早于传入消息之前的若干条消息
     */
    ChatMessage[] getBeforeMessage(ChatMessage msg);
}
