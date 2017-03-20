package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.bean.config.VersionMessage;
import org.miaowo.miaowo.root.BaseEvent;

import okhttp3.Call;

/**
 * 版本信息
 * Created by luqin on 17-3-12.
 */

public class VersionEvent extends BaseEvent {
    public VersionMessage message;

    public VersionEvent(Call call, VersionMessage message) {
        super(call);
        this.message = message;
    }
}
