package org.miaowo.miaowo.bean.data

import android.os.Parcel
import android.os.Parcelable

/**
 * pid : 1168
 * uid : 7
 * tid : 197
 * content :
 *
 *[@Systemd](https://miaowo.org/uid/1)<br></br>
 * 说实话，我查了查awesome，个人不喜欢这样的。不可重叠，硬伤，alt+tab效率也不差。
 *
 * 暂时不喜换系统，webstorm我现在就装着，不过基本没用过 /(ㄒoㄒ)/~~
 *
 * [Blog.miaowo.org](http://Blog.miaowo.org) 现在还是空着的啊，不过既然计划要把公告去掉的话，我就不在APP里面预留了。
 *
 * rhel。。。也行，不过都差不多吧。说这些有点远，得先搞完喵窝APP再说。不然一不小心硬盘再挂掉哭都没地方哭去。
 *
 * 装软件很烦吗？我感觉 webapp 依托服务器，万一服务器挂了就完了，不太安全持久，而且外面套一层浏览器的壳也挺难看的，显示效果也不好保证。而且webapp比一般APP多一步打开浏览器的步骤，比较麻烦。想用旧版本也不太可能，离线功能也是个问题。
 *
 * 下一步到底是更进一步继续看 nativeApp 呢，还是看 webApp，有点迷茫了。nativeAPP 再深入就得研究 Linux，想看 root 后的功能，或者图形-&gt;游戏。webAPP的话，低平台依赖是个好处，而且如果解决了离线问题的话还是很不错的，但是好像基于web框架做的APP运行性能不如本地。不管C++还是JS我都是只看过一遍语法，也不存在什么难度不同。愁。不过幸好这个APP够我再写一个月的了，我还有比较充裕的时间考虑。

 * timestamp : 1489774776511
 * deleted : false
 * upvotes : 0
 * downvotes : 0
 * user : {"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"}
 * title : {"tid":197,"uid":7,"cid":"6","mainPid":1128,"title":"搭好了leanote了","slug":"197/搭好了leanote了","postcount":16,"deleted":false}
 * category : {"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0}
 * isMainPost : false
 * votes : 0
 * timestampISO : 2017-03-17T18:19:36.511Z
 */

