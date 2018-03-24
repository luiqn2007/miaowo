package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Privileges(

        @field:SerializedName("topics:tag")
        val topicsTag: Boolean? = null,

        @field:SerializedName("uid")
        val uid: Int? = null,

        @field:SerializedName("isAdminOrMod")
        val isAdminOrMod: Boolean? = null,

        @field:SerializedName("topics:create")
        val topicsCreate: Boolean? = null,

        @field:SerializedName("read")
        val read: Boolean? = null,

        @field:SerializedName("editable")
        val editable: Boolean? = null,

        @field:SerializedName("topics:read")
        val topicsRead: Boolean? = null,

        @field:SerializedName("view_deleted")
        val viewDeleted: Boolean? = null,

        @field:SerializedName("cid")
        val cid: String? = null,

        @field:SerializedName("topics:delete")
        val topicsDelete: Boolean? = null,

        @field:SerializedName("deletable")
        val deletable: Boolean? = null,

        @field:SerializedName("posts:delete")
        val postsDelete: Boolean? = null,

        @field:SerializedName("tid")
        val tid: String? = null,

        @field:SerializedName("posts:edit")
        val postsEdit: Boolean? = null,

        @field:SerializedName("view_thread_tools")
        val viewThreadTools: Boolean? = null,

        @field:SerializedName("disabled")
        val disabled: Boolean? = null,

        @field:SerializedName("topics:reply")
        val topicsReply: Boolean? = null
)