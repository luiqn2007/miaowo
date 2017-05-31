package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

/**
 * 问题详情页中的问题
 * Created by luqin on 17-3-17.
 */

open class Question private constructor(
        var tid: Int = 0,
        var uid: Int = 0,
        var cid: String? = null,
        var mainPid: Int = 0,
        var title: String? = null,
        var slug: String? = null,
        var timestamp: Long = 0,
        var lastposttime: Long = 0,
        var postcount: Int = 0,
        var viewcount: Int = 0,
        var isLocked: Boolean = false,
        var isDeleted: Boolean = false,
        var isPinned: Boolean = false,
        var teaserPid: String? = null,
        var titleRaw: String? = null,
        var timestampISO: String? = null,
        var lastposttimeISO: String? = null,
        var category: Category? = null,
        var isIsFollowing: Boolean = false,
        var isIsNotFollowing: Boolean = false,
        var isIsIgnoring: Boolean = false,
        var bookmark: Any? = null,
        var isUnreplied: Boolean = false,
        var privileges: Privilege? = null,
        var topicStaleDays: Int = 0,
        @SerializedName("reputation:disabled")
        var isReputationDisabled: Boolean = false,
        @SerializedName("downvote:disabled")
        var isDownvoteDisabled: Boolean = false,
        @SerializedName("feeds:disableRSS")
        var isFeedsDisableRSS: Boolean = false,
        var bookmarkThreshold: Int = 0,
        var postEditDuration: Int = 0,
        var postDeleteDuration: Int = 0,
        var isScrollToMyPost: Boolean = false,
        var rssFeedUrl: String? = null,
        var pagination: Pagination? = null,
        var isLoggedIn: Boolean = false,
        var relative_path: String? = null,
        var url: String? = null,
        var bodyClass: String? = null,
        var tags: List<*>? = null,
        var posts: List<Post>? = null,
        var thread_tools: List<*>? = null,
        var postSharing: List<*>? = null,
        var related: List<*>? = null,
        var icons: List<*>? = null
): Parcelable {

    /**
     * tid : 195
     * uid : 1
     * cid : 5
     * mainPid : 1119
     * title : 你日常的包里都有些什么？
     * slug : 195/你日常的包里都有些什么
     * timestamp : 1489208654708
     * lastposttime : 1489679398250
     * postcount : 5
     * viewcount : 18
     * locked : false
     * deleted : false
     * pinned : false
     * teaserPid : 1137
     * titleRaw : 你日常的包里都有些什么？
     * timestampISO : 2017-03-11T05:04:14.708Z
     * lastposttimeISO : 2017-03-16T15:49:58.250Z
     * tags : []
     * posts : [{"pid":1119,"uid":1,"tid":195,"content":"
     *
     *我的<\/p>\n\n * 水杯<\/li>\n * Kindle<\/li>\n * 电车卡<\/li>\n * 学生卡<\/li>\n * 移动电源<\/li>\n * 手机 x2<\/li>\n * 各类数据线<\/li>\n * 零钱<\/li>\n * 一卷纸巾<\/li>\n * 钥匙<\/li>\n<\/ul>\n","timestamp":1489208654708,"deleted":false,"upvotes":0,"downvotes":0,"votes":0,"timestampISO":"2017-03-11T05:04:14.708Z","editedISO":"","index":0,"user":{"username":"Systemd","userslug":"systemd","lastonline":1489670891717,"picture":"","fullname":"","signature":"","reputation":-1,"postcount":165,"banned":false,"status":"offline","uid":1,"groupTitle":"","icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-03-16T13:28:11.717Z","custom_profile_info":[]},"editor":null,"bookmarked":false,"upvoted":false,"downvoted":false,"replies":0,"selfPost":false,"display_edit_tools":true,"display_delete_tools":true,"display_moderator_tools":true,"display_move_tools":false,"display_post_menu":true},{"pid":1120,"uid":7,"tid":195,"content":"
     *
     *手机*2<br></br>\n身份证，校园卡<br></br>\n中行卡<br></br>\n公交卡，公共自行车卡<br></br>\n充电宝，数据线<\/p>\n
     *
     *没有钥匙，宿舍门刷卡进的<\/p>\n","timestamp":1489209879235,"deleted":false,"upvotes":0,"downvotes":0,"votes":0,"timestampISO":"2017-03-11T05:24:39.235Z","editedISO":"","index":1,"user":{"username":"么么么喵","userslug":"么么么喵","lastonline":1489690682372,"picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","fullname":"么么么喵","signature":"","reputation":1,"postcount":106,"banned":false,"status":"online","uid":7,"groupTitle":"","icon:text":"么","icon:bgColor":"#1b5e20","lastonlineISO":"2017-03-16T18:58:02.372Z","custom_profile_info":[]},"editor":null,"bookmarked":false,"upvoted":false,"downvoted":false,"replies":0,"selfPost":true,"display_edit_tools":true,"display_delete_tools":true,"display_moderator_tools":true,"display_move_tools":true,"display_post_menu":true},{"pid":1123,"uid":57,"tid":195,"content":"
     *
     *钱包，钥匙，湿巾，纸巾，唇膏，小镜子梳子，笔和便利贴<\/p>\n","timestamp":1489338930820,"deleted":false,"upvotes":0,"downvotes":0,"votes":0,"timestampISO":"2017-03-12T17:15:30.820Z","editedISO":"","index":2,"user":{"username":"XiaoBai","userslug":"xiaobai","lastonline":1489338930909,"picture":"/uploads/files/148294821000092706de1477dd987b2128f70fbb9a5db.jpg","fullname":"","signature":"","reputation":0,"postcount":35,"banned":false,"status":"offline","uid":57,"groupTitle":"","icon:text":"X","icon:bgColor":"#ff5722","lastonlineISO":"2017-03-12T17:15:30.909Z","custom_profile_info":[]},"editor":null,"bookmarked":false,"upvoted":false,"downvoted":false,"replies":0,"selfPost":false,"display_edit_tools":true,"display_delete_tools":true,"display_moderator_tools":true,"display_move_tools":true,"display_post_menu":true},{"pid":1124,"uid":11,"tid":195,"content":"
     *
     *钱<br></br>\n手机<br></br>\n水杯<br></br>\n雨伞<br></br>\n纸巾<br></br>\n钥匙<br></br>\n书<br></br>\n笔<br></br>\n还有公交卡和校园一卡通（一卡通倒是会经常忘带_(:з」∠)_）<\/p>\n","timestamp":1489378865881,"deleted":false,"edited":1489378929756,"editor":{"username":"小贼不乖","userslug":"小贼不乖","uid":11},"upvotes":0,"downvotes":0,"votes":0,"timestampISO":"2017-03-13T04:21:05.881Z","editedISO":"2017-03-13T04:22:09.756Z","index":3,"user":{"username":"小贼不乖","userslug":"小贼不乖","lastonline":1489572710179,"picture":"/uploads/files/1483678110140jpeg_20160930_173917_-1157967832.jpg","fullname":"","signature":"
     *
     *(･ิϖ･ิ)っ签爪爪<\/p>\n","reputation":11,"postcount":187,"banned":false,"status":"offline","uid":11,"groupTitle":"","icon:text":"小","icon:bgColor":"#33691e","lastonlineISO":"2017-03-15T10:11:50.179Z","custom_profile_info":[]},"bookmarked":false,"upvoted":false,"downvoted":false,"replies":0,"selfPost":false,"display_edit_tools":true,"display_delete_tools":true,"display_moderator_tools":true,"display_move_tools":true,"display_post_menu":true},{"pid":1137,"uid":108,"tid":195,"content":"
     *
     *有时候不带包，可能一带就会和搬家一样。放进游戏机漫画书电脑水杯什么的。主要的还是钥匙，各种卡们，润唇膏，笔，手机，湿巾。夏天会加瓶防晒霜，驱蚊止痒的。墨镜夹片？<\/p>\n","timestamp":1489679398250,"deleted":false,"upvotes":0,"downvotes":0,"votes":0,"timestampISO":"2017-03-16T15:49:58.250Z","editedISO":"","index":4,"user":{"username":"奇奇酱","userslug":"奇奇酱","lastonline":1489679399282,"picture":"","fullname":"","signature":"","reputation":0,"postcount":12,"banned":false,"status":"offline","uid":108,"groupTitle":null,"icon:text":"奇","icon:bgColor":"#1b5e20","lastonlineISO":"2017-03-16T15:49:59.282Z","custom_profile_info":[]},"editor":null,"bookmarked":false,"upvoted":false,"downvoted":false,"replies":0,"selfPost":false,"display_edit_tools":true,"display_delete_tools":true,"display_moderator_tools":true,"display_move_tools":true,"display_post_menu":true}]
     * category : {"cid":5,"name":"提问","description":"","descriptionParsed":"","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0,"topic_count":67,"post_count":452,"disabled":false,"order":5,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":452,"totalTopicCount":67}
     * thread_tools : []
     * isFollowing : true
     * isNotFollowing : false
     * isIgnoring : false
     * bookmark : null
     * postSharing : []
     * related : []
     * unreplied : false
     * icons : []
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"提问","url":"/category/5/提问"},{"text":"你日常的包里都有些什么？"}]
     * privileges : {"topic:reply":true,"topic:read":true,"topic:delete":true,"posts:edit":true,"posts:delete":true,"read":true,"view_thread_tools":true,"editable":true,"deletable":true,"view_deleted":true,"isAdminOrMod":true,"disabled":false,"tid":"195","uid":7}
     * topicStaleDays : 60
     * reputation:disabled : true
     * downvote:disabled : false
     * feeds:disableRSS : true
     * bookmarkThreshold : 5
     * postEditDuration : 0
     * postDeleteDuration : 0
     * scrollToMyPost : true
     * rssFeedUrl : /topic/195.rss
     * pagination : {"prev":{"page":1,"active":false},"next":{"page":1,"active":false},"rel":[],"pages":[],"currentPage":1,"pageCount":1}
     * loggedIn : true
     * relative_path :
     * template : {"name":"topic","topic":true}
     * url : /topic/195/%E4%BD%A0%E6%97%A5%E5%B8%B8%E7%9A%84%E5%8C%85%E9%87%8C%E9%83%BD%E6%9C%89%E4%BA%9B%E4%BB%80%E4%B9%88/5
     * bodyClass : page-topic page-topic-195 page-topic-你日常的包里都有些什么
     * loggedInUser : {"username":"么么么喵","userslug":"么么么喵","email":"1105188240@qq.com","joindate":1479650239103,"lastonline":1489690682372,"picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","fullname":"么么么喵","location":"","birthday":"","website":"","signature":"","uploadedpicture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","profileviews":42,"reputation":1,"postcount":106,"topiccount":10,"lastposttime":1489690682264,"banned":0,"status":"online","uid":7,"passwordExpiry":0,"cover:position":"50.0262% 46.4866%","cover:url":"/uploads/files/1482860372268profilecover","githubid":"18262245","followingCount":3,"groupTitle":"","aboutme":"","banned:expire":0,"followerCount":4,"icon:text":"么","icon:bgColor":"#1b5e20","joindateISO":"2016-11-20T13:57:19.103Z","lastonlineISO":"2017-03-16T18:58:02.372Z"}
     */

    protected constructor(`in`: Parcel) : this(
        tid = `in`.readInt(),
        uid = `in`.readInt(),
        cid = `in`.readString(),
        mainPid = `in`.readInt(),
        title = `in`.readString(),
        slug = `in`.readString(),
        timestamp = `in`.readLong(),
        lastposttime = `in`.readLong(),
        postcount = `in`.readInt(),
        viewcount = `in`.readInt(),
        isLocked = `in`.readByte().toInt() != 0,
        isDeleted = `in`.readByte().toInt() != 0,
        isPinned = `in`.readByte().toInt() != 0,
        teaserPid = `in`.readString(),
        titleRaw = `in`.readString(),
        timestampISO = `in`.readString(),
        lastposttimeISO = `in`.readString(),
        category = `in`.readParcelable<Category>(Category::class.java.classLoader),
        isIsFollowing = `in`.readByte().toInt() != 0,
        isIsNotFollowing = `in`.readByte().toInt() != 0,
        isIsIgnoring = `in`.readByte().toInt() != 0,
        isUnreplied = `in`.readByte().toInt() != 0,
        privileges = `in`.readParcelable<Privilege>(Privilege::class.java.classLoader),
        topicStaleDays = `in`.readInt(),
        isReputationDisabled = `in`.readByte().toInt() != 0,
        isDownvoteDisabled = `in`.readByte().toInt() != 0,
        isFeedsDisableRSS = `in`.readByte().toInt() != 0,
        bookmarkThreshold = `in`.readInt(),
        postEditDuration = `in`.readInt(),
        postDeleteDuration = `in`.readInt(),
        isScrollToMyPost = `in`.readByte().toInt() != 0,
        rssFeedUrl = `in`.readString(),
        pagination = `in`.readParcelable<Pagination>(Pagination::class.java.classLoader),
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        posts = `in`.createTypedArrayList(Post.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(tid)
        dest.writeInt(uid)
        dest.writeString(cid)
        dest.writeInt(mainPid)
        dest.writeString(title)
        dest.writeString(slug)
        dest.writeLong(timestamp)
        dest.writeLong(lastposttime)
        dest.writeInt(postcount)
        dest.writeInt(viewcount)
        dest.writeByte((if (isLocked) 1 else 0).toByte())
        dest.writeByte((if (isDeleted) 1 else 0).toByte())
        dest.writeByte((if (isPinned) 1 else 0).toByte())
        dest.writeString(teaserPid)
        dest.writeString(titleRaw)
        dest.writeString(timestampISO)
        dest.writeString(lastposttimeISO)
        dest.writeParcelable(category, flags)
        dest.writeByte((if (isIsFollowing) 1 else 0).toByte())
        dest.writeByte((if (isIsNotFollowing) 1 else 0).toByte())
        dest.writeByte((if (isIsIgnoring) 1 else 0).toByte())
        dest.writeByte((if (isUnreplied) 1 else 0).toByte())
        dest.writeParcelable(privileges, flags)
        dest.writeInt(topicStaleDays)
        dest.writeByte((if (isReputationDisabled) 1 else 0).toByte())
        dest.writeByte((if (isDownvoteDisabled) 1 else 0).toByte())
        dest.writeByte((if (isFeedsDisableRSS) 1 else 0).toByte())
        dest.writeInt(bookmarkThreshold)
        dest.writeInt(postEditDuration)
        dest.writeInt(postDeleteDuration)
        dest.writeByte((if (isScrollToMyPost) 1 else 0).toByte())
        dest.writeString(rssFeedUrl)
        dest.writeParcelable(pagination, flags)
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(posts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Question> = object : Parcelable.Creator<Question> {
            override fun createFromParcel(`in`: Parcel): Question {
                return Question(`in`)
            }

            override fun newArray(size: Int): Array<Question?> {
                return arrayOfNulls(size)
            }
        }
    }
}
