package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 聊天室列表
 * Created by luqin on 17-4-7.
 */

open class ChatRoomList private constructor(
        var uid: Int = 0,  // uid : 7
        var userslug: String? = null,  // userslug : 么么么喵
        var nextStart: Int = 0,  // nextStart : 20
        var isAllowed: Boolean = false,  // allowed : true
        var title: String? = null,  // title : [[pages:chats]]
        var isLoggedIn: Boolean = false,  // loggedIn : true
        var relative_path: String? = null,  // relative_path :
        var url: String? = null,  // url : /user/%E4%B9%88%E4%B9%88%E4%B9%88%E5%96%B5/chats
        var bodyClass: String? = null,  // bodyClass : page-user page-user-么么么喵 page-user-chats
        var rooms: List<ChatRoom>? = null  // rooms : [{"owner":7,"roomId":36,"roomName":"","users":[{...}]
): Parcelable {
    protected constructor(`in`: Parcel) : this (
        uid = `in`.readInt(),
        userslug = `in`.readString(),
        nextStart = `in`.readInt(),
        isAllowed = `in`.readByte().toInt() != 0,
        title = `in`.readString(),
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        rooms = `in`.createTypedArrayList(ChatRoom.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(uid)
        dest.writeString(userslug)
        dest.writeInt(nextStart)
        dest.writeByte((if (isAllowed) 1 else 0).toByte())
        dest.writeString(title)
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(rooms)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<ChatRoomList> = object : Parcelable.Creator<ChatRoomList> {
            override fun createFromParcel(`in`: Parcel): ChatRoomList {
                return ChatRoomList(`in`)
            }

            override fun newArray(size: Int): Array<ChatRoomList?> {
                return arrayOfNulls(size)
            }
        }
    }
}
