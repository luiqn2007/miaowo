package org.miaowo.miaowo.interfaces.apiInterface

import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.bean.*
import org.miaowo.miaowo.data.config.VersionMessage
import retrofit2.Call
import retrofit2.http.*

interface IApiDocs {

    /**
     * 版本信息
     */
    @GET
    fun version(@Url url: String = "http://git.oschina.net/lq2007/miaowo/raw/master/app/version"): Call<VersionMessage>

    /**
     * 用户信息
     */
    @GET("api/user/{username}")
    fun user(@Path("username") username: String,
             @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<User>

    @GET("api/users/{qs}")
    fun users(@Path("qs") qs: String? = null,
              @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<Users>

    /**
     * 主页
     */
    @GET("api")
    fun index(): Call<Index>

    /**
     * 未读列表
     */
    @GET("api/unread/{qs}")
    fun unread(@Path("qs") qs: String? = null,
               @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<Category>

    /**
     * 通知列表
     */
    @GET("api/notifications")
    fun notification(@Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<Notifications>

    /**
     * 主题列表
     */
    @GET("api/category/{cid}/{qs}")
    fun category(@Path("cid") cid: Int,
                 @Path("qs") qs: String = "",
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<Category>

    /**
     * 话题
     */
    @GET("api/topic/{tid}")
    fun topic(@Path("tid") tid: Int,
              @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<Topic>


    /**
     * 用户话题列表
     */
    @GET("api/user/{username}/topics")
    fun userTopics(@Path("username") username: String,
                   @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<UserTopics>

    /**
     * 搜索
     */
    @GET("api/search")
    fun searchUser(@Query("term") key: String,
                   @Query("in") queryIn: String = "users",
                   @Header("Authorization") authorization: String = "Bearer ${API.token[0]}",
                   @Query("_uid") searchUid: Int? = null): Call<SearchResult>

    @GET("api/search")
    fun searchTopic(@Query("term") key: String,
                    @Query("showAs") showAs: String = "posts",
                    @Query("in") queryIn: String = "titlesposts",
                    @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<SearchResult>

    /**
     * 聊天
     */
    @GET("api/user/{username}/chats")
    fun chatRoom(@Path("username") username: String = API.user.username,
                 @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ChatRooms>


    @GET("api/user/{username}/chats/{roomId}")
    fun chatMessage(@Path("roomId") roomId: Int,
                    @Path("username") username: String = API.user.username,
                    @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ChatRoom>

    /**
     * 用户帖子
     */
    @GET("api/user/{username}/posts")
    fun post(@Path("username") username: String = API.user.username,
             @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<UserPosts>
}