package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Users(

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("reputation:disabled")
        val reputationDisabled: Boolean? = null,

        @field:SerializedName("isAdminOrGlobalMod")
        val isAdminOrGlobalMod: Boolean? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("users")
        val users: List<User> = emptyList(),

        @field:SerializedName("adminInviteOnly")
        val adminInviteOnly: Boolean? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("inviteOnly")
        val inviteOnly: Boolean? = null,

        @field:SerializedName("userCount")
        val userCount: Int? = null,

        @field:SerializedName("maximumInvites")
        val maximumInvites: String? = null,

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("bodyClass")
        val bodyClass: String? = null,

        @field:SerializedName("invites")
        val invites: Int? = null,

        @field:SerializedName("relative_path")
        val relativePath: String? = null,

        @field:SerializedName("section_joindate")
        val sectionJoindate: Boolean? = null,

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb?>? = null
)