package org.miaowo.miaowo.bean.config;

/**
 * 软件版本信息
 * Created by lq2007 on 16-11-26.
 */

public class VersionMessage {
    // 版本代号
    private int version;
    // 当前版本
    private String versionName;
    // 版本介绍
    private String message;
    // 下载地址
    private String url;

    public VersionMessage(int version, String versionName, String message, String url) {
        this.version = version;
        this.versionName = versionName;
        this.message = message;
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
