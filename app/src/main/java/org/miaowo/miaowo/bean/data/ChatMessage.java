package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
public class ChatMessage implements Parcelable {

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

    protected ChatMessage(Parcel in) {
        id = in.readInt();
        time = in.readLong();
        from = in.readParcelable(User.class.getClassLoader());
        to = in.readParcelable(User.class.getClassLoader());
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(time);
        dest.writeParcelable(from, flags);
        dest.writeParcelable(to, flags);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
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
