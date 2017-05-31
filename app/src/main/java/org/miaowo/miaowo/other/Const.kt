package org.miaowo.miaowo.other

import org.miaowo.miaowo.base.BaseActivity

/**
 * 各种常量
 * Created by luqin on 17-5-27.
 */
object Const {
    val DEF = "default"
    val TAG = "TYPE"
    val TITLE = "TITLE"
    val CONTENT = "CONTENT"
    val URL = "url"
    val SLUG = "slug"
    val NAME = "name"
    // sp
    val SP_DEFAULT = "miaowo"
    val SP_FIRST_BOOT = "first_boot"
    val SP_SAVE = "save_password"
    val SP_USER = "username"
    val SP_PWD = "password"
    val SP_USE_TAB = "square_tab_mode"
    val SP_CLEAN_TOKENS = "auto_clean_token"
    // id
    val MY = 1
    val TO = 2
    val NO_ID = -1
    val ID = "id"
    // json
    val JSON_PAIR = arrayOf(Pair("JSON.parse('", "\')"),
            Pair("<script id=\"ajaxify-data\" type=\"application/json\">", "</script>"))
    // fun-get res
    fun <T> tryGet(onErr: (err: Exception) -> T? = { defErr<T>(it) }, onGet: () -> T?): T? = try {
        onGet()
    } catch (err: Exception) { onErr(err) }
    fun <T> defErr(err: Exception): T? {
        BaseActivity.get?.handleError(err)
        return null
    }
}
