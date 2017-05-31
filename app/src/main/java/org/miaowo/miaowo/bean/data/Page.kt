package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Page信息 暂未用过
 */

data class Page(
        var page: Int = 0,  // page : 1
        var isActive: Boolean = false,  // active : false
        var qs: String? = null   // qs : page=1
): Parcelable {
    companion object {
        val CREATOR = object : Parcelable.Creator<Page> {
            override fun createFromParcel(`in`: Parcel) = Page(`in`)
            override fun newArray(size: Int): Array<Page?> = arrayOfNulls(size)
        }
    }

    constructor(`in`: Parcel) :this(
        page = `in`.readInt(),
        isActive = `in`.readByte().toInt() != 0,
        qs = `in`.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(page)
        dest.writeByte((if (isActive) 1 else 0).toByte())
        dest.writeString(qs)
    }

    internal class Rel {
        var rel: String? = null  // rel : next
        var href: String? = null  // href : ?page=2
    }
}
