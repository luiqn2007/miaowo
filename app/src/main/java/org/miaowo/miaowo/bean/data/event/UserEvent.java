package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.root.BaseEvent;

import okhttp3.Call;

/**
 * 嵌入用户信息
 * Created by luqin on 17-3-18.
 */

public class UserEvent extends BaseEvent {
    public User user;

    public UserEvent(Call call, User user) {
        super(call);
        this.user = user;
    }
}
