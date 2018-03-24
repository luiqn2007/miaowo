package org.miaowo.miaowo.bean.data

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class ChatRoom(

        @field:SerializedName("owner")
        val owner: Int? = null,

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("rooms")
        val rooms: List<ChatRoom> = emptyList(),

        @field:SerializedName("canReply")
        val canReply: Boolean? = null,

        @field:SerializedName("groupChat")
        val groupChat: Boolean? = null,

        @field:SerializedName("maximumUsersInChatRoom")
        val maximumUsersInChatRoom: Int? = null,

        @field:SerializedName("showUserInput")
        val showUserInput: Boolean? = null,

        @field:SerializedName("nextStart")
        val nextStart: Int? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("roomId")
        val roomId: Int = -1,

        @field:SerializedName("roomName")
        val roomName: String? = null,

        @field:SerializedName("users")
        val users: List<User> = emptyList(),

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("uid")
        val uid: Int? = null,

        @field:SerializedName("isOwner")
        val isOwner: Boolean? = null,

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("bodyClass")
        val bodyClass: String? = null,

        @field:SerializedName("messages")
        val messages: List<ChatMessage> = emptyList(),

        @field:SerializedName("usernames")
        val usernames: String? = null,

        @field:SerializedName("relative_path")
        val relativePath: String? = null,

        @field:SerializedName("userslug")
        val userslug: String? = null,

        @field:SerializedName("maximumChatMessageLength")
        val maximumChatMessageLength: Int? = null,

        @field:SerializedName("lastUser")
        val lastUser: User? = null
)