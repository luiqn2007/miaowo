package org.miaowo.miaowo.set;

import org.greenrobot.eventbus.EventBus;
import org.miaowo.miaowo.bean.data.event.ExceptionEvent;
import org.miaowo.miaowo.bean.data.event.FileEvent;
import org.miaowo.miaowo.bean.data.event.SearchEvent;
import org.miaowo.miaowo.bean.data.event.VersionEvent;
import org.miaowo.miaowo.bean.data.web.QuestionSearchPage;
import org.miaowo.miaowo.util.BeanUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Callback
 * Created by luqin on 17-3-12.
 */

public enum Callbacks implements Callback {
    VERSION {
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            EventBus.getDefault().post(new VersionEvent(call, BeanUtil.utils().buildVersion(response)));
        }
    },

    SEARCH_QUESTION {
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            EventBus.getDefault().post(new SearchEvent(call,
                    BeanUtil.utils().buildFromLastJson(response, QuestionSearchPage.class)));
        }
    },

    UPDATE {
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            EventBus.getDefault().post(new FileEvent(call, null));
        }
    };

    @Override
    public void onFailure(Call call, IOException e) {
        EventBus.getDefault().post(new ExceptionEvent(call, e));
    }
}
