package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.root.BaseEvent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 搜索结果
 * Created by luqin on 17-3-18.
 */

public class SearchEvent extends BaseEvent {
    public Object result;
    public Response response;

    public SearchEvent(Call call, Object result) {
        super(call);
        this.result = result;
    }
}
