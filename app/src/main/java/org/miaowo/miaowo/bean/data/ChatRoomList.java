package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 聊天室列表
 * Created by luqin on 17-4-7.
 */

public class ChatRoomList implements Parcelable {

    /**
     * rooms : [{"owner":7,"roomId":36,"roomName":"","users":[{"username":"Systemd","userslug":"systemd","lastonline":1491548837260,"picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-04-07T07:07:17.260Z"}],"groupChat":false,"unread":false,"teaser":{"fromuid":7,"content":"还有，chrome调试js，我想知道点击一个按钮后都执行了哪些代码，怎么看","timestamp":1491548940413,"timestampISO":"2017-04-07T07:09:00.413Z","user":{"username":"么么么喵","userslug":"么么么喵","lastonline":1491549327331,"picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20","lastonlineISO":"2017-04-07T07:15:27.331Z"}},"lastUser":{"username":"Systemd","userslug":"systemd","lastonline":1491548837260,"picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-04-07T07:07:17.260Z"},"usernames":"Systemd"}]
     * uid : 7
     * userslug : 么么么喵
     * nextStart : 20
     * allowed : true
     * title : [[pages:chats]]
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"么么么喵","url":"/user/么么么喵"},{"text":"[[pages:chats]]"}]
     * loggedIn : true
     * relative_path :
     * template : {"name":"chats","chats":true}
     * url : /user/%E4%B9%88%E4%B9%88%E4%B9%88%E5%96%B5/chats
     * bodyClass : page-user page-user-么么么喵 page-user-chats
     */

    private int uid;
    private String userslug;
    private int nextStart;
    private boolean allowed;
    private String title;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<ChatRoom> rooms;

    protected ChatRoomList(Parcel in) {
        uid = in.readInt();
        userslug = in.readString();
        nextStart = in.readInt();
        allowed = in.readByte() != 0;
        title = in.readString();
        loggedIn = in.readByte() != 0;
        relative_path = in.readString();
        url = in.readString();
        bodyClass = in.readString();
        rooms = in.createTypedArrayList(ChatRoom.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(userslug);
        dest.writeInt(nextStart);
        dest.writeByte((byte) (allowed ? 1 : 0));
        dest.writeString(title);
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeString(relative_path);
        dest.writeString(url);
        dest.writeString(bodyClass);
        dest.writeTypedList(rooms);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatRoomList> CREATOR = new Creator<ChatRoomList>() {
        @Override
        public ChatRoomList createFromParcel(Parcel in) {
            return new ChatRoomList(in);
        }

        @Override
        public ChatRoomList[] newArray(int size) {
            return new ChatRoomList[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserslug() {
        return userslug;
    }

    public void setUserslug(String userslug) {
        this.userslug = userslug;
    }

    public int getNextStart() {
        return nextStart;
    }

    public void setNextStart(int nextStart) {
        this.nextStart = nextStart;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRelative_path() {
        return relative_path;
    }

    public void setRelative_path(String relative_path) {
        this.relative_path = relative_path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass = bodyClass;
    }

    public List<ChatRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<ChatRoom> rooms) {
        this.rooms = rooms;
    }
}
