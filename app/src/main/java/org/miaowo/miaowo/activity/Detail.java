package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.TitleContentAdapter;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.Request;

public class Detail extends BaseActivity {
    final public static String TITLE = "title";

    @BindView(R.id.lv_title) ListView title;
    private API mApi;
    private HttpUtil mHttp;
    private JsonUtil mJson;
    private TitleContentAdapter mAdapter;
    private Title mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    @Override
    public void initActivity() {
        mApi = new API();
        mHttp = HttpUtil.utils();
        mJson = JsonUtil.utils();
        mAdapter = new TitleContentAdapter();
        title.setAdapter(mAdapter);
        mTitle = getIntent().getParcelableExtra(TITLE);
        load();
    }

    private void load() {
        Request request = new Request.Builder().url(String.format(getString(R.string.url_topic), mTitle.getSlug())).build();
        mHttp.post(request,
                (call, response) -> mAdapter.update(mJson.buildFromAPI(response, Question.class).getPosts()),
                (call, e) -> toast(e.getMessage(), TastyToast.ERROR));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String content = data.getStringExtra(Add.TITLE) + '\n' + data.getStringExtra(Add.CONTENT);
            int tid = data.getIntExtra(Add.TAG, -1);
            if (tid != -1) {
                FormBody body = new FormBody.Builder()
                        .add("content", content)
                        .build();
                mApi.useAPI(API.APIType.TOPICS, String.valueOf(tid), API.Method.POST, true, body, (call, response) -> load());
            }
        }
    }
}
