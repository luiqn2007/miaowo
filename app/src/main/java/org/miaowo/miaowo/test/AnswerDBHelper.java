package org.miaowo.miaowo.test;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.miaowo.miaowo.D;

/**
 * 存放用户信息
 * Created by luqin on 17-1-25.
 */

public class AnswerDBHelper extends SQLiteOpenHelper {
    public static String table = "answer";

    public static String ID = "_id";
    public static String QUESTION = "question";
    public static String REPLY = "reply";
    public static String MESSAGE = "message";
    public static String USER = "user";
    public static String TIME = "time";
    public static String[] getCumns = new String[]{ID, QUESTION, REPLY, MESSAGE, USER, TIME};

    public AnswerDBHelper() {
        super(D.getInstance().activeActivity, table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + table + " (" +
                        ID + " integer primary key autoincrement, " +
                        QUESTION + " integer, " +
                        REPLY + " integer, " +
                        MESSAGE + " text, " +
                        USER + " integer, " +
                        TIME + " bigint);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table);
        onCreate(db);
    }
}
