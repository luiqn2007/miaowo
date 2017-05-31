package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

class Privilege private constructor(
        @SerializedName("topic:reply")
        var isTopicsReply: Boolean = false,
        @SerializedName("topic:read")
        var isTopicsRead: Boolean = false,
        @SerializedName("topic:delete")
        var isTopicsDelete: Boolean = false,
        @SerializedName("posts:edit")
        var isPostsEdit: Boolean = false,
        @SerializedName("posts:delete")
        var isPostsDelete: Boolean = false,
        var isRead: Boolean = false,
        var isView_thread_tools: Boolean = false,
        var isEditable: Boolean = false,
        var isDeletable: Boolean = false,
        var isView_deleted: Boolean = false,
        var isIsAdminOrMod: Boolean = false,
        var isDisabled: Boolean = false,
        var cid: Int = 0,
        var tid: Int = 0,
        var uid: Int = 0
): Parcelable {

    protected constructor(`in`: Parcel) : this(
        isTopicsReply = `in`.readByte().toInt() != 0,
        isTopicsRead = `in`.readByte().toInt() != 0,
        isTopicsDelete = `in`.readByte().toInt() != 0,
        isPostsEdit = `in`.readByte().toInt() != 0,
        isPostsDelete = `in`.readByte().toInt() != 0,
        isRead = `in`.readByte().toInt() != 0,
        isView_thread_tools = `in`.readByte().toInt() != 0,
        isEditable = `in`.readByte().toInt() != 0,
        isDeletable = `in`.readByte().toInt() != 0,
        isView_deleted = `in`.readByte().toInt() != 0,
        isIsAdminOrMod = `in`.readByte().toInt() != 0,
        isDisabled = `in`.readByte().toInt() != 0,
        cid = `in`.readInt(),
        tid = `in`.readInt(),
        uid = `in`.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeByte((if (isTopicsReply) 1 else 0).toByte())
        dest.writeByte((if (isTopicsRead) 1 else 0).toByte())
        dest.writeByte((if (isTopicsDelete) 1 else 0).toByte())
        dest.writeByte((if (isPostsEdit) 1 else 0).toByte())
        dest.writeByte((if (isPostsDelete) 1 else 0).toByte())
        dest.writeByte((if (isRead) 1 else 0).toByte())
        dest.writeByte((if (isView_thread_tools) 1 else 0).toByte())
        dest.writeByte((if (isEditable) 1 else 0).toByte())
        dest.writeByte((if (isDeletable) 1 else 0).toByte())
        dest.writeByte((if (isView_deleted) 1 else 0).toByte())
        dest.writeByte((if (isIsAdminOrMod) 1 else 0).toByte())
        dest.writeByte((if (isDisabled) 1 else 0).toByte())
        dest.writeInt(cid)
        dest.writeInt(tid)
        dest.writeInt(uid)
    }

    companion object {

        val CREATOR: Parcelable.Creator<Privilege> = object : Parcelable.Creator<Privilege> {
            override fun createFromParcel(`in`: Parcel): Privilege {
                return Privilege(`in`)
            }

            override fun newArray(size: Int): Array<Privilege?> {
                return arrayOfNulls(size)
            }
        }
    }
}
