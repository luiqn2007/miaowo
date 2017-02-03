package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * 表示用户的bean类
 * Created by lq2007 on 16-11-21.
 */

public class User implements Parcelable {
    // id
    private int id;
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
    private int[] focusMe;
    // 关注
    private int[] focus;
    // 密码
    private String pwd;
    // 喵龄
    private long age;
    // 头像地址
    private String headImg;

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
    public User(int id, String name, String summary) {
        this.id = id;
        this.name = name;
        this.summary = summary;
    }

    public User(int id, String name, String pwd, String summary, int authority, int question, int scan, int[] focusMe, int[] focus, long age, String headImg) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.summary = summary;
        this.authority = authority;
        this.question = question;
        this.scan = scan;
        this.focusMe = focusMe;
        this.focus = focus;
        this.age = age;
        this.headImg = headImg;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        summary = in.readString();
        authority = in.readInt();
        question = in.readInt();
        scan = in.readInt();
        focusMe = in.createIntArray();
        focus = in.createIntArray();
        pwd = in.readString();
        age = in.readLong();
        headImg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(summary);
        dest.writeInt(authority);
        dest.writeInt(question);
        dest.writeInt(scan);
        dest.writeIntArray(focusMe);
        dest.writeIntArray(focus);
        dest.writeString(pwd);
        dest.writeLong(age);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getAuthority() {
        return authority;
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

    public int[] getFocusMe() {
        return focusMe;
    }

    public void setFocusMe(int[] focusMe) {
        this.focusMe = focusMe;
    }

    public int[] getFocus() {
        return focus;
    }

    public void setFocus(int[] focus) {
        this.focus = focus;
    }

    public String getPwd() {
        return pwd;
    }

    public long getAge() {
        return age;
    }

    public String getHeadImg() {
        return headImg;
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
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", authority=" + authority +
                ", question=" + question +
                ", scan=" + scan +
                ", focusMe=" + Arrays.toString(focusMe) +
                ", focus=" + Arrays.toString(focus) +
                ", pwd='" + pwd + '\'' +
                ", age=" + age +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
