package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.Post;

/**
 * 用于从服务器获取问题/回答/回复，软件更新等信息，及向服务器发送消息的接口
 * Created by lq2007 on 16-11-21.
 */

public interface Message {

    /**
     * 发送问题
     */
    void sendQuestion(String name, String title, String content);

    /**
     * 回答问题
     */
    void sendReply(Post question, String content);

    /**
     * 检查新版本信息
     */
    void checkUpdate();
}
