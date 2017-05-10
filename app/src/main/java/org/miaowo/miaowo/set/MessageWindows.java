package org.miaowo.miaowo.set;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.QuestionDetailListAdapter;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.custom.FloatView;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseSet;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

import okhttp3.Request;

/**
 * 有关消息，回复的弹窗集合
 * Created by luqin on 17-1-17.
 */

public class MessageWindows extends BaseSet {

    private MessageWindows() {}
    public static MessageWindows windows() { return new MessageWindows(); }

    public FloatView showQuestion(String slug) {
        FloatView view = new FloatView("问题", R.layout.window_question);
        View v = view.getView();

        TextView tv_title = (TextView) v.findViewById(R.id.tv_page);
        TextView tv_count = (TextView) v.findViewById(R.id.tv_count);
        ListView lv_question = (ListView) v.findViewById(R.id.lv_question);

        tv_title.setText(R.string.data_loading);
        tv_count.setText(R.string.data_loading);

        Request request = new Request.Builder().url(String.format(BaseActivity.get.getString(R.string.url_topic), slug)).build();
        HttpUtil.utils().post(request, (call, response) -> {
                    Question question = JsonUtil.utils().buildFromAPI(response, Question.class);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        tv_title.setText(FormatUtil.format().praseHtml(question.getTitle()));
                        tv_count.setText(question.getPostcount() + " 回复, " + question.getViewcount() + " 浏览");
                        lv_question.setAdapter(new QuestionDetailListAdapter(BaseActivity.get, question));
                    });
                },(call, e) -> BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                    tv_title.setText(R.string.err_load);
                    tv_count.setText(R.string.err_load);
                }));
        return view.defaultBar().show();
    }
}
