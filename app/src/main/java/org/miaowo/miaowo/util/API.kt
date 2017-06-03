package org.miaowo.miaowo.util

import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Miao
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.bean.config.VersionMessage
import org.miaowo.miaowo.bean.data.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.ChatButton
import java.io.IOException

/**
 * 封装 API
 */
object API {
    var loginUser: User? = null
    var token: String = ""

    private val mUrl = App.i.getString(R.string.url_api)

    private fun useAPI(api: Type, sub: String, method: Method, useToken: Boolean, body: FormBody?, callback: (call: Call, response: Response) -> Unit) {
        val request = Request.Builder()
                .url(mUrl.format(api.name, sub))
                .method(method.name, body)
        if (useToken) request.addHeader("Authorization", "Bearer $token")
        HttpUtil.post(request.build()) { call, response -> callback(call, response) }
    }

    private enum class Method {
        POST, PUT, GET, DELETE
    }

    private enum class Type {
        users, categories, groups, topics, posts, util;
    }

    /**
     * 用于登录
     */
    object Login {

        /**
         * 注销当前 Token
         */
        fun clearToken() {
            try {
                val request = Request.Builder()
                        .url(App.i.getString(R.string.url_api, Type.users.name, "${loginUser?.uid}/tokens/$token"))
                        .delete()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                HttpUtil.post(request) { _, _ -> }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                token = ""
            }
        }

