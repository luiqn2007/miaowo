package org.miaowo.miaowo.impl;

import org.miaowo.miaowo.bean.data.web.Post;
import org.miaowo.miaowo.bean.data.web.Question;
import org.miaowo.miaowo.impl.interfaces.Message;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Callbacks;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.HttpUtil;

/**
 * {@link Message} 的具体实现类
 * Created by lq2007 on 16-11-21.
 */

public class MsgImpl implements Message {

    private BaseActivity mContext;

    public MsgImpl(BaseActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void sendQuestion(String name, String title, String content) {
        mContext.handleError(Exceptions.E_NONE);
    }

    @Override
    public void sendReply(Post question, String content) {
        mContext.handleError(Exceptions.E_NONE);
    }

    @Override
    public void checkUpdate() {
        HttpUtil.utils().post("http://www.baidu.com", Callbacks.VERSION);
    }
}
