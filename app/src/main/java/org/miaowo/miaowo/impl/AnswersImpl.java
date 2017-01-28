package org.miaowo.miaowo.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.impl.interfaces.Answers;
import org.miaowo.miaowo.test.AnswerDBHelper;
import org.miaowo.miaowo.test.DbUtil;
import org.miaowo.miaowo.test.QuestionDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 回答/回复操作 实现类
 * Created by luqin on 17-1-26.
 */

public class  AnswersImpl implements Answers {
    @Override
    public Answer getAnswer(int id) {
        SQLiteDatabase answerDb = (new AnswerDBHelper()).getReadableDatabase();
        Cursor cursor = answerDb.query(AnswerDBHelper.table, AnswerDBHelper.getCumns,
                AnswerDBHelper.ID + " = ? ",
                new String[]{Integer.toString(id)}, null, null, null);
        Answer[] answers = DbUtil.parseAnswers(cursor);
        cursor.close();
        answerDb.close();

        if (answers.length == 0) return null;
        return answers[0];
    }

    @Override
    public void sendAnswer(Answer answer) throws Exception {
        SQLiteDatabase answerDb = (new AnswerDBHelper()).getWritableDatabase();
        answerDb.insert(AnswerDBHelper.table, AnswerDBHelper.ID, DbUtil.convert(answer));
        answerDb.close();

        SQLiteDatabase questionDb = (new QuestionDBHelper()).getWritableDatabase();
        Question q = (new AnswersImpl()).getFinalAnswer(answer).getQuestion();
        ContentValues cv = new ContentValues();
        cv.put(QuestionDBHelper.REPLY, q.getReply() + 1);
        questionDb.update(QuestionDBHelper.table, cv,
                QuestionDBHelper.ID + " = ? ", new String[]{Integer.toString(q.getId())});
        questionDb.close();
    }

    @Override
    public HashMap<Answer, ArrayList<Answer>> getAnswers(Question question) throws Exception {
        SQLiteDatabase answerDb = (new AnswerDBHelper()).getReadableDatabase();
        Cursor cursor = answerDb.query(AnswerDBHelper.table, AnswerDBHelper.getCumns,
                AnswerDBHelper.QUESTION + " = ? ",
                new String[]{Integer.toString(question.getId())}, null, null, null);
        Answer[] all = DbUtil.parseAnswers(cursor);
        cursor.close();
        answerDb.close();

        ArrayList<Answer> answers = new ArrayList<>();
        ArrayList<Answer> replies = new ArrayList<>();
        for (Answer answer : all) {
            if (answer.getReply() == null) answers.add(answer);
            else replies.add(answer);
        }
        HashMap<Answer, ArrayList<Answer>> answerArrayListHashMap = new HashMap<>();
        for (Answer answer : answers) {
            answerArrayListHashMap.put(answer, new ArrayList<>());
        }
        for (Answer reply : replies) {
            Answer finalAnswer = getFinalAnswer(reply);
            ArrayList<Answer> answerArrayList = answerArrayListHashMap.get(finalAnswer);
            answerArrayList.add(reply);
            answerArrayListHashMap.put(finalAnswer, answerArrayList);
        }
        return answerArrayListHashMap;
    }

    @Override
    public Answer getFinalAnswer(Answer answer) {
        Answer finalAnswer = answer;
        while (finalAnswer.getReply() != null) {
            finalAnswer = finalAnswer.getReply();
        }
        return finalAnswer;
    }

}
