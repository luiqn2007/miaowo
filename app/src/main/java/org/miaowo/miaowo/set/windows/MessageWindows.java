package org.miaowo.miaowo.set.windows;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.adapter.QuestionDetailListAdapter;
import org.miaowo.miaowo.bean.data.web.QuestionDetail;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Message;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseSet;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.view.FloatView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 有关消息，回复的弹窗集合
 * Created by luqin on 17-1-17.
 */

public class MessageWindows extends BaseSet {

    private Message mMessage;
    private State mState;
    private BaseActivity mContext;

    private MessageWindows(BaseActivity context) {
        mContext = context;
        mMessage = new MsgImpl(mContext);
        mState = new StateImpl(mContext);
    }
    public static MessageWindows windows(BaseActivity context) { return new MessageWindows(context); }

    public FloatView showQuestion(String slug) {
        FloatView view = new FloatView(mContext, "问题", R.layout.window_question);
        View v = view.getView();

        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) v.findViewById(R.id.tv_count);
        TextView tv_question = (TextView) v.findViewById(R.id.tv_question);
        ListView lv_question = (ListView) v.findViewById(R.id.lv_question);

        tv_title.setText("加载中");
        tv_question.setText("加载中");
        tv_count.setText("加载中");

        HttpUtil.utils().post(mContext.getString(R.string.url_home) + mContext.getString(R.string.url_topic) + slug, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.runOnUiThread(() -> {
                    tv_title.setText("加载失败");
                    tv_question.setText("加载失败");
                    tv_count.setText("加载失败");
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                QuestionDetail questionDetail = BeanUtil.utils().buildFromLastJson(response, QuestionDetail.class);
                mContext.runOnUiThread(() -> {
                    tv_title.setText(questionDetail.getTitleRaw());
                    tv_question.setText(questionDetail.getPosts().get(0).getContent());
                    tv_count.setText(questionDetail.getPostcount() + " 回复, " + questionDetail.getViewcount() + " 浏览");
                    lv_question.setAdapter(new QuestionDetailListAdapter(mContext, questionDetail));
                });
            }
        });

        return view.defaultBar().show();
    }

    public FloatView showNewReply(QuestionDetail.PostsBean question) {
        FloatView view = new FloatView(mContext, "回复: " + question.getUser().getUsername(), R.layout.window_reply);
        View v = view.getView();

        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        EditText et_content = (EditText) v.findViewById(R.id.et_content);

        btn_send.setOnClickListener(v1 -> mMessage.sendReply(question, et_content.getText().toString()));

        return view.defaultBar().show();
    }
    public FloatView showNewQuestion() {
        if (!mState.isLogin()) {
            mContext.handleError(Exceptions.E_NON_LOGIN);
        }

        final FloatView view = new FloatView(mContext, "新问题", R.layout.window_ask);
        View v = view.getView();

        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        final RadioGroup rg_type = (RadioGroup) v.findViewById(R.id.rg_type);
        btn_send.setOnClickListener(v1 -> {
            String name;
            switch (rg_type.getCheckedRadioButtonId()) {
                case R.id.rb_ann:
                    name = Miao.FRAGMENT_ANNOUNCEMENT;
                    break;
                case R.id.rb_ask:
                    name = Miao.FRAGMENT_QUESTION;
                    break;
                case R.id.rb_daily:
                    name = Miao.FRAGMENT_DAILY;
                    break;
                case R.id.rb_water:
                    name = Miao.FRAGMENT_WATER;
                    break;
                default:
                    name = null;
            }
            if (name == null) {
                return;
            }
            try {
                QuestionDetail.PostsBean q = new QuestionDetail.PostsBean();
                q.setUser((QuestionDetail.PostsBean.UserBean) mState.loginedUser());
                q.setContent(et_content.getText().toString());
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();

                mMessage.sendQuestion(name, title, content);
                view.dismiss(false);
            } catch (Exception e) {
                mContext.handleError(e);
            }
        });

        return view.defaultBar().show();
    }
}
