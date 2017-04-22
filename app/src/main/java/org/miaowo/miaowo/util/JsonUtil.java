package org.miaowo.miaowo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;
import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.root.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * 生成 JavaBean 类
 * Created by luqin on 17-3-10.
 */

public class JsonUtil {
    private static JsonUtil util;
    private JsonUtil() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }
    public static JsonUtil utils() {
        if (util == null) {
            synchronized (JsonUtil.class) {
                if (util == null) {
                    util = new JsonUtil();
                }
            }
        }
        return util;
    }

    private Gson gson;

    private ArrayList<String> getJsons(Response response) throws IOException{
        ArrayList<String> jsons = new ArrayList<>();
        String[] lines;
        lines = response.body().string().split("\n");
        String[] start = new String[]{
                "JSON.parse('",
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
                    if (line.lastIndexOf(end[i]) < 0) continue;
                    jsons.add(line.substring(s + start[i].length(), line.lastIndexOf(end[i])).trim());
                    break;
                }
            }
        }
        return jsons;
    }
    public VersionMessage buildVersion(Response response) {
        try {
            return buildFromAPI(response, VersionMessage.class);
        } catch (IOException e) {
            BaseActivity.get.toast("无法获取新版本信息", TastyToast.ERROR);
            return null;
        }
    }
    public<T> T buildFromAPI(Response response, Class<T> typeClass) throws IOException {
        return gson.fromJson(response.body().string(), typeClass);
    }
    public String getToken(Response response) throws IOException, JSONException {
        String token = "";
        ArrayList<String> jsons = getJsons(response);
        for (String json : jsons) {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("csrf_token")) {
                token = jsonObject.getString("csrf_token");
                break;
            }
        }
        return token;
    }
}
