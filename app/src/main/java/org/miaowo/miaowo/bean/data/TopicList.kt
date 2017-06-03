package org.miaowo.miaowo.bean.data

/**
 * 话题列表
 *
 * 忽略的属性：
 * template : {"name":"tags","tags":true}
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[tags:tags]]"}]
 */
data class TopicList (
        var nextStart: Int = 0,  // nextStart : 100
        var title: String? = null,  // title : [[pages:tags]]
        var isLoggedIn: Boolean = false,  // loggedIn : true
        var relative_path: String? = null,  // relative_path :
        var url: String? = null,  // url : /tags
        var bodyClass: String? = null,  // bodyClass : page-tags
        var tags: List<Topic>? = null  // tags : [{"value":"猜猜我是谁","score":2,...}]
)