package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 话题
 */
open class Topic private constructor(
        var value: String? = null,  // value : 猜猜我是谁
        var score: Int = 0,  // score : 2
        var color: String? = null,  // color :
        var bgColor: String? = null  // bgColor :
): Parcelable {
    protected constructor(`in`: Parcel): this(
        value = `in`.readString(),
        score = `in`.readInt(),
        color = `in`.readString(),
        bgColor = `in`.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(value)
        dest.writeInt(score)
        dest.writeString(color)
        dest.writeString(bgColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Topic> = object : Parcelable.Creator<Topic> {
            override fun createFromParcel(`in`: Parcel): Topic {
                return Topic(`in`)
            }

            override fun newArray(size: Int): Array<Topic?> {
                return arrayOfNulls(size)
            }
        }
    }
}
