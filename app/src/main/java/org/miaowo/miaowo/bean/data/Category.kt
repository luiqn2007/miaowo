package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 类别
 *
 * 忽略的属性:
 * class : col-md-3 col-xs-6
 * unread-class :
 * children : []
 * tagWhitelist : []
 */
open class Category(
        var value: String? = null, // value : all
        var text: String? = null, // text : [[unread:all_categories]]
        var cid: Int = 0, // cid : 5
        var name: String? = null, // name : 提问
        var description: String? = null, // description :
        var descriptionParsed: String? = null, // descriptionParsed :
        var icon: String? = null, // icon : fa-question
        var bgColor: String? = null, // bgColor : #e95c5a
        var color: String? = null, // color : #fff
        var slug: String? = null, // slug : 5/提问
        var parentCid: Int = 0, // parentCid : 0
        var topic_count: Int = 0, // topic_count : 67
        var post_count: Int = 0, // post_count : 452
        var posts: List<Post>? = null,
        var isDisabled: Boolean = false, // disabled : false
        var order: Int = 0, // order : 5
        var link: String? = null, // link :
        var numRecentReplies: Int = 0, // numRecentReplies : 1
        var imageClass: String? = null, // imageClass : cover
        var undefined: String? = null, // undefined : on
        var totalPostCount: Int = 0, // totalPostCount : 452
        var totalTopicCount: Int = 0  // totalTopicCount : 67,
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Category> = object : Parcelable.Creator<Category> {
            override fun createFromParcel(`in`: Parcel): Category {
                return Category(`in`)
            }

            override fun newArray(size: Int): Array<Category?> {
                return arrayOfNulls(size)
            }
        }
    }

    protected constructor(`in`: Parcel) : this(
            value = `in`.readString(),
            text = `in`.readString(),
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
            posts = `in`.createTypedArrayList(Post.CREATOR),
            isDisabled = `in`.readByte().toInt() != 0,
            order = `in`.readInt(),
            link = `in`.readString(),
            numRecentReplies = `in`.readInt(),
            imageClass = `in`.readString(),
            undefined = `in`.readString(),
            totalPostCount = `in`.readInt(),
            totalTopicCount = `in`.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(value)
        dest.writeString(text)
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
        dest.writeTypedList(posts)
        dest.writeByte((if (isDisabled) 1 else 0).toByte())
        dest.writeInt(order)
        dest.writeString(link)
        dest.writeInt(numRecentReplies)
        dest.writeString(imageClass)
        dest.writeString(undefined)
        dest.writeInt(totalPostCount)
        dest.writeInt(totalTopicCount)
    }

    override fun describeContents(): Int {
        return 0
    }
}
