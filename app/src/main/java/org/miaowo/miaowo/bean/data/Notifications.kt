package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Notifications(

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("filters")
        val filters: List<Filter> = emptyList(),

        @field:SerializedName("title")
        val title: String = "",

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("moderatorFilters")
        val moderatorFilters: List<Filter> = emptyList(),

        @field:SerializedName("url")
        val url: String = "",

        @field:SerializedName("selectedFilter")
        val selectedFilter: Filter? = null,

        @field:SerializedName("regularFilters")
        val regularFilters: List<Filter> = emptyList(),

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean = false,

        @field:SerializedName("bodyClass")
        val bodyClass: String = "",

        @field:SerializedName("relative_path")
        val relativePath: String = "",

        @field:SerializedName("notifications")
        val notifications: List<Notification> = emptyList(),

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb> = emptyList()
)