package org.miaowo.miaowo.interfaces.apiInterface

import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import retrofit2.Call
import retrofit2.http.*

/**
 * /posts
 * Posts
 */
interface IApiPosts {

    /**
     * PUT /:pid
     * Edits a post by post ID
     * Requires: content
     * Accepts: title, topic_thumb, tags(array)
     */
    @FormUrlEncoded
    @PUT("api/v2/posts/{pid}")
    fun edit(@Path("pid") pid: Int,
             @Field("content") content: String,
             @Field("title") title: String? = null,
             @Field("topic_thumb") topic_thumb: String? = null,
             @Field("tags") tags: Iterable<String>,
             @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:pid
     * Purges a post, thereby removing it from the database completely (Careful: There is no confirmation!)
     * Accepts: No parameters
     */
    @DELETE("api/v2/posts/{pid}")
    fun delete(@Path("pid") pid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:pid/state
     * Deletes a post (that is, a soft-delete)
     * Accepts: No parameters
     */
    @DELETE("api/v2/posts/{pid}/state")
    fun softDelete(@Path("pid") pid: Int,
                   @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:pid/state
     * Restores a post
     * Accepts: No parameters
     */
    @PUT("api/v2/posts/{pid}/state")
    fun restore(@Path("pid") pid: Int,
                @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * POST /:pid/vote
     * Votes for a post
     * Requires: delta
     * delta must be a number. If delta > 0, it's considered an upvote;
     *                         if delta < 0, it's considered a downvote;
     *                         otherwise, it's an unvote.
     */
    @FormUrlEncoded
    @PUT("api/v2/posts/{pid}/vote")
    fun vote(@Path("pid") pid: Int,
             @Field("delta") delta: Float,
             @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:pid/vote
     * Unvotes a post
     * Accepts: No parameters
     */
    @DELETE("api/v2/posts/{pid}/vote")
    fun unVote(@Path("pid") pid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>
}