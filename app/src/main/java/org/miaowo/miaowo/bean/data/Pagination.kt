package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * rel : [{"rel":"next","href":"?page=2"}]
 * pages : [{"page":1,"active":true,"qs":"page=1"},{"page":2,"active":false,"qs":"page=2"},{"page":3,"active":false,"qs":"page=3"},{"page":4,"active":false,"qs":"page=4"},{"page":5,"active":false,"qs":"page=5"},{"separator":true},{"page":11,"active":false,"qs":"page=11"},{"page":12,"active":false,"qs":"page=12"}]
 * currentPage : 1
 * pageCount : 12
 * prev : {"page":1,"active":false,"qs":"page=1"}
 * next : {"page":2,"active":true,"qs":"page=2"}
 */

open class Pagination private constructor(
        var currentPage: Int = 0,
        var pageCount: Int = 0,
        var prev: Page? = null,
        var next: Page? = null,
        var rel: List<Page.Rel>? = null,
        var pages: List<Page>? = null
) : Parcelable {
    protected constructor(`in`: Parcel) : this(
        currentPage = `in`.readInt(),
        pageCount = `in`.readInt(),
        prev = `in`.readParcelable<Page>(Page::class.java.classLoader),
        next = `in`.readParcelable<Page>(Page::class.java.classLoader),
        pages = `in`.createTypedArrayList(Page.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(currentPage)
        dest.writeInt(pageCount)
        dest.writeParcelable(prev, flags)
        dest.writeParcelable(next, flags)
        dest.writeTypedList(pages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Pagination> = object : Parcelable.Creator<Pagination> {
            override fun createFromParcel(`in`: Parcel): Pagination {
                return Pagination(`in`)
            }

            override fun newArray(size: Int): Array<Pagination?> {
                return arrayOfNulls(size)
            }
        }
    }
}
