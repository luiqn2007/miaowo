package org.miaowo.miaowo.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.test.DbUtil;
import org.miaowo.miaowo.test.UserDBHelper;
import org.miaowo.miaowo.util.PwdUtil;

/**
 * {@link State} 的具体实现类
 * Created by luqin on 16-12-30.
 */

public class StateImpl implements State {

    @Override
    public void login(User u) throws Exception {
        checkUser(u);

        String pwd = PwdUtil.getMD5(u.getPwd(), u.getName());
        SQLiteDatabase userDb = (new UserDBHelper()).getReadableDatabase();
        Cursor query = userDb.query(UserDBHelper.table, UserDBHelper.getCumns,
                UserDBHelper.NAME + " = ? and " + UserDBHelper.PWD + " = ?",
                new String[]{u.getName(), pwd}, null, null, null);
        if (query.getCount() == 0) {
            throw Exceptions.E_WRONG_USER_PWD;
        }
        User[] users = DbUtil.parseUser(query);
        query.close();
        userDb.close();

        if (users.length == 0) {
            throw Exceptions.E_WRONG_USER_PWD;
        }
        D.getInstance().thisUser = users[0];
    }

    @Override
    public void logout() {
        D.getInstance().thisUser = D.getInstance().guest;
    }

    @Override
    public void regist(User u) throws Exception {
        UserDBHelper userDBHelper = new UserDBHelper();
        SQLiteDatabase userDb = userDBHelper.getWritableDatabase();

        checkUser(u);
        String pwd = PwdUtil.getMD5(u.getPwd(), u.getName());
        User usr = new User(0, u.getName(), pwd, u.getSummary(), 0, 0, 0, new int[0], new int[0], System.currentTimeMillis(), "default");

        Cursor query = userDb.query(UserDBHelper.table, UserDBHelper.getCumns,
                UserDBHelper.NAME + " = ?", new String[]{usr.getName()},
                null, null, null);
        if (query.getCount() > 0) {
            throw Exceptions.E_HAD_USER;
        }
        query.close();

        userDb.insert(UserDBHelper.table, UserDBHelper.ID, DbUtil.convert(usr));
        userDb.close();

        login(u);
    }

    @Override
    public void remove(User u) throws Exception {
        SQLiteDatabase userDb = (new UserDBHelper()).getWritableDatabase();
        userDb.delete(UserDBHelper.table, "_id = ?", new String[]{Integer.toString(u.getId())});
        userDb.close();
    }

    @Override
    public User getLocalUser() {
        return D.getInstance().thisUser;
    }

    private void checkUser(User u) throws Exception {
        if (TextUtils.isEmpty(u.getName()))
            throw Exceptions.E_ILL_USER_PWD;
        if (TextUtils.isEmpty(u.getPwd()) || PwdUtil.getMD5("", u.getName()).equals(u.getPwd()))
            throw Exceptions.E_ILL_USER_PWD;
    }
}
