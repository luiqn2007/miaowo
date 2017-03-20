package org.miaowo.miaowo.bean.data.web;

import com.google.gson.annotations.SerializedName;

import org.miaowo.miaowo.bean.data.User;

/**
 * 嵌入网页的用户信息
 * Created by luqin on 17-3-18.
 */

public class InnerUser implements User {

    /**
     * username : 么么么喵
     * userslug : 么么么喵
     * email : 1105188240@qq.com
     * picture : /uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg
     * reputation : 1
     * status : online
     * uid : 7
     * email:confirmed : false
     * icon:text : 么
     * icon:bgColor : #1b5e20
     * isAdmin : true
     * isGlobalMod : false
     * isMod : false
     * isEmailConfirmSent : false
     */

    private String username;
    private String userslug;
    private String email;
    private String picture;
    private int reputation;
    private String status;
    private int uid;
    @SerializedName("email:confirmed")
    private boolean emailConfirme;
    @SerializedName("icon:text")
    private String iconText;
    @SerializedName("icon:bgColor")
    private String iconBgColor;
    private boolean isAdmin;
    private boolean isGlobalMod;
    private boolean isMod;
    private boolean isEmailConfirmSent;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserslug() {
        return userslug;
    }

    public void setUserslug(String userslug) {
        this.userslug = userslug;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    @Override
    public boolean emailConfirmed() {
        return false;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isEmailConfirme() {
        return emailConfirme;
    }

    public void setEmailConfirme(boolean emailConfirme) {
        this.emailConfirme = emailConfirme;
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }

    public String getIconBgColor() {
        return iconBgColor;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public int getPostcount() {
        return 0;
    }

    public void setIconBgColor(String iconBgColor) {
        this.iconBgColor = iconBgColor;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsGlobalMod() {
        return isGlobalMod;
    }

    public void setIsGlobalMod(boolean isGlobalMod) {
        this.isGlobalMod = isGlobalMod;
    }

    public boolean isIsMod() {
        return isMod;
    }

    public void setIsMod(boolean isMod) {
        this.isMod = isMod;
    }

    public boolean isIsEmailConfirmSent() {
        return isEmailConfirmSent;
    }

    public void setIsEmailConfirmSent(boolean isEmailConfirmSent) {
        this.isEmailConfirmSent = isEmailConfirmSent;
    }

    @Override
    public String toString() {
        return "InnerUser{" +
                "username='" + username + '\'' +
                ", userslug='" + userslug + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", reputation=" + reputation +
                ", status='" + status + '\'' +
                ", uid=" + uid +
                ", emailConfirme=" + emailConfirme +
                ", iconText='" + iconText + '\'' +
                ", iconBgColor='" + iconBgColor + '\'' +
                ", isAdmin=" + isAdmin +
                ", isGlobalMod=" + isGlobalMod +
                ", isMod=" + isMod +
                ", isEmailConfirmSent=" + isEmailConfirmSent +
                '}';
    }
}
