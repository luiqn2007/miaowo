package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.ChatRoom;

/**
 * 聊天的有关方法
 * Created by luqin on 16-12-31.
 */

public interface Chat {

    void send(ChatRoom room, String message);
}
