package org.miaowo.miaowo.bean;

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
    // 提问时间
    private String time;
    // 提问计数
    private int reply, view;
    // 提问分类
    private int type;

    public Question(long id, User user, String title, String message, String time, int reply, int view, int type) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.time = time;
        this.reply = reply;
        this.view = view;
        this.type = type;
    }

    protected Question(Parcel in) {
        id = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        title = in.readString();
        message = in.readString();
        time = in.readString();
        reply = in.readInt();
        view = in.readInt();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(user, flags);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(time);
        dest.writeInt(reply);
        dest.writeInt(view);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (reply != question.reply) return false;
        if (view != question.view) return false;
        if (type != question.type) return false;
        if (user != null ? !user.equals(question.user) : question.user != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (message != null ? !message.equals(question.message) : question.message != null)
            return false;
        return time != null ? time.equals(question.time) : question.time == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + reply;
        result = 31 * result + view;
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}