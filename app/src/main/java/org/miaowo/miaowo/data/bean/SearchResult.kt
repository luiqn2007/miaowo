package org.miaowo.miaowo.data.bean

data class SearchResult(
        val matchCount: Int = 0,
        val pageCount: Int = 1,
        val timing: Float = 0.00f,
        val users: List<User> = emptyList(),
        val posts: List<Post> = emptyList(),
        val time: Float = 0.00f,
        val pagination: Pagination? = null,
        val showAsPosts: Boolean = false,
        val showAsTopics: Boolean = false,
        val expandSearch: Boolean = false,
        val searchDefaultSortBy: String = "",
        val search_query: String = "",
        val term: String = "",
        val loggedIn: Boolean = false,
        val relative_path: String = ""
)