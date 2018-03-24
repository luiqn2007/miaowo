package org.miaowo.miaowo.bean.data

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class UserPosts(

        @field:SerializedName("websiteLink")
        val websiteLink: String? = null,

        @field:SerializedName("reputation")
        val reputation: Int? = null,

        @field:SerializedName("posts")
        val posts: List<Post> = emptyList(),

        @field:SerializedName("sso")
        val sso: List<Any?>? = null,

        @field:SerializedName("icon:text")
        val iconText: String? = null,

        @field:SerializedName("isGlobalModerator")
        val isGlobalModerator: Boolean? = null,

        @field:SerializedName("joindate")
        val joindate: Long? = null,

        @field:SerializedName("profile_links")
        val profileLinks: List<Any?>? = null,

        @field:SerializedName("reputation:disabled")
        val reputationDisabled: Boolean? = null,

        @field:SerializedName("banned_until_readable")
        val bannedUntilReadable: String? = null,

        @field:SerializedName("isAdmin")
        val isAdmin: Boolean? = null,

        @field:SerializedName("moderationNote")
        val moderationNote: String? = null,

        @field:SerializedName("ips")
        val ips: List<String?>? = null,

        @field:SerializedName("aboutme")
        val aboutme: String? = null,

        @field:SerializedName("email:confirmed")
        val emailConfirmed: Boolean? = null,

        @field:SerializedName("isTargetAdmin")
        val isTargetAdmin: Boolean? = null,

        @field:SerializedName("isAdminOrGlobalModerator")
        val isAdminOrGlobalModerator: Boolean? = null,

        @field:SerializedName("emailClass")
        val emailClass: String? = null,

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("downvote:disabled")
        val downvoteDisabled: Boolean? = null,

        @field:SerializedName("topiccount")
        val topiccount: Int? = null,

        @field:SerializedName("relative_path")
        val relativePath: String? = null,

        @field:SerializedName("isSelf")
        val isSelf: Boolean? = null,

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb?>? = null,

        @field:SerializedName("status")
        val status: String? = null,

        @field:SerializedName("birthday")
        val birthday: String? = null,

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("showHidden")
        val showHidden: Boolean? = null,

        @field:SerializedName("yourid")
        val yourid: Int? = null,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("lastposttime")
        val lastposttime: Long? = null,

        @field:SerializedName("isModerator")
        val isModerator: Boolean? = null,

        @field:SerializedName("signature")
        val signature: String? = null,

        @field:SerializedName("icon:bgColor")
        val iconBgColor: String? = null,

        @field:SerializedName("canEdit")
        val canEdit: Boolean? = null,

        @field:SerializedName("flags")
        val flags: Any? = null,

        @field:SerializedName("groupTitle")
        val groupTitle: String? = null,

        @field:SerializedName("noItemsFoundKey")
        val noItemsFoundKey: String? = null,

        @field:SerializedName("nextStart")
        val nextStart: Int? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("followingCount")
        val followingCount: Int? = null,

        @field:SerializedName("lastonlineISO")
        val lastonlineISO: String? = null,

        @field:SerializedName("email:disableEdit")
        val emailDisableEdit: Boolean? = null,

        @field:SerializedName("uid")
        val uid: String? = null,

        @field:SerializedName("canChangePassword")
        val canChangePassword: Boolean? = null,

        @field:SerializedName("profileviews")
        val profileviews: Int? = null,

        @field:SerializedName("bodyClass")
        val bodyClass: String? = null,

        @field:SerializedName("cover:url")
        val coverUrl: String? = null,

        @field:SerializedName("banned")
        val banned: Any?,

        @field:SerializedName("banned_until")
        val bannedUntil: Int? = null,

        @field:SerializedName("userslug")
        val userslug: String? = null,

        @field:SerializedName("followerCount")
        val followerCount: Int? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("website")
        val website: String? = null,

        @field:SerializedName("isFollowing")
        val isFollowing: Boolean? = null,

        @field:SerializedName("uploadedpicture")
        val uploadedpicture: String? = null,

        @field:SerializedName("canBan")
        val canBan: Boolean? = null,

        @field:SerializedName("lastonline")
        val lastonline: Long? = null,

        @field:SerializedName("disableSignatures")
        val disableSignatures: Boolean? = null,

        @field:SerializedName("banned:expire")
        val bannedExpire: Any? = null,

        @field:SerializedName("groups")
        val groups: List<Any?>? = null,

        @field:SerializedName("username:disableEdit")
        val usernameDisableEdit: Boolean? = null,

        @field:SerializedName("picture")
        val picture: String? = null,

        @field:SerializedName("joindateISO")
        val joindateISO: String? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("isSelfOrAdminOrGlobalModerator")
        val isSelfOrAdminOrGlobalModerator: Boolean? = null,

        @field:SerializedName("websiteName")
        val websiteName: String? = null,

        @field:SerializedName("isAdminOrGlobalModeratorOrModerator")
        val isAdminOrGlobalModeratorOrModerator: Boolean? = null,

        @field:SerializedName("cover:position")
        val coverPosition: String? = null,

        @field:SerializedName("postcount")
        val postcount: Int? = null,

        @field:SerializedName("location")
        val location: String? = null,

        @field:SerializedName("fullname")
        val fullname: String? = null,

        @field:SerializedName("age")
        val age: Int? = null,

        @field:SerializedName("theirid")
        val theirid: String? = null,

        @field:SerializedName("username")
        val username: String? = null
)