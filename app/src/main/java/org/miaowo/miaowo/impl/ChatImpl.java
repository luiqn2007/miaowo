package org.miaowo.miaowo.impl;

import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.root.BaseActivity;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {

    @Override
    public void send(ChatRoom room, String message) {
        // TODO: 发送聊天信息
        if (BaseActivity.get instanceof org.miaowo.miaowo.activity.Chat) {
            ChatMessage message1 = new ChatMessage(message, System.currentTimeMillis(),
                    (new StateImpl()).loginUser().getUid(), room.getRoomId(), 1, room.getLastUser(), 0, null, false, "", 0);
            ChatMessage message2 = new ChatMessage("测试用 自动回复", System.currentTimeMillis(),
                    room.getLastUser().getUid(), room.getRoomId(), 2, room.getLastUser(), 0, null, false, "", 0);
            ((org.miaowo.miaowo.activity.Chat) BaseActivity.get).sendMessage(message1);
            ((org.miaowo.miaowo.activity.Chat) BaseActivity.get).sendMessage(message2);
        }
    }
}
