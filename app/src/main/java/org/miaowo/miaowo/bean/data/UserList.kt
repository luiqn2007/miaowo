package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

/**
 * 用户列表
 *
 * 忽略的属性：
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[global:users]]"}]
 * template : {"name":"users","users":true}
 */
open class UserList private constructor(
        var pagination: Pagination? = null,  // pagination : {"rel":[{"re...}
        var userCount: Int = 0,  // userCount : 119
        var title: String? = null,  // title : [[pages:users/latest]]
        var isIsAdminOrGlobalMod: Boolean = false,  // isAdminOrGlobalMod : true
        var isSection_joindate: Boolean = false,  // section_joindate : true
        var maximumInvites: String? = null,  // maximumInvites :
        var isInviteOnly: Boolean = false,  // inviteOnly : false
        var isAdminInviteOnly: Boolean = false,  // adminInviteOnly : false
        @SerializedName("reputation:disabled")
        var isReputationDisabled: Boolean = false,  // reputation:disabled : true
        var invites: Int = 0,  // invites : 0
        var isLoggedIn: Boolean = false,  // loggedIn : true
        var relative_path: String? = null,  // relative_path :
        var url: String? = null,  // url : /users
        var bodyClass: String? = null,  // bodyClass : page-users
        var users: List<User>? = null  // users : [{"username":"楱椿","userslu...}]
): Parcelable {
    protected constructor(`in`: Parcel) : this(
        pagination = `in`.readParcelable<Pagination>(Pagination::class.java.classLoader),
        userCount = `in`.readInt(),
        title = `in`.readString(),
        isIsAdminOrGlobalMod = `in`.readByte().toInt() != 0,
        isSection_joindate = `in`.readByte().toInt() != 0,
        maximumInvites = `in`.readString(),
        isInviteOnly = `in`.readByte().toInt() != 0,
        isAdminInviteOnly = `in`.readByte().toInt() != 0,
        isReputationDisabled = `in`.readByte().toInt() != 0,
        invites = `in`.readInt(),
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        users = `in`.createTypedArrayList(User.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(pagination, flags)
        dest.writeInt(userCount)
        dest.writeString(title)
        dest.writeByte((if (isIsAdminOrGlobalMod) 1 else 0).toByte())
        dest.writeByte((if (isSection_joindate) 1 else 0).toByte())
        dest.writeString(maximumInvites)
        dest.writeByte((if (isInviteOnly) 1 else 0).toByte())
        dest.writeByte((if (isAdminInviteOnly) 1 else 0).toByte())
        dest.writeByte((if (isReputationDisabled) 1 else 0).toByte())
        dest.writeInt(invites)
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(users)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<UserList> = object : Parcelable.Creator<UserList> {
            override fun createFromParcel(`in`: Parcel): UserList {
                return UserList(`in`)
            }

            override fun newArray(size: Int): Array<UserList?> {
                return arrayOfNulls(size)
            }
        }
    }
}
