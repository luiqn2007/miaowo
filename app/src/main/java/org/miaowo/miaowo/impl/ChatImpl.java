package org.miaowo.miaowo.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.test.ChatListDBHelper;
import org.miaowo.miaowo.test.ChatMessageDBHelper;
import org.miaowo.miaowo.test.DbUtil;

import java.util.Arrays;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {
    private Context context;

    public ChatImpl() {
        this.context = D.getInstance().activeActivity;
    }

    @Override
    public User[] getChatList() {
        User[] users;
        SQLiteDatabase chatListDb = (new ChatListDBHelper()).getReadableDatabase();
        int id = D.getInstance().thisUser.getId();
        Cursor cursor = chatListDb.query(ChatListDBHelper.table, ChatListDBHelper.getCumns,
                ChatListDBHelper.ID + " = ? ",
                new String[]{Integer.toString(id)}, null, null, null);
        users = DbUtil.parseUser(cursor);
        cursor.close();
        chatListDb.close();
        return users;
    }

    @Override
    public void sendMessage(ChatMessage msg) throws Exception {
        User[] chatList = getChatList();
        if (Arrays.binarySearch(chatList, msg.getFrom()) < 0) {
            SQLiteDatabase chatListDb = (new ChatListDBHelper()).getWritableDatabase();
            int[] users = new int[chatList.length + 1];
            for (int i = 0; i < chatList.length; i++) {
                users[i] = chatList[i].getId();
            }
            users[users.length - 1] = msg.getFrom().getId();
            ContentValues cv = new ContentValues();
            cv.put(ChatListDBHelper.LIST, DbUtil.toString(users));
            chatListDb.update(ChatListDBHelper.table, cv,
                    ChatListDBHelper.ID + " = ? ",
                    new String[]{Integer.toString(D.getInstance().thisUser.getId())});
            chatListDb.close();
        }

        SQLiteDatabase chatMsgDb = (new ChatMessageDBHelper()).getWritableDatabase();
        chatMsgDb.insert(ChatMessageDBHelper.table, ChatMessageDBHelper.ID, DbUtil.convert(msg));
        chatMsgDb.close();
    }

    @Override
    public void pushMessage(ChatMessage msg) {
        Intent intent = new Intent(C.BC_CHAT);
        intent.putExtra(C.EXTRA_ITEM, msg);
        context.sendBroadcast(intent);
    }

    @Override
    public ChatMessage[] getBeforeMessage(ChatMessage msg) {
        SQLiteDatabase chatMsgDb = (new ChatMessageDBHelper()).getReadableDatabase();
        Cursor cursorf = chatMsgDb.query(ChatMessageDBHelper.table, ChatMessageDBHelper.getCumns,
                ChatMessageDBHelper.FROM + " = ? and " + ChatMessageDBHelper.TO + " = ? and " + ChatMessageDBHelper.TIME + " > ? ",
                new String[]{Integer.toString(msg.getFrom().getId()), Integer.toString(msg.getTo().getId()), Long.toString(msg.getTime())},
                null, null, null);
        ChatMessage[] msgFrom = DbUtil.parseChatMessage(cursorf);
        Cursor cursort = chatMsgDb.query(ChatMessageDBHelper.table, ChatMessageDBHelper.getCumns,
                ChatMessageDBHelper.FROM + " = ? and " + ChatMessageDBHelper.TO + " = ? and " + ChatMessageDBHelper.TIME + " > ? ",
                new String[]{Integer.toString(msg.getTo().getId()), Integer.toString(msg.getFrom().getId()), Long.toString(msg.getTime())},
                null, null, null);
        ChatMessage[] msgTo = DbUtil.parseChatMessage(cursort);
        cursorf.close();
        cursort.close();
        chatMsgDb.close();
        ChatMessage[] messages = Arrays.copyOf(msgFrom, msgFrom.length + msgTo.length);
        for (int i = 0; i < msgTo.length; i++) {
            messages[msgFrom.length + i] = msgTo[i];
        }
        return messages;
    }
}
