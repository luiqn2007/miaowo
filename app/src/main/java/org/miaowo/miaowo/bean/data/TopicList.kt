package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 话题列表
 *
 * 忽略的属性：
 * template : {"name":"tags","tags":true}
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[tags:tags]]"}]
 */
open class TopicList private constructor(
        var nextStart: Int = 0,  // nextStart : 100
        var title: String? = null,  // title : [[pages:tags]]
        var isLoggedIn: Boolean = false,  // loggedIn : true
        var relative_path: String? = null,  // relative_path :
        var url: String? = null,  // url : /tags
        var bodyClass: String? = null,  // bodyClass : page-tags
        var tags: List<Topic>? = null  // tags : [{"value":"猜猜我是谁","score":2,...}]
): Parcelable {
    protected constructor(`in`: Parcel): this(
        nextStart = `in`.readInt(),
        title = `in`.readString(),
        isLoggedIn = `in`.readByte().toInt() != 0,
        relative_path = `in`.readString(),
        url = `in`.readString(),
        bodyClass = `in`.readString(),
        tags = `in`.createTypedArrayList(Topic.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(nextStart)
        dest.writeString(title)
        dest.writeByte((if (isLoggedIn) 1 else 0).toByte())
        dest.writeString(relative_path)
        dest.writeString(url)
        dest.writeString(bodyClass)
        dest.writeTypedList(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<TopicList> = object : Parcelable.Creator<TopicList> {
            override fun createFromParcel(`in`: Parcel): TopicList {
                return TopicList(`in`)
            }

            override fun newArray(size: Int): Array<TopicList?> {
                return arrayOfNulls(size)
            }
        }
    }
}
