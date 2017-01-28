package org.miaowo.miaowo.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.AnswersImpl;
import org.miaowo.miaowo.impl.QuestionsImpl;
import org.miaowo.miaowo.impl.UsersImpl;

/**
 * 数据库读写辅助类
 * Created by luqin on 17-1-25.
 */

public class DbUtil {

    public static ContentValues convert(User u) {
        ContentValues user = new ContentValues();
        user.put(UserDBHelper.NAME, u.getName());
        user.put(UserDBHelper.PWD, u.getPwd());
        user.put(UserDBHelper.SUMMARY, u.getSummary());
        user.put(UserDBHelper.HEAD, u.getHeadImg());
        user.put(UserDBHelper.DATE, u.getAge());
        user.put(UserDBHelper.AUTHORITY, u.getAuthority());
        user.put(UserDBHelper.QUESTION, u.getQuestion());
        user.put(UserDBHelper.SCAN, u.getScan());
        user.put(UserDBHelper.FOCUS_ME, toString(u.getFocusMe()));
        user.put(UserDBHelper.FOCUS, toString(u.getFocus()));
        return user;
    }
    public static ContentValues convert(Answer a) {
        ContentValues answer = new ContentValues();
        answer.put(AnswerDBHelper.MESSAGE, a.getMessage());
        answer.put(AnswerDBHelper.QUESTION, a.getQuestion().getId());
        answer.put(AnswerDBHelper.REPLY, a.getReply() == null ? -1 : a.getReply().getId());
        answer.put(AnswerDBHelper.TIME, a.getTime());
        answer.put(AnswerDBHelper.USER, a.getUser().getId());
        return answer;
    }
    public static ContentValues convert(Question q) {
        ContentValues question = new ContentValues();
        question.put(QuestionDBHelper.MSG, q.getMessage());
        question.put(QuestionDBHelper.REPLY, q.getReply());
        question.put(QuestionDBHelper.TIME, q.getTime());
        question.put(QuestionDBHelper.TITLE, q.getTitle());
        question.put(QuestionDBHelper.TYPE, q.getType());
        question.put(QuestionDBHelper.USER, q.getUser().getId());
        question.put(QuestionDBHelper.VIEW, q.getView());
        return question;
    }
    public static ContentValues convert(ChatMessage cm) {
        ContentValues message = new ContentValues();
        message.put(ChatMessageDBHelper.TO, cm.getTo().getId());
        message.put(ChatMessageDBHelper.TIME, cm.getTime());
        message.put(ChatMessageDBHelper.MESSAGE, cm.getMessage());
        message.put(ChatMessageDBHelper.FROM, cm.getFrom().getId());
        return message;
    }

    public static User[] parseUser(Cursor cursor) {
        User[] users = new User[cursor.getCount()];
        int i = 0;
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(UserDBHelper.ID));
            String name = cursor.getString(cursor.getColumnIndex(UserDBHelper.NAME));
            String pwd = cursor.getString(cursor.getColumnIndex(UserDBHelper.PWD));
            String summary = cursor.getString(cursor.getColumnIndex(UserDBHelper.SUMMARY));
            String head = cursor.getString(cursor.getColumnIndex(UserDBHelper.HEAD));
            long time = cursor.getLong(cursor.getColumnIndex(UserDBHelper.DATE));
            int authority = cursor.getInt(cursor.getColumnIndex(UserDBHelper.AUTHORITY));
            int question = cursor.getInt(cursor.getColumnIndex(UserDBHelper.QUESTION));
            int scan = cursor.getInt(cursor.getColumnIndex(UserDBHelper.SCAN));
            String favorite = cursor.getString(cursor.getColumnIndex(UserDBHelper.FOCUS_ME));
            String focus = cursor.getString(cursor.getColumnIndex(UserDBHelper.FOCUS));
            users[i] = new User(
                    id, name, pwd, summary, authority, question, scan, toIntArray(favorite), toIntArray(focus), time, head
            );
            i++;
        }
        return users;
    }
    public static Question[] parseQuestions(Cursor cursor, int count) {
        int relCount = cursor.getCount();
        if (count >= 1) relCount = relCount >= count ? count : relCount;
        Question[] questions = new Question[relCount];
        int i = 0;
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(QuestionDBHelper.ID));
            String message = cursor.getString(cursor.getColumnIndex(QuestionDBHelper.MSG));
            int reply = cursor.getInt(cursor.getColumnIndex(QuestionDBHelper.REPLY));
            long time = cursor.getLong(cursor.getColumnIndex(QuestionDBHelper.TIME));
            String title = cursor.getString(cursor.getColumnIndex(QuestionDBHelper.TITLE));
            String type = cursor.getString(cursor.getColumnIndex(QuestionDBHelper.TYPE));
            int userId = cursor.getInt(cursor.getColumnIndex(QuestionDBHelper.USER));
            int view = cursor.getInt(cursor.getColumnIndex(QuestionDBHelper.VIEW));

            User u = (new UsersImpl()).getUser(userId);
            questions[i] = new Question(id, u, title, message, time, reply, view, type);
            i++;

            if (i == questions.length) {
                return questions;
            }
        }
        return questions;
    }
    public static Answer[] parseAnswers(Cursor cursor) {
        Answer[] answers = new Answer[cursor.getCount()];
        int i = 0;
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(AnswerDBHelper.ID));
            String message = cursor.getString(cursor.getColumnIndex(AnswerDBHelper.MESSAGE));
            int questionId = cursor.getInt(cursor.getColumnIndex(AnswerDBHelper.QUESTION));
            int replyId = cursor.getInt(cursor.getColumnIndex(AnswerDBHelper.REPLY));
            long time = cursor.getLong(cursor.getColumnIndex(AnswerDBHelper.TIME));
            int userId = cursor.getInt(cursor.getColumnIndex(AnswerDBHelper.USER));

            Question q = (new QuestionsImpl()).getQuestion(questionId);
            User u = (new UsersImpl()).getUser(userId);
            Answer a = null;
            if (replyId >= 0) {
                a = (new AnswersImpl()).getAnswer(replyId);
            }
            answers[i] = new Answer(id, q, a, message, u, time);
            i++;
        }
        return answers;
    }
    public static ChatMessage[] parseChatMessage(Cursor cursor) {
        ChatMessage[] messages = new ChatMessage[cursor.getCount()];
        int i = 0;
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ChatMessageDBHelper.ID));
            int fromId = cursor.getInt(cursor.getColumnIndex(ChatMessageDBHelper.FROM));
            int toId = cursor.getInt(cursor.getColumnIndex(ChatMessageDBHelper.TO));
            String message = cursor.getString(cursor.getColumnIndex(ChatMessageDBHelper.MESSAGE));
            long time = cursor.getLong(cursor.getColumnIndex(ChatMessageDBHelper.TIME));

            User from = (new UsersImpl()).getUser(fromId);
            User to = (new UsersImpl()).getUser(toId);
            messages[i] = new ChatMessage(id, time, from, to, message);
            i++;
        }
        return messages;
    }

    public static String toString(int[] arr) {
        if (arr.length == 0) {
            return "";
        }
        if (arr.length == 1) {
            return Integer.toString(arr[0]);
        }
        StringBuilder sb = new StringBuilder(Integer.toString(arr[0]));
        for (int i = 1; i < arr.length; i++) {
            sb.append(",");
            sb.append(Integer.toString(i));
        }
        return sb.toString();
    }

    private static int[] toIntArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return new int[0];
        }
        String[] split = str.split(",");
        int[] ret = new int[split.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.valueOf(split[i]);
        }
        return ret;
    }
}
