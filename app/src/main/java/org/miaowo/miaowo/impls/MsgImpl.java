package org.miaowo.miaowo.impls;

import android.support.annotation.WorkerThread;

import org.miaowo.miaowo.T;
import org.miaowo.miaowo.beans.Question;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.beans.VersionMessage;
import org.miaowo.miaowo.impls.interfaces.Message;

import java.util.ArrayList;

/**
 * {@link Message} 的具体实现类
 * Created by lq2007 on 16-11-21.
 */

public class MsgImpl implements Message {

    @Override
    @WorkerThread
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
                    null,
                    "昨天"
            ));
        }
        return items;
    }

    @Override
    @WorkerThread
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