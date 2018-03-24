package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Pagination(

        @field:SerializedName("next")
        val next: Page? = null,

        @field:SerializedName("pageCount")
        val pageCount: Int? = null,

        @field:SerializedName("pages")
        val pages: List<Page?>? = null,

        @field:SerializedName("prev")
        val prev: Page? = null,

        @field:SerializedName("rel")
        val rel: List<Rel?>? = null,

        @field:SerializedName("currentPage")
        val currentPage: Int? = null
)