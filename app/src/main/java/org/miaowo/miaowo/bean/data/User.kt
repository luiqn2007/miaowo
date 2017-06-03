package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

/**
 * 具体用户页面
 *
 * 忽略的属性：
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"么么么喵"}]
 * template : {"name":"account/profile","account/profile":true}
 * profile_links : []
 * sso : []
 * groups : []
 *
 * Created by luqin on 17-3-18.
 */

open class User private constructor(
        var username: String,  // username : 么么么喵
        var userslug: String,  // userslug : 么么么喵
        var email: String,  // email : 1105188240@qq.com
        var joindate: Long,  // joindate : 1479650239103
        var lastonline: Long,  // lastonline : 1489775541488
        var picture: String,  // picture : /uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg
        var fullname: String,  // fullname : 么么么喵a
        var location: String,  // location :
        var birthday: String,  // birthday :
        var website: String,  // website :
        var signature: String,  // signature :
        var uploadedpicture: String,  // uploadedpicture : /uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg
        var profileviews: Int,  // profileviews : 42
        var postcount: Int,  // postcount : 116
        var topiccount: Int,  // topiccount : 10
        var lastposttime: Long,  // lastposttime : 1489774776511
        val reputation: Int,  // banned : false
        var status: String,  // status : online
        var uid: Int,  // uid : 7
        var passwordExpiry: Int,  // passwordExpiry : 0
        @SerializedName("cover:position")
        var coverPosition: String,  // cover:position : 50.0262% 46.4866%
        @SerializedName("cover:url")
        var coverUrl: String,  // cover:url : /uploads/files/1482860372268profilecover
        var githubid: String,  // githubid : 18262245
        var followingCount: Int,  // followingCount : 3
        var groupTitle: String,  // groupTitle :
        @SerializedName("banned:expire")
        var bannedExpire: Int,  // banned:expire : 0
        var followerCount: Int,  // followerCount : 4
        @SerializedName("icon:text")
        var iconText: String,  // icon:text : 么
        @SerializedName("icon:bgColor")
        var iconBgColor: String,  // icon:bgColor : #1b5e20
        var joindateISO: String,  // joindateISO : 2016-11-20T13:57:19.103Z
        var lastonlineISO: String,  // lastonlineISO : 2017-03-17T18:32:21.488Z
        var age: Int,  // age : 0
        var emailClass: String,  // emailClass :
        var yourid: Int,  // yourid : 7
        var theirid: Int,  // theirid : 7
        var isIsTargetAdmin: Boolean,  // isTargetAdmin : true
        var isAdmin: Boolean,  // isAdmin : true
        @SerializedName("isGlobalMod")
        var isIsGlobalModerator: Boolean,  // isGlobalModerator : false
        @SerializedName("isMod")
        var isIsModerator: Boolean,  // isModerator : false
        var isIsAdminOrGlobalModerator: Boolean,  // isAdminOrGlobalModerator : true
        var isIsAdminOrGlobalModeratorOrModerator: Boolean,  // isAdminOrGlobalModeratorOrModerator : true
        var isIsSelfOrAdminOrGlobalModerator: Boolean,  // isSelfOrAdminOrGlobalModerator : true
        var isCanEdit: Boolean,  // canEdit : true
        var isCanBan: Boolean,  // canBan : true
        var isCanChangePassword: Boolean,  // canChangePassword : true
        var isIsSelf: Boolean,  // isSelf : true
        var isIsFollowing: Boolean,  // isFollowing : false
        var isShowHidden: Boolean,  // showHidden : true
        var isDisableSignatures: Boolean,  // disableSignatures : false
        @SerializedName("reputation:disabled")
        var isReputationDisabled: Boolean,  // reputation:disabled : true
        @SerializedName("downvote:disabled")
        var isDownvoteDisabled: Boolean,  // downvote:disabled : false
        @SerializedName("email:confirmed")
        var isEmailConfirmed: Boolean,  // email:confirmed : false
        var websiteLink: String,  // websiteLink : http://
        var websiteName: String,  // websiteName :
        var moderationNote: String,  // moderationNote :
        @SerializedName("username:disableEdit")
        var isUsernameDisableEdit: Boolean,  // username:disableEdit : false
        @SerializedName("email:disableEdit")
        var isEmailDisableEdit: Boolean,  // email:disableEdit : false
        var hasPrivateChat: Int,  // hasPrivateChat : 0
        var nextStart: Int,  // nextStart : 10
        var title: String,  // title : 么么么喵
        var password: String,  //
        var pagination: Pagination,  // pagination : {"rel":[{"rel":"...}
        var isLoggedIn: Boolean,  // loggedIn : true
        var relative_path: String,  // relative_path :
        var url: String,  // url : /user/%E4%B9%88%E4%B9%88%E4%B9%88%E5%96%B5
        var bodyClass: String,  // bodyClass : page-user page-user-么么么喵
        var ips: List<String>,  // ips : ["117.136.67.26","122.192.20.50","153.36.217.132","122.192.20.52","153.36.215.172"]
        var posts: List<Post>,  // posts : [{"pid":1168,"uid":7,"tid":197,"content":"...}]
        val isEmailConfirmSent: Boolean,  //
        val aboutme: String,  //
        val banned_until: Int,  //
        val banned_until_readable: String,  //
        var token: String  //
): Parcelable {
    protected constructor(`in`: Parcel) : this(
        username = `in`.readString(),
        userslug = `in`.readString(),
        email = `in`.readString(),
        joindate = `in`.readLong(),
        lastonline = `in`.readLong(),
        picture = `in`.readString(),
        fullname = `in`.readString(),
        location = `in`.readString(),
        birthday = `in`.readString(),
        website = `in`.readString(),
        signature = `in`.readString(),
        uploadedpicture = `in`.readString(),
        profileviews = `in`.readInt(),
        postcount = `in`.readInt(),
        topiccount = `in`.readInt(),
        lastposttime = `in`.readLong(),
        reputation = `in`.readInt(),
        status = `in`.readString(),
        uid = `in`.readInt(),
        passwordExpiry = `in`.readInt(),
        coverPosition = `in`.readString(),
        coverUrl = `in`.readString(),
        githubid = `in`.readString(),
        followingCount = `in`.readInt(),
        groupTitle = `in`.readString(),
        bannedExpire = `in`.readInt(),
        followerCount = `in`.readInt(),
        iconText = `in`.readString(),
        iconBgColor = `in`.readString(),
        joindateISO = `in`.readString(),
        lastonlineISO = `in`.readString(),
        age = `in`.readInt(),
        emailClass = `in`.readString(),
        yourid = `in`.readInt(),
        theirid = `in`.readInt(),
        isIsTargetAdmin = `in`.readByte().toInt() != 0,
        isAdmin = `in`.readByte().toInt() != 0,
        isIsGlobalModerator = `in`.readByte().toInt() != 0,
        isIsModerator = `in`.readByte().toInt() != 0,
        isIsAdminOrGlobalModerator = `in`.readByte().toInt() != 0,
        isIsAdminOrGlobalModeratorOrModerator = `in`.readByte().toInt() != 0,
        isIsSelfOrAdminOrGlobalModerator = `in`.readByte().toInt() != 0,
        isCanEdit = `in`.readByte().toInt() != 0,
        isCanBan = `in`.readByte().toInt() != 0,
        isCanChangePassword = `in`.readByte().toInt() != 0,
        isIsSelf = `in`.readByte().toInt() != 0,
        isIsFollowing = `in`.readByte().toInt() != 0,
        isShowHidden = `in`.readByte().toInt() != 0,
        isDisableSignatures = `in`.readByte().toInt() != 0,
        isReputationDisabled = `in`.readByte().toInt() != 0,
        isDownvoteDisabled = `in`.readByte().toInt() != 0,
        isEmailConfirmed = `in`.readByte().toInt() != 0,
        websiteLink = `in`.readString(),
        websiteName = `in`.readString(),
        moderationNote = `in`.readString(),
        isUsernameDisableEdit = `in`.readByte().toInt() != 0,
        isEmailDisableEdit = `in`.readByte().toInt() != 0,
        hasPrivateChat = `in`.readInt(),
        nextStart = `in`.readInt(),
        title = `in`.readString(),
        password = `in`.readString(),
        pagination = `in`.readParcelable<Pagination>(Pagination::class.java.classLoader),
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        ips = `in`.createStringArrayList(),
        posts = `in`.createTypedArrayList(Post.CREATOR),
        isEmailConfirmSent = `in`.readByte().toInt() != 0,
        aboutme = `in`.readString(),
        banned_until = `in`.readInt(),
        banned_until_readable = `in`.readString(),
        token = `in`.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(username)
        dest.writeString(userslug)
        dest.writeString(email)
        dest.writeLong(joindate)
        dest.writeLong(lastonline)
        dest.writeString(picture)
        dest.writeString(fullname)
        dest.writeString(location)
        dest.writeString(birthday)
        dest.writeString(website)
        dest.writeString(signature)
        dest.writeString(uploadedpicture)
        dest.writeInt(profileviews)
        dest.writeInt(postcount)
        dest.writeInt(topiccount)
        dest.writeLong(lastposttime)
        dest.writeInt(reputation)
        dest.writeString(status)
        dest.writeInt(uid)
        dest.writeInt(passwordExpiry)
        dest.writeString(coverPosition)
        dest.writeString(coverUrl)
        dest.writeString(githubid)
        dest.writeInt(followingCount)
        dest.writeString(groupTitle)
        dest.writeInt(bannedExpire)
        dest.writeInt(followerCount)
        dest.writeString(iconText)
        dest.writeString(iconBgColor)
        dest.writeString(joindateISO)
        dest.writeString(lastonlineISO)
        dest.writeInt(age)
        dest.writeString(emailClass)
        dest.writeInt(yourid)
        dest.writeInt(theirid)
        dest.writeByte((if (isIsTargetAdmin) 1 else 0).toByte())
        dest.writeByte((if (isAdmin) 1 else 0).toByte())
        dest.writeByte((if (isIsGlobalModerator) 1 else 0).toByte())
        dest.writeByte((if (isIsModerator) 1 else 0).toByte())
        dest.writeByte((if (isIsAdminOrGlobalModerator) 1 else 0).toByte())
        dest.writeByte((if (isIsAdminOrGlobalModeratorOrModerator) 1 else 0).toByte())
        dest.writeByte((if (isIsSelfOrAdminOrGlobalModerator) 1 else 0).toByte())
        dest.writeByte((if (isCanEdit) 1 else 0).toByte())
        dest.writeByte((if (isCanBan) 1 else 0).toByte())
        dest.writeByte((if (isCanChangePassword) 1 else 0).toByte())
        dest.writeByte((if (isIsSelf) 1 else 0).toByte())
        dest.writeByte((if (isIsFollowing) 1 else 0).toByte())
        dest.writeByte((if (isShowHidden) 1 else 0).toByte())
        dest.writeByte((if (isDisableSignatures) 1 else 0).toByte())
        dest.writeByte((if (isReputationDisabled) 1 else 0).toByte())
        dest.writeByte((if (isDownvoteDisabled) 1 else 0).toByte())
        dest.writeByte((if (isEmailConfirmed) 1 else 0).toByte())
        dest.writeString(websiteLink)
        dest.writeString(websiteName)
        dest.writeString(moderationNote)
        dest.writeByte((if (isUsernameDisableEdit) 1 else 0).toByte())
        dest.writeByte((if (isEmailDisableEdit) 1 else 0).toByte())
        dest.writeInt(hasPrivateChat)
        dest.writeInt(nextStart)
        dest.writeString(title)
        dest.writeString(password)
        dest.writeParcelable(pagination, flags)
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeStringList(ips)
        dest.writeTypedList(posts)
        dest.writeByte((if (isEmailConfirmSent) 1 else 0).toByte())
        dest.writeString(aboutme)
        dest.writeInt(banned_until)
        dest.writeString(banned_until_readable)
        dest.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(`in`: Parcel): User {
                return User(`in`)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }
}