        /**
         * 登录
         */
        fun login(user: String, pwd: String) {
            if (checkUser(user, pwd)) {
                BaseActivity.get?.setProcess(0, App.i.getString(R.string.process_info))
                val request = Request.Builder().url(App.i.getString(R.string.url_login)).build()
                HttpUtil.post(request) { _, rspCsrf ->
                    try {
                        val token = JsonUtil.getCsrf(rspCsrf)
                        val body = FormBody.Builder()
                                .add("username", user)
                                .add("password", pwd)
                                .add("remember", "on").build()
                        val login = Request.Builder().url(App.i.getString(R.string.url_login))
                                .post(body)
                                .addHeader("x-csrf-token", token)
                                .build()
                        BaseActivity.get?.setProcess(25, App.i.getString(R.string.process_login))
                        HttpUtil.post(login) { _, response1 ->
                            val msg = response1.body()?.string() ?: "[[error empty]]"
                            if (msg.startsWith("[[error")) {
                                BaseActivity.get?.runOnUiThreadIgnoreError {
                                    Miao.fg_miao.prepareLogin()
                                    BaseActivity.get?.processError(Exception(msg.substring(2, msg.length - 2)))
                                }
                            } else {
                                BaseActivity.get?.setProcess(50, App.i.getString(R.string.process_user))
                                val userR = Request.Builder().url(App.i.getString(R.string.url_user, user)).build()
                                HttpUtil.post(userR) { _, rspReg ->
                                    val l = Doc.build(rspReg, User::class.java)
                                    l?.password = pwd
                                    loginResult(l)
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        throw IOException(App.i.getString(R.string.err_token))
                    }
                }
            }
        }

        /**
         * 登出
         */
        fun logout() {
            HttpUtil.clearCookies()
            BaseActivity.get?.runOnUiThreadIgnoreError { ChatButton.hide() }
            BaseActivity.get?.runOnUiThreadIgnoreError { Miao.fg_miao.prepareLogin() }
            Thread(Runnable { clearToken() })
            loginUser = null
        }

        /**
         * 注册
         */
        fun register(user: String, pwd: String, email: String) {
            if (checkUser(user, pwd, email)) {
                BaseActivity.get?.setProcess(0, App.i.getString(R.string.process_info))
                val request = Request.Builder().url(App.i.getString(R.string.url_login)).build()
                HttpUtil.post(request) { _, response ->
                    try {
                        val token = JsonUtil.getCsrf(response)
                        val body = FormBody.Builder()
                                .add("username", user)
                                .add("password", pwd)
                                .add("email", email)
                                .add("remember", "on").build()
                        val reg = Request.Builder().url(App.i.getString(R.string.url_register))
                                .post(body).addHeader("x-csrf-token", token)
                                .build()
                        BaseActivity.get?.setProcess(25, App.i.getString(R.string.process_reg))
                        HttpUtil.post(reg) { _, response1 ->
                            val msg = response1.body()!!.string()
                            if (msg.startsWith("[[error")) {
                                BaseActivity.get?.runOnUiThreadIgnoreError {
                                    Miao.fg_miao.prepareLogin()
                                    BaseActivity.get?.processError(Exception(msg.substring(2, msg.length - 2)))
                                }
                            } else {
                                BaseActivity.get?.setProcess(50, App.i.getString(R.string.process_user))
                                Doc.user(user) {
                                    it?.password = pwd
                                    loginResult(it)
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        throw IOException(App.i.getString(R.string.err_token))
                    }
                }
            }
        }

        private fun loginResult(user: User?) {
            if (user == null || user.uid == 0) {
                BaseActivity.get?.processError(Exception(App.i.getString(R.string.process_err)))
                return
            }
            loginUser = user
            BaseActivity.get?.setProcess(75, App.i.getString(R.string.process_upload) ?: "")
            Use.newToken(user.password) { token, message ->
                if (token.isEmpty()) {
                    BaseActivity.get?.processError(message!!)
                } else {
                    API.token = token
                    BaseActivity.get?.setProcess(100, "欢迎回来, ${user.username}")
                    if (BaseActivity.get is Miao) {
                        BaseActivity.get?.runOnUiThreadIgnoreError { Miao.fg_miao.loginSucceed() }
                    }
                }
            }
        }

        private fun checkUser(username: String = loginUser!!.username, password: String, email: String = "login@miaowo.org"): Boolean {
            if (email.isEmpty() || !email.contains("@")) {
                BaseActivity.get?.handleError(R.string.err_email)
                Miao.fg_miao.prepareLogin()
                return false
            }
            if (username.isEmpty()) {
                BaseActivity.get?.handleError(R.string.err_username)
                Miao.fg_miao.prepareLogin()
                return false
            }
            if (password.isEmpty() || username == password || password.length < 6) {
                BaseActivity.get?.handleError(R.string.err_password)
                Miao.fg_miao.prepareLogin()
                return false
            }
            return true
        }
    }

    /**
     * 使用 WriteAPI
     */
    object Use {
        private fun getResultMessage(response: Response): String {
            val msg = JSONObject(response.body()?.string() ?: "")
            if ("ok" == msg["code"]) {
                return "ok"
            } else {
                return try {
                    msg.getString("message")
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }
        }

        /**
         * 关注
         */
        fun focus(uid: Int, callback: (message: String) -> Unit) {
            API.useAPI(API.Type.users, "$uid/follow", API.Method.POST, true, FormBody.Builder().build()) { _, rspFocus ->
                callback(getResultMessage(rspFocus))
            }
        }

        /**
         * 取消关注
         */
        fun unfocus(uid: Int, callback: (message: String) -> Unit) {
            API.useAPI(API.Type.users, "$uid/follow", API.Method.DELETE, true, null) { _, rspCancel ->
                callback(getResultMessage(rspCancel))
            }
        }

        /**
         * 发送回复
         */
        fun sendTopic(tid: Int, content: String, callback: (message: String) -> Unit) {
            if (tid != -1) {
                val body = FormBody.Builder().add("content", content).build()
                API.useAPI(API.Type.topics, tid.toString(), API.Method.POST, true, body) { _, response ->
                    callback(getResultMessage(response))
                }
            }
        }

        /**
         * 发送主题
         */
        fun sendTitle(cid: Int, title: String, content: String, callback: (message: String) -> Unit) {
            val body = FormBody.Builder()
                    .add("cid", cid.toString())
                    .add("title", title)
                    .add("content", content)
                    .build()
            API.useAPI(API.Type.topics, "", API.Method.POST, true, body) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * 获取当前用户可用 Token
         */
        fun getTokens(callback: (tokens: MutableList<String>) -> Unit) {
            API.useAPI(API.Type.users, "${API.loginUser!!.uid}/tokens", API.Method.GET, true, null) { _, response ->
                val tokens = mutableListOf<String>()
                try {
                    val tokenArray = JSONObject(response.body()?.string())
                            .getJSONObject("payload").getJSONArray("tokens")
                    (0..tokenArray.length() - 1).forEach { tokens.add(tokenArray.getString(it)) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                callback(tokens)
            }
        }

        /**
         * 为当前用户申请一个新的 Token
         */
        fun newToken(pwd: String, callback: (token: String, message: Exception?) -> Unit) {
            val body = FormBody.Builder().add("password", pwd).build()
            useAPI(Type.users, "${loginUser!!.uid}/tokens", Method.POST, false, body) { _, response ->
                try {
                    callback(JsonUtil.getToken(response)!!, null)
                } catch (e: JSONException) {
                    callback("", e)
                }
            }
        }

        /**
         * 删除一个可用 Token
         */
        fun removeToken(token: String, callback: (message: String) -> Unit = {}) {
            API.useAPI(API.Type.users, "${API.loginUser!!.uid}/tokens/$token", API.Method.DELETE, true, null) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * 发送聊天信息
         */
        fun sendChat(toUid: Int, roomId: Int, message: String, callback: (message: String) -> Unit) {
            val body = FormBody.Builder()
                    .add("message", message)
                    .add("timestamp", System.currentTimeMillis().toString())
                    .add("roomId", roomId.toString())
                    .build()
            API.useAPI(API.Type.users, "$toUid/chats", API.Method.POST, true, body) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * 更改密码
         */
        fun changePwd(oriPwd: String, newPwd: String, callback: (message: String) -> Unit) {
            val uid = loginUser!!.uid
            val bodyPwd = FormBody.Builder()
                    .add("uid", uid.toString())
                    .add("new", newPwd)
                    .add("current", oriPwd)
                    .build()
            API.useAPI(API.Type.users, uid.toString() + "/password", API.Method.POST, true, bodyPwd) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * 更改用户资料
         */
        fun updateUser(name: String = loginUser!!.username, email: String, fullName: String, website: String, location: String, birthday: String, signature: String, callback: (message: String) -> Unit) {
            val bodyUser = FormBody.Builder()
                    .add("username", name)
                    .add("email", email)
                    .add("username", fullName)
                    .add("website", website)
                    .add("location", location)
                    .add("birthday", birthday)
                    .add("signature", signature)
                    .build()
            API.useAPI(API.Type.users, loginUser!!.uid.toString(), API.Method.PUT, true, bodyUser) { _, response ->
                callback(getResultMessage(response))
            }
        }

        /**
         * 注销用户
         */
        fun removeUser(uid: Int = loginUser!!.uid, callback: (message: String) -> Unit) {
            API.useAPI(API.Type.users, uid.toString(), API.Method.DELETE, true, null) { _, response ->
                callback(getResultMessage(response))
            }
        }
    }

    /**
     * 直接解析 Json
     */
    object Doc {
        private val gson = GsonBuilder().serializeNulls().create()

        /**
         * 版本信息
         */
        fun version(callback: (ver: VersionMessage?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_update)).build()
            HttpUtil.post(request, { _, _ -> }) { _, response ->
                callback(build(response, VersionMessage::class.java))
            }
        }

        /**
         * 用户信息
         */
        fun user(name: String, callback: (user: User?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_user, name)).build()
            HttpUtil.post(request) { _, rspUser ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(rspUser, User::class.java))
                }
            }
        }

        fun user(callback: (users: UserList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_users)).build()
            HttpUtil.post(request) { _, rspUser ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(rspUser, UserList::class.java))
                }
            }
        }

        /**
         * 主页
         */
        fun home(callback: (home: Home?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_home, "/api/")).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, Home::class.java))
                }
            }
        }

        /**
         * 未读列表
         */
        fun unread(callback: (category: TitleList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_unread)).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, TitleList::class.java))
                }
            }
        }

        /**
         * 主题列表
         */
        fun category(slug: String, page: Int, callback: (category: TitleList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_category, slug, page)).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, TitleList::class.java))
                }
            }
        }

        /**
         * 话题(1 列表 2 具体 3 用户)
         */
        fun topic(onErr: (call: Call, e: IOException) -> Unit, callback: (topic: TopicList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_tags, "")).build()
            HttpUtil.post(request, onErr) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, TopicList::class.java))
                }
            }
        }

        fun topic(name: String = loginUser!!.username, onErr: (call: Call, e: IOException) -> Unit, callback: (topic: TitleList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_tags, name)).build()
            HttpUtil.post(request, onErr) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, TitleList::class.java))
                }
            }
        }

        fun topic(name: String = loginUser!!.username, callback: (topic: UserTopicList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_user_topic, name)).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, UserTopicList::class.java))
                }
            }
        }

        /**
         * 搜索用户
         */
        fun searchUser(key: String, callback: (ret: UserSearch?) -> Unit) {
            val url = App.i.getString(R.string.url_search, key, "&in=users")
            val request = Request.Builder().url(url).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, UserSearch::class.java))
                }
            }
        }

        /**
         * 搜索主题
         */
        fun searchTopic(key: String, callback: (ret: QuestionSearch?) -> Unit) {
            val url = App.i.getString(R.string.url_search, key, "&in=titlesposts&showAs=posts")
            val request = Request.Builder().url(url).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, QuestionSearch::class.java))
                }
            }
        }

        /**
         * 主题详情
         */
        fun question(slug: String, callback: (message: Question?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_topic, slug)).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, Question::class.java))
                }
            }
        }

        /**
         * 聊天列表
         */
        fun chatRoom(callback: (rooms: ChatRoomList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_chat_room, API.loginUser!!.username.toLowerCase())).build()
            HttpUtil.post(request) { _, response ->
                callback(build(response, ChatRoomList::class.java))
            }
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, ChatRoomList::class.java))
                }
            }
        }

        fun chatMessage(roomId: Int, callback: (room: ChatMessageList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_chat_message,
                    API.loginUser!!.username.toLowerCase(), roomId)).build()
            HttpUtil.post(request) { _, response ->
                BaseActivity.get?.runOnUiThreadIgnoreError {
                    callback(build(response, ChatMessageList::class.java))
                }
            }
        }

        /**
         * 用户登录信息
         */
        fun info(name: String = loginUser!!.username, callback: (info: UserInfoList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_user_info, name)).build()
            HttpUtil.post(request) { _, response ->
                callback(build(response, UserInfoList::class.java))
            }
        }

        /**
         * 用户帖子
         */
        fun post(name: String = loginUser!!.username, callback: (info: UserPostList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_user_post, name)).build()
            HttpUtil.post(request) { _, response ->
                callback(build(response, UserPostList::class.java))
            }
        }

        /**
         * 用户关注
         */
        fun following(name: String = loginUser!!.username, callback: (following: UserFollowList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_user_follow, name)).build()
            HttpUtil.post(request) { _, response ->
                callback(build(response, UserFollowList::class.java))
            }
        }

        fun follower(name: String = loginUser!!.username, callback: (following: UserFollowList?) -> Unit) {
            val request = Request.Builder().url(App.i.getString(R.string.url_user_follower, name)).build()
            HttpUtil.post(request) { _, response ->
                callback(build(response, UserFollowList::class.java))
            }
        }

        /**
         * 直接解析 JSON
         */
        fun <T> build(response: Response, typeClass: Class<T>, onErr: (err: Exception) -> T? = { Const.defErr<T>(it) }) =
                Const.tryGet(onErr) { gson.fromJson(response.body()?.string() ?: "", typeClass) }
    }
}
