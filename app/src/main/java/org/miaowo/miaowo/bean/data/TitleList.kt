package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

/**
 * 问题列表
 *
 * 忽略的属性:
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"公告","url":"/category/1/announcements"}]
 * template : {"name":"category","category":true}:{"page":1,"active":false},"rel":[],"pages":[],"currentPage":1,"pageCount":1}
 *
 * Created by luqin on 17-3-15.
 */
open class TitleList private constructor(
        var cid: Int = 0, // cid : 1
        var name: String? = null, // name : 公告
        var description: String? = null, // description :
        var descriptionParsed: String? = null, // descriptionParsed :
        var icon: String? = null, // icon : fa-bullhorn
        var bgColor: String? = null, // bgColor : #fda34b
        var color: String? = null, // color : #fff
        var slug: String? = null, // slug : 1/announcements
        var parentCid: Int = 0, // parentCid : 0
        var topic_count: Int = 0, // topic_count : 2
        var post_count: Int = 0, // post_count : 5
        var isDisabled: Boolean = false, // disabled : false
        var order: Int = 0, // order : 2
        var link: String? = null, // link :
        var numRecentReplies: Int = 0, // numRecentReplies : 1
        @SerializedName("class")
        var classX: String? = null, // class : col-md-3 col-xs-6
        var imageClass: String? = null, // imageClass : cover
        var undefined: String? = null, // undefined : on
        var totalPostCount: Int = 0, // totalPostCount : 5
        var totalTopicCount: Int = 0, // totalTopicCount : 2
        @SerializedName("unread-class")
        var unreadclass: String? = null, // unread-class :
        var nextStart: Int = 0, // nextStart : 20
        var isIsIgnored: Boolean = false, // isIgnored : false
        var privileges: Privilege? = null, // privileges : {"topic:create":true,"title...}
        var isShowSelect: Boolean = false, // showSelect : true
        @SerializedName("feeds:disableRSS")
        var isFeedsDisableRSS: Boolean = false, // feeds:disableRSS : true
        var rssFeedUrl: String? = null, // rssFeedUrl : /category/1.rss
        var title: String? = null, // title : 公告
        var pagination: Pagination? = null, // pagination : {"prev":{"page":1,"active":false},"next"
        var isLoggedIn: Boolean = false, // loggedIn : true
        var relative_path: String? = null, // relative_path :
        var url: String? = null, // url : /category/1/announcements
        var bodyClass: String? = null, // bodyClass : page-category page-category-1 page-category-announcements
        var children: List<*>? = null, // children : []
        var tagWhitelist: List<*>? = null, // tagWhitelist : []
        var topics: List<Title>? = null  // topic : [{"tid":187,"ui...}]
) : Parcelable {
    protected constructor(`in`: Parcel) : this(
            cid = `in`.readInt(),
            name = `in`.readString(),
            description = `in`.readString(),
            descriptionParsed = `in`.readString(),
            icon = `in`.readString(),
            bgColor = `in`.readString(),
            color = `in`.readString(),
            slug = `in`.readString(),
            parentCid = `in`.readInt(),
            topic_count = `in`.readInt(),
            post_count = `in`.readInt(),
            isDisabled = `in`.readByte().toInt() != 0,
            order = `in`.readInt(),
            link = `in`.readString(),
            numRecentReplies = `in`.readInt(),
            classX = `in`.readString(),
            imageClass = `in`.readString(),
            undefined = `in`.readString(),
            totalPostCount = `in`.readInt(),
            totalTopicCount = `in`.readInt(),
            unreadclass = `in`.readString(),
            nextStart = `in`.readInt(),
            isIsIgnored = `in`.readByte().toInt() != 0,
            privileges = `in`.readParcelable<Privilege>(Privilege::class.java.classLoader),
            isShowSelect = `in`.readByte().toInt() != 0,
            isFeedsDisableRSS = `in`.readByte().toInt() != 0,
            rssFeedUrl = `in`.readString(),
            title = `in`.readString(),
            pagination = `in`.readParcelable<Pagination>(Pagination::class.java.classLoader),
            isLoggedIn = `in`.readByte().toInt() != 0,
            relative_path = `in`.readString(),
            url = `in`.readString(),
            bodyClass = `in`.readString(),
            topics = `in`.createTypedArrayList(Title.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(cid)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(descriptionParsed)
        dest.writeString(icon)
        dest.writeString(bgColor)
        dest.writeString(color)
        dest.writeString(slug)
        dest.writeInt(parentCid)
        dest.writeInt(topic_count)
        dest.writeInt(post_count)
        dest.writeByte((if (isDisabled) 1 else 0).toByte())
        dest.writeInt(order)
        dest.writeString(link)
        dest.writeInt(numRecentReplies)
        dest.writeString(classX)
        dest.writeString(imageClass)
        dest.writeString(undefined)
        dest.writeInt(totalPostCount)
        dest.writeInt(totalTopicCount)
        dest.writeString(unreadclass)
        dest.writeInt(nextStart)
        dest.writeByte((if (isIsIgnored) 1 else 0).toByte())
        dest.writeParcelable(privileges, flags)
        dest.writeByte((if (isShowSelect) 1 else 0).toByte())
        dest.writeByte((if (isFeedsDisableRSS) 1 else 0).toByte())
        dest.writeString(rssFeedUrl)
        dest.writeString(title)
        dest.writeParcelable(pagination, flags)
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(topics)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "TitleList(cid=$cid, name=$name, description=$description, descriptionParsed=$descriptionParsed, icon=$icon, bgColor=$bgColor, color=$color, slug=$slug, parentCid=$parentCid, topic_count=$topic_count, post_count=$post_count, isDisabled=$isDisabled, order=$order, link=$link, numRecentReplies=$numRecentReplies, classX=$classX, imageClass=$imageClass, undefined=$undefined, totalPostCount=$totalPostCount, totalTopicCount=$totalTopicCount, unreadclass=$unreadclass, nextStart=$nextStart, isIsIgnored=$isIsIgnored, privileges=$privileges, isShowSelect=$isShowSelect, isFeedsDisableRSS=$isFeedsDisableRSS, rssFeedUrl=$rssFeedUrl, title=$title, pagination=$pagination, isLoggedIn=$isLoggedIn, relative_path=$relative_path, url=$url, bodyClass=$bodyClass, children=$children, tagWhitelist=$tagWhitelist, topic=$topics)"
    }


    companion object {

        val CREATOR: Parcelable.Creator<TitleList> = object : Parcelable.Creator<TitleList> {
            override fun createFromParcel(`in`: Parcel): TitleList {
                return TitleList(`in`)
            }

            override fun newArray(size: Int): Array<TitleList?> {
                return arrayOfNulls(size)
            }
        }
    }
}
