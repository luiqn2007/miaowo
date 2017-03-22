package org.miaowo.miaowo.bean.data.web;

import android.os.Parcel;
import android.os.Parcelable;

public class Teaser implements Parcelable {
    /**
     * pid : 1101
     * uid : 91
     * tid : 187
     * content : <p>加油↖(^ω^)↗</p>

     * timestamp : 1488896525661
     * user : {"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"}
     * timestampISO : 2017-03-07T14:22:05.661Z
     * index : 4
     */

    private int pid;
    private int uid;
    private int tid;
    private String content;
    private long timestamp;
    private User user;
    private String timestampISO;
    private int index;

    protected Teaser(Parcel in) {
        pid = in.readInt();
        uid = in.readInt();
        tid = in.readInt();
        content = in.readString();
        timestamp = in.readLong();
        timestampISO = in.readString();
        index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeInt(uid);
        dest.writeInt(tid);
        dest.writeString(content);
        dest.writeLong(timestamp);
        dest.writeString(timestampISO);
        dest.writeInt(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Teaser> CREATOR = new Creator<Teaser>() {
        @Override
        public Teaser createFromParcel(Parcel in) {
            return new Teaser(in);
        }

        @Override
        public Teaser[] newArray(int size) {
            return new Teaser[size];
        }
    };

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimestampISO() {
        return timestampISO;
    }

    public void setTimestampISO(String timestampISO) {
        this.timestampISO = timestampISO;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
