package org.miaowo.miaowo.impls;

import android.support.annotation.WorkerThread;

import org.miaowo.miaowo.T;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.interfaces.State;
import org.miaowo.miaowo.utils.MD5Util;

/**
 * {@link State} 的具体实现类
 * Created by luqin on 16-12-30.
 */

public class StateImpl implements State {
    @Override
    @WorkerThread
    public void login(User u) throws Exception {
        T.isLogin = true;
        String rlPwd = MD5Util.getMD5(u.getPwd(), u.getName());
        if (T.users.size() <= 1) {
            throw new Exception("无账户");
        }
        for (User user : T.users) {
            if (user.getName().equals(u.getName()) && user.getPwd().equals(rlPwd)) {
                T.localUser = u;
                return;
            }
        }
        throw new Exception("用户名或密码错误");
    }

    @Override
    public void logout() {
        T.localUser = T.users.get(0);
        T.isLogin = false;
    }

    @Override
    @WorkerThread
    public void regist(User u) throws Exception {
        String pwd = MD5Util.getMD5(u.getPwd(), u.getName());
        T.users.add(T.users.size(), new User(T.users.size(), u.getName(), u.getSummary(), pwd, T.getRadomImgUrl()));
        T.localUser = T.users.get(T.users.size() - 1);
        T.isLogin = true;
    }

    @Override
    @WorkerThread
    public void remove(User u) throws Exception {
        T.isLogin = false;
    }

    @Override
    public User getLocalUser() {
        return T.localUser;
    }
}
