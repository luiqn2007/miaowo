package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 表示问题的bean类
 *
 * Created by lq2007 on 16-11-21.
 */

public class Question implements Parcelable {

    // ID
    private int id;
    // 提问人
    private User user;
    // 问题题目
    private String title;
    // 问题内容
    private String message;
    // 提问时间
    private long time;
    // 提问计数
    private int reply, view;
    // 提问分类
    private String type;

    public Question(int id, User user, String title, String message, long time, int reply, int view, String type) {
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
        id = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        title = in.readString();
        message = in.readString();
        type = in.readString();
        time = in.readLong();
        reply = in.readInt();
        view = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(user, flags);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(type);
        dest.writeLong(time);
        dest.writeInt(reply);
        dest.writeInt(view);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getReply() {
        return reply;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id == question.id
                && time == question.time
                && reply == question.reply
                && view == question.view
                && (user != null ? user.equals(question.user) : question.user == null
                && (title != null ? title.equals(question.title) : question.title == null
                && (message != null ? message.equals(question.message) : question.message == null
                && (type != null ? type.equals(question.type) : question.type == null))));

    }

    @Override
    public int hashCode() {
        int result = id ^ (id >>> 32);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + reply;
        result = 31 * result + view;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", reply=" + reply +
                ", view=" + view +
                ", type='" + type + '\'' +
                '}';
    }
}