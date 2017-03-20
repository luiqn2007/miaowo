package org.miaowo.miaowo.impl;

import org.miaowo.miaowo.bean.data.web.ChatMessage;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {
    private BaseActivity mContext;

    public ChatImpl(BaseActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void loadList() {
        mContext.handleError(Exceptions.E_NONE);
    }

    @Override
    public void refresh() {
        mContext.handleError(Exceptions.E_NONE);
    }

    @Override
    public void sendMessage(ChatMessage msg) {
        mContext.handleError(Exceptions.E_NONE);
    }

    @Override
    public void loadBefore() {
        mContext.handleError(Exceptions.E_NONE);
    }
}
