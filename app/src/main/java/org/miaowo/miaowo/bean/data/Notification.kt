package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Notification(

        @field:SerializedName("image")
        val image: String? = null,

        @field:SerializedName("read")
        val read: Boolean? = null,

        @field:SerializedName("mergeId")
        val mergeId: String? = null,

        @field:SerializedName("bodyLong")
        val bodyLong: String = "",

        @field:SerializedName("importance")
        val importance: Int? = null,

        @field:SerializedName("nid")
        val nid: String? = null,

        @field:SerializedName("pid")
        val pid: Int? = null,

        @field:SerializedName("type")
        val type: String? = null,

        @field:SerializedName("tid")
        val tid: Int? = null,

        @field:SerializedName("topicTitle")
        val topicTitle: String? = null,

        @field:SerializedName("path")
        val path: String? = null,

        @field:SerializedName("readClass")
        val readClass: String? = null,

        @field:SerializedName("datetime")
        val datetime: Long? = null,

        @field:SerializedName("from")
        val from: Int? = null,

        @field:SerializedName("bodyShort")
        val bodyShort: String = "",

        @field:SerializedName("datetimeISO")
        val datetimeISO: String? = null,

        @field:SerializedName("user")
        val user: User? = null
)