package org.miaowo.miaowo.bean

import org.miaowo.miaowo.bean.data.Topic
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.API

/**
 * 搜索结果
 * Created by lq200 on 2018/2/19.
 */
data class SearchResult(
        val type: API.SearchType,
        val userResult: User? = null,
        val topicResult: Topic? = null
)
