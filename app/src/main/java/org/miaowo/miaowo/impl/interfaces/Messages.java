package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Question;

/**
 * 用于从服务器获取问题/回答/回复，软件更新等信息，及向服务器发送消息的接口
 * Created by lq2007 on 16-11-21.
 */

public interface Messages {
    int SEARCH_POSITION_UP = 1;
    int SEARCH_POSITION_DOWN = 2;

    /**
     * 获取以前的问题列表，用于刷新
     * @param type 获取列表的内容，存放于其实现类
     * @param position 上/下滑
     */
    void loadQuestions(String type, int position);

    /**
     * 获取主题列表
     * @param position 上/下滑
     */
    void loadTopics(int position);

    /**
     * 发送问题
     * @param question 提问问题
     */
    void sendQuestion(Question question);

    /**
     * 搜索问题
     * @param key 关键字
     */
    void searchQuestion(String key);

    /**
     * 搜索标题
     * @param key 关键字
     */
    void searchTopic(String key);

    /**
     * 回答问题
     * @param answer 回答
     */
    void answer(Answer answer);

    /**
     * 检查新版本信息
     * @param version 当前版本代号
     */
    void getUpdateMessage(int version);
}
