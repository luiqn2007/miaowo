package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ChatTeaser(

        @field:SerializedName("fromuid")
        val fromuid: Int? = null,

        @field:SerializedName("timestampISO")
        val timestampISO: String? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("timestamp")
        val timestamp: Long? = null
)