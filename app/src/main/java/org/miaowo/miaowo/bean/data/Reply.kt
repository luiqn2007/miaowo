package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Reply(

        @field:SerializedName("hasMore")
        val hasMore: Boolean? = null,

        @field:SerializedName("count")
        val count: Int? = null,

        @field:SerializedName("text")
        val text: String? = null,

        @field:SerializedName("users")
        val users: List<Any?>? = null
)