package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

open class Teaser private constructor(
        var fromuid: Int = 0,
        var pid: Int = 0,
        var uid: Int = 0,
        var tid: Int = 0,
        var content: String? = null,
        var timestamp: Long = 0,
        var user: User? = null,
        var timestampISO: String? = null,
        var index: Int = 0
): Parcelable {
    /**
     * pid : 1101
     * uid : 91
     * tid : 187
     * content :
     *
     *加油↖(^ω^)↗

     * timestamp : 1488896525661
     * user : {"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"}
     * timestampISO : 2017-03-07T14:22:05.661Z
     * index : 4
     */
    protected constructor(`in`: Parcel) : this(
        fromuid = `in`.readInt(),
        pid = `in`.readInt(),
        uid = `in`.readInt(),
        tid = `in`.readInt(),
        content = `in`.readString(),
        timestamp = `in`.readLong(),
        timestampISO = `in`.readString(),
        index = `in`.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(fromuid)
        dest.writeInt(pid)
        dest.writeInt(uid)
        dest.writeInt(tid)
        dest.writeString(content)
        dest.writeLong(timestamp)
        dest.writeString(timestampISO)
        dest.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Teaser> = object : Parcelable.Creator<Teaser> {
            override fun createFromParcel(`in`: Parcel): Teaser {
                return Teaser(`in`)
            }

            override fun newArray(size: Int): Array<Teaser?> {
                return arrayOfNulls(size)
            }
        }
    }
}
