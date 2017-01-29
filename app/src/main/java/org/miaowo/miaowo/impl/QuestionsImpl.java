package org.miaowo.miaowo.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.VersionMessage;
import org.miaowo.miaowo.impl.interfaces.Questions;
import org.miaowo.miaowo.test.DbUtil;
import org.miaowo.miaowo.test.QuestionDBHelper;

/**
 * {@link Questions} 的具体实现类
 * Created by lq2007 on 16-11-21.
 */

public class QuestionsImpl implements Questions {

    @Override
    public Question[] checkQuestions(String type, int position, int count, long time) throws Exception {
        Question[] items;
        SQLiteDatabase questionDb = (new QuestionDBHelper()).getReadableDatabase();
        Cursor cursor = null;
        switch (position) {
            case C.LF_POSITION_DOWN:
                cursor = questionDb.query(QuestionDBHelper.table, QuestionDBHelper.getCumns,
                        QuestionDBHelper.TYPE + " = ? and " + QuestionDBHelper.TIME + " > ? ",
                        new String[]{type, Long.toString(time)}, null, null, null);
                break;
            case C.LF_POSITION_UP:
                cursor = questionDb.query(QuestionDBHelper.table, QuestionDBHelper.getCumns,
                        QuestionDBHelper.TYPE + " = ? ",
                        new String[]{type}, null, null, null);
                break;
        }
        items = DbUtil.parseQuestions(cursor, count);
        cursor.close();
        questionDb.close();
        return items;
    }

    @Override
    public void sendQuestion(Question question) throws Exception {
        SQLiteDatabase questionDb = (new QuestionDBHelper()).getWritableDatabase();
        questionDb.insert(QuestionDBHelper.table, QuestionDBHelper.ID, DbUtil.convert(question));
        questionDb.close();
    }

    @Override
    public Question[] searchQuestion(String key) {
        Question[] questions;
        SQLiteDatabase questionDb = (new QuestionDBHelper()).getReadableDatabase();
        Cursor cursor = questionDb.query(QuestionDBHelper.table, QuestionDBHelper.getCumns,
                QuestionDBHelper.TITLE + " like ? and " + QuestionDBHelper.USER + " > 0 ",
                new String[]{"%" + key + "%"}, null, null, null);
        questions = DbUtil.parseQuestions(cursor, -1);
        cursor.close();
        questionDb.close();
        return questions;
    }

    @Override
    public Question[] searchTopic(String key) {
        Question[] questions;
        SQLiteDatabase questionDb = (new QuestionDBHelper()).getReadableDatabase();
        Cursor cursor = questionDb.query(QuestionDBHelper.table, QuestionDBHelper.getCumns,
                QuestionDBHelper.TITLE + " like ? and " + QuestionDBHelper.USER + " < 0 ",
                new String[]{"%" + key + "%"}, null, null, null);
        questions = DbUtil.parseQuestions(cursor, -1);
        cursor.close();
        questionDb.close();
        return questions;
    }

    @Override
    public Question getQuestion(int id) {
        Question[] questions;
        SQLiteDatabase questionDb = (new QuestionDBHelper()).getReadableDatabase();
        Cursor cursor = questionDb.query(QuestionDBHelper.table, QuestionDBHelper.getCumns,
                QuestionDBHelper.ID + " =? ",
                new String[]{Integer.toString(id)}, null, null, null);
        questions = DbUtil.parseQuestions(cursor, -1);
        cursor.close();
        questionDb.close();
        if (questions.length == 0) return null;
        return questions[0];
    }

    @Override
    public VersionMessage getUpdateMessage(int version) {
        String versionName, versionMessage;
        versionName = "喵窝 单机版";
        versionMessage = "" +
                "喵窝单机测试版，喵们拿去自娱自乐吧\n" +
                "哪里丑爆了，哪里有bug，别忘了反馈给我哦\n" +
                "就用那个悬浮球就能反馈，多谢了";
        return new VersionMessage(1, versionName, versionMessage, null);
    }

}
