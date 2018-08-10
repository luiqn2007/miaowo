package org.miaowo.miaowo.interfaces.apiInterface

import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import retrofit2.Call
import retrofit2.http.*

/**
 * /category
 * Categories
 *
 * Admin!!
 * 本 API 仅能由管理员权限使用
 */
interface IApiCategories {

    /**
     * POST /
     * Creates a new category
     * Requires: name
     * Accepts: description, bgColor(#RGB), color(#RGB), parentCid, class
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "cid": 9,
     *         "name": "测试",
     *         "description": "测试Category API ",
     *         "descriptionParsed": "",
     *         "icon": "",
     *         "bgColor": "#FF0000",
     *         "color": "#00FF00",
     *         "slug": "9/测试",
     *         "parentCid": "0",
     *         "topic_count": 0,
     *         "post_count": 0,
     *         "disabled": 0,
     *         "order": 9,
     *         "link": "",
     *         "numRecentReplies": 1,
     *         "class": "col-md-3 col-xs-6",
     *         "imageClass": "cover"
     *     }
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/category/")
    fun create(@Field("name") name: String,
               @Field("description") description: String = "",
               @Field("bgColor") bgColor: String = "#e95c5a",
               @Field("color") color: String = "#fff",
               @Field("parentCid") parentCid: Int = 0,
               @Field("icon") icon: String = "",
               @Field("clazz") clazz: String = "col-md-3 col-xs-6",
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:cid
     * Updates a category's data
     * Accepts: name, description, bgColor, color, parentCid
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @FormUrlEncoded
    @PUT("api/v2/category/{cid}")
    fun update(@Path("cid") cid: Int,
               @Field("name") name: String,
               @Field("description") description: String = "",
               @Field("bgColor") bgColor: String = "#e95c5a",
               @Field("color") color: String = "#fff",
               @Field("icon") icon: String? = null,
               @Field("parentCid") parentCid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:cid
     * Purges a category, including all topics and posts inside of it (Careful: There is no confirmation!)
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/category/{cid}")
    fun delete(@Path("cid") cid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:cid/state
     * Enables a category
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @PUT("api/v2/category/{cid}/state")
    fun enable(@Path("cid") cid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:cid/state
     * Disables a category
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/category/{cid}/state")
    fun disable(@Path("cid") cid: Int,
                @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:cid/privileges
     * Adds group privileges to a category
     * Requires: privileges (array), groups (array)
     *
     * privileges(特权?) // groups: JSON Array
     */
    @FormUrlEncoded
    @PUT("api/v2/category/{cid}/privileges")
    fun privilegesAdd(@Path("cid") cid: Int,
                      @Field("privileges") privileges: Iterable<String>,
                      @Field("groups") groups: Iterable<String>,
                      @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:cid/privileges
     * Deletes group privileges from a category
     * Requires: privileges (array), groups (array)
     */
    @FormUrlEncoded
    @DELETE("api/v2/category/{cid}/privileges")
    fun privilegesDelete(@Path("cid") cid: Int,
                         @Field("privileges") privileges: Iterable<String>,
                         @Field("groups") groups: Iterable<String>,
                         @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>
}