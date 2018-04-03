package org.miaowo.miaowo

import android.net.Uri
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.lInfo
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.bean.SearchResult
import org.miaowo.miaowo.bean.config.VersionMessage
import org.miaowo.miaowo.bean.data.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.HttpUtil
import org.miaowo.miaowo.util.JsonUtil
import org.miaowo.miaowo.util.call
import org.miaowo.miaowo.bean.data.Users as BUsers

/**
 * 封装 API, 基本使用的是 WriteAPI v2
 * https://github.com/NodeBB/nodebb-plugin-write-api
 *
 * api/v2 Endpoints
 * Note: When requested with a master token, an additional parameter (_uid) is required in the data
 * payload so the Write API can execute the requested action under the correct user context.
 * This limitation means that certain actions only work with a specific uid. For example,
 * PUT /:uid updates a user's profile information, but is only accessible by the uid of the user
 * itself, or an administrative uid. All other uids passed in will result in an error.
 */
@Suppress("unused")
object API {
    @Volatile
    var user = User.logout
    var token = Token(user.uid, "")
    val isLogin
        get() = user.isLogin
    private val gson = GsonBuilder().serializeNulls().create()

    private val mUrl = App.i.getString(R.string.url_api)

    private fun useAPI(API: Type, sub: String, method: Method, body: FormBody?,
                       useToken: Boolean = true, useSavedToken: Boolean = false,
                       callback: (call: Call?, response: Response?) -> Unit) {
        val request = Request.Builder()
                .url(mUrl.format(API.name.toLowerCase(), sub))
                .method(method.name, body)
        if (useToken) request.addHeader("Authorization", "Bearer ${(if (useSavedToken) token.savedToken() else token).token}")
        request.build().call { call, response -> callback(call, response) }
    }

