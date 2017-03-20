package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.bean.data.web.ChatMessage;
import org.miaowo.miaowo.root.BaseEvent;

import okhttp3.Call;

/**
 * 聊天消息信息, 暂时无用
 * Created by luqin on 17-3-15.
 */

public class ChatEvent extends BaseEvent {
    public ChatMessage chatMessage;

    public ChatEvent(Call call, ChatMessage chatMessage) {
        super(call);
        this.chatMessage = chatMessage;
    }
}
