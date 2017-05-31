package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 搜索用户结果
 *
 * 忽略的属性：
 * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[global:search]]"}]
 * template : {"name":"search","search":true}
 *
 * Created by luqin on 17-3-18.
 */

open class UserSearch private constructor(
        var matchCount: Int = 0,  // matchCount : 1
        var pageCount: Int = 0,  // pageCount : 1
        var timing: String? = null,  // timing : 0.95
        var search_query: String? = null,  // search_query : 啊
        var time: String? = null,  // time : 0.95
        var categoriesCount: Int = 0,  // categoriesCount : 4
        var pagination: Pagination? = null,  // pagination : {"prev":{"page":1,...}
        var isShowAsPosts: Boolean = false,  // showAsPosts : true
        var isShowAsTopics: Boolean = false,  // showAsTopics : false
        var title: String? = null,  // title : [[global:header.search]]
        var isExpandSearch: Boolean = false,  // expandSearch : false
        var isLoggedIn: Boolean = false,  // loggedIn : true
        var relative_path: String? = null,  // relative_path :
        var url: String? = null,  // url : /search
        var bodyClass: String? = null,  // bodyClass : page-search
        var users: List<User>? = null,  // users : [{"username":"啊辄不吃鱼","u...}]
        var categories: List<Category>? = null  // categories : [{"value":"all","text":"[[unr...}]
): Parcelable {
    protected constructor(`in`: Parcel) : this(
        matchCount = `in`.readInt(),
        pageCount = `in`.readInt(),
        timing = `in`.readString(),
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
        users = `in`.createTypedArrayList(User.CREATOR),
        categories = `in`.createTypedArrayList(Category.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(matchCount)
        dest.writeInt(pageCount)
        dest.writeString(timing)
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
        dest.writeTypedList(users)
        dest.writeTypedList(categories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<UserSearch> = object : Parcelable.Creator<UserSearch> {
            override fun createFromParcel(`in`: Parcel): UserSearch {
                return UserSearch(`in`)
            }

            override fun newArray(size: Int): Array<UserSearch?> {
                return arrayOfNulls(size)
            }
        }
    }
}
