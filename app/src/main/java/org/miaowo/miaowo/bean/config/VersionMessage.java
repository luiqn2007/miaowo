package org.miaowo.miaowo.bean.config;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 软件版本信息
 * Created by lq2007 on 16-11-26.
 */

public class VersionMessage implements Parcelable {
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

    protected VersionMessage(Parcel in) {
        version = in.readInt();
        versionName = in.readString();
        message = in.readString();
        url = in.readString();
    }

    public static final Creator<VersionMessage> CREATOR = new Creator<VersionMessage>() {
        @Override
        public VersionMessage createFromParcel(Parcel in) {
            return new VersionMessage(in);
        }

        @Override
        public VersionMessage[] newArray(int size) {
            return new VersionMessage[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(version);
        dest.writeString(versionName);
        dest.writeString(message);
        dest.writeString(url);
    }
}
