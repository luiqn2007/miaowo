package org.miaowo.miaowo.impl;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.test.DbUtil;
import org.miaowo.miaowo.test.UserDBHelper;

import java.util.Arrays;


/**
 * {@link Users} 的具体实现类
 * Created by lq2007 on 16-11-23.
 */

public class UsersImpl implements Users {

    @Override
    public User getUser(long id) {
        if (id < 0) {
            return null;
        } else {
            SQLiteDatabase userDb = (new UserDBHelper()).getReadableDatabase();
            Cursor userCursor = userDb.query(UserDBHelper.table, UserDBHelper.getCumns,
                    UserDBHelper.ID + " = ? ",
                    new String[]{Long.toString(id)}, null, null, null);
            User[] users = DbUtil.parseUser(userCursor);
            userCursor.close();
            userDb.close();
            if (users.length == 0) return null;
            else return users[0];
        }
    }

    @Override
    public User[] searchUsers(String userName) {
        if (TextUtils.isEmpty(userName)) {
            return new User[0];
        } else {
            SQLiteDatabase userDb = (new UserDBHelper()).getReadableDatabase();
            Cursor userCursor = userDb.query(UserDBHelper.table, UserDBHelper.getCumns,
                    UserDBHelper.NAME + " like ? ",
                    new String[]{"%" + userName + "%"}, null, null, null);
            User[] users = DbUtil.parseUser(userCursor);
            userCursor.close();
            userDb.close();
            return users;
        }
    }

    @Override
    public void focusUser(User u, boolean auto) throws Exception {
        User localUser = (new StateImpl()).getLocalUser();
        if (localUser.getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
        }
        int[] oldFocusMe = u.getFocusMe();
        int[] oldFocus = localUser.getFocus();
        int[] focusMe, focus;
        Arrays.sort(oldFocus);
        Arrays.sort(oldFocusMe);
        int searchMe = Arrays.binarySearch(oldFocusMe, localUser.getId());
        int search = Arrays.binarySearch(oldFocus, u.getId());
        if (searchMe >= 0) {
            if (auto) {
                focusMe = new int[oldFocusMe.length - 1];
                for (int i = 0; i < oldFocusMe.length; i++) {
                    if (searchMe > i) {
                        focusMe[i] = oldFocusMe[i];
                    } else if (searchMe < i) {
                        focusMe[i - 1] = oldFocusMe[i];
                    }
                }
                focus = new int[oldFocus.length - 1];
                for (int i = 0; i < oldFocus.length; i++) {
                    if (search > i) {
                        focus[i] = oldFocus[i];
                    } else if (search < i) {
                        focus[i - 1] = oldFocus[i];
                    }
                }
            } else return;
        } else {
            focusMe = Arrays.copyOf(oldFocusMe, oldFocusMe.length + 1);
            focusMe[focusMe.length - 1] = localUser.getId();
            focus = Arrays.copyOf(oldFocus, oldFocus.length + 1);
            focus[focus.length - 1] = u.getId();
        }

        u.setFocusMe(focusMe);
        localUser.setFocus(focus);

        SQLiteDatabase userDb = (new UserDBHelper()).getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(UserDBHelper.FOCUS_ME, DbUtil.toString(focusMe));
        ContentValues cv2 = new ContentValues();
        cv2.put(UserDBHelper.FOCUS, DbUtil.toString(focus));
        userDb.update(UserDBHelper.table, cv1, UserDBHelper.ID + " = ? ", new String[]{String.valueOf(u.getId())});
        userDb.update(UserDBHelper.table, cv2, UserDBHelper.ID + " = ? ", new String[]{String.valueOf(localUser.getId())});
        userDb.close();
    }
}
