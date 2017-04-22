package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 聊天内容列表
 * Created by luqin on 17-4-7.
 */

public class ChatMessageList implements Parcelable {

    /**
     * owner : 7
     * roomId : 36
     * roomName :
     * messages : [{"content":"<p>你看看这个访问过程中传递的Cookie信息正确不，总感觉在哪一步把登陆的Cookie信息覆盖了<\/p>\n","timestamp":1489897236466,"fromuid":7,"roomId":"36","messageId":196,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-03-19T04:20:36.466Z","newSet":false,"cleanedContent":"你看看这个访问过程中传递的Cookie信息正确不，总感觉在哪一步把登陆的Cookie信息覆盖了\n","index":12},{"content":"<p>直接发消息太长，上传了，链接: <a href=\"https://pan.baidu.com/s/1eSzKzp4\" rel=\"nofollow\">https://pan.baidu.com/s/1eSzKzp4<\/a> 密码: ww17<\/p>\n","timestamp":1489897353990,"fromuid":7,"roomId":"36","messageId":197,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-03-19T04:22:33.990Z","newSet":false,"cleanedContent":"直接发消息太长，上传了，链接: https://pan.baidu.com/s/1eSzKzp4 密码: ww17\n","index":11},{"content":"<p>我回家去看看\u2026\u2026我也不太了解<\/p>\n","timestamp":1489909354420,"fromuid":1,"roomId":"36","messageId":198,"fromUser":{"username":"Systemd","userslug":"systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"self":0,"timestampISO":"2017-03-19T07:42:34.420Z","newSet":true,"cleanedContent":"我回家去看看\u2026\u2026我也不太了解\n","index":10},{"content":"<p>我就是想知道是不是发生了Cookie覆盖，给你发的也是我进行网络访问时候的打印的log。。。当我访问 问题 页面时候，身份状态是游客，而登陆后第一次访问miaowo.org身份正确，是APP测试<\/p>\n","timestamp":1489942540647,"fromuid":7,"roomId":"36","messageId":199,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-03-19T16:55:40.647Z","newSet":true,"cleanedContent":"我就是想知道是不是发生了Cookie覆盖，给你发的也是我进行网络访问时候的打印的log。。。当我访问 问题 页面时候，身份状态是游客，而登陆后第一次访问miaowo.org身份正确，是APP测试\n","index":9},{"content":"<p>可以等等么？下一个更新会加入 API...<\/p>\n","timestamp":1490003356271,"fromuid":1,"roomId":"36","messageId":200,"fromUser":{"username":"Systemd","userslug":"systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"self":0,"timestampISO":"2017-03-20T09:49:16.271Z","newSet":true,"cleanedContent":"可以等等么？下一个更新会加入 API...\n","index":8},{"content":"<p>可以登录发帖什么的<\/p>\n","timestamp":1490003369526,"fromuid":1,"roomId":"36","messageId":201,"fromUser":{"username":"Systemd","userslug":"systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"self":0,"timestampISO":"2017-03-20T09:49:29.526Z","newSet":false,"cleanedContent":"可以登录发帖什么的\n","index":7},{"content":"<p>大概会是这周末<\/p>\n","timestamp":1490003398192,"fromuid":1,"roomId":"36","messageId":202,"fromUser":{"username":"Systemd","userslug":"systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"self":0,"timestampISO":"2017-03-20T09:49:58.192Z","newSet":false,"cleanedContent":"大概会是这周末\n","index":6},{"content":"<p>（然而我得先搞定我的作业 QAQ ）<\/p>\n","timestamp":1490003436520,"fromuid":1,"roomId":"36","messageId":203,"fromUser":{"username":"Systemd","userslug":"systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"self":0,"timestampISO":"2017-03-20T09:50:36.520Z","newSet":false,"cleanedContent":"（然而我得先搞定我的作业 QAQ ）\n","index":5},{"content":"<p>目前还有很多测试要做\u2026不知道上线会不会出问题<\/p>\n","timestamp":1490012041940,"fromuid":1,"roomId":"36","messageId":204,"fromUser":{"username":"Systemd","userslug":"systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"self":0,"timestampISO":"2017-03-20T12:14:01.940Z","newSet":true,"cleanedContent":"目前还有很多测试要做\u2026不知道上线会不会出问题\n","index":4},{"content":"<p>你先做着吧，我现在还仅仅是查看问题，还没实现。。。<\/p>\n","timestamp":1490013052866,"fromuid":7,"roomId":"36","messageId":205,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-03-20T12:30:52.866Z","newSet":true,"cleanedContent":"你先做着吧，我现在还仅仅是查看问题，还没实现。。。\n","index":3},{"content":"<p>另外，就现有的发送功能实现，我可能还得看看源码。<\/p>\n","timestamp":1490015496310,"fromuid":7,"roomId":"36","messageId":206,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-03-20T13:11:36.310Z","newSet":true,"cleanedContent":"另外，就现有的发送功能实现，我可能还得看看源码。\n","index":2},{"content":"<p>发送功能怎么实现的啊。。。我只找到了你自定义了一个 post/reply 类型，然后发现js代码全都是混淆过的，看蒙了。。。<\/p>\n","timestamp":1491548835983,"fromuid":7,"roomId":"36","messageId":230,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-04-07T07:07:15.983Z","newSet":true,"cleanedContent":"发送功能怎么实现的啊。。。我只找到了你自定义了一个 post/reply 类型，然后发现js代码全都是混淆过的，看蒙了。。。\n","index":1},{"content":"<p>还有，chrome调试js，我想知道点击一个按钮后都执行了哪些代码，怎么看<\/p>\n","timestamp":1491548940413,"fromuid":7,"roomId":"36","messageId":231,"fromUser":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"self":1,"timestampISO":"2017-04-07T07:09:00.413Z","newSet":false,"cleanedContent":"还有，chrome调试js，我想知道点击一个按钮后都执行了哪些代码，怎么看\n","index":0}]
     * isOwner : true
     * users : [{"username":"Systemd","picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"}]
     * canReply : true
     * groupChat : false
     * rooms : [{"owner":7,"roomId":36,"roomName":"","users":[{"username":"Systemd","userslug":"systemd","lastonline":1491548837260,"picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-04-07T07:07:17.260Z"}],"groupChat":false,"unread":false,"teaser":{"fromuid":7,"content":"还有，chrome调试js，我想知道点击一个按钮后都执行了哪些代码，怎么看","timestamp":1491548940413,"timestampISO":"2017-04-07T07:09:00.413Z","user":{"username":"么么么喵","userslug":"么么么喵","lastonline":1491550217226,"picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20","lastonlineISO":"2017-04-07T07:30:17.226Z"}},"lastUser":{"username":"Systemd","userslug":"systemd","lastonline":1491548837260,"picture":"","status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-04-07T07:07:17.260Z"},"usernames":"Systemd"}]
     * uid : 7
     * userslug : 么么么喵
     * nextStart : 20
     * usernames : Systemd
     * title : Systemd
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"么么么喵","url":"/user/么么么喵"},{"text":"[[pages:chats]]","url":"/user/么么么喵/chats"},{"text":"Systemd"}]
     * maximumUsersInChatRoom : 0
     * maximumChatMessageLength : 1000
     * showUserInput : true
     * loggedIn : true
     * relative_path :
     * template : {"name":"chats","chats":true}
     * url : /user/%E4%B9%88%E4%B9%88%E4%B9%88%E5%96%B5/chats/36
     * bodyClass : page-user page-user-么么么喵 page-user-chats
     */

