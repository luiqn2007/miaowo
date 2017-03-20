package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.root.BaseEvent;

import okhttp3.Call;

/**
 * 注册信息
 * Created by luqin on 17-3-12.
 */

public class RegisterEvent extends BaseEvent {
    public String username, password, email;

    public RegisterEvent(Call call, String username, String password, String email) {
        super(call);
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
