package org.miaowo.miaowo.interfaces.apiInterface

import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import retrofit2.Call
import retrofit2.http.*

/**
 * /topics
 * Topics
 */
interface IApiTopics {

    /**
     * POST /
     * Creates a new topic
     * Requires: cid, title, content
     * Accepts: tags (array)
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "topicData": {} => (Object: Topic)
     *         "postData": {} => (Object: Post)
     *     }
     * }
     *
     * Error:
     * {
     *     "code": "internal-server-error",
     *     "message": "[[error:too-many-tags, 5]]",
     *     "params": {}
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/topics/")
    fun create(@Field("cid") cid: Int,
               @Field("title") title: String,
               @Field("content") content: String,
               @Field("tags") tags: Iterable<String>,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * POST /:tid
     * Posts a new reply to the topic
     * Requires: content
     * Accepts: toPid
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {} => (Object: Post)
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/topics/{tid}")
    fun reply(@Path("tid") tid: Int,
              @Field("content") content: String,
              @Field("toPid") toPid: Int? = null,
              @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:tid
     * Updates a post in a topic
     * Requires: pid, content
     * Accepts: handle, title, topic_thumb, tags(array)
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "editor": {
     *             "username": "TestApiPwd",
     *             "userslug": "testapipwd",
     *             "uid": 203
     *         },
     *         "topic": {} => (Object: Topic),
     *         "post": {} => (Object: Post, has edit content)
     *     }
     * }
     */
    @FormUrlEncoded
    @PUT("api/v2/topics/{tid}")
    fun update(@Path("tid") tid: Int,
               @Field("pid") pid: Int,
               @Field("content") content: String,
               @Field("handler") handle: String? = null,
               @Field("title") title: String? = null,
               @Field("topic_thumb") topic_thumb: String? = null,
               @Field("tags") tags: Iterable<String> = emptyList(),
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:tid
     * Purges a topic, including all posts inside (Careful: There is no confirmation!)
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/topics/{tid}")
    fun purge(@Path("tid") tid: Int): Call<ResponseBody>

    /**
     * DELETE /:tid/state
     * Deletes a topic (that is, a soft-delete) (Careful: There is no confirmation!)
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/topics/{tid}/state")
    fun delete(@Path("tid") tid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:tid/state
     * Restores a topic (Careful: There is no confirmation!)
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @PUT("api/v2/topics/{tid}/state")
    fun restore(@Path("tid") tid: Int,
                @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:tid/follow
     * Subscribes a user to a topic
     * Accepts: No parameters
     */
    @PUT("api/v2/topics/{tid}/follow")
    fun follow(@Path("tid") tid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:tid/follow
     * Unsubscribes a user to a topic
     * Accepts: No parameters
     */
    @DELETE("api/v2/topics/{tid}/follow")
    fun unfollow(@Path("tid") tid: Int,
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:tid/tags
     * Creates or update tags in a topic
     * Requires: tags(array)
     * This method does not append tags, it replaces the tag set associated with the topic
     */
    @FormUrlEncoded
    @PUT("api/v2/topics/{tid}/tags")
    fun updateTag(@Path("tid") tid: Int,
                  @Field("tags") tags: Iterable<String>,
                  @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:tid/tags
     * Accepts: No parameters
     * Clears the tag set associates with a topic
     */
    @DELETE("api/v2/topics/{tid}/tags")
    fun cleanTag(@Path("tid") tid: Int,
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>
}