package org.miaowo.miaowo.interfaces.apiInterface

import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import retrofit2.Call
import retrofit2.http.*

/**
 * /groups
 * Groups
 *
 * create, delete 需要 Admin 权限
 * 其他需要 Admin 或 owner 权限
 *
 */
interface IApiGroups {

    /**
     * POST /
     * Creates a new group
     * Requires: name
     * Accepts: description, hidden, private, ownerUid
     *
     * {
     *     "code": "internal-server-error",
     *     "message": "group-already-exists",
     *     "params": {}
     * }
     *
     * {
     *     "code": "ok",
     *     "payload": {
     *         "name": "api_test",
     *         "slug": "api_test",
     *         "createtime": 1533176844356,
     *         "userTitle": "api_test",
     *         "description": "test group api",
     *         "memberCount": 1,
     *         "deleted": 0,
     *         "hidden": 0,
     *         "system": 0,
     *         "private": null,
     *         "disableJoinRequests": 0,
     *         "ownerUid": "203"
     *     }
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/groups/")
    fun create(@Field("name") name: String,
               @Field("description") description: String,
               @Field("hidden") hidden: Boolean = false,
               @Field("private") private: Boolean = false,
               @Field("ownerUid") ownerUid: Int = API.user.uid,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:slug
     * Deletes a group (Careful: There is no confirmation!)
     * Accepts: No parameters
     *
     * {
     *     "code": "forbidden",
     *     "message": "You are not authorised to make this call",
     *     "params": {}
     * }
     *
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/groups/{slug}")
    fun delete(@Path("slug") slug: String,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:slug/membership
     * Joins a group (or requests membership if it is a private group)
     * Accepts: No parameters
     *
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @PUT("api/v2/groups/{slug}/membership")
    fun join(@Path("slug") slug: String,
             @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:slug/membership/
     * Requires: uid
     * Adds a user to a group (The calling user has to be an administrator)
     * Accepts: No parameters
     *
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @FormUrlEncoded
    @PUT("api/v2/groups/{slug}/membership/{uid}")
    fun add(@Path("slug") slug: String,
            @Field("uid") uid: Int,
            @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:slug/membership
     * Leaves a group
     * Accepts: No parameters
     *
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/groups/{slug}/membership")
    fun leave(@Path("slug") slug: String,
              @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:slug/membership/:uid
     * Requires: uid
     * Removes a user from a group (The calling user has to be an administrator)
     * Accepts: No parameters
     *
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @FormUrlEncoded
    @DELETE("api/v2/groups/{slug}/membership/")
    fun delete(@Path("slug") slug: String,
               @Field("uid") uid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>
}