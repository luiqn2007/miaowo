package org.miaowo.miaowo.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.MyApplication;
import org.miaowo.miaowo.service.WebService;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.test.ChatListDBHelper;
import org.miaowo.miaowo.test.ChatMessageDBHelper;
import org.miaowo.miaowo.test.DbUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.util.Arrays;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {
    private Context context;
    private Users mUsers;

    public ChatImpl() {
        this.context = D.getInstance().activeActivity;
        mUsers = new UsersImpl();
    }

    @Override
    public User[] getChatList() {
        int[] userIds;
        User[] users;
        SQLiteDatabase chatListDb = (new ChatListDBHelper()).getReadableDatabase();
        int id = D.getInstance().thisUser.getId();
        Cursor cursor = chatListDb.query(ChatListDBHelper.table, ChatListDBHelper.getCumns,
                ChatListDBHelper.ID + " = ? ",
                new String[]{Integer.toString(id)}, null, null, null);
        userIds = DbUtil.toIntArray(cursor.moveToNext()
                ? cursor.getString(cursor.getColumnIndex(ChatListDBHelper.LIST))
                : "");
        cursor.close();
        chatListDb.close();

        users = new User[userIds.length];
        for (int i = 0; i < userIds.length; i++) {
            users[i] = mUsers.getUser(userIds[i]);
        }
        return users;
    }

    @Override
    public void refreshChatList(User u) {
        User[] chatList = getChatList();
        // 无列表
        if (chatList.length == 0) {
            int[] users = new int[]{u.getId()};
            SQLiteDatabase chatListDb = (new ChatListDBHelper()).getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ChatListDBHelper.ID, D.getInstance().thisUser.getId());
            cv.put(ChatListDBHelper.LIST, DbUtil.toString(users));
            chatListDb.insert(ChatListDBHelper.table, null, cv);
            chatListDb.close();
            return;
        }
        // 有列表
        boolean isIn = false;
        for (User user : chatList) {
            if (user.equals(u)) {
                isIn = true;
                break;
            }
        }
        if (!isIn) {
            int[] users = new int[chatList.length + 1];
            for (int i = 0; i < chatList.length; i++) {
                users[i] = chatList[i].getId();
            }
            users[users.length - 1] = u.getId();
            SQLiteDatabase chatListDb = (new ChatListDBHelper()).getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ChatListDBHelper.LIST, DbUtil.toString(users));
            chatListDb.update(ChatListDBHelper.table, cv,
                    ChatListDBHelper.ID + " = ? ",
                    new String[]{Integer.toString(D.getInstance().thisUser.getId())});
            chatListDb.close();
        }
    }

    @Override
    public void sendMessage(ChatMessage msg) throws Exception {
        if (TextUtils.isEmpty(msg.getMessage())) throw Exceptions.E_EMPTY_MESSAGE;

        SQLiteDatabase chatMsgDb = (new ChatMessageDBHelper()).getWritableDatabase();
        chatMsgDb.insert(ChatMessageDBHelper.table, ChatMessageDBHelper.ID, DbUtil.convert(msg));
        chatMsgDb.close();
    }

    @Override
    public void pushMessage(ChatMessage msg) {
        Intent intent = new Intent(WebService.BC_CHAT);
        intent.putExtra(MyApplication.EXTRA_ITEM, msg);
        context.sendBroadcast(intent);
    }

    @Override
    public ChatMessage[] getBeforeMessage(ChatMessage msg) {
        LogUtil.i("lastMsg", msg);
        SQLiteDatabase chatMsgDb = (new ChatMessageDBHelper()).getReadableDatabase();
        Cursor cursorf = chatMsgDb.query(ChatMessageDBHelper.table, ChatMessageDBHelper.getCumns,
                ChatMessageDBHelper.FROM + " = ? and " + ChatMessageDBHelper.TO + " = ? and " + ChatMessageDBHelper.TIME + " < " + msg.getTime(),
                new String[]{Integer.toString(msg.getFrom().getId()), Integer.toString(msg.getTo().getId())},
                null, null, null);
        ChatMessage[] msgFrom = DbUtil.parseChatMessage(cursorf);
        Cursor cursort = chatMsgDb.query(ChatMessageDBHelper.table, ChatMessageDBHelper.getCumns,
                ChatMessageDBHelper.FROM + " = ? and " + ChatMessageDBHelper.TO + " = ? and " + ChatMessageDBHelper.TIME  + " < " + msg.getTime(),
                new String[]{Integer.toString(msg.getTo().getId()), Integer.toString(msg.getFrom().getId())},
                null, null, null);
        ChatMessage[] msgTo = DbUtil.parseChatMessage(cursort);
        cursorf.close();
        cursort.close();
        chatMsgDb.close();
        ChatMessage[] messages = Arrays.copyOf(msgFrom, msgFrom.length + msgTo.length);
        System.arraycopy(msgTo, 0, messages, msgFrom.length, msgTo.length);
        Arrays.sort(messages, (o1, o2) -> (o1.getTime() - o2.getTime()) > 0 ? 1 : -1);
        return messages;
    }
}