open class Post private constructor(
        var pid: Int = 0,
        var uid: Int = 0,
        var tid: Int = 0,
        var content: String? = null,
        var timestamp: Long = 0,
        var isDeleted: Boolean = false,
        var upvotes: Int = 0,
        var downvotes: Int = 0,
        var votes: Int = 0,
        var timestampISO: String? = null,
        var editedISO: String? = null,
        var index: Int = 0,
        var user: User? = null,
        var editor: Any? = null,
        var isBookmarked: Boolean = false,
        var isUpvoted: Boolean = false,
        var isDownvoted: Boolean = false,
        var replies: Int = 0,
        var isSelfPost: Boolean = false,
        var isDisplay_edit_tools: Boolean = false,
        var isDisplay_delete_tools: Boolean = false,
        var isDisplay_moderator_tools: Boolean = false,
        var isDisplay_move_tools: Boolean = false,
        var isDisplay_post_menu: Boolean = false,
        var edited: Long = 0,
        var title: Title? = null,
        var category: Category? = null,
        var isIsMainPost: Boolean = false
) : Parcelable {
    /**
     * pid : 1119
     * uid : 1
     * tid : 195
     * content :
     *
     *我的
     *
     *  * 水杯
     *  * Kindle
     *  * 电车卡
     *  * 学生卡
     *  * 移动电源
     *  * 手机 x2
     *  * 各类数据线
     *  * 零钱
     *  * 一卷纸巾
     *  * 钥匙
     *

     * timestamp : 1489208654708
     * deleted : false
     * upvotes : 0
     * downvotes : 0
     * votes : 0
     * timestampISO : 2017-03-11T05:04:14.708Z
     * editedISO :
     * index : 0
     * user : {"username":"Systemd","userslug":"systemd","lastonline":1489670891717,"picture":"","fullname":"","signature":"","reputation":-1,"postcount":165,"banned":false,"status":"offline","uid":1,"groupTitle":"","icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-03-16T13:28:11.717Z","custom_profile_info":[]}
     * editor : null
     * bookmarked : false
     * upvoted : false
     * downvoted : false
     * replies : 0
     * selfPost : false
     * display_edit_tools : true
     * display_delete_tools : true
     * display_moderator_tools : true
     * display_move_tools : false
     * display_post_menu : true
     * edited : 1489378929756
     */

    protected constructor(`in`: Parcel) : this(
            pid = `in`.readInt(),
            uid = `in`.readInt(),
            tid = `in`.readInt(),
            content = `in`.readString(),
            timestamp = `in`.readLong(),
            isDeleted = `in`.readByte().toInt() != 0,
            upvotes = `in`.readInt(),
            downvotes = `in`.readInt(),
            votes = `in`.readInt(),
            timestampISO = `in`.readString(),
            editedISO = `in`.readString(),
            index = `in`.readInt(),
            user = `in`.readParcelable<User>(User::class.java.classLoader),
            isBookmarked = `in`.readByte().toInt() != 0,
            isUpvoted = `in`.readByte().toInt() != 0,
            isDownvoted = `in`.readByte().toInt() != 0,
            replies = `in`.readInt(),
            isSelfPost = `in`.readByte().toInt() != 0,
            isDisplay_edit_tools = `in`.readByte().toInt() != 0,
            isDisplay_delete_tools = `in`.readByte().toInt() != 0,
            isDisplay_moderator_tools = `in`.readByte().toInt() != 0,
            isDisplay_move_tools = `in`.readByte().toInt() != 0,
            isDisplay_post_menu = `in`.readByte().toInt() != 0,
            edited = `in`.readLong(),
            title = `in`.readParcelable<Title>(Title::class.java.classLoader),
            category = `in`.readParcelable<Category>(Category::class.java.classLoader),
            isIsMainPost = `in`.readByte().toInt() != 0
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(pid)
        dest.writeInt(uid)
        dest.writeInt(tid)
        dest.writeString(content)
        dest.writeLong(timestamp)
        dest.writeByte((if (isDeleted) 1 else 0).toByte())
        dest.writeInt(upvotes)
        dest.writeInt(downvotes)
        dest.writeInt(votes)
        dest.writeString(timestampISO)
        dest.writeString(editedISO)
        dest.writeInt(index)
        dest.writeParcelable(user, flags)
        dest.writeByte((if (isBookmarked) 1 else 0).toByte())
        dest.writeByte((if (isUpvoted) 1 else 0).toByte())
        dest.writeByte((if (isDownvoted) 1 else 0).toByte())
        dest.writeInt(replies)
        dest.writeByte((if (isSelfPost) 1 else 0).toByte())
        dest.writeByte((if (isDisplay_edit_tools) 1 else 0).toByte())
        dest.writeByte((if (isDisplay_delete_tools) 1 else 0).toByte())
        dest.writeByte((if (isDisplay_moderator_tools) 1 else 0).toByte())
        dest.writeByte((if (isDisplay_move_tools) 1 else 0).toByte())
        dest.writeByte((if (isDisplay_post_menu) 1 else 0).toByte())
        dest.writeLong(edited)
        dest.writeParcelable(title, flags)
        dest.writeParcelable(category, flags)
        dest.writeByte((if (isIsMainPost) 1 else 0).toByte())
    }

    companion object {

        val CREATOR: Parcelable.Creator<Post> = object : Parcelable.Creator<Post> {
            override fun createFromParcel(`in`: Parcel): Post {
                return Post(`in`)
            }

            override fun newArray(size: Int): Array<Post?> {
                return arrayOfNulls(size)
            }
        }
    }
}
