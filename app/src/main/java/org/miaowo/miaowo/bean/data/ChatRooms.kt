package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ChatRooms(

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("uid")
        val uid: Int? = null,

        @field:SerializedName("rooms")
        val rooms: List<ChatRoom> = emptyList(),

        @field:SerializedName("allowed")
        val allowed: Boolean? = null,

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("bodyClass")
        val bodyClass: String? = null,

        @field:SerializedName("nextStart")
        val nextStart: Int? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("relative_path")
        val relativePath: String? = null,

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("userslug")
        val userslug: String? = null,

        @field:SerializedName("url")
        val url: String? = null
)