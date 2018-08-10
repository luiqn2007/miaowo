package org.miaowo.miaowo.other

import org.miaowo.miaowo.R

/**
 * 各种常量
 * Created by luqin on 17-5-27.
 */
object Const {
    const val TAG = "tag"
    const val NAME = "name"
    const val URL = "url"
    const val USER = "user"
    const val ID = "id"
    const val ID2 = "id2"
    const val PWD = "pwd"
    const val CHECK = "checkPwd"
    const val EMAIL = "email"
    const val SAVE = "isSave"
    const val REPLY = "reply"
    const val TYPE = "type"
    const val CALL = "call"
    // sp
    const val SP_FIRST_BOOT = "first_boot"
    const val SP_SAVE = "save_password"
    const val SP_USER = "username"
    const val SP_PWD = "password"
    const val SP_HIDE_BODY = "square_tab_mode"
    const val SP_CLEAN_TOKENS = "auto_clean_token"
    const val SP_SHOW_TYPE = "auto_clean_token"
    const val SP_PW_INPUT_X = "last_input_popup_window_x"
    const val SP_PW_INPUT_Y = "last_input_popup_window_y"
    // category
    const val CBODY_CONTENT = 0
    const val CBODY_FIRST = 1
    const val CBODY_LAST = 2
    // results
    const val RET_OK = "OK"
    // id
    const val MY = 1
    const val TO = 2
    // Markdown
    const val MD_BOLD = 0
    const val MD_ITALIC = 1
    const val MD_LIST = 2
    const val MD_LIST_OL = 3
    const val MD_ST = 4
    const val MD_UL = 5
    const val MD_QUOTE = 6
    const val MD_CODE = 7
    const val MD_FULL = 8
    const val MD_IMAGE = 9
    const val MD_EMOJI = 10
    const val MD_LINK = 11
    // other
    const val URL_BASE = "https://www.miaowo.org"
    const val TOKEN_MASTER = "62872274-e7a6-44fe-a9c6-621a1ae2b62c"

    val COLORS = arrayOf(
            R.color.md_amber_700,
            R.color.md_blue_700,
            R.color.md_brown_700,
            R.color.md_cyan_700,
            R.color.md_green_700,
            R.color.md_grey_700,
            R.color.md_indigo_700,
            R.color.md_lime_700,
            R.color.md_orange_700,
            R.color.md_pink_700,
            R.color.md_purple_700,
            R.color.md_red_700,
            R.color.md_teal_700,
            R.color.md_yellow_700
    )

    val DOWNLOAD_FILE_TYPES = arrayOf(".zip", ".rar", ".pdf", ".doc", ".xls", ".mp3", ".wma"
            , ".ogg", ".m4a", ".wav", ".avi", ".mov", ".mp4", ".mpg", ".3gp", ".bin", ".apk"
            , ".gif", ".png", ".jpg", ".jpeg")
}
