package org.miaowo.miaowo.bean.data;

import okhttp3.Call;
import okhttp3.Response;

/**
 * EventBus 发送的信息载体
 * Created by luqin on 17-3-6.
 */

public class EventMsg<T> {
    public enum DATA_TYPE {
        ANSWER, CHAT, QUESTION, VERSION, USER,
        SEARCH_USER, SEARCH_QUESTION, SEARCH_TOPIC, CATEGORY,
        EXCEPTION, TEST,
        LOGIN, REGISTER
    }

    private DATA_TYPE type;
    private Call call;
    private Response response;
    private T message;

    public EventMsg(DATA_TYPE type, Call call, T message) {
        this.type = type;
        this.call = call;
        this.message = message;
    }
    public EventMsg(DATA_TYPE type, Call call, Response response, T message) {
        this.type = type;
        this.call = call;
        this.response = response;
        this.message = message;
    }

    public DATA_TYPE type() {
        return type;
    }

    public Call call() {
        return call;
    }

    public Response response() {
        return response;
    }

    public T message() {
        return message;
    }
}
