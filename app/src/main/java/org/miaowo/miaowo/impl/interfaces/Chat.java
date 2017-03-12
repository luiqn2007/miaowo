package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.ChatMessage;

/**
 * 聊天的有关方法
 * Created by luqin on 16-12-31.
 */

public interface Chat {

    /**
     * 获取所有已打开聊天用户列表
     */
    void loadList();

    /**
     * 刷新聊天列表
     */
    void refresh();

    /**
     * 向服务器发送一条聊天消息
     * @param msg 消息
     * @throws Exception 发送失败的信息
     */
    void sendMessage(ChatMessage msg);

    /**
     * 获取以前的消息
     */
    void loadBefore();
}
