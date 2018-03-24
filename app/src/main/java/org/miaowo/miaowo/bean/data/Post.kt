package org.miaowo.miaowo.bean.data

import com.google.gson.annotations.SerializedName
import org.miaowo.miaowo.interfaces.IPostItem
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class Post(

        @field:SerializedName("upvotes")
        val upvotes: Int = -1,

        @field:SerializedName("pid")
        val pid: Int = -1,

        @field:SerializedName("timestampISO")
        val timestampISO: String? = null,

        @field:SerializedName("downvotes")
        val downvotes: Int = -1,

        @field:SerializedName("tid")
        val tid: Int = -1,

        @field:SerializedName("content")
        val content: String? = null,

        @field:SerializedName("uid")
        val uid: Int = -1,

        @field:SerializedName("deleted")
        val deleted: Boolean? = null,

        @field:SerializedName("isMainPost")
        val isMainPost: Boolean? = null,

        @field:SerializedName("topic")
        val topic: Topic? = null,

        @field:SerializedName("votes")
        val votes: Int = -1,

        @field:SerializedName("category")
        val category: Category? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("timestamp")
        val timestamp: Long = -1,

        @field:SerializedName("parentCid")
        val parentCid: Int = -1,

        @field:SerializedName("index")
        val index: Int = -1,

        @field:SerializedName("cid")
        val cid: String? = null,

        @field:SerializedName("selfPost")
        val selfPost: Boolean? = null,

        @field:SerializedName("display_edit_tools")
        val displayEditTools: Boolean? = null,

        @field:SerializedName("editedISO")
        val editedISO: String? = null,

        @field:SerializedName("display_moderator_tools")
        val displayModeratorTools: Boolean? = null,

        @field:SerializedName("downvoted")
        val downvoted: Boolean? = null,

        @field:SerializedName("display_delete_tools")
        val displayDeleteTools: Boolean? = null,

        @field:SerializedName("editor")
        val editor: Editor? = null,

        @field:SerializedName("edited")
        val edited: Long? = null,

        @field:SerializedName("bookmarked")
        val bookmarked: Boolean? = null,

        @field:SerializedName("replies")
        val replies: Reply? = null,

        @field:SerializedName("display_move_tools")
        val displayMoveTools: Boolean? = null,

        @field:SerializedName("upvoted")
        val upvoted: Boolean? = null,

        @field:SerializedName("display_post_menu")
        val displayPostMenu: Boolean? = null

) : IPostItem {
    constructor(topic: Topic?) : this(
            timestamp = topic?.teaser?.timestamp ?: -1,
            timestampISO = topic?.teaser?.timestampISO,
            pid = topic?.teaser?.pid ?: -1,
            tid = topic?.teaser?.tid ?: -1,
            uid = topic?.teaser?.uid ?: -1,
            user = topic?.teaser?.user,
            content = topic?.teaser?.content,
            topic = topic?.copy()
    )
}