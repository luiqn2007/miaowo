package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Unreads(

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("pageCount")
        val pageCount: Int? = null,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("topics")
        val topics: List<Topic> = emptyList(),

        @field:SerializedName("querystring")
        val querystring: String? = null,

        @field:SerializedName("nextStart")
        val nextStart: Int? = null,

        @field:SerializedName("filters")
        val filters: List<Filter> = emptyList(),

        @field:SerializedName("title")
        val title: String = "",

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("showSelect")
        val showSelect: Boolean? = null,

        @field:SerializedName("url")
        val url: String = "",

        @field:SerializedName("selectedFilter")
        val selectedFilter: Filter? = null,

        @field:SerializedName("selectedCids")
        val selectedCids: List<Any> = emptyList(),

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("bodyClass")
        val bodyClass: String? = null,

        @field:SerializedName("topicCount")
        val topicCount: Int? = null,

        @field:SerializedName("categories")
        val categories: List<Category> = emptyList(),

        @field:SerializedName("relative_path")
        val relativePath: String? = null,

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb> = emptyList()
)