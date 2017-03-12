package org.miaowo.miaowo.util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.miaowo.miaowo.bean.data.EventMsg;
import org.miaowo.miaowo.root.D;

/**
 * 对 EventBus 的二次封装
 * 用于消息的发送和处理
 * Created by luqin on 17-3-7.
 */

public class EventUtil {
    private static EventBus eventBus;
    private static EventHandler handler;
    static {
        eventBus = EventBus.getDefault();
        handler = new EventHandler();
        eventBus.register(handler);
    }

    public static void post(EventMsg message) {
        prepare(message);
        LogUtil.i("发送数据");
        if (message == null) return;
        if (!eventBus.isRegistered(handler)) eventBus.register(handler);
        handler.using++;
        eventBus.post(message);
    }

    // 发送之前的准备
    private static void prepare(EventMsg message) {
        switch (message.type()) {
            case ANSWER:
                break;
            case CHAT:
                break;
            case EXCEPTION:
                break;
            case LOGIN:
                break;
            case REGISTER:
                break;
            case QUESTION:
                break;
            case VERSION:
                break;
            case USER:
                break;
            case TEST:
                break;
            case SEARCH_QUESTION:
                break;
            case SEARCH_USER:
                break;
            case SEARCH_TOPIC:
                break;
            case CATEGORY:
                break;
            default:
                break;
        }
    }
}

class EventHandler {
    int using = 0;
    private EventBus eventBus = EventBus.getDefault();

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void onMessageEvent(EventMsg message) {
        handlerMsg(message);
    }

    private void handlerMsg(EventMsg message) {
        Object msg = message.message();
        switch (message.type()) {
            case TEST:
                LogUtil.i("测试完成");
                break;
            case ANSWER:
                break;
            case CHAT:
                break;
            case EXCEPTION:
                D.getInstance().activeActivity.handleError((Exception) msg);
                break;
            case LOGIN:
                break;
            case REGISTER:
                break;
            case QUESTION:
                break;
            case VERSION:
                break;
            case USER:
                break;
            case SEARCH_QUESTION:
                break;
            case SEARCH_USER:
                break;
            case SEARCH_TOPIC:
                break;
            case CATEGORY:
                break;
            default:
                D.getInstance().activeActivity.handleError(new Exception("未绑定操作"));
                break;
        }

        using--;
        if (using == 0) {
            eventBus.unregister(this);
        }
    }
}