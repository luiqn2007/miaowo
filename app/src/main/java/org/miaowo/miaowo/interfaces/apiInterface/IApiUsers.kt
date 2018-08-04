package org.miaowo.miaowo.interfaces.apiInterface

import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import org.miaowo.miaowo.other.Const
import retrofit2.Call
import retrofit2.http.*

/**
 * /users
 * Users
 *
 * 注意: 以 Admin 结尾的必须用有管理员权限的 Token 调用, 否则返回
 * {
 *     "code": "forbidden",
 *     "message": "You are not authorised to make this call",
 *     "params": {}
 * }
 */
interface IApiUsers {

    /**
     * POST /
     * Creates a new user
     * Requires: username
     * Accepts: password, email
     * Any other data passed in will be saved into the user hash
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "uid": 204
     *     }
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/users/")
    fun create(@Field("username") username: String,
               @Field("password") password: String,
               @Field("email") email: String,
               @Field("_uid") createdUid: Int = 7,
               @Header("Authorization") authorization: String = "Bearer ${Const.TOKEN_MASTER}"): Call<ResponseBody>

    /**
     * PUT /:uid
     * Updates a user's profile information
     * Accepts: username, email, fullname, website, location, birthday, signature
     * Also accepts any values exposed via the action:user.updateProfile hook
     * The uid specified in the route path is optional.
     * Without it, the profile of the calling user is edited.
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @FormUrlEncoded
    @PUT("api/v2/users/{uid}")
    fun update(@Field("username") name: String,
               @Field("email") email: String,
               @Field("fullname") fullName: String,
               @Field("website") website: String,
               @Field("location") location: String,
               @Field("birthday") birthday: String,
               @Field("signature") signature: String,
               @Path("uid") uid: Int = API.user.uid,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:uid
     * Deletes a user from NodeBB (Careful: There is no confirmation!)
     * Accepts: No parameters
     * Can be called by either the target uid itself, or an administrative uid.
     */
    @DELETE("api/v2/users/{uid}")
    fun delete(@Path("uid") uid: Int = API.user.uid,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:uid/password
     * Changes a user's password
     * Requires: uid, new
     * Accepts: current
     * current is required if the calling user is not an administrator
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     *
     * Error:
     * {
     *     "code": "internal-server-error",
     *     "message": "invalid-password",
     *     "params": {}
     * }
     */
    @FormUrlEncoded
    @PUT("api/v2/users/{uid}/password")
    fun password(@Field("current") old: String,
                 @Field("new") new: String,
                 @Field("uid") uid: Int = API.user.uid,
                 @Path("uid") uidPath: Int = API.user.uid,
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:uid/follow
     * Follows another user
     * Accepts: No parameters
     *
     * ResponseBody
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @PUT("api/v2/users/{uid}/follow")
    fun follow(@Path("uid") uid: Int,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:uid/follow
     * Unfollows another user
     * Accepts: No parameters
     *
     * ResponseBody
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/users/{uid}/follow")
    fun unfollow(@Path("uid") uid: Int,
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * POST /:uid/chats
     * Sends a chat message to another user
     * Requires: message
     * Accepts: timestamp, quiet
     * timestamp (unix timestamp in ms) allows messages to be sent from the past (useful when importing chats)
     * quiet if set, will not notify the user that a chat message has been received (also useful during imports)
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "content": "<p>聊天测试</p>\n",
     *         "fromuid": 7,
     *         "roomId": 62,
     *         "timestamp": 1533122984819,
     *         "messageId": 903,
     *         "fromUser": {
     *             "uid": 7,
     *             "username": "么么么喵",
     *             "userslug": "么么么喵",
     *             "picture": "/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg",
     *             "status": "offline",
     *             "icon:text": "么",
     *             "icon:bgColor": "#1b5e20"
     *         },
     *         "self": 1,
     *         "timestampISO": "2018-08-01T11:29:44.819Z",
     *         "newSet": true,
     *         "cleanedContent": "聊天测试\n",
     *         "mid": 903
     *     }
     * }
     *
     * roomId = Null:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "content": "<p>聊天测试 同一聊天室</p>\n",
     *         "fromuid": 7,
     *         "roomId": "62",
     *         "timestamp": 1533123091208,
     *         "messageId": 904,
     *         "fromUser": {
     *             "uid": 7,
     *             "username": "么么么喵",
     *             "userslug": "么么么喵",
     *             "picture": "/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg",
     *             "status": "offline",
     *             "icon:text": "么",
     *             "icon:bgColor": "#1b5e20"
     *         },
     *         "self": 1,
     *         "timestampISO": "2018-08-01T11:31:31.208Z",
     *         "newSet": false,
     *         "cleanedContent": "聊天测试 同一聊天室\n",
     *         "mid": 904
     *     }
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/users/{uid}/chats")
    fun chat(@Path("uid") uid: Int,
             @Field("message") message: String,
             @Field("roomId") roomId: Int? = null,
             @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * PUT /:uid/ban
     * Bans a user
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @PUT("api/v2/users/{uid}/ban")
    fun banAdmin(@Path("uid") uid: Int,
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * DELETE /:uid/ban
     * Bans a user
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/users/{uid}/ban")
    fun unbanAdmin(@Path("uid") uid: Int,
                   @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * GET /:uid/tokens
     * Retrieves a list of active tokens for that user
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "tokens": [
     *             "ecfc24b0-0454-4787-8590-68854e409cfa",
     *             "12bd0b7c-c484-42e8-accf-09b9ed8748ee"
     *         ]
     *     }
     * }
     */
    @GET("api/v2/users/{uid}/tokens")
    fun tokens(@Path("uid") uid: Int = API.user.uid,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>

    /**
     * POST /:uid/tokens
     * Creates a new user token for the passed in uid
     * Accepts: No parameters normally, will accept password in lieu of Bearer token
     * Can be called with an active token for that user
     * This is the only route that will allow you to pass in password in the request body.
     * Generate a new token and then use the token in subsequent calls.
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {
     *         "token": "ecfc24b0-0454-4787-8590-68854e409cfa"
     *     }
     * }
     */
    @FormUrlEncoded
    @POST("api/v2/users/{uid}/tokens")
    fun tokenCreate(@Field("password") pwd: String = API.user.password,
                    @Path("uid") uid: Int = API.user.uid): Call<ResponseBody>

    /**
     * DELETE /:uid/tokens/:token
     * Revokes an active user token
     * Accepts: No parameters
     *
     * ResponseBody:
     * {
     *     "code": "ok",
     *     "payload": {}
     * }
     */
    @DELETE("api/v2/users/{uid}/tokens/{token}")
    fun tokenRemove(@Path("token") token: String,
                    @Path("uid") uid: Int,
                    @Header("Authorization") authorization: String = "Bearer ${API.token.last()}"): Call<ResponseBody>
}