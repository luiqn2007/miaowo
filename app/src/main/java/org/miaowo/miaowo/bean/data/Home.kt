package org.miaowo.miaowo.bean.data

import com.google.gson.annotations.SerializedName

/**
 * 主页
 * Created by luqin on 17-5-31.
 */
data class Home(
        val loggedIn: Boolean? = null,
        val bodyClass: String? = null,
        val categories: List<Category>? = null,
        val title: String? = null,
        @field:SerializedName("relative_path")
        val relativePath: String? = null,
        val url: String? = null
)