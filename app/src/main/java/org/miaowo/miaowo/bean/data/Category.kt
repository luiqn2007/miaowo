package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

/**
https://www.miaowo.org/api/category/6/%E7%81%8C%E6%B0%B4
 */
@Generated("com.robohorse.robopojogenerator")
data class Category(

        @field:SerializedName("unread-class")
        val unreadClass: String = "",

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("privileges")
        val privileges: Privileges? = null,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("color")
        val color: String = "",

        @field:SerializedName("numRecentReplies")
        val numRecentReplies: Int = -1,

        @field:SerializedName("icon")
        val icon: String = "",

        @field:SerializedName("link")
        val link: String = "",

        @field:SerializedName("description")
        val description: String = "",

        @field:SerializedName("nextStart")
        val nextStart: Int = -1,

        @field:SerializedName("title")
        val title: String = "",

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("showSelect")
        val showSelect: Boolean? = null,

        @field:SerializedName("undefined")
        val undefined: String = "",

        @field:SerializedName("totalTopicCount")
        val totalTopicCount: Int = -1,

        @field:SerializedName("parentCid")
        val parentCid: Int = -1,

        @field:SerializedName("totalPostCount")
        val totalPostCount: Int = -1,

        @field:SerializedName("descriptionParsed")
        val descriptionParsed: String = "",

        @field:SerializedName("bgColor")
        val bgColor: String = "",

        @field:SerializedName("children")
        val children: List<Any> = emptyList(),

        @field:SerializedName("bodyClass")
        val bodyClass: String = "",

        @field:SerializedName("disabled")
        val disabled: Boolean? = null,

        @field:SerializedName("rssFeedUrl")
        val rssFeedUrl: String = "",

        @field:SerializedName("tagWhitelist")
        val tagWhitelist: List<Any> = emptyList(),

        @field:SerializedName("class")
        val jsonMemberClass: String = "",

        @field:SerializedName("slug")
        val slug: String = "",

        @field:SerializedName("order")
        val order: Int = -1,

        @field:SerializedName("topics")
        val topics: List<Topic> = emptyList(),

        @field:SerializedName("isIgnored")
        val isIgnored: Boolean? = null,

        @field:SerializedName("topic_count")
        val topicCount: Int = -1,

        @field:SerializedName("url")
        val url: String = "",

        @field:SerializedName("imageClass")
        val imageClass: String = "",

        @field:SerializedName("feeds:disableRSS")
        val feedsDisableRSS: Boolean? = null,

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean? = null,

        @field:SerializedName("name")
        val name: String = "",

        @field:SerializedName("post_count")
        val postCount: Int = -1,

        @field:SerializedName("relative_path")
        val relativePath: String = "",

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb> = emptyList(),

        @field:SerializedName("cid")
        val cid: Int = -1,

        @field:SerializedName("posts")
        val posts: List<Post> = emptyList(),

        @field:SerializedName("teaser")
        val teaser: Teaser? = null,

        @field:SerializedName("image")
        val image: Any? = null
)