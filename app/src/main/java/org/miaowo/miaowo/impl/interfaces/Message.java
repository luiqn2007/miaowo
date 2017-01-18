package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.Answer;
import org.miaowo.miaowo.bean.Question;
import org.miaowo.miaowo.bean.VersionMessage;

import java.util.ArrayList;

/**
 * 用于从服务器获取问题/回答/回复，软件更新等信息，及向服务器发送消息的接口
 * Created by lq2007 on 16-11-21.
 */

public interface Message {

    /**
     * 获取以前的问题列表，用于刷新
     * @param type 获取列表的内容，存放于其实现类
     * @return 返回的列表
     */
    ArrayList<Question> checkQuestions(int type, int position, int count) throws Exception;

    /**
     * 发送问题
     * @param question 提问问题
     */
    void sendQuestion(Question question) throws Exception;

    /**
     * 发送回答
     * @param answer 回答
     * @throws Exception
     */
    void sendAnswer(Answer answer) throws Exception;

    /**
     * 获取新版本信息
     * @param version 当前版本代号
     * @return 新版本信息
     */
    VersionMessage getUpdateMessage(int version);
}
