package org.miaowo.miaowo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 表示问题的bean类
 *
 * Created by lq2007 on 16-11-21.
 */

public class Question implements Parcelable {
    // ID
    private long id;
    // 提问人
    private User user;
    // 问题题目
    private String title;
    // 问题内容
    private String message;
    // 回复条目
    private ArrayList<Answer> answers;
    // 提问时间
    private String time;

    public Question(long id, User user, String title, String message, ArrayList<Answer> answers, String time) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.answers = answers;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<Question> getCREATOR() {
        return CREATOR;
    }

    protected Question(Parcel in) {
        id = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        title = in.readString();
        message = in.readString();
        time = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(user, flags);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(time);
    }
}