    private fun getResultMessage(response: Response?): String {
        val msg = JSONObject(response?.body()?.string() ?: "")
        lInfo("resultMessage: $msg")
        return if (Const.RET_OK == msg["code"].toString().toUpperCase()) {
            Const.RET_OK
        } else {
            try {
                msg.getString("message")
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }

    private fun listToArray(list: List<String>) = list.joinToString(prefix = "[", postfix = "]")

    private enum class Method { POST, PUT, GET, DELETE }

    private enum class Type { USERS, CATEGORIES, GROUPS, TOPICS, POSTS, UTIL; }

    enum class SearchType { USER, TOPIC }

    /**
     * 与账户有关
     */
    object Profile {

        /**
         * 登录
         */
        fun login(user: String, pwd: String, callback: (process: Float, result: Any) -> Unit) {
            // 检查是否已经登录
            if (API.user.username == user && API.user.password == pwd) {
                // 未登录 直接返回
                if (!API.isLogin) return
                // token 信息存在 已经登录
                if (API.token.token.isNotBlank()) callback(100f, "OK")
                else { // token 信息不存在 由注册
                    callback(60.0f, App.i.getString(R.string.process_get_token))
                    // Get Token
                    lInfo("token: $user")
                    Users.newToken(pwd) { message ->
                        if (message != null) throw message
                        else {
                            callback(80f, App.i.getString(R.string.process_category))
                            API.Doc.index {
                                callback(100f, it?.categories ?: emptyList<Category>())
                            }
                        }
                    }
                }
                return
            }
            // 检查用户名/密码是否有误
            val checkResult = checkUser(user, pwd)
            if (checkResult != "OK") {
                callback(-1f, checkResult)
                return
            }
            // 登录
            val call = HttpUtil.LinearCall()
                    // Csrf
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(0f, App.i.getString(R.string.process_info))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val csrf = JsonUtil.getCsrf(response)
                            if (csrf.isBlank()) throw Exception(App.i.getString(R.string.err_token))
                            return csrf
                        }
                    }) {
                        Request.Builder().url(App.i.getString(R.string.url_login)).build()
                    }
                    // Login
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(20f, App.i.getString(R.string.process_login))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val msg = response?.body()?.string() ?: "[[error empty]]"
                            if (msg.startsWith("[[error"))
                                throw Exception(msg.substring(2, msg.length - 2))
                            return null
                        }
                    }) {
                        val body = FormBody.Builder()
                                .add("username", user)
                                .add("password", pwd).build()
                        Request.Builder().url(App.i.getString(R.string.url_login))
                                .post(body)
                                .addHeader("x-csrf-token", it as? String ?: "").build()
                    }
                    // Get Detail User Message
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(40.0f, App.i.getString(R.string.process_user))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val gUser = API.Doc.build(response, User::class.java)
                            if (gUser != null) {
                                gUser.password = pwd
                                API.user = gUser
                            }
                            if (!API.isLogin)
                                throw Exception("Please login first!")
                            return null
                        }
                    }) {
                        Request.Builder().url(App.i.getString(R.string.url_user, user)).build()
                    }
                    // Register Token
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(60.0f, App.i.getString(R.string.process_get_token))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            API.token.set(JsonUtil.getToken(response)!!)
                            return null
                        }
                    }) {
                        val body = FormBody.Builder().add("password", pwd).build()
                        Request.Builder()
                                .url(mUrl.format(Type.USERS.name.toLowerCase(), "${API.user.uid}/tokens"))
                                .method(Method.POST.name, body).build()
                    }
                    // Load Category
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(80f, App.i.getString(R.string.process_category))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val index = API.Doc.build(response, Index::class.java)
                            callback(100f, index?.categories ?: emptyList<Category>())
                            return null
                        }
                    }) {
                        Request.Builder().url(App.i.getString(R.string.url_home_head, "/api")).build()
                    }
            call.interruptWhileError = true
            call.defaultErrorHandler = { _, e ->
                callback(-1f, e ?: Exception(App.i.getString(R.string.err_no_err)))
            }
            call.call()
        }

        /**
         * 登出
         */
        fun logout() {
            HttpUtil.clearCookies()
            token.save()

            if (API.isLogin && spGet(Const.SP_CLEAN_TOKENS, true)) Users.getTokens {
                it.forEach {
                    Users.removeToken(it) {}
                }
            }

            user = User.logout
            token.cleanTokenSafe()
        }

        /**
         * 注册
         */
        fun register(d: Boolean, user: String, pwd: String, email: String, callback: (process: Float, result: Any) -> Unit) {
            d.toString()
            val checkResult = checkUser(user, pwd, email)
            if (checkResult != "OK") {
                callback(-1f, checkResult)
                return
            }

            // 注册
            val call = HttpUtil.LinearCall()
                    // Get Csrf
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(0f, App.i.getString(R.string.process_info))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val csrf = JsonUtil.getCsrf(response)
                            if (csrf.isBlank()) throw Exception(App.i.getString(R.string.err_token))
                            return csrf
                        }
                    }) {
                        Request.Builder().url(App.i.getString(R.string.url_login)).build()
                    }
                    // Register
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(20.0f, App.i.getString(R.string.process_reg))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val msg = response?.body()!!.string()
                            if (msg.startsWith("[[error")) {
                                throw Exception(msg.substring(2, msg.length - 2))
                            }
                            return null
                        }
                    }) {
                        val body = FormBody.Builder()
                                .add("email", email)
                                .add("username", user)
                                .add("password", pwd)
                                .add("password-confirm", pwd)
                                .add("remember", "on").build()
                        Request.Builder().url(App.i.getString(R.string.url_register))
                                .post(body).addHeader("x-csrf-token", it as String)
                                .build()
                    }
                    // Back
                    .next(object : HttpUtil.LinearCallback() {
                        override fun beforeResult(call: Call?) {
                            callback(40.0f, App.i.getString(R.string.process_user))
                        }

                        override fun onResult(call: Call?, response: Response?, lastResult: Any?): Any? {
                            val rUser = Doc.build(response, User::class.java)
                            if (rUser is User) {
                                rUser.password = pwd
                                API.user = rUser
                                callback(60f, "OK")
                            }
                            return null
                        }
                    }) {
                        Request.Builder().url(App.i.getString(R.string.url_user, user)).build()
                    }
            call.interruptWhileError = true
            call.defaultErrorHandler = { _, e ->
                callback(-1f, e ?: Exception(App.i.getString(R.string.err_no_err)))
            }
            call.call()
        }

        /**
         * 注册
         */
        fun register(user: String, pwd: String, email: String, callback: (process: Float, result: Any) -> Unit) {
            val check = checkUser(user, pwd, email)
            if (check != Const.RET_OK) {
                callback(-1f, check)
                return
            }

            callback(0f, "Create")
            Users.create(user, pwd, email) {
                if (it.toUpperCase() != Const.RET_OK) {
                    callback(-1f, it)
                } else {
                    login(user, pwd, callback)
                }
            }
        }

        fun resetPassword(email: String, callback: (message: String) -> Unit) {
            // TODO: 重置密码
            callback(email)
        }

        /**
         * 检查用户名、密码、邮箱是否合法
         */
        fun checkUser(username: String = user.username,
                      password: String = "123456789",
                      email: String = "login@miaowo.org"): String {
            return if (email.isBlank() || "@" !in email)
                App.i.getString(R.string.err_email)
            else if (username.isBlank())
                App.i.getString(R.string.err_username)
            else if (password.isBlank() || username == password || password.length < 6)
                App.i.getString(R.string.err_password)
            else Const.RET_OK
        }
    }

    val emptyBody = FormBody.Builder().build()!!

    /**
     * /users
     * Users
     */
    object Users {
        private val api = API.Type.USERS

        /**
         * POST /
         * Creates a new user
         * Requires: username
         * Accepts: password, email
         * Any other data passed in will be saved into the user hash
         */
        fun create(username: String,
                   password: String,
                   email: String,
                   callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("email", email).build()
            useAPI(api, "/", Method.POST, form, false) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:uid
         * Updates a user's profile information
         * Accepts: username, email, fullname, website, location, birthday, signature
         * Also accepts any values exposed via the action:user.updateProfile hook
         * The uid specified in the route path is optional. Without it, the profile of the calling user is edited.
         */
        fun update(name: String = user.username,
                   email: String = user.email,
                   fullName: String = user.fullname,
                   website: String = user.website,
                   location: String = user.location,
                   birthday: String = user.birthday,
                   signature: String = user.signature,
                   callback: (message: String) -> Unit) {
            val bodyUser = FormBody.Builder()
                    .add("username", name)
                    .add("email", email)
                    .add("username", fullName)
                    .add("website", website)
                    .add("location", location)
                    .add("birthday", birthday)
                    .add("signature", signature)
                    .build()
            useAPI(api, user.uid.toString(), Method.PUT, bodyUser) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:uid
         * Deletes a user from NodeBB (Careful: There is no confirmation!)
         * Accepts: No parameters
         * Can be called by either the target uid itself, or an administrative uid.
         */
        fun delete(uid: Int = user.uid,
                   callback: (message: String) -> Unit) {
            if (uid != user.uid || !user.isAdmin) callback("Can be called by either the target uid itself, or an administrative uid")
            API.useAPI(api, uid.toString(), API.Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:uid/password
         * Changes a user's password
         * Requires: uid, new
         * Accepts: current
         * current is required if the calling user is not an administrator
         */
        fun password(uid: Int = user.uid,
                     current: String = user.password,
                     new: String,
                     callback: (result: String) -> Unit) {
            val checkResult = Profile.checkUser(password = new)
            if (checkResult != Const.RET_OK) {
                callback(checkResult)
                return
            }

            val form = FormBody.Builder()
                    .add("uid", uid.toString())
                    .add("new", new)
            if (!user.isAdmin)
                form.add("current", current)
            useAPI(api, "${user.uid}/password", Method.PUT, form.build()) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:uid/follow
         * Follows another user
         * Accepts: No parameters
         */
        fun follow(uid: Int,
                   callback: (result: String) -> Unit) {
            useAPI(api, "$uid/follow", Method.PUT, emptyBody) { _, rspFocus ->
                callback(getResultMessage(rspFocus))
            }
        }

        /**
         * DELETE /:uid/follow
         * Unfollows another user
         * Accepts: No parameters
         */
        fun unFollow(uid: Int,
                     callback: (message: String) -> Unit) {
            useAPI(api, "$uid/follow", Method.DELETE, null) { _, rspCancel ->
                callback(getResultMessage(rspCancel))
            }
        }

        /**
         * POST /:uid/chats
         * Sends a chat message to another user
         * Requires: message
         * Accepts: timestamp, quiet
         * timestamp (unix timestamp in ms) allows messages to be sent from the past (useful when importing chats)
         * quiet if set, will not notify the user that a chat message has been received (also useful during imports)
         */
        fun chat(uid: Int,
                 message: String,
                 roomId: Int? = null,
                 callback: (message: String) -> Unit) {
            val body = FormBody.Builder()
                    .add("message", message)
                    .add("timestamp", System.currentTimeMillis().toString())
            if (roomId != null && roomId > 0) body.add("roomId", roomId.toString())
            useAPI(api, "$uid/chats", Method.POST, body.build()) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:uid/ban
         * Bans a user
         */
        fun ban(uid: Int,
                callback: (message: String) -> Unit) {
            useAPI(api, "$uid/chats", Method.PUT, emptyBody) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:uid/ban
         * Bans a user
         */
        fun unBan(uid: Int,
                  callback: (message: String) -> Unit) {
            useAPI(api, "$uid/chats", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * GET /:uid/tokens
         * Retrieves a list of active tokens for that user
         * Accepts: No parameters
         */
        fun getTokens(callback: (tokens: MutableList<String>) -> Unit) {
            useAPI(api, "${user.uid}/tokens", Method.GET, null) { _, response ->
                val tokens = mutableListOf<String>()
                val json = response?.body()?.string()
                lInfo(json ?: "null")
                val tokenArray = JSONObject(json)
                        .getJSONObject("payload")
                        .getJSONArray("tokens")
                (0 until tokenArray.length())
                        .forEach { tokens.add(tokenArray.getString(it)) }
                callback(tokens)
            }
        }

        /**
         * POST /:uid/tokens
         * Creates a new user token for the passed in uid
         * Accepts: No parameters normally, will accept password in lieu of Bearer token
         * Can be called with an active token for that user
         * This is the only route that will allow you to pass in password in the request body.
         * Generate a new token and then use the token in subsequent calls.
         */
        fun newToken(pwd: String = user.password,
                     callback: (message: Exception?) -> Unit) {
            val body = FormBody.Builder()
            if (!isLogin || token.token.isBlank())
                body.add("password", pwd)
            useAPI(api, "${user.uid}/tokens", Method.POST, body.build(), false) { _, response ->
                try {
                    token.set(JsonUtil.getToken(response)!!)
                    callback(null)
                } catch (e: Exception) {
                    callback(e)
                }
            }
        }

        /**
         * DELETE /:uid/tokens/:token
         * Revokes an active user token
         * Accepts: No parameters
         */
        fun removeToken(token: String,
                        save: Boolean = false,
                        callback: (message: String) -> Unit) {
            if (save) API.token.save()
            val savedToken = API.token.savedToken()
            useAPI(api, "${savedToken.uid}/tokens/$token", Method.DELETE, null, useSavedToken = true) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * /groups
     * Groups
     */
    object Groups {
        private val api = API.Type.GROUPS

        /**
         * POST /
         * Creates a new group
         * Requires: name
         * Accepts: description, hidden, private, ownerUid
         */
        fun create(name: String,
                   description: String,
                   hidden: Boolean,
                   private: Boolean,
                   ownerUid: Int = user.uid,
                   callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("name", name)
                    .add("description", description)
                    .add("hidden", hidden.toString())
                    .add("private", private.toString())
                    .add("ownerUid", ownerUid.toString()).build()
            useAPI(api, "", Method.POST, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:slug
         * Deletes a group (Careful: There is no confirmation!)
         * Accepts: No parameters
         */
        fun delete(slug: String,
                   callback: (message: String) -> Unit) {
            useAPI(api, slug, Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:slug/membership
         * Joins a group (or requests membership if it is a private group)
         * Accepts: No parameters
         */
        fun join(slug: String,
                 callback: (message: String) -> Unit) {
            useAPI(api, "$slug/membership", Method.PUT, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:slug/membership/:uid
         * Adds a user to a group (The calling user has to be an administrator)
         * Accepts: No parameters
         */
        fun add(slug: String,
                uid: Int,
                callback: (message: String) -> Unit) {
            if (!user.isAdmin)
                throw Exception("The calling user has to be an administrator")
            useAPI(api, "$slug/membership/$uid", Method.PUT, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:slug/membership
         * Leaves a group
         * Accepts: No parameters
         */
        fun leave(slug: String,
                  callback: (message: String) -> Unit) {
            useAPI(api, "$slug/membership", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:slug/membership/:uid
         * Removes a user from a group (The calling user has to be an administrator)
         * Accepts: No parameters
         */
        fun remove(slug: String,
                   uid: Int,
                   callback: (message: String) -> Unit) {
            if (!user.isAdmin)
                throw Exception("The calling user has to be an administrator")
            useAPI(api, "$slug/membership/$uid", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * /categories
     * Categories
     */
    object Categories {
        private val api = API.Type.CATEGORIES

        /**
         * POST /
         * Creates a new category
         * Requires: name
         * Accepts: description, bgColor, color, parentCid, class
         */
        fun create(name: String,
                   description: String,
                   bgColor: Int,
                   color: Int,
                   parentCid: Int,
                   clazz: String,
                   callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("name", name)
                    .add("description", description)
                    .add("bgColor", bgColor.toString())
                    .add("color", color.toString())
                    .add("parentCid", parentCid.toString())
                    .add("clazz", clazz).build()
            useAPI(api, "", Method.POST, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:cid
         * Updates a category's data
         * Accepts: name, description, bgColor, color, parentCid
         */
        fun update(cid: Int,
                   name: String,
                   description: String,
                   bgColor: Int,
                   color: Int,
                   parentCid: Int,
                   callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("name", name)
                    .add("description", description)
                    .add("bgColor", bgColor.toString())
                    .add("color", color.toString())
                    .add("parentCid", parentCid.toString()).build()
            useAPI(api, cid.toString(), Method.PUT, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:cid
         * Purges a category, including all topics and posts inside of it (Careful: There is no confirmation!)
         * Accepts: No parameters
         */
        fun delete(cid: Int,
                   callback: (message: String) -> Unit) {
            useAPI(api, cid.toString(), Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:cid/state
         * Enables a category
         * Accepts: No parameters
         */
        fun enable(cid: Int,
                   callback: (message: String) -> Unit) {
            useAPI(api, "$cid/state", Method.PUT, emptyBody) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:cid/state
         * Disables a category
         * Accepts: No parameters
         */
        fun disable(cid: Int,
                    callback: (message: String) -> Unit) {
            useAPI(api, "$cid/state", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:cid/privileges
         * Adds group privileges to a category
         * Requires: privileges (array), groups (array)
         */
        fun addPrivileges(cid: Int,
                          privileges: List<String>,
                          groups: List<String>,
                          callback: (message: String) -> Unit) {
            val p = listToArray(privileges)
            val g = listToArray(groups)
            val form = FormBody.Builder()
                    .add("privileges", p)
                    .add("groups", g).build()
            useAPI(api, "$cid/privileges", Method.PUT, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:cid/privileges
         * Deletes group privileges from a category
         * Requires: privileges (array), groups (array)
         */
        fun deletePrivileges(cid: Int,
                             privileges: List<String>,
                             groups: List<String>,
                             callback: (message: String) -> Unit) {
            val p = listToArray(privileges)
            val g = listToArray(groups)
            val form = FormBody.Builder()
                    .add("privileges", p)
                    .add("groups", g).build()
            useAPI(api, "$cid/privileges", Method.DELETE, form) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * /topics
     * Topics
     */
    object Topics {
        private val api = API.Type.TOPICS

        /**
         * POST /
         * Creates a new topic
         * Requires: cid, title, content
         * Accepts: tags (array)
         */
        fun create(cid: Int,
                   title: String,
                   content: String,
                   tags: List<String>,
                   callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("cid", cid.toString())
                    .add("title", title)
                    .add("content", content)
                    .add("tags", listToArray(tags)).build()
            useAPI(api, "", Method.POST, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * POST /:tid
         * Posts a new reply to the topic
         * Requires: content
         * Accepts: toPid
         */
        fun reply(tid: Int,
                  content: String,
                  toPid: Int? = null,
                  callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("tid", tid.toString())
                    .add("content", content)
            if (toPid != null && toPid >= 0)
                form.add("toPid", toPid.toString())
            useAPI(api, tid.toString(), Method.POST, form.build()) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:tid
         * Updates a post in a topic
         * Requires: pid, content
         * Accepts: handle, title, topic_thumb, tags
         */
        fun update(tid: Int,
                   pid: Int,
                   content: String,
                   handle: String? = null,
                   title: String? = null,
                   topic_thumb: String? = null,
                   tags: List<String>? = null,
                   callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("pid", pid.toString())
                    .add("content", content)
            if (handle != null) form.add("handler", handle)
            if (title != null) form.add("title", title)
            if (topic_thumb != null) form.add("topic_thumb", topic_thumb)
            if (tags != null) form.add("tags", listToArray(tags))
            useAPI(api, tid.toString(), Method.PUT, form.build()) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:tid
         * Purges a topic, including all posts inside (Careful: There is no confirmation!)
         * Accepts: No parameters
         */
        fun delete(tid: Int,
                   callback: (message: String) -> Unit) {
            useAPI(api, tid.toString(), Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:tid/state
         * Deletes a topic (that is, a soft-delete) (Careful: There is no confirmation!)
         * Accepts: No parameters
         */
        fun softDelete(tid: Int,
                       callback: (message: String) -> Unit) {
            useAPI(api, "$tid/state", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:tid/state
         * Restores a topic (Careful: There is no confirmation!)
         * Accepts: No parameters
         */
        fun restore(tid: Int,
                    callback: (message: String) -> Unit) {
            useAPI(api, "$tid/state", Method.PUT, emptyBody) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:tid/follow
         * Subscribes a user to a topic
         * Accepts: No parameters
         */
        fun follow(tid: Int,
                   callback: (message: String) -> Unit) {
            useAPI(api, "$tid/follow", Method.PUT, emptyBody) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:tid/follow
         * Unsubscribes a user to a topic
         * Accepts: No parameters
         */
        fun unFollow(tid: Int,
                     callback: (message: String) -> Unit) {
            useAPI(api, "$tid/follow", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:tid/tags
         * Creates or update tags in a topic
         * Requires: tags
         * This method does not append tags, it replaces the tag set associated with the topic
         */
        fun updateTag(tid: Int,
                      tags: List<String>,
                      callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("tags", listToArray(tags)).build()
            useAPI(api, "$tid/tags", Method.PUT, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:tid/tags
         * Accepts: No parameters
         * Clears the tag set associates with a topic
         */
        fun cleanTag(tid: Int,
                     callback: (message: String) -> Unit) {
            useAPI(api, "$tid/tags", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * /posts
     * Posts
     */
    object Posts {
        private val api = API.Type.POSTS

        /**
         * PUT /:pid
         * Edits a post by post ID
         * Requires: content
         * Accepts: title, topic_thumb, tags
         */
        fun edit(pid: Int,
                 content: String,
                 title: String? = null,
                 topic_thumb: String? = null,
                 tags: List<String>? = null,
                 callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("content", content)
            if (title != null) form.add("title", title)
            if (topic_thumb != null) form.add("topic_thumb", topic_thumb)
            if (tags != null) form.add("tags", listToArray(tags))

            useAPI(api, pid.toString(), Method.PUT, form.build()) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:pid
         * Purges a post, thereby removing it from the database completely (Careful: There is no confirmation!)
         * Accepts: No parameters
         */
        fun delete(pid: Int,
                   callback: (message: String) -> Unit) {
            useAPI(api, pid.toString(), Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:pid/state
         * Deletes a post (that is, a soft-delete)
         * Accepts: No parameters
         */
        fun softDelete(pid: Int,
                       callback: (message: String) -> Unit) {
            useAPI(api, "$pid/state", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * PUT /:pid/state
         * Restores a post
         * Accepts: No parameters
         */
        fun restore(pid: Int,
                    callback: (message: String) -> Unit) {
            useAPI(api, "$pid/state", Method.PUT, emptyBody) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * POST /:pid/vote
         * Votes for a post
         * Requires: delta
         * delta must be a number. If delta > 0, it's considered an upvote;
         *                         if delta < 0, it's considered a downvote;
         *                         otherwise, it's an unvote.
         */
        fun vote(pid: Int,
                 delta: Float,
                 callback: (message: String) -> Unit) {
            val form = FormBody.Builder()
                    .add("delta", delta.toString()).build()
            useAPI(api, "$pid/vote", Method.POST, form) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * DELETE /:pid/vote
         * Unvotes a post
         * Accepts: No parameters
         */
        fun unVote(pid: Int,
                   callback: (message: String) -> Unit) {
            useAPI(api, "$pid/vote", Method.DELETE, null) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * /util
     * Util
     */
    object Util {
        private val api = API.Type.UTIL

        /**
         * POST /upload
         * Uploads a File
         * Accepts: A multipart files array files[]
         */
        fun uploadFile(files: List<Uri>, callback: (message: String) -> Unit) {
            // TODO: updateFile
            val form = FormBody.Builder()
                    .add("files", listToArray(files.map { it.path })).build()
            useAPI(api, "upload", Method.POST, form) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * 直接解析 Json
     */
    object Doc {

        /**
         * 版本信息
         */
        fun version(callback: (ver: VersionMessage?) -> Unit) {
            val url = App.i.getString(R.string.url_update)
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, VersionMessage::class.java))
            }
        }

        /**
         * 用户信息
         */
        fun user(name: String, callback: (user: User?) -> Unit) {
            val url = App.i.getString(R.string.url_user, name)
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, User::class.java))
            }
        }

        fun user(uid: Int, callback: (user: User?) -> Unit) {
            var rUser: User? = null
            var findOver = false
            var qs: String? = null
            while (rUser == null || findOver) {
                users(qs) {
                    rUser = it?.users?.firstOrNull { it.uid == uid }
                    findOver = it?.pagination?.atLast ?: true
                    qs = it?.pagination?.next?.qs
                }
            }
            if (rUser != null) user(rUser!!.username, callback)
            else user(API.user.username, callback)
        }

        fun users(qs: String? = null, callback: (users: BUsers?) -> Unit) {
            val url = App.i.getString(R.string.url_users)
            Request.Builder().url(urlBuilder(url, qs)).build().call { _, response ->
                callback(build(response, BUsers::class.java))
            }
        }

        /**
         * 主页
         */
        fun index(callback: (home: Index?) -> Unit) {
            val url = App.i.getString(R.string.url_home_head, "/api")
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, Index::class.java))
            }
        }

        /**
         * 未读列表
         */
        fun unread(qs: String? = null, callback: (category: Category?) -> Unit) {
            val url = App.i.getString(R.string.url_unread)
            Request.Builder().url(urlBuilder(url, qs)).build().call { _, response ->
                Miao.i.runOnUiThread {
                    callback(build(response, Category::class.java))
                }
            }
        }

        /**
         * 通知列表
         */
        fun notification(callback: (notifications: Notifications?) -> Unit) {
            val url = App.i.getString(R.string.url_notification)
            Request.Builder().url(url).build().call { _, response ->
                Miao.i.runOnUiThread {
                    callback(build(response, Notifications::class.java))
                }
            }
        }

        /**
         * 主题列表
         */
        fun category(cid: Int, qs: String? = null, callback: (category: Category?) -> Unit) {
            val url = App.i.getString(R.string.url_category, cid)
            Request.Builder().url(urlBuilder(url, qs)).build().call { _, response ->
                callback(build(response, Category::class.java))
            }
        }

        /**
         * 话题(1 具体 2 用户)
         */
        fun topic(tid: Int, callback: (topic: Topic?) -> Unit) {
            val url = App.i.getString(R.string.url_topic, tid)
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, Topic::class.java))
            }
        }

        fun topic(name: String = user.username, callback: (topic: UserTopics?) -> Unit) {
            val url = App.i.getString(R.string.url_user_topic, name)
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, UserTopics::class.java))
            }
        }

        /**
         * 搜索
         */
        fun search(key: String, searchType: SearchType, callback: (message: SearchResult) -> Unit) {
            val url = when (searchType) {
                SearchType.USER -> App.i.getString(R.string.url_search, key, "&in=USERS")
                SearchType.TOPIC -> App.i.getString(R.string.url_search, key, "&in=titlesposts&showAs=posts")
            }
            Request.Builder().url(url).build().call { _, response ->
                when (searchType) {
                    SearchType.USER ->
                        callback(SearchResult(searchType, userResult = build(response, User::class.java)))
                    SearchType.TOPIC ->
                        callback(SearchResult(searchType, topicResult = build(response, Topic::class.java)))
                }
            }
        }

        /**
         * 聊天列表
         */
        fun chatRoom(callback: (rooms: ChatRooms?) -> Unit) {
            val url = App.i.getString(R.string.url_chat_room, user.username.toLowerCase())
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, ChatRooms::class.java))
            }
        }

        fun chatMessage(roomId: Int, callback: (room: ChatRoom?) -> Unit) {
            val url = App.i.getString(R.string.url_chat_message, user.username.toLowerCase(), roomId)
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, ChatRoom::class.java))
            }
        }

        /**
         * 用户帖子
         */
        fun post(name: String = user.username, callback: (info: UserPosts?) -> Unit) {
            val url = App.i.getString(R.string.url_user_post, name)
            Request.Builder().url(url).build().call { _, response ->
                callback(build(response, UserPosts::class.java))
            }
        }

        /**
         * 用户关注
         */
//        fun following(name: String = user.username, callback: (following: UserFollowList?) -> Unit) {
//            val url = App.i.getString(R.string.url_user_follow, name)
//            val request = Request.Builder().url(url).build()
//            HttpUtil.post(request) { _, response ->
//                callback(build(response, UserFollowList::class.java))
//            }
//        }
//        fun follower(name: String = user.username, callback: (following: UserFollowList?) -> Unit) {
//            val url = App.i.getString(R.string.url_user_follower, name)
//            val request = Request.Builder().url(url).build()
//            HttpUtil.post(request) { _, response ->
//                callback(build(response, UserFollowList::class.java))
//            }
//        }

        /**
         * 直接解析 JSON
         */
        fun <T> build(response: Response?, typeClass: Class<T>, onErr: (err: Exception) -> T? = { it.printStackTrace();null }) =
                try {
                    gson.fromJson(response?.body()?.string() ?: "", typeClass)
                } catch (e: Exception) {
                    onErr(e)
                }

        private fun urlBuilder(url: String, qs: String?): String {
            val rQs = if (qs.isNullOrBlank()) "?$qs" else ""
            return "$url$rQs"
        }
    }

    class Token(var uid: Int, var token: String) {
        // 备份区
        private var savedToken: String? = null
        private var savedUid: Int? = null

        fun save(): Token {
            savedToken = token
            savedUid = uid
            return savedToken()
        }

        fun savedToken() = Token(savedUid ?: user.uid, savedToken ?: "")

        fun set(token: String, uid: Int = API.user.uid) {
            this.uid = uid
            this.token = token
        }

        fun cleanToken() {
            token = ""
            uid = -1
        }

        fun cleanTokenSafe() {
            if (!isLogin) cleanToken()
        }
    }
}
