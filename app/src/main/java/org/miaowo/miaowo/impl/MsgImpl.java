package org.miaowo.miaowo.impl;

import android.support.annotation.WorkerThread;

import org.miaowo.miaowo.T;
import org.miaowo.miaowo.bean.Answer;
import org.miaowo.miaowo.bean.Question;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.bean.VersionMessage;
import org.miaowo.miaowo.impl.interfaces.Message;

import java.util.ArrayList;

/**
 * {@link Message} 的具体实现类
 * Created by lq2007 on 16-11-21.
 */

public class MsgImpl implements Message {

    @Override
    public ArrayList<Question> checkQuestions(int type, int position, int count) throws Exception {
        ArrayList<Question> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(new Question(
                    i,
                    new User(
                            i,
                            "问题：" + i,
                            "喵---" + i,
                            0, 1, 2, 99, false, 4, true, 50,
                            T.getRadomImgUrl()
                    ),
                    "问题：" + type + " --- " + i,
                    "问题内容：----------------------" + i,
                    "昨天",
                    i, 10 + i, 0
            ));
        }
        return items;
    }

    @Override
    public void sendQuestion(Question question) throws Exception {

    }

    @Override
    public void sendAnswer(Answer answer) throws Exception {

    }

    @Override
    public VersionMessage getUpdateMessage(int version) {
        String versionName, versionMessage;
        versionName = "好奇喵 7.2.0";
        versionMessage = "" +
                "[添加] 对回答的评论功能\n" +
                "[优化] 主页改为问题广场\n" +
                "[优化] 回答中用户名统一用主题色\n" +
                "[修复] 网络连接超时过多的bug\n" +
                "[修复] 部分机型logo错误的bug";
        return new VersionMessage(1, versionName, versionMessage, null);
    }

}
