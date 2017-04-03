package org.miaowo.miaowo.impl;


import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;


/**
 * {@link Users} 的具体实现类
 * Created by lq2007 on 16-11-23.
 */

public class UsersImpl implements Users {
    private BaseActivity mContext;

    public UsersImpl(BaseActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public User getUser(String name) {
        return null;
    }

    @Override
    public void focusUser(User u) {
        mContext.handleError(new Exception("暂时搞不懂怎么关注的"));
    }

    @Override
    public void updateUser(String user, String pwd, String email) {
        mContext.handleError(new Exception("暂时搞不懂怎么修改的"));
    }

    @Override
    public void updateUserHead(String headString) {

    }
}
