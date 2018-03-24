package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

/**
 * https://www.miaowo.org/api
 */
@Generated("com.robohorse.robopojogenerator")
data class Index(

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("bodyClass")
        val bodyClass: String? = null,

        @field:SerializedName("categories")
        val categories: List<Category> = emptyList(),

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("relative_path")
        val relativePath: String? = null,

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("url")
        val url: String? = null
)