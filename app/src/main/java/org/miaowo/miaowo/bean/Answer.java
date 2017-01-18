package org.miaowo.miaowo.bean;

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
    private Question Question;
    // 回复
    private Answer reply;
    // 内容
    private String content;
    // 回答用户
    private User user;
    // 时间
    private long time;

    // 回复
    public Answer(int id, Answer reply, String content, User user, long time) {
        this.id = id;
        this.reply = reply;
        this.content = content;
        this.user = user;
        this.time = time;
    }

    public Answer(int id, Question question, String content, User user, long time) {
        this.id = id;
        Question = question;
        this.content = content;
        this.user = user;
        this.time = time;
    }

    protected Answer(Parcel in) {
        id = in.readInt();
        Question = in.readParcelable(org.miaowo.miaowo.bean.Question.class.getClassLoader());
        reply = in.readParcelable(Answer.class.getClassLoader());
        content = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        time = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(Question, flags);
        dest.writeParcelable(reply, flags);
        dest.writeString(content);
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

    public org.miaowo.miaowo.bean.Question getQuestion() {
        return Question;
    }

    public void setQuestion(org.miaowo.miaowo.bean.Question question) {
        Question = question;
    }

    public Answer getReply() {
        return reply;
    }

    public void setReply(Answer reply) {
        this.reply = reply;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

        if (id != answer.id) return false;
        if (time != answer.time) return false;
        if (Question != null ? !Question.equals(answer.Question) : answer.Question != null)
            return false;
        if (reply != null ? !reply.equals(answer.reply) : answer.reply != null) return false;
        if (!content.equals(answer.content)) return false;
        return user.equals(answer.user);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (Question != null ? Question.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        result = 31 * result + content.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }
}
