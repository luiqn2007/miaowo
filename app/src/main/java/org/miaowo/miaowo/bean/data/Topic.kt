package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import org.miaowo.miaowo.interfaces.IPostItem

/**
 * https://www.miaowo.org/api/topic/265/%E5%85%B3%E4%BA%8E%E5%96%B5%E7%AA%9D-web-%E7%AB%AF%E4%B8%AD%E5%86%85%E5%AE%B9%E7%9A%84%E5%B8%83%E5%B1%80-%E5%A4%A7%E5%AE%B6%E6%9D%A5%E8%AE%A8%E8%AE%BA%E4%B8%80%E4%B8%8B%E5%90%A7
 */
@Generated("com.robohorse.robopojogenerator")
data class Topic(

        @field:SerializedName("privileges")
        val privileges: Privileges? = null,

        @field:SerializedName("upvotes")
        val upvotes: Int = -1,

        @field:SerializedName("unreplied")
        val unreplied: Boolean = false,

        @field:SerializedName("postDeleteDuration")
        val postDeleteDuration: Int = -1,

        @field:SerializedName("topicStaleDays")
        val topicStaleDays: Int = -1,

        @field:SerializedName("teaserPid")
        val teaserPid: String = "",

        @field:SerializedName("teaser")
        val teaser: Teaser? = null,

        @field:SerializedName("loggedInUser")
        val loggedInUser: User? = null,

        @field:SerializedName("posts")
        val posts: List<Post> = emptyList(),

        @field:SerializedName("tid")
        val tid: Int = -1,

        @field:SerializedName("postEditDuration")
        val postEditDuration: Int = -1,

        @field:SerializedName("mainPid")
        val mainPid: String = "",

        @field:SerializedName("rssFeedUrl")
        val rssFeedUrl: String = "",

        @field:SerializedName("locked")
        val locked: Boolean = false,

        @field:SerializedName("slug")
        val slug: String = "",

        @field:SerializedName("reputation:disabled")
        val reputationDisabled: Boolean = false,

        @field:SerializedName("downvotes")
        val downvotes: Int = -1,

        @field:SerializedName("tags")
        val tags: List<Any> = emptyList(),

        @field:SerializedName("bookmark")
        val bookmark: Int = -1,

        @field:SerializedName("feeds:disableRSS")
        val feedsDisableRSS: Boolean = false,

        @field:SerializedName("isNotFollowing")
        val isNotFollowing: Boolean = false,

        @field:SerializedName("deletedTimestampISO")
        val deletedTimestampISO: String = "",

        @field:SerializedName("loggedIn")
        val loggedIn: Boolean = false,

        @field:SerializedName("downvote:disabled")
        val downvoteDisabled: Boolean = false,

        @field:SerializedName("postIndex")
        val postIndex: Int = -1,

        @field:SerializedName("titleRaw")
        val titleRaw: String = "",

        @field:SerializedName("relative_path")
        val relativePath: String = "",

        @field:SerializedName("breadcrumbs")
        val breadcrumbs: List<Breadcrumb> = emptyList(),

        @field:SerializedName("cid")
        val cid: String = "",

        @field:SerializedName("template")
        val template: Template? = null,

        @field:SerializedName("pinned")
        val pinned: Boolean = false,

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("lastposttime")
        val lastposttime: Long? = null,

        @field:SerializedName("thumb")
        val thumb: String = "",

        @field:SerializedName("bookmarkThreshold")
        val bookmarkThreshold: Int = -1,

        @field:SerializedName("postSharing")
        val postSharing: List<Any> = emptyList(),

        @field:SerializedName("timestampISO")
        val timestampISO: String = "",

        @field:SerializedName("title")
        val title: String = "",

        @field:SerializedName("widgets")
        val widgets: Widgets? = null,

        @field:SerializedName("deleter")
        val deleter: Any? = null,

        @field:SerializedName("uid")
        val uid: Int = -1,

        @field:SerializedName("lastposttimeISO")
        val lastposttimeISO: String = "",

        @field:SerializedName("related")
        val related: List<Any> = emptyList(),

        @field:SerializedName("bodyClass")
        val bodyClass: String = "",

        @field:SerializedName("tagWhitelist")
        val tagWhitelist: List<Any> = emptyList(),

        @field:SerializedName("timestamp")
        val timestamp: Long? = null,

        @field:SerializedName("isIgnoring")
        val isIgnoring: Boolean = false,

        @field:SerializedName("isFollowing")
        val isFollowing: Boolean = false,

        @field:SerializedName("scrollToMyPost")
        val scrollToMyPost: Boolean = false,

        @field:SerializedName("icons")
        val icons: List<Any> = emptyList(),

        @field:SerializedName("url")
        val url: String = "",

        @field:SerializedName("deleted")
        val deleted: Boolean = false,

        @field:SerializedName("postcount")
        val postcount: Int = -1,

        @field:SerializedName("thread_tools")
        val threadTools: List<Any> = emptyList(),

        @field:SerializedName("viewcount")
        val viewcount: Int = -1,

        @field:SerializedName("votes")
        val votes: Int = -1,

        @field:SerializedName("category")
        val category: Category? = null,

        @field:SerializedName("unread")
        val unread: Boolean = false,

        @field:SerializedName("isOwner")
        val isOwner: Boolean = false,

        @field:SerializedName("ignored")
        val ignored: Boolean = false,

        @field:SerializedName("index")
        val index: Int = -1,

        @field:SerializedName("user")
        val user: User? = null
) : IPostItem {
    fun postFromTeaser(): Post? {
        if (teaser == null) return null

        return Post(
                timestamp = teaser.timestamp,
                timestampISO = teaser.timestampISO,
                pid = teaser.pid,
                tid = teaser.tid,
                uid = teaser.uid,
                user = user,
                content = teaser.content,
                topic = copy(),
                fromTeaser = true,
                extraMessage = postcount
        )
    }
}