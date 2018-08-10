package org.miaowo.miaowo

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.interfaces.apiInterface.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.ReadonlyCookieJar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 封装 API, 基本使用的是 WriteAPI v2
 * https://github.com/NodeBB/nodebb-plugin-write-api
 *
 * api/v2 Endpoints
 * Note: When requested with a master token, an additional parameter (_uid) is required in the data
 * payload so the Write API can execute the requested action under the correct user context.
 * This limitation means that certain actions only work with a specific uid. For example,
 * PUT /:uid updates a user's profile information, but is only accessible by the uid of the user
 * itself, or an administrative uid. All other uids passed in will results in an error.
 */
@Suppress("unused")
object API {
    @Volatile
    var user = User.logout
    var token = mutableListOf<String>()
    val isLogin get() = user.isLogin
    val loginRecord = mutableListOf<User>()

    val gson = GsonBuilder().serializeNulls().create()
    val okhttp = OkHttpClient.Builder()
            .cookieJar(ReadonlyCookieJar)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("${Const.URL_BASE}/")
            .client(okhttp)
            .build()

    val Users = retrofit.create(IApiUsers::class.java)!!
    val Groups = retrofit.create(IApiGroups::class.java)!!
    val Categories = retrofit.create(IApiCategories::class.java)!!
    val Topics = retrofit.create(IApiTopics::class.java)!!
    val Posts = retrofit.create(IApiPosts::class.java)!!
    val Util = retrofit.create(IApiUtil::class.java)!!
    val Docs = retrofit.create(IApiDocs::class.java)!!
}