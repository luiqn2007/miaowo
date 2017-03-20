package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.root.BaseEvent;

import okhttp3.Call;

/**
 * 网络错误消息
 * Created by luqin on 17-3-12.
 */

public class ExceptionEvent extends BaseEvent {
    public Exception e;

    public ExceptionEvent(Call call, Exception e) {
        super(call);
        this.e = e;
    }
}
