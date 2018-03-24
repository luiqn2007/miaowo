package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

/**
 * Prev, Next, PagesItem
 */
@Generated("com.robohorse.robopojogenerator")
data class Page(

        @field:SerializedName("qs")
        val qs: String? = null,

        @field:SerializedName("active")
        val active: Boolean? = null,

        @field:SerializedName("page")
        val page: Int? = null
)