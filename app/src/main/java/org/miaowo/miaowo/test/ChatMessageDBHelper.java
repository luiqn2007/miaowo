package org.miaowo.miaowo.test;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.miaowo.miaowo.root.D;

/**
 * 存放用户信息
 * Created by luqin on 17-1-25.
 */

public class ChatMessageDBHelper extends SQLiteOpenHelper {
    public static String table = "chatMsg";

    public static String ID = "_id";
    public static String MESSAGE = "message";
    public static String FROM = "from_id";
    public static String TO = "to_id";
    public static String TIME = "time";
    public static String[] getCumns = new String[]{ID, MESSAGE, FROM, TO, TIME};

    public ChatMessageDBHelper() {
        super(D.getInstance().activeActivity, table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + table + " (" +
                        ID + " integer primary key autoincrement, " +
                        MESSAGE + " text, " +
                        TIME + " bigint, " +
                        FROM + " integer, " +
                        TO  + " integer);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table);
        onCreate(db);
    }
}
