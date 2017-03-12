package org.miaowo.miaowo.bean.data;

import com.google.gson.annotations.SerializedName;

/**
 * 表示用户的bean类
 * Created by lq2007 on 16-11-21.
 */

public class User {
    public long joindate;
    public long lastonline;
    public long lastposttime;
    public String username;
    public String userslug;
    public String email;
    public String picture;
    public String status;
    public String signature;
    @SerializedName("icon:text")
    public String iconText;
    @SerializedName("icon:bgColor")
    public String iconBgColor;
    public int reputation;
    public int uid;
    public int postcount;
    public int banned;
    public int profileviews;
    public int followerCount;
    public int followingCount;
    @SerializedName("email:confirmed")
    public boolean emailConfirmed;
    public boolean isAdmin;
    public boolean isGlobalMod;
    public boolean isMod;
    public boolean isEmailConfirmSent;

    public String password;

    public User() {}
    public User(User u) {
        this.username = u.username;
        this.userslug = u.userslug;
        this.email = u.email;
        this.picture = u.picture;
        this.status = u.status;
        this.signature = u.signature;
        this.iconText = u.iconText;
        this.iconBgColor = u.iconBgColor;
        this.reputation = u.reputation;
        this.uid = u.uid =
        this.postcount = u.postcount;
        this.banned = u.banned;
        this.emailConfirmed = u.emailConfirmed;
        this.isAdmin = u.isAdmin;
        this.isGlobalMod = u.isGlobalMod;
        this.isMod = u.isMod;
        this.isEmailConfirmSent = u.isEmailConfirmSent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uid != user.uid) return false;
        return username != null ? username.equals(user.username) : user.username == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + uid;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userslug='" + userslug + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", status='" + status + '\'' +
                ", iconText='" + iconText + '\'' +
                ", iconBgColor='" + iconBgColor + '\'' +
                ", reputation=" + reputation +
                ", uid=" + uid +
                ", emailConfirmed=" + emailConfirmed +
                ", isAdmin=" + isAdmin +
                ", isGlobalMod=" + isGlobalMod +
                ", isMod=" + isMod +
                ", isEmailConfirmSent=" + isEmailConfirmSent +
                '}';
    }
}
