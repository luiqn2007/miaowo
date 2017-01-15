package org.miaowo.miaowo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 表示用户的bean类
 *
 * Created by lq2007 on 16-11-21.
 */

public class User implements Parcelable {
    // id
    private long id;
    // 用户名
    private String name;
    // 个性签名
    private String summary;
    // 威望
    private int authority;
    // 帖子
    private int question;
    // 浏览
    private int scan;
    // 粉丝
    private int favorite;
    private boolean isFavorite;
    // 关注
    private int focus;
    private boolean isFocus;
    // 密码
    private String pwd;
    // 喵龄
    private int age;
    // 头像地址
    private String headImg;

    // 用于测试
    public User(long id, String name, String summary, String pwd, String headImg) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.pwd = pwd;
        this.headImg = headImg;
    }

    // 用于注册
    public User(String name, String summary, String pwd) {
        this.name = name;
        this.summary = summary;
        this.pwd = pwd;
    }

    // 用于登录
    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    // 用于界面显示，为当前登录账户
    public User(long id, String name, String summary) {
        this.id = id;
        this.name = name;
        this.summary = summary;
    }

    // 用于用户详细信息
    public User(long id, String name, String summary, int authority, int question, int scan, int favorite, boolean isFavorite, int focus, boolean isFocus, int age, String headImg) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.authority = authority;
        this.question = question;
        this.scan = scan;
        this.favorite = favorite;
        this.isFavorite = isFavorite;
        this.focus = focus;
        this.isFocus = isFocus;
        this.age = age;
        this.headImg = headImg;
    }

    protected User(Parcel in) {
        id = in.readLong();
        name = in.readString();
        summary = in.readString();
        authority = in.readInt();
        question = in.readInt();
        scan = in.readInt();
        favorite = in.readInt();
        isFavorite = in.readByte() != 0;
        focus = in.readInt();
        isFocus = in.readByte() != 0;
        pwd = in.readString();
        age = in.readInt();
        headImg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(summary);
        dest.writeInt(authority);
        dest.writeInt(question);
        dest.writeInt(scan);
        dest.writeInt(favorite);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeInt(focus);
        dest.writeByte((byte) (isFocus ? 1 : 0));
        dest.writeString(pwd);
        dest.writeInt(age);
        dest.writeString(headImg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public int getScan() {
        return scan;
    }

    public void setScan(int scan) {
        this.scan = scan;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
