package org.miaowo.miaowo.beans;

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
public class ChatMessage {

    // 聊天室
    ChatRoom chatRoom;
    // 谁发的
    User from;
    // 发给谁
    User to;
    // 消息内容
    String message;

    public ChatMessage(User from, User to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (chatRoom != null ? !chatRoom.equals(that.chatRoom) : that.chatRoom != null)
            return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }
}
