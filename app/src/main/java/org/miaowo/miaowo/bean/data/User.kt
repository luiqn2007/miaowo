package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import org.miaowo.miaowo.base.extra.calcHashCode

/**
 * https://www.miaowo.org/api/user/systemd
 */
@Generated("com.robohorse.robopojogenerator")
data class User(

        @field:SerializedName("websiteLink")
        val websiteLink: String = "",

        @field:SerializedName("posts")
        val posts: List<Post> = emptyList(),

        @field:SerializedName("sso")
        val sso: List<Any> = emptyList(),

        @field:SerializedName("icon:text")
        val iconText: String = "",

        @field:SerializedName("isGlobalModerator")
        val isGlobalModerator: Boolean = false,

        @field:SerializedName("joindate")
        val joindate: Long? = null,

        @field:SerializedName("profile_links")
        val profileLinks: List<Any> = emptyList(),

        @field:SerializedName("reputation:disabled")
        val reputationDisabled: Boolean = false,

        @field:SerializedName("banned_until_readable")
        val bannedUntilReadable: String = "",

        @field:SerializedName("isAdmin")
        val isAdmin: Boolean = false,

        @field:SerializedName("moderationNote")
        val moderationNote: String = "",

        @field:SerializedName("ips")
        val ips: List<String> = emptyList(),

        @field:SerializedName("aboutme")
        val aboutme: String = "",

        @field:SerializedName("email:confirmed")
        val emailConfirmed: Any? = null,

        @field:SerializedName("isTargetAdmin")
        val isTargetAdmin: Boolean = false,

        @field:SerializedName("isAdminOrGlobalModerator")
        val isAdminOrGlobalModerator: Boolean = false,

        @field:SerializedName("emailClass")
        val emailClass: String = "",

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean = false,

        @field:SerializedName("downvote:disabled")
        val downvoteDisabled: Boolean = false,

        @field:SerializedName("topiccount")
        val topiccount: Int = -1,

        @field:SerializedName("relative_path")
        val relativePath: String = "",

        @field:SerializedName("isSelf")
        val isSelf: Boolean = false,

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb> = emptyList(),

        @field:SerializedName("status")
        val status: String = "",

        @field:SerializedName("birthday")
        val birthday: String = "",

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("showHidden")
        val showHidden: Boolean = false,

        @field:SerializedName("yourid")
        val yourid: Int = -1,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("lastposttime")
        val lastposttime: Long? = null,

        @field:SerializedName("isModerator")
        val isModerator: Boolean = false,

        @field:SerializedName("signature")
        val signature: String = "",

        @field:SerializedName("icon:bgColor")
        val iconBgColor: String = "",

        @field:SerializedName("canEdit")
        val canEdit: Boolean = false,

        @field:SerializedName("flags")
        val flags: Any? = null,

        @field:SerializedName("groupTitle")
        val groupTitle: String = "",

        @field:SerializedName("nextStart")
        val nextStart: Int = -1,

        @field:SerializedName("title")
        val title: String = "",

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("followingCount")
        val followingCount: Int = -1,

        @field:SerializedName("lastonlineISO")
        val lastonlineISO: String = "",

        @field:SerializedName("email:disableEdit")
        val emailDisableEdit: Boolean = false,

        @field:SerializedName("uid")
        val uid: Int = -1,

        @field:SerializedName("canChangePassword")
        val canChangePassword: Boolean = false,

        @field:SerializedName("profileviews")
        val profileviews: Int = -1,

        @field:SerializedName("bodyClass")
        val bodyClass: String = "",

        @field:SerializedName("cover:url")
        val coverUrl: String = "",

        @field:SerializedName("banned")
        val banned: Any? = null,

        @field:SerializedName("banned_until")
        val bannedUntil: Int = -1,

        @field:SerializedName("userslug")
        val userslug: String = "",

        @field:SerializedName("followerCount")
        val followerCount: Int = -1,

        @field:SerializedName("email")
        val email: String = "",

        @field:SerializedName("website")
        val website: String = "",

        @field:SerializedName("isFollowing")
        val isFollowing: Boolean = false,

        @field:SerializedName("uploadedpicture")
        val uploadedpicture: String = "",

        @field:SerializedName("canBan")
        val canBan: Boolean = false,

        @field:SerializedName("lastonline")
        val lastonline: Long? = null,

        @field:SerializedName("disableSignatures")
        val disableSignatures: Boolean = false,

        @field:SerializedName("banned:expire")
        val bannedExpire: Any? = null,

        @field:SerializedName("groups")
        val groups: List<Any> = emptyList(),

        @field:SerializedName("username:disableEdit")
        val usernameDisableEdit: Boolean = false,

        @field:SerializedName("picture")
        val picture: String = "",

        @field:SerializedName("joindateISO")
        val joindateISO: String = "",

        @field:SerializedName("url")
        val url: String = "",

        @field:SerializedName("isSelfOrAdminOrGlobalModerator")
        val isSelfOrAdminOrGlobalModerator: Boolean = false,

        @field:SerializedName("websiteName")
        val websiteName: String = "",

        @field:SerializedName("hasPrivateChat")
        val hasPrivateChat: String = "",

        @field:SerializedName("isAdminOrGlobalModeratorOrModerator")
        val isAdminOrGlobalModeratorOrModerator: Boolean = false,

        @field:SerializedName("cover:position")
        val coverPosition: String = "",

        @field:SerializedName("postcount")
        val postcount: Int = -1,

        @field:SerializedName("location")
        val location: String = "",

        @field:SerializedName("fullname")
        val fullname: String = "",

        @field:SerializedName("age")
        val age: Int = -1,

        @field:SerializedName("theirid")
        val theirid: String = "",

        @field:SerializedName("username")
        val username: String = "",

        @field:SerializedName("reputation")
        val reputation: Int = -1,

        @field:SerializedName("custom_profile_info")
        val customProfileInfo: List<Any> = emptyList(),

        var password: String = "",

        var token: String = ""
) {
    companion object {
        val logout = User()
    }

    override fun equals(other: Any?): Boolean {
        return other is User && other.uid == uid
    }

    override fun hashCode(): Int {
        return calcHashCode(uid, username, fullname, isAdmin)
    }
}