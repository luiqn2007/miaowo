package org.miaowo.miaowo.root;

import okhttp3.Call;

/**
 * 网络消息
 * Created by luqin on 17-3-12.
 */

public class BaseEvent {
    public Call call;

    public BaseEvent(Call call) {
        this.call = call;
    }
}
