package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

/**
 * 话题
 */
@Generated("com.robohorse.robopojogenerator")
data class Topic(
        @field:SerializedName("pinned")
        val pinned: Boolean? = null,

        @field:SerializedName("lastposttime")
        val lastposttime: Long? = null,

        @field:SerializedName("unreplied")
        val unreplied: Boolean? = null,

        @field:SerializedName("unread")
        val unread: Boolean? = null,

        @field:SerializedName("timestampISO")
        val timestampISO: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("teaserPid")
        val teaserPid: String? = null,

        @field:SerializedName("tid")
        val tid: Int? = null,

        @field:SerializedName("uid")
        val uid: Int? = null,

        @field:SerializedName("lastposttimeISO")
        val lastposttimeISO: String? = null,

        @field:SerializedName("isOwner")
        val isOwner: Boolean? = null,

        @field:SerializedName("mainPid")
        val mainPid: Int? = null,

        @field:SerializedName("locked")
        val locked: Boolean? = null,

        @field:SerializedName("slug")
        val slug: String? = null,

        @field:SerializedName("timestamp")
        val timestamp: Long? = null,

        @field:SerializedName("ignored")
        val ignored: Boolean? = null,

        @field:SerializedName("icons")
        val icons: List<Any?>? = null,

        @field:SerializedName("tags")
        val tags: List<Any?>? = null,

        @field:SerializedName("bookmark")
        val bookmark: Any? = null,

        @field:SerializedName("deleted")
        val deleted: Boolean? = null,

        @field:SerializedName("postcount")
        val postcount: Int? = null,

        @field:SerializedName("viewcount")
        val viewcount: Int? = null,

        @field:SerializedName("titleRaw")
        val titleRaw: String? = null,

        @field:SerializedName("category")
        val category: Category? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("cid")
        val cid: String? = null,

        @field:SerializedName("teaser")
        val teaser: Teaser? = null,

        val value: String? = null,  // value : 猜猜我是谁
        val score: Int = 0,  // score : 2
        val color: String? = null,  // color :
        val bgColor: String? = null  // bgColor :
)