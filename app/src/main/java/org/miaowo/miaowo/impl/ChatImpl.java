package org.miaowo.miaowo.impl;

import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.impl.interfaces.Chat;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;

/**
 * 聊天实现类
 * Created by luqin on 16-12-31.
 */

public class ChatImpl implements Chat {
    @Override
    public void loadList() {
        D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
    }

    @Override
    public void refresh() {
        D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
    }

    @Override
    public void sendMessage(ChatMessage msg) {
        D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
    }

    @Override
    public void loadBefore() {
        D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
    }
}
