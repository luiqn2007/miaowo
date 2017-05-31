package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 问题列表中的问题
 */
open class Title private constructor(
        var tid: Int = 0,
        var uid: Int = 0,
        var cid: Int = 0,
        var mainPid: String? = null,
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
        var thumb: String? = null,
        var titleRaw: String? = null,
        var timestampISO: String? = null,
        var lastposttimeISO: String? = null,
        var category: Category? = null,
        var user: User? = null,
        var teaser: Teaser? = null,
        var isIsOwner: Boolean = false,
        var isIgnored: Boolean = false,
        var isUnread: Boolean = false,
        var bookmark: Any? = null,
        var isUnreplied: Boolean = false,
        var index: Int = 0,
        var tags: List<*>? = null,
        var icons: List<*>? = null
): Parcelable {
    /**
     * tid : 187
     * uid : 7
     * cid : 1
     * mainPid : 1041
     * title : APP制作进度
     * slug : 187/app制作进度
     * timestamp : 1488525534569
     * lastposttime : 1488896525661
     * postcount : 4
     * viewcount : 65
     * locked : false
     * deleted : false
     * pinned : false
     * teaserPid : 1101
     * thumb :
     * titleRaw : APP制作进度
     * timestampISO : 2017-03-03T07:18:54.569Z
     * lastposttimeISO : 2017-03-07T14:22:05.661Z
     * category : {"cid":1,"name":"公告","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","disabled":false,"image":null}
     * user : {"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","fullname":"么么么喵","signature":"","reputation":1,"postcount":113,"banned":0,"status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"}
     * teaser : {"pid":1101,"uid":91,"tid":187,"content":"
     *
     *加油↖(^ω^)↗<\/p>\n","timestamp":1488896525661,"user":{"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"},"timestampISO":"2017-03-07T14:22:05.661Z","index":4}
     * tags : []
     * isOwner : true
     * ignored : false
     * unread : false
     * bookmark : null
     * unreplied : false
     * icons : []
     * index : 0
     */

    protected constructor(`in`: Parcel): this (
        tid = `in`.readInt(),
        uid = `in`.readInt(),
        cid = `in`.readInt(),
        mainPid = `in`.readString(),
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
        thumb = `in`.readString(),
        titleRaw = `in`.readString(),
        timestampISO = `in`.readString(),
        lastposttimeISO = `in`.readString(),
        category = `in`.readParcelable<Category>(Category::class.java.classLoader),
        user = `in`.readParcelable<User>(User::class.java.classLoader),
        teaser = `in`.readParcelable<Teaser>(Teaser::class.java.classLoader),
        isIsOwner = `in`.readByte().toInt() != 0,
        isIgnored = `in`.readByte().toInt() != 0,
        isUnread = `in`.readByte().toInt() != 0,
        isUnreplied = `in`.readByte().toInt() != 0,
        index = `in`.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(tid)
        dest.writeInt(uid)
        dest.writeInt(cid)
        dest.writeString(mainPid)
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
        dest.writeString(thumb)
        dest.writeString(titleRaw)
        dest.writeString(timestampISO)
        dest.writeString(lastposttimeISO)
        dest.writeParcelable(category, flags)
        dest.writeParcelable(user, flags)
        dest.writeParcelable(teaser, flags)
        dest.writeByte((if (isIsOwner) 1 else 0).toByte())
        dest.writeByte((if (isIgnored) 1 else 0).toByte())
        dest.writeByte((if (isUnread) 1 else 0).toByte())
        dest.writeByte((if (isUnreplied) 1 else 0).toByte())
        dest.writeInt(index)
    }

    companion object {

        val CREATOR: Parcelable.Creator<Title> = object : Parcelable.Creator<Title> {
            override fun createFromParcel(`in`: Parcel): Title {
                return Title(`in`)
            }

            override fun newArray(size: Int): Array<Title?> {
                return arrayOfNulls(size)
            }
        }
    }
}
