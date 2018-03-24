package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Template(

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("category")
        val category: Boolean? = null,

        @field:SerializedName("account/profile")
        val accountProfile: Boolean? = null,

        @field:SerializedName("categories")
        val categories: Boolean? = null,

        @field:SerializedName("topic")
        val topic: Boolean? = null,

        @field:SerializedName("account/topics")
        val accountTopics: Boolean? = null,

        @field:SerializedName("account/posts")
        val accountPosts: Boolean? = null,

        @field:SerializedName("chats")
        val chats: Boolean? = null,

        @field:SerializedName("users")
        val users: Boolean? = null
)