package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.VersionMessage;

import java.util.ArrayList;

/**
 * 用于从服务器获取问题/回答/回复，软件更新等信息，及向服务器发送消息的接口
 * Created by lq2007 on 16-11-21.
 */

public interface Questions {

    /**
     * 获取以前的问题列表，用于刷新
     * @param type 获取列表的内容，存放于其实现类
     * @param count 获取数量
     * @param position 上/下滑
     * @param time 上滑，截止时间
     * @return 返回的列表
     */
    Question[] checkQuestions(String type, int position, int count, long time) throws Exception;

    /**
     * 发送问题
     * @param question 提问问题
     */
    void sendQuestion(Question question) throws Exception;

    /**
     * 搜索问题
     * @param key 关键字
     * @return 包含关键字的问题集
     */
    Question[] searchQuestion(String key) throws Exception;

    /**
     * 搜索标题
     * @param key 关键字
     * @return 带有关键字的标题
     */
    Question[] searchTopic(String key) throws Exception;

    /**
     * 根据 id 获取问题
     * @param id 问题ID
     * @return
     */
    Question getQuestion(int id);

    /**
     * 获取新版本信息
     * @param version 当前版本代号
     * @return 新版本信息
     */
    VersionMessage getUpdateMessage(int version);
}
