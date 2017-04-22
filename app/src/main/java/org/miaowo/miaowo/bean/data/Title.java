package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Title implements Parcelable {
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
     * teaser : {"pid":1101,"uid":91,"tid":187,"content":"<p>加油↖(^ω^)↗<\/p>\n","timestamp":1488896525661,"user":{"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"},"timestampISO":"2017-03-07T14:22:05.661Z","index":4}
     * tags : []
     * isOwner : true
     * ignored : false
     * unread : false
     * bookmark : null
     * unreplied : false
     * icons : []
     * index : 0
     */

    private int tid;
    private int uid;
    private String cid;
    private String mainPid;
    private String title;
    private String slug;
    private long timestamp;
    private long lastposttime;
    private int postcount;
    private int viewcount;
    private boolean locked;
    private boolean deleted;
    private boolean pinned;
    private String teaserPid;
    private String thumb;
    private String titleRaw;
    private String timestampISO;
    private String lastposttimeISO;
    private Category category;
    private User user;
    private Teaser teaser;
    private boolean isOwner;
    private boolean ignored;
    private boolean unread;
    private Object bookmark;
    private boolean unreplied;
    private int index;
    private List<?> tags;
    private List<?> icons;

    protected Title(Parcel in) {
        tid = in.readInt();
        uid = in.readInt();
        cid = in.readString();
        mainPid = in.readString();
        title = in.readString();
        slug = in.readString();
        timestamp = in.readLong();
        lastposttime = in.readLong();
        postcount = in.readInt();
        viewcount = in.readInt();
        locked = in.readByte() != 0;
        deleted = in.readByte() != 0;
        pinned = in.readByte() != 0;
        teaserPid = in.readString();
        thumb = in.readString();
        titleRaw = in.readString();
        timestampISO = in.readString();
        lastposttimeISO = in.readString();
        category = in.readParcelable(Category.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
        teaser = in.readParcelable(Teaser.class.getClassLoader());
        isOwner = in.readByte() != 0;
        ignored = in.readByte() != 0;
        unread = in.readByte() != 0;
        unreplied = in.readByte() != 0;
        index = in.readInt();
    }

    public static final Creator<Title> CREATOR = new Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel in) {
            return new Title(in);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMainPid() {
        return mainPid;
    }

    public void setMainPid(String mainPid) {
        this.mainPid = mainPid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLastposttime() {
        return lastposttime;
    }

    public void setLastposttime(long lastposttime) {
        this.lastposttime = lastposttime;
    }

    public int getPostcount() {
        return postcount;
    }

    public void setPostcount(int postcount) {
        this.postcount = postcount;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public String getTeaserPid() {
        return teaserPid;
    }

    public void setTeaserPid(String teaserPid) {
        this.teaserPid = teaserPid;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitleRaw() {
        return titleRaw;
    }

    public void setTitleRaw(String titleRaw) {
        this.titleRaw = titleRaw;
    }

    public String getTimestampISO() {
        return timestampISO;
    }

    public void setTimestampISO(String timestampISO) {
        this.timestampISO = timestampISO;
    }

    public String getLastposttimeISO() {
        return lastposttimeISO;
    }

    public void setLastposttimeISO(String lastposttimeISO) {
        this.lastposttimeISO = lastposttimeISO;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Teaser getTeaser() {
        return teaser;
    }

    public void setTeaser(Teaser teaser) {
        this.teaser = teaser;
    }

    public boolean isIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Object getBookmark() {
        return bookmark;
    }

    public void setBookmark(Object bookmark) {
        this.bookmark = bookmark;
    }

    public boolean isUnreplied() {
        return unreplied;
    }

    public void setUnreplied(boolean unreplied) {
        this.unreplied = unreplied;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public List<?> getIcons() {
        return icons;
    }

    public void setIcons(List<?> icons) {
        this.icons = icons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tid);
        dest.writeInt(uid);
        dest.writeString(cid);
        dest.writeString(mainPid);
        dest.writeString(title);
        dest.writeString(slug);
        dest.writeLong(timestamp);
        dest.writeLong(lastposttime);
        dest.writeInt(postcount);
        dest.writeInt(viewcount);
        dest.writeByte((byte) (locked ? 1 : 0));
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeByte((byte) (pinned ? 1 : 0));
        dest.writeString(teaserPid);
        dest.writeString(thumb);
        dest.writeString(titleRaw);
        dest.writeString(timestampISO);
        dest.writeString(lastposttimeISO);
        dest.writeParcelable(category, flags);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(teaser, flags);
        dest.writeByte((byte) (isOwner ? 1 : 0));
        dest.writeByte((byte) (ignored ? 1 : 0));
        dest.writeByte((byte) (unread ? 1 : 0));
        dest.writeByte((byte) (unreplied ? 1 : 0));
        dest.writeInt(index);
    }
}
