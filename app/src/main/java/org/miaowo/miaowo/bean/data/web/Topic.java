package org.miaowo.miaowo.bean.data.web;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable {
    /**
     * value : 猜猜我是谁
     * score : 2
     * color :
     * bgColor :
     */

    private String value;
    private int score;
    private String color;
    private String bgColor;

    protected Topic(Parcel in) {
        value = in.readString();
        score = in.readInt();
        color = in.readString();
        bgColor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeInt(score);
        dest.writeString(color);
        dest.writeString(bgColor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
