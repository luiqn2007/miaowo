package org.miaowo.miaowo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
public class ChatMessage implements Parcelable {

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

    protected ChatMessage(Parcel in) {
        from = in.readParcelable(User.class.getClassLoader());
        to = in.readParcelable(User.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

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

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (!chatRoom.equals(that.chatRoom)) return false;
        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = chatRoom.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(from, flags);
        dest.writeParcelable(to, flags);
        dest.writeString(message);
    }
}
