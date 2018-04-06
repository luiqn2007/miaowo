package org.miaowo.miaowo.other

/**
 * 各种常量
 * Created by luqin on 17-5-27.
 */
object Const {
    const val TAGS = "tags"
    const val TAG = "tag"
    const val NAME = "name"
    const val USER = "user"
    const val ID = "id"
    const val PWD = "pwd"
    const val CHECK = "checkPwd"
    const val EMAIL = "email"
    const val SAVE = "isSave"
    const val REPLY = "reply"
    const val TYPE = "type"
    const val CALL = "call"
    // IProcessable
    const val LOGIN = "login"
    // sp
    const val SP_DEFAULT = "miaowo"
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
    // fragment
    const val FG_ADD_TO_BACK_STACK = "add_to_back_stack"
    const val FG_POP_ALL = "pop_other_fragment_from_back_stack"
    // result
    const val RET_FILE = 20
    const val RET_OK = "OK"
    const val RET_ERR = "ERROR"
    // id
    const val MY = 1
    const val TO = 2
    const val NO_ID = -1
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

    val DOWNLOAD_FILE_TYPES = arrayOf(".zip", ".rar", ".pdf", ".doc", ".xls", ".mp3", ".wma"
            , ".ogg", ".m4a", ".wav", ".avi", ".mov", ".mp4", ".mpg", ".3gp", ".bin", ".apk"
            , ".gif", ".png", ".jpg", ".jpeg")
}
