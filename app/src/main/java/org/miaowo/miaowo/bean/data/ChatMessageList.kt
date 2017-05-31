package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 聊天内容列表
 *
 * 忽略属性：
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"么么么喵","url":"/user/么么么喵"},{"text":"[[pages:chats]]","url":"/user/么么么喵/chats"},{"text":"Systemd"}]
 * template : {"name":"chats","chats":true}
 *
 * Created by luqin on 17-4-7.
 */

open class ChatMessageList private constructor(
        var owner: Int = 0,  // owner : 7
        var roomId: Int = 0,  // roomId : 36
        var roomName: String? = null,  // roomName :
        var isIsOwner: Boolean = false,  // isOwner : true
        var isCanReply: Boolean = false,  // canReply : true
        var isGroupChat: Boolean = false,  // groupChat : false
        var uid: Int = 0,  // uid : 7
        var userslug: String? = null,  // userslug : 么么么喵
        var nextStart: Int = 0,  // nextStart : 20
        var usernames: String? = null,  // usernames : Systemd
        var title: String? = null,  // title : Systemd
        var maximumUsersInChatRoom: Int = 0,  // maximumUsersInChatRoom : 0
        var maximumChatMessageLength: Int = 0,  // maximumChatMessageLength : 1000
        var isShowUserInput: Boolean = false,  // showUserInput : true
        var isLoggedIn: Boolean = false,  // loggedIn : true
        var relative_path: String? = null,  // relative_path :
        var url: String? = null,  // url : /user/%E4%B9%88%E4%B9%88%E4%B9%88%E5%96%B5/chats/36
        var bodyClass: String? = null,  // bodyClass : page-user page-user-么么么喵 page-user-chats
        var messages: List<ChatMessage>? = null,  // messages : [{"content":"你看看这个访问过程中......}]
        var users: List<User>? = null,  // users : [{"username":"Systemd","picture":"","status":"o...}]
        var rooms: List<ChatRoom>? = null  // rooms : [{"owner":7,"roomId":36,"roomName":"","users":[{"username":"Systemd",...}]
): Parcelable {
    protected constructor(`in`: Parcel) : this(
        owner = `in`.readInt(),
        roomId = `in`.readInt(),
        roomName = `in`.readString(),
        isIsOwner = `in`.readByte().toInt() != 0,
        isCanReply = `in`.readByte().toInt() != 0,
        isGroupChat = `in`.readByte().toInt() != 0,
        uid = `in`.readInt(),
        userslug = `in`.readString(),
        nextStart = `in`.readInt(),
        usernames = `in`.readString(),
        title = `in`.readString(),
        maximumUsersInChatRoom = `in`.readInt(),
        maximumChatMessageLength = `in`.readInt(),
        isShowUserInput = `in`.readByte().toInt() != 0,
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        messages = `in`.createTypedArrayList(ChatMessage.CREATOR),
        users = `in`.createTypedArrayList(User.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(owner)
        dest.writeInt(roomId)
        dest.writeString(roomName)
        dest.writeByte((if (isIsOwner) 1 else 0).toByte())
        dest.writeByte((if (isCanReply) 1 else 0).toByte())
        dest.writeByte((if (isGroupChat) 1 else 0).toByte())
        dest.writeInt(uid)
        dest.writeString(userslug)
        dest.writeInt(nextStart)
        dest.writeString(usernames)
        dest.writeString(title)
        dest.writeInt(maximumUsersInChatRoom)
        dest.writeInt(maximumChatMessageLength)
        dest.writeByte((if (isShowUserInput) 1 else 0).toByte())
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(messages)
        dest.writeTypedList(users)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object {
        val CREATOR: Parcelable.Creator<ChatMessageList> = object : Parcelable.Creator<ChatMessageList> {
            override fun createFromParcel(`in`: Parcel): ChatMessageList {
                return ChatMessageList(`in`)
            }
            override fun newArray(size: Int): Array<ChatMessageList?> {
                return arrayOfNulls(size)
            }
        }
    }
}
