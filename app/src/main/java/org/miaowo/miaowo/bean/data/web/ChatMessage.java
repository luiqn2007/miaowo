package org.miaowo.miaowo.bean.data.web;

import org.miaowo.miaowo.bean.data.User;

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
public class ChatMessage {

    // 消息id
    private int id;
    // 发送时间
    private long time;
    // 谁发的
    private User from;
    // 发给谁
    private User to;
    // 消息内容
    private String message;

    public ChatMessage(int id, long time, User from, User to, String message) {
        this.id = id;
        this.time = time;
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", time=" + time +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                '}';
    }
}
