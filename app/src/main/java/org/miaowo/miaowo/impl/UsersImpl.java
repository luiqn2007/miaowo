package org.miaowo.miaowo.impl;


import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.HttpUtil;


/**
 * {@link Users} 的具体实现类
 * Created by lq2007 on 16-11-23.
 */

public class UsersImpl implements Users {

    private HttpUtil http;

    public UsersImpl() {
        http = HttpUtil.utils();
    }

    @Override
    public void getUser(String name, HttpUtil.CallbackRun callback, HttpUtil.CallbackErr onErr) {
        http.post(String.format(BaseActivity.get.getString(R.string.url_user), name), callback, onErr);
    }

    @Override
    public void focusUser(User u) {
        // TODO: 关注用户
    }

    @Override
    public void updateUser(String user, String pwd, String email) {
        // TODO: 更新用户
    }

    @Override
    public void updateUserHead(String headString) {
        // TODO: 更新用户头像
    }
}
