package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 搜索帖子和主题
 * Created by luqin on 17-3-18.
 */

open class QuestionSearch private constructor(
        var matchCount: Int = 0,
        var pageCount: Int = 0,
        var search_query: String? = null,
        var time: String? = null,
        var categoriesCount: Int = 0,
        var pagination: Pagination? = null,
        var isShowAsPosts: Boolean = false,
        var isShowAsTopics: Boolean = false,
        var title: String? = null,
        var isExpandSearch: Boolean = false,
        var isLoggedIn: Boolean = false,
        var relative_path: String? = null,
        var url: String? = null,
        var bodyClass: String? = null,
        var posts: List<Post>? = null,
        var categories: List<Category>? = null
): Parcelable {

    /**
     * posts : [{"pid":575,"uid":18,"tid":111,"content":"
     *
     *[@不必再说<\/a> 啊~啊~啊~一脸别扭傲娇推开我的小不什么的我也稀饭（抖M脸淫笑）<\/p>\n","timestamp":1485872640802,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"},"topic":{"tid":111,"uid":65,"cid":"6","mainPid":527,"title":"报道！","slug":"111/报道","postcount":15,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-01-31T14:24:00.802Z"},{"pid":728,"uid":99,"tid":135,"content":"](\"https://miaowo.org/uid/65\")
     *
     *啊\u2026\u2026好期待啊！！希望做出一个一样温暖的喵窝！！<\/p>\n","timestamp":1486138179739,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"九尾yueye","userslug":"九尾yueye","picture":"/uploads/files/148559721400460856166_p0_master1200.jpg","uid":99,"icon:text":"九","icon:bgColor":"#e65100"},"topic":{"tid":135,"uid":32,"cid":"5","mainPid":721,"title":"会有一个新喵窝app吗？","slug":"135/会有一个新喵窝app吗","postcount":3,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-03T16:09:39.739Z"},{"pid":330,"uid":74,"tid":80,"content":"
     *
     *\u2014\u2014呐我**烟花<\/strong>啊(๑\u2022ั็ω\u2022็ั๑)<\/p>\n","timestamp":1485534712150,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"哈那比酱","userslug":"哈那比酱","picture":"","uid":74,"icon:text":"哈","icon:bgColor":"#3f51b5"},"topic":{"tid":80,"uid":74,"cid":"6","mainPid":330,"title":"今天熬夜2333( \u2022̀∀\u2022́ )","slug":"80/今天熬夜2333","postcount":6,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":true,"votes":0,"timestampISO":"2017-01-27T16:31:52.150Z"},{"pid":770,"uid":1,"tid":139,"content":"**
     *
     *[@么么么喵<\/a> 我上了 CLOUDFLARE 的 CDN 啊\u2026\u2026接下来打算折腾一下香港的\u2026<\/p>\n","timestamp":1486255662954,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"Systemd","userslug":"systemd","picture":"","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"topic":{"tid":139,"uid":7,"cid":"5","mainPid":760,"title":"喵窝的服务器在海外吗？","slug":"139/喵窝的服务器在海外吗","postcount":5,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-05T00:47:42.954Z"},{"pid":165,"uid":56,"tid":33,"content":"](\"https://miaowo.org/uid/7\")
     *
     *啊 |･ω･｀)！这里是豆芽，又来猫窝辣~<br></br>\n唔啊，，有点蠢啊。<br></br>\n猫窝怎么用啊？<\/p>\n","timestamp":1482943365660,"deleted":false,"upvotes":1,"downvotes":0,"user":{"username":"豆芽昂","userslug":"豆芽昂","picture":"","uid":56,"icon:text":"豆","icon:bgColor":"#607d8b"},"topic":{"tid":33,"uid":56,"cid":"5","mainPid":165,"title":"蠢】豆芽参上","slug":"33/蠢-豆芽参上","postcount":3,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":true,"votes":1,"timestampISO":"2016-12-28T16:42:45.660Z"},{"pid":402,"uid":18,"tid":77,"content":"
     *
     *[@图图图<\/a> 啊~！被图亲亲了~嘿嘿嘿（娇羞笑）\u2044(\u2044 \u2044\u2022\u2044ω\u2044\u2022\u2044 \u2044)\u2044（撒娇蹭蹭图）嗯！我们很多人还在哟！幸好最后一刻图你回来了ww新的一年也请多多指教啦=3=<\/p>\n","timestamp":1485588816309,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"},"topic":{"tid":77,"uid":94,"cid":"5","mainPid":312,"title":"这里图，恭祝大家新年快乐，鸡年大吉","slug":"77/这里图-恭祝大家新年快乐-鸡年大吉","postcount":9,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-01-28T07:33:36.309Z"},{"pid":1001,"uid":18,"tid":180,"content":"](\"https://miaowo.org/uid/94\")
     *
     *@小贼不乖 不多捏， 就写过五封而己❣❣ꉂ ૡ(・ꈊ・า˒˒)<br></br>\n都是心情好到飞起嗨到爆表的时候写出来的hhhhhhhhh<br></br>\n内容挺羞耻play的说ww<br></br>\n啊！不过经你说起这个话题我在想平时悠哉悠哉的时候也可以写一封耶挺有意思的w(○-艸･)*:ﾟ･☆<\/p>\n","timestamp":1488106660889,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"},"topic":{"tid":180,"uid":11,"cid":"6","mainPid":988,"title":"你写过情书吗","slug":"180/你写过情书吗","postcount":15,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-26T10:57:40.889Z"},{"pid":874,"uid":11,"tid":157,"content":"
     *
     *哈哈小系统好可爱<br></br>\n嗯很多都没有其实_(:з」∠)*来自早已忘记地理的文科生<br></br>\n东北觉得只有黑龙江 |<\/em>・)<br></br>\n西北觉得只有新疆和西藏 |*・)<br></br>\n西南觉得只有四川重庆云南，再加个海南（额海南算西南么 (ー`´ー)）<br></br>\n东南就福建浙江和广东，啊还有台湾ԅ(¯﹃¯ԅ)<br></br>\n至于其他就，啊武汉啊西安啊上海啊北京按城市来了<\/em>(:з」∠)_<br></br>\n武汉是哪个省的？  啊？湖南还湖北？∠( ᐛ 」∠)_<\/p>\n","timestamp":1486874377584,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"小贼不乖","userslug":"小贼不乖","picture":"/uploads/files/1483678110140jpeg_20160930_173917_-1157967832.jpg","uid":11,"icon:text":"小","icon:bgColor":"#33691e"},"topic":{"tid":157,"uid":38,"cid":"6","mainPid":861,"title":"在你的印象里，中国34和省级行政区划单位，最没有存在感的是哪个？我觉得是青海呢，想到青海，就想到格尔木，青海湖，然后就没有了\u2026\u2026","slug":"157/在你的印象里-中国34和省级行政区划单位-最没有存在感的是哪个-我觉得是青海呢-想到青海-就想到格尔木-青海湖-然后就没有了","postcount":5,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-12T04:39:37.584Z"}]
     * matchCount : 8
     * pageCount : 1
     * search_query : 啊
     * time : 0.03
     * categories : [{"value":"all","text":"[[unread:all_categories]]"},{"value":"watched","text":"[[category:watched-categories]]"},{"cid":7,"name":"每日问题","description":"","descriptionParsed":"","icon":"fa-calendar-check-o","bgColor":"#e95c5a","color":"#fff","slug":"7/每日问题","parentCid":0,"topic_count":0,"post_count":0,"disabled":false,"order":1,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":0,"totalTopicCount":0,"unread-class":"","children":[],"tagWhitelist":[],"value":7,"text":"每日问题"},{"cid":1,"name":"公告","description":"","descriptionParsed":"","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","parentCid":0,"topic_count":2,"post_count":5,"disabled":false,"order":2,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":5,"totalTopicCount":2,"unread-class":"","children":[],"tagWhitelist":[],"value":1,"text":"公告"},{"cid":5,"name":"提问","description":"","descriptionParsed":"","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0,"topic_count":69,"post_count":472,"disabled":false,"order":5,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":472,"totalTopicCount":69,"unread-class":"","children":[],"tagWhitelist":[],"value":5,"text":"提问"},{"cid":6,"name":"灌水","description":"","descriptionParsed":"","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0,"topic_count":123,"post_count":682,"disabled":false,"order":6,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":682,"totalTopicCount":123,"unread-class":"","children":[],"tagWhitelist":[],"value":6,"text":"灌水"}]
     * categoriesCount : 4
     * pagination : {"prev":{"page":1,"active":false},"next":{"page":1,"active":false},"rel":[],"pages":[],"currentPage":1,"pageCount":1}
     * showAsPosts : true
     * showAsTopics : false
     * title : [[global:header.search]]
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[global:search]]"}]
     * expandSearch : false
     * loggedIn : true
     * relative_path :
     * template : {"name":"search","search":true}
     * url : /search
     * bodyClass : page-search
     ** */

    protected constructor(`in`: Parcel) : this(
        matchCount = `in`.readInt(),
        pageCount = `in`.readInt(),
        search_query = `in`.readString(),
        time = `in`.readString(),
        categoriesCount = `in`.readInt(),
        pagination = `in`.readParcelable<Pagination>(Pagination::class.java.classLoader),
        isShowAsPosts = `in`.readByte().toInt() != 0,
        isShowAsTopics = `in`.readByte().toInt() != 0,
        title = `in`.readString(),
        isExpandSearch = `in`.readByte().toInt() != 0,
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        posts = `in`.createTypedArrayList(Post.CREATOR),
        categories = `in`.createTypedArrayList(Category.CREATOR)
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(matchCount)
        dest.writeInt(pageCount)
        dest.writeString(search_query)
        dest.writeString(time)
        dest.writeInt(categoriesCount)
        dest.writeParcelable(pagination, flags)
        dest.writeByte((if (isShowAsPosts) 1 else 0).toByte())
        dest.writeByte((if (isShowAsTopics) 1 else 0).toByte())
        dest.writeString(title)
        dest.writeByte((if (isExpandSearch) 1 else 0).toByte())
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(posts)
        dest.writeTypedList(categories)
    }

    companion object {

        val CREATOR: Parcelable.Creator<QuestionSearch> = object : Parcelable.Creator<QuestionSearch> {
            override fun createFromParcel(`in`: Parcel): QuestionSearch {
                return QuestionSearch(`in`)
            }

            override fun newArray(size: Int): Array<QuestionSearch?> {
                return arrayOfNulls(size)
            }
        }
    }
}
