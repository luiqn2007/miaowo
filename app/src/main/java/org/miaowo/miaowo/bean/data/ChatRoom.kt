package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 聊天室
 * Created by luqin on 17-4-7.
 */

open class ChatRoom private constructor(
        var owner: Int = 0,  // owner : 7
        var roomId: Int = 0,  // roomId : 36
        var roomName: String? = null,  // roomName :
        var isGroupChat: Boolean = false,  // groupChat : false
        var isUnread: Boolean = false,  // unread : false
        var teaser: Teaser? = null,  // teaser : {"fromuid":7,"content":"还有，chrome调试js，我想知道点击一个按钮后都执行了哪些代码，怎么看","t...}
        var lastUser: User? = null,  // lastUser : {"username":"Systemd","userslug":"systemd","lastonl...}
        var usernames: String? = null,  // usernames : Systemd
        var users: List<User>? = null  // users : [{"username":"Systemd","userslug":"systemd","...}]
): Parcelable {
    protected constructor(`in`: Parcel) : this(
        owner = `in`.readInt(),
        roomId = `in`.readInt(),
        roomName = `in`.readString(),
        isGroupChat = `in`.readByte().toInt() != 0,
        isUnread = `in`.readByte().toInt() != 0,
        teaser = `in`.readParcelable<Teaser>(Teaser::class.java.classLoader),
        lastUser = `in`.readParcelable<User>(User::class.java.classLoader),
        usernames = `in`.readString(),
        users = `in`.createTypedArrayList(User.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(owner)
        dest.writeInt(roomId)
        dest.writeString(roomName)
        dest.writeByte((if (isGroupChat) 1 else 0).toByte())
        dest.writeByte((if (isUnread) 1 else 0).toByte())
        dest.writeParcelable(teaser, flags)
        dest.writeParcelable(lastUser, flags)
        dest.writeString(usernames)
        dest.writeTypedList(users)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val room = other as ChatRoom?

        return roomId == room!!.roomId

    }

    override fun hashCode(): Int {
        return roomId
    }

    companion object {

        val CREATOR: Parcelable.Creator<ChatRoom> = object : Parcelable.Creator<ChatRoom> {
            override fun createFromParcel(`in`: Parcel): ChatRoom {
                return ChatRoom(`in`)
            }

            override fun newArray(size: Int): Array<ChatRoom?> {
                return arrayOfNulls(size)
            }
        }
    }
}
