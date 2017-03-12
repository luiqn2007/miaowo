package org.miaowo.miaowo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Category;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.EventMsg;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.Search;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.fragment.TopicFragment;
import org.miaowo.miaowo.set.Exceptions;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * 生成 JavaBean 类
 * Created by luqin on 17-3-10.
 */

public class BeanUtil {
    private BeanUtil() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }
    public static BeanUtil utils() { return new BeanUtil(); }

    private Gson gson;

    public ArrayList<String> getJsons(Response response) {
        ArrayList<String> jsons = new ArrayList<>();
        String[] lines;
        try {
            lines = response.body().string().split("\n");
        } catch (IOException e) {
            return jsons;
        }
        String[] start = new String[]{
                "JSON.parse(\'",
                "<script id=\"ajaxify-data\" type=\"application/json\">"
        };
        String[] end = new String[]{
                ")",
                "</script>"
        };
        int s;
        TopicFragment.clearText();
        for (String line: lines){
            for (int i = 0; i < start.length; i++) {
                s = line.indexOf(start[i]);
                if (s >= 0) {
                    if (line.lastIndexOf(end[i]) < 0) {
                        continue;
                    }
                    jsons.add(line.substring(s + start[i].length(), line.lastIndexOf(end[i])));
                    break;
                }
            }
        }
        return jsons;
    }
    public Answer buildAnswer(Response response) {
        ArrayList<String> jsons = getJsons(response);
        if (!jsons.isEmpty()) {
            for (String json : jsons) {
                if (json.contains("uid") && json.contains("username")) {
                    return gson.fromJson(json, Answer.class);
                }
            }
        }
        EventUtil.post(new EventMsg<>(EventMsg.DATA_TYPE.EXCEPTION, null, response, Exceptions.E_NON_LOGIN));
        return null;
    }
    public Question buildQuestion(Response response) {
        ArrayList<String> jsons = getJsons(response);
        if (!jsons.isEmpty()) {
            for (String json : jsons) {
                if (json.contains("uid") && json.contains("username")) {
                    return gson.fromJson(json, Question.class);
                }
            }
        }
        EventUtil.post(new EventMsg<>(EventMsg.DATA_TYPE.EXCEPTION, null, response, Exceptions.E_NON_LOGIN));
        return null;
    }
    public ChatMessage buildChat(Response response) {
        ArrayList<String> jsons = getJsons(response);
        if (!jsons.isEmpty()) {
            for (String json : jsons) {
                if (json.contains("uid") && json.contains("username")) {
                    return gson.fromJson(json, ChatMessage.class);
                }
            }
        }
        EventUtil.post(new EventMsg<>(EventMsg.DATA_TYPE.EXCEPTION, null, response, Exceptions.E_NON_LOGIN));
        return null;
    }
    public User buildUser(Response response) {
        ArrayList<String> jsons = getJsons(response);
        if (!jsons.isEmpty()) {
            for (String json : jsons) {
                if (json.contains("uid") && json.contains("username")) {
                    return gson.fromJson(json, User.class);
                }
            }
        }
        EventUtil.post(new EventMsg<>(EventMsg.DATA_TYPE.EXCEPTION, null, response, Exceptions.E_NON_LOGIN));
        return null;
    }
    public <T> Search<T> buildSearch(Response response) {
        return new Search<T>();
    }
    public VersionMessage buildVersion(Response response) {
        return null;
    }
    public Category buildCategory(Response response) {
        return null;
    }

    public String toJson(Answer answer) {
        return "";
    }
    public String toJson(Question answer) {
        return "";
    }
    public String toJson(ChatMessage answer) {
        return "";
    }
    public String toJson(User answer) {
        return "";
    }

}
