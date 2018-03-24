package org.miaowo.miaowo.bean.data

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class ChatMessage(

        @field:SerializedName("fromuid")
        val fromuid: Int? = null,

        @field:SerializedName("fromUser")
        val fromUser: User? = null,

        @field:SerializedName("messageId")
        val messageId: Int? = null,

        @field:SerializedName("self")
        val self: Int? = null,

        @field:SerializedName("newSet")
        val newSet: Boolean? = null,

        @field:SerializedName("index")
        val index: Int? = null,

        @field:SerializedName("timestampISO")
        val timestampISO: String? = null,

        @field:SerializedName("cleanedContent")
        val cleanedContent: String? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("roomId")
        val roomId: String? = null,

        @field:SerializedName("timestamp")
        val timestamp: Long = 0
)