package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
public class ChatMessage implements Parcelable {
    /**
     * content : <p>你看看这个访问过程中传递的Cookie信息正确不，总感觉在哪一步把登陆的Cookie信息覆盖了</p>

     * timestamp : 1489897236466
     * fromuid : 7
     * roomId : 36
     * messageId : 196
     * fromUser : {"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"}
     * self : 1
     * timestampISO : 2017-03-19T04:20:36.466Z
     * newSet : false
     * cleanedContent : 你看看这个访问过程中传递的Cookie信息正确不，总感觉在哪一步把登陆的Cookie信息覆盖了

     * index : 12
     */

    private String content;
    private long timestamp;
    private int fromuid;
    private int roomId;
    private int messageId;
    private User fromUser;
    private int self;
    private String timestampISO;
    private boolean newSet;
    private String cleanedContent;
    private int index;

    protected ChatMessage(Parcel in) {
        content = in.readString();
        timestamp = in.readLong();
        fromuid = in.readInt();
        roomId = in.readInt();
        messageId = in.readInt();
        fromUser = in.readParcelable(User.class.getClassLoader());
        self = in.readInt();
        timestampISO = in.readString();
        newSet = in.readByte() != 0;
        cleanedContent = in.readString();
        index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeLong(timestamp);
        dest.writeInt(fromuid);
        dest.writeInt(roomId);
        dest.writeInt(messageId);
        dest.writeParcelable(fromUser, flags);
        dest.writeInt(self);
        dest.writeString(timestampISO);
        dest.writeByte((byte) (newSet ? 1 : 0));
        dest.writeString(cleanedContent);
        dest.writeInt(index);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFromuid() {
        return fromuid;
    }

    public void setFromuid(int fromuid) {
        this.fromuid = fromuid;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }

    public String getTimestampISO() {
        return timestampISO;
    }

    public void setTimestampISO(String timestampISO) {
        this.timestampISO = timestampISO;
    }

    public boolean isNewSet() {
        return newSet;
    }

    public void setNewSet(boolean newSet) {
        this.newSet = newSet;
    }

    public String getCleanedContent() {
        return cleanedContent;
    }

    public void setCleanedContent(String cleanedContent) {
        this.cleanedContent = cleanedContent;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ChatMessage(String content, long timestamp, int fromuid, int roomId, int messageId, User fromUser, int self, String timestampISO, boolean newSet, String cleanedContent, int index) {
        this.content = content;
        this.timestamp = timestamp;
        this.fromuid = fromuid;
        this.roomId = roomId;
        this.messageId = messageId;
        this.fromUser = fromUser;
        this.self = self;
        this.timestampISO = timestampISO;
        this.newSet = newSet;
        this.cleanedContent = cleanedContent;
        this.index = index;
    }
}
