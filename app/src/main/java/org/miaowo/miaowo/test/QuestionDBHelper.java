package org.miaowo.miaowo.test;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.miaowo.miaowo.D;

/**
 * 存放用户信息
 * Created by luqin on 17-1-25.
 */

public class QuestionDBHelper extends SQLiteOpenHelper {
    public static String table = "question";

    public static String ID = "_id";
    public static String USER = "user";
    public static String TITLE = "title";
    public static String MSG = "message";
    public static String TIME = "time";
    public static String REPLY = "reply";
    public static String VIEW = "view";
    public static String TYPE = "type";
    public static String[] getCumns = new String[]{ID, USER, TITLE, MSG, TIME, REPLY, VIEW, TYPE};

    public QuestionDBHelper() {
        super(D.getInstance().activeActivity, table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + table + " (" +
                        ID + " integer primary key autoincrement, " +
                        USER + " integer, " +
                        TITLE + " varchar(20), " +
                        MSG + " text, " +
                        TIME + " bigint, " +
                        REPLY + " integer, " +
                        VIEW + " integer, " +
                        TYPE  + " varchar(20));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table);
        onCreate(db);
    }
}
