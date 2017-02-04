package org.miaowo.miaowo.test;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.miaowo.miaowo.root.D;

/**
 * 存放用户信息
 * Created by luqin on 17-1-25.
 */

public class ChatListDBHelper extends SQLiteOpenHelper {
    public static String table = "chatList";

    public static String ID = "_id";
    public static String LIST = "list";
    public static String[] getCumns = new String[]{ID, LIST};

    public ChatListDBHelper() {
        super(D.getInstance().activeActivity, table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + table + " (" +
                        ID + " integer primary key, " +
                        LIST  + " text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table);
        onCreate(db);
    }
}
