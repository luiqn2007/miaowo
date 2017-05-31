package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 聊天信息
 * Created by luqin on 16-12-31.
 */
open class ChatMessage private constructor(
        var content: String? = null,  // content : 你看看这个访问过程中传递的Cookie信息正确不，总感觉在哪一步把登陆的Cookie信息覆盖了
        var timestamp: Long = 0,  // timestamp : 1489897236466
        var fromuid: Int = 0,  // fromuid : 7
        var roomId: Int = 0,  // roomId : 36
        var messageId: Int = 0,  // messageId : 196
        var fromUser: User? = null,  // fromUser : {"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"}
        var self: Int = 0,  // self : 1
        var timestampISO: String? = null,  // timestampISO : 2017-03-19T04:20:36.466Z
        var isNewSet: Boolean = false,  // newSet : false
        var cleanedContent: String? = null,  // cleanedContent : 你看看这个访问过程中传递的Cookie信息正确不，总感觉在哪一步把登陆的Cookie信息覆盖了
        var index: Int = 0  // index : 12
): Parcelable {
    protected constructor(`in`: Parcel) : this(
        content = `in`.readString(),
        timestamp = `in`.readLong(),
        fromuid = `in`.readInt(),
        roomId = `in`.readInt(),
        messageId = `in`.readInt(),
        fromUser = `in`.readParcelable<User>(User::class.java.classLoader),
        self = `in`.readInt(),
        timestampISO = `in`.readString(),
        isNewSet = `in`.readByte().toInt() != 0,
        cleanedContent = `in`.readString(),
        index = `in`.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(content)
        dest.writeLong(timestamp)
        dest.writeInt(fromuid)
        dest.writeInt(roomId)
        dest.writeInt(messageId)
        dest.writeParcelable(fromUser, flags)
        dest.writeInt(self)
        dest.writeString(timestampISO)
        dest.writeByte((if (isNewSet) 1 else 0).toByte())
        dest.writeString(cleanedContent)
        dest.writeInt(index)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object {
        val CREATOR: Parcelable.Creator<ChatMessage> = object : Parcelable.Creator<ChatMessage> {
            override fun createFromParcel(`in`: Parcel): ChatMessage {
                return ChatMessage(`in`)
            }
            override fun newArray(size: Int): Array<ChatMessage?> {
                return arrayOfNulls(size)
            }
        }
    }
}
