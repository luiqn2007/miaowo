package org.miaowo.miaowo.test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.miaowo.miaowo.D;

/**
 * 存放用户信息
 * Created by luqin on 17-1-25.
 */

public class UserDBHelper extends SQLiteOpenHelper {
    public static String table = "user";

    public static String ID = "_id";
    public static String NAME = "name";
    public static String PWD = "pwd";
    public static String SUMMARY = "summary";
    public static String HEAD = "head";
    public static String DATE = "date";
    public static String AUTHORITY = "authority";
    public static String QUESTION = "question";
    public static String SCAN = "scan";
    public static String FOCUS_ME = "focus_me";
    public static String FOCUS = "focus";
    public static String[] getCumns = new String[]{ID, NAME, PWD, SUMMARY, HEAD, DATE, AUTHORITY, QUESTION, SCAN, FOCUS_ME, FOCUS};

    public UserDBHelper() {
        super(D.getInstance().activeActivity, table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + table + " (" +
                        ID + " integer primary key autoincrement, " +
                        NAME + " varchar(20), " +
                        PWD + " varchar(20), " +
                        SUMMARY + " varchar(255), " +
                        HEAD + " varchar(10), " +
                        DATE + " bigint, " +
                        AUTHORITY + " integer, " +
                        QUESTION + " integer, " +
                        SCAN + " integer, " +
                        FOCUS_ME + " text, " +
                        FOCUS  + " text);"
        );
        ContentValues defaultUser = new ContentValues();
        defaultUser.put(ID, -1);
        defaultUser.put(NAME, "流浪喵");
        defaultUser.put(PWD, "0");
        defaultUser.put(SUMMARY, "欢迎来到喵窝");
        defaultUser.put(HEAD, "default");
        defaultUser.put(DATE, System.currentTimeMillis());
        defaultUser.put(AUTHORITY, 0);
        defaultUser.put(QUESTION, 0);
        defaultUser.put(SCAN, 0);
        defaultUser.put(FOCUS_ME, "");
        defaultUser.put(FOCUS, "");
        db.insert(table, null, defaultUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table);
        onCreate(db);
    }
}
