package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import org.miaowo.miaowo.interfaces.IPostItem

@Generated("com.robohorse.robopojogenerator")
data class Teaser(

        @field:SerializedName("timestampISO")
        val timestampISO: String? = null,

        @field:SerializedName("timestamp")
        val timestamp: Long = 0,

        @field:SerializedName("index")
        val index: Int = -1,

        @field:SerializedName("pid")
        val pid: Int = -1,

        @field:SerializedName("uid")
        val uid: Int = -1,

        @field:SerializedName("tid")
        val tid: Int = -1,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("content")
        val content: String? = null

)