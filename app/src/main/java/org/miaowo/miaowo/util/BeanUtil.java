package org.miaowo.miaowo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.bean.data.event.ExceptionEvent;
import org.miaowo.miaowo.bean.data.web.InnerUser;
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

    ArrayList<String> getJsons(Response response) {
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
                "\')",
                "</script>"
        };
        for (String line: lines){
            for (int i = 0; i < start.length; i++) {
                int s = line.indexOf(start[i]);
                if (s >= 0) {
                    if (line.lastIndexOf(end[i]) < 0) {
                        continue;
                    }
                    jsons.add(line.substring(s + start[i].length(), line.lastIndexOf(end[i])).trim());
                    break;
                }
            }
        }
        return jsons;
    }
    public InnerUser buildUser(Response response) {
        ArrayList<String> jsons = getJsons(response);
        if (!jsons.isEmpty()) {
            for (String json : jsons) {
                if (json.contains("uid") && json.contains("username")) {
                    InnerUser innerUser =null;
                    try {
                        innerUser = gson.fromJson(json, InnerUser.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return innerUser;
                }
            }
        }
        EventBus.getDefault().post(new ExceptionEvent(null, Exceptions.E_NON_MESSAGE));
        return null;
    }
    public VersionMessage buildVersion(Response response) {
        return new VersionMessage(99, "更新测试", "更新测试啊啊啊", "http://www.baidu.com");
    }
    public<T> T buildFromLastJson(Response response, Class<T> typeClass) {
        try {
            ArrayList<String> jsons = getJsons(response);
            String json = jsons.isEmpty() ? null : jsons.get(jsons.size() - 1);
            return gson.fromJson(json, typeClass);
        } catch (Exception e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ExceptionEvent(null, Exceptions.E_NON_MESSAGE));
        }
        return null;
    }
}
