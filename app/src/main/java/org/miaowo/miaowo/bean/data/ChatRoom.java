package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 聊天室
 * Created by luqin on 17-4-7.
 */

public class ChatRoom implements Parcelable {
    /**
     * owner : 7
     * roomId : 36
     * roomName :
     * users : [{"username":"Systemd","userslug":"systemd","lastonline":1491548837260,"picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-04-07T07:07:17.260Z"}]
     * groupChat : false
     * unread : false
     * teaser : {"fromuid":7,"content":"还有，chrome调试js，我想知道点击一个按钮后都执行了哪些代码，怎么看","timestamp":1491548940413,"timestampISO":"2017-04-07T07:09:00.413Z","user":{"username":"么么么喵","userslug":"么么么喵","lastonline":1491549327331,"picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20","lastonlineISO":"2017-04-07T07:15:27.331Z"}}
     * lastUser : {"username":"Systemd","userslug":"systemd","lastonline":1491548837260,"picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-04-07T07:07:17.260Z"}
     * usernames : Systemd
     */

    private int owner;
    private int roomId;
    private String roomName;
    private boolean groupChat;
    private boolean unread;
    private Teaser teaser;
    private User lastUser;
    private String usernames;
    private List<User> users;

    protected ChatRoom(Parcel in) {
        owner = in.readInt();
        roomId = in.readInt();
        roomName = in.readString();
        groupChat = in.readByte() != 0;
        unread = in.readByte() != 0;
        teaser = in.readParcelable(Teaser.class.getClassLoader());
        lastUser = in.readParcelable(User.class.getClassLoader());
        usernames = in.readString();
        users = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(owner);
        dest.writeInt(roomId);
        dest.writeString(roomName);
        dest.writeByte((byte) (groupChat ? 1 : 0));
        dest.writeByte((byte) (unread ? 1 : 0));
        dest.writeParcelable(teaser, flags);
        dest.writeParcelable(lastUser, flags);
        dest.writeString(usernames);
        dest.writeTypedList(users);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatRoom> CREATOR = new Creator<ChatRoom>() {
        @Override
        public ChatRoom createFromParcel(Parcel in) {
            return new ChatRoom(in);
        }

        @Override
        public ChatRoom[] newArray(int size) {
            return new ChatRoom[size];
        }
    };

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isGroupChat() {
        return groupChat;
    }

    public void setGroupChat(boolean groupChat) {
        this.groupChat = groupChat;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Teaser getTeaser() {
        return teaser;
    }

    public void setTeaser(Teaser teaser) {
        this.teaser = teaser;
    }

    public User getLastUser() {
        return lastUser;
    }

    public void setLastUser(User lastUser) {
        this.lastUser = lastUser;
    }

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoom room = (ChatRoom) o;

        return roomId == room.roomId;

    }

    @Override
    public int hashCode() {
        return roomId;
    }
}
