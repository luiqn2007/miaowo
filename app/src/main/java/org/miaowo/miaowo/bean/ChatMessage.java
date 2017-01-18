package org.miaowo.miaowo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
public class ChatMessage implements Parcelable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;
        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + message.hashCode();
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
