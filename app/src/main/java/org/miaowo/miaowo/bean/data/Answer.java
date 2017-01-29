package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 回答
 * Created by luqin on 16-12-28.
 */
public class Answer implements Parcelable {
    // id
    private int id;
    // 回答
    private Question question;
    // 回复
    private Answer reply;
    // 内容
    private String message;
    // 回答用户
    private User user;
    // 时间
    private long time;

    // 回复
    public Answer(int id, Question question, Answer reply, String message, User user, long time) {
        this.id = id;
        this.question = question;
        this.reply = reply;
        this.message = message;
        this.user = user;
        this.time = time;
    }

    protected Answer(Parcel in) {
        id = in.readInt();
        question = in.readParcelable(org.miaowo.miaowo.bean.data.Question.class.getClassLoader());
        reply = in.readParcelable(Answer.class.getClassLoader());
        message = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        time = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(question, flags);
        dest.writeParcelable(reply, flags);
        dest.writeString(message);
        dest.writeParcelable(user, flags);
        dest.writeLong(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public org.miaowo.miaowo.bean.data.Question getQuestion() {
        return question;
    }

    public void setQuestion(org.miaowo.miaowo.bean.data.Question question) {
        this.question = question;
    }

    public Answer getReply() {
        return reply;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id == answer.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
