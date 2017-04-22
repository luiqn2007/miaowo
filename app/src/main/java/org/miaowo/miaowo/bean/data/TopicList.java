package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TopicList implements Parcelable {

    /**
     * tags : [{"value":"猜猜我是谁","score":2,"color":"","bgColor":""},{"value":"好奇喵app","score":1,"color":"","bgColor":""},{"value":"这是测试","score":0,"color":"","bgColor":""}]
     * nextStart : 100
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[tags:tags]]"}]
     * title : [[pages:tags]]
     * loggedIn : true
     * relative_path :
     * template : {"name":"tags","tags":true}
     * url : /tags
     * bodyClass : page-tags
     */

    private int nextStart;
    private String title;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<Topic> tags;

    protected TopicList(Parcel in) {
        nextStart = in.readInt();
        title = in.readString();
        loggedIn = in.readByte() != 0;
        relative_path = in.readString();
        url = in.readString();
        bodyClass = in.readString();
        tags = in.createTypedArrayList(Topic.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nextStart);
        dest.writeString(title);
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeString(relative_path);
        dest.writeString(url);
        dest.writeString(bodyClass);
        dest.writeTypedList(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopicList> CREATOR = new Creator<TopicList>() {
        @Override
        public TopicList createFromParcel(Parcel in) {
            return new TopicList(in);
        }

        @Override
        public TopicList[] newArray(int size) {
            return new TopicList[size];
        }
    };

    public int getNextStart() {
        return nextStart;
    }

    public void setNextStart(int nextStart) {
        this.nextStart = nextStart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRelative_path() {
        return relative_path;
    }

    public void setRelative_path(String relative_path) {
        this.relative_path = relative_path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass = bodyClass;
    }

    public List<Topic> getTags() {
        return tags;
    }

    public void setTags(List<Topic> tags) {
        this.tags = tags;
    }
}
