package org.miaowo.miaowo.impl;

import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.EventMsg;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.impl.interfaces.Messages;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.EventUtil;
import org.miaowo.miaowo.util.HttpUtil;

import java.net.URLEncoder;

/**
 * {@link Messages} 的具体实现类
 * Created by lq2007 on 16-11-21.
 */

public class MsgImpl implements Messages {

    @Override
    public void loadQuestions(String type, int position) {
        String url;
        switch (type) {
            case Miao.FRAGMENT_ANNOUNCEMENT:
                url = "https://miaowo.org/category/1/announcements";
                break;
            case Miao.FRAGMENT_DAILY:
                url = "https://miaowo.org/category/7/%E6%AF%8F%E6%97%A5%E9%97%AE%E9%A2%98";
                break;
            case Miao.FRAGMENT_QUESTION:
                url = "https://miaowo.org/category/5/%E6%8F%90%E9%97%AE";
                break;
            case Miao.FRAGMENT_WATER:
                url = "https://miaowo.org/category/6/%E7%81%8C%E6%B0%B4";
                break;
            default:
                return;
        }
        switch (position) {
            case SEARCH_POSITION_DOWN:
                D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
                break;
            case SEARCH_POSITION_UP:
                HttpUtil.utils().post(url, EventMsg.DATA_TYPE.CATEGORY);
                break;
        }
    }

    @Override
    public void loadTopics(int position) {
        String url = "https://miaowo.org/tags";
        switch (position) {
            case SEARCH_POSITION_DOWN:
                D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
                break;
            case SEARCH_POSITION_UP:
                HttpUtil.utils().post(url, EventMsg.DATA_TYPE.SEARCH_TOPIC);
                break;
        }
    }

    @Override
    public void sendQuestion(Question question) {
        D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
    }

    @Override
    public void searchQuestion(String key) {
        String url = "https://miaowo.org/search?term="
                + URLEncoder.encode(key)
                + "&in=titlesposts&showAs=posts";
        HttpUtil.utils().post(url, EventMsg.DATA_TYPE.SEARCH_QUESTION);
    }

    @Override
    public void searchTopic(String key) {
        String url = "https://miaowo.org/search?term="
                + URLEncoder.encode(key)
                + "&in=titlesposts&showAs=posts";
        HttpUtil.utils().post(url, EventMsg.DATA_TYPE.SEARCH_TOPIC);
    }

    @Override
    public void answer(Answer answer) {
        D.getInstance().activeActivity.handleError(Exceptions.E_NONE);
    }

    @Override
    public void getUpdateMessage(int version) {
        String versionName, versionMessage;
        versionName = "喵窝 单机版";
        versionMessage = "" +
                "喵窝单机测试版，喵们拿去自娱自乐吧\n" +
                "哪里丑爆了，哪里有bug，别忘了反馈给我哦\n" +
                "就用那个悬浮球就能反馈，多谢了";
        VersionMessage vm = new VersionMessage(1, versionName, versionMessage, null);
        EventUtil.post(new EventMsg<>(EventMsg.DATA_TYPE.VERSION, null, vm));
    }


}
