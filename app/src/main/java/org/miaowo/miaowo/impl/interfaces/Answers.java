package org.miaowo.miaowo.impl.interfaces;

import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Question;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 回答/回复操作
 * Created by luqin on 17-1-26.
 */

public interface Answers {

    /**
     * 通过 id 获取 answer
     * @param id 回答/回复 id
     * @return 获取的对象
     */
    Answer getAnswer(int id);

    /**
     * 发送回答
     * @param answer 回答
     * @throws Exception
     */
    void sendAnswer(Answer answer) throws Exception;

    /**
     * 获取问题回答
     * @param question 要获取回答的问题
     * @return 问题的回答
     * @throws Exception 查询出错
     */
    HashMap<Answer, ArrayList<Answer>> getAnswers(Question question) throws Exception;

    /**
     * 获取回复的根回答
     * @param answer 回复
     * @return 回答
     */
    Answer getFinalAnswer(Answer answer);
}