    private int owner;
    private int roomId;
    private String roomName;
    private boolean isOwner;
    private boolean canReply;
    private boolean groupChat;
    private int uid;
    private String userslug;
    private int nextStart;
    private String usernames;
    private String title;
    private int maximumUsersInChatRoom;
    private int maximumChatMessageLength;
    private boolean showUserInput;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<ChatMessage> messages;
    private List<User> users;
    private List<ChatRoom> rooms;

    protected ChatMessageList(Parcel in) {
        owner = in.readInt();
        roomId = in.readInt();
        roomName = in.readString();
        isOwner = in.readByte() != 0;
        canReply = in.readByte() != 0;
        groupChat = in.readByte() != 0;
        uid = in.readInt();
        userslug = in.readString();
        nextStart = in.readInt();
        usernames = in.readString();
        title = in.readString();
        maximumUsersInChatRoom = in.readInt();
        maximumChatMessageLength = in.readInt();
        showUserInput = in.readByte() != 0;
        loggedIn = in.readByte() != 0;
        relative_path = in.readString();
        url = in.readString();
        bodyClass = in.readString();
        messages = in.createTypedArrayList(ChatMessage.CREATOR);
        users = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(owner);
        dest.writeInt(roomId);
        dest.writeString(roomName);
        dest.writeByte((byte) (isOwner ? 1 : 0));
        dest.writeByte((byte) (canReply ? 1 : 0));
        dest.writeByte((byte) (groupChat ? 1 : 0));
        dest.writeInt(uid);
        dest.writeString(userslug);
        dest.writeInt(nextStart);
        dest.writeString(usernames);
        dest.writeString(title);
        dest.writeInt(maximumUsersInChatRoom);
        dest.writeInt(maximumChatMessageLength);
        dest.writeByte((byte) (showUserInput ? 1 : 0));
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeString(relative_path);
        dest.writeString(url);
        dest.writeString(bodyClass);
        dest.writeTypedList(messages);
        dest.writeTypedList(users);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatMessageList> CREATOR = new Creator<ChatMessageList>() {
        @Override
        public ChatMessageList createFromParcel(Parcel in) {
            return new ChatMessageList(in);
        }

        @Override
        public ChatMessageList[] newArray(int size) {
            return new ChatMessageList[size];
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

    public boolean isIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public boolean isGroupChat() {
        return groupChat;
    }

    public void setGroupChat(boolean groupChat) {
        this.groupChat = groupChat;
    }

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

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaximumUsersInChatRoom() {
        return maximumUsersInChatRoom;
    }

    public void setMaximumUsersInChatRoom(int maximumUsersInChatRoom) {
        this.maximumUsersInChatRoom = maximumUsersInChatRoom;
    }

    public int getMaximumChatMessageLength() {
        return maximumChatMessageLength;
    }

    public void setMaximumChatMessageLength(int maximumChatMessageLength) {
        this.maximumChatMessageLength = maximumChatMessageLength;
    }

    public boolean isShowUserInput() {
        return showUserInput;
    }

    public void setShowUserInput(boolean showUserInput) {
        this.showUserInput = showUserInput;
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

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<ChatRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<ChatRoom> rooms) {
        this.rooms = rooms;
    }
}
