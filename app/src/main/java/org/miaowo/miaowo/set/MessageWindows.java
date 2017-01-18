package org.miaowo.miaowo.set;

import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.Answer;
import org.miaowo.miaowo.bean.Question;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Message;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.util.PopupUtil;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 有关消息，回复的弹窗集合
 * Created by luqin on 17-1-17.
 */

public class MessageWindows {

    private Message mMessage;
    private State mState;
    private D d;

    public MessageWindows() {
        d = D.getInstance();
        mMessage = new MsgImpl();
        mState = new StateImpl();
    }

    // 问题
    public PopupWindow showQuestion(final Question question) throws Exception {

        return PopupUtil.showPopupWindowInCenter(R.layout.window_question
                , new PopupUtil.PopupWindowInit() {
                    @Override
                    public void init(View v, PopupWindow window) {
                        TextView tv_user = (TextView) v.findViewById(R.id.tv_user);
                        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
                        TextView tv_count = (TextView) v.findViewById(R.id.tv_count);
                        TextView tv_question = (TextView) v.findViewById(R.id.tv_question);
                        ImageView iv_user = (ImageView) v.findViewById(R.id.iv_user);
                        Button btn_answer = (Button) v.findViewById(R.id.btn_send);

                        Picasso.with(D.getInstance().activeActivity).load(question.getUser().getHeadImg())
                                .fit().transform(new CropCircleTransformation())
                                .into(iv_user);
                        tv_user.setText(question.getUser().getName());
                        tv_title.setText(question.getTitle());
                        tv_question.setText(question.getMessage());
                        tv_count.setText(question.getReply() + " 回复, " + question.getView() + " 浏览");
                        btn_answer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    showAnswer(question);
                                } catch (Exception e) {
                                    D.getInstance().activeActivity.handleError(e);
                                }
                            }
                        });
                    }
                }, null);
    }

    // 提问
    public PopupWindow showNewQuestion() throws Exception {
        if (mState.getLocalUser().getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
        }

        return PopupUtil.showPopupWindowInCenter(R.layout.window_ask
                , new PopupUtil.PopupWindowInit() {
                    @Override
                    public void init(View v, PopupWindow window) {
                        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
                        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
                        Button btn_send = (Button) v.findViewById(R.id.btn_send);
                        final RadioGroup rg_type = (RadioGroup) v.findViewById(R.id.rg_type);

                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int type = 0;
                                switch (rg_type.getCheckedRadioButtonId()) {
                                    case R.id.rb_ann:
                                        type = C.LF_TYPE_ANNOUNCEMENT;
                                        break;
                                    case R.id.rb_ask:
                                        type = C.LF_TYPE_QUESTION;
                                        break;
                                    case R.id.rb_daily:
                                        type = C.LF_TYPE_DAILY;
                                        break;
                                    case R.id.rb_water:
                                        type = C.LF_TYPE_WATER;
                                        break;
                                }
                                Question q = new Question(0, mState.getLocalUser(),
                                        et_title.getText().toString(),
                                        et_content.getText().toString(),
                                        null, 0, 0, type);

                                new AsyncTask<Question, Void, Exception>() {

                                    @Override
                                    protected Exception doInBackground(Question... params) {
                                        try {
                                            mMessage.sendQuestion(params[0]);
                                        } catch (Exception e) {
                                            return e;
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Exception e) {
                                        if (e != null) {
                                            d.activeActivity.handleError(e);
                                        }
                                    }
                                }.execute(q);
                            }
                        });
                    }
                }, null);
    }

    // 回答
    public PopupWindow showAnswer(final Question question) throws Exception {
        if (mState.getLocalUser().getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
        }

        return PopupUtil.showPopupWindowInCenter(R.layout.window_ask
                , new PopupUtil.PopupWindowInit() {
                    @Override
                    public void init(View v, PopupWindow window) {
                        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
                        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
                        Button btn_send = (Button) v.findViewById(R.id.btn_send);
                        v.findViewById(R.id.rg_type).setVisibility(View.GONE);
                        et_title.setText("正在回答: " + question.getTitle());
                        et_title.setEnabled(false);

                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Answer a = new Answer(0, question, et_content.getText().toString(), mState.getLocalUser(), System.currentTimeMillis());

                                new AsyncTask<Answer, Void, Exception>() {

                                    @Override
                                    protected Exception doInBackground(Answer... params) {
                                        try {
                                            mMessage.sendAnswer(params[0]);
                                        } catch (Exception e) {
                                            return e;
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Exception e) {
                                        if (e != null) {
                                            d.activeActivity.handleError(e);
                                        }
                                    }
                                }.execute(a);
                            }
                        });
                    }
                }, null);
    }

    // 回复
    public PopupWindow showAnswer(final Answer answer) throws Exception {
        if (mState.getLocalUser().getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
        }

        return PopupUtil.showPopupWindowInCenter(R.layout.window_ask
                , new PopupUtil.PopupWindowInit() {
                    @Override
                    public void init(View v, PopupWindow window) {
                        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
                        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
                        Button btn_send = (Button) v.findViewById(R.id.btn_send);
                        v.findViewById(R.id.rg_type).setVisibility(View.GONE);
                        et_title.setText("正在回复: " + answer.getUser().getName());
                        et_title.setEnabled(false);

                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Answer a = new Answer(0, answer, et_content.getText().toString(), mState.getLocalUser(), System.currentTimeMillis());

                                new AsyncTask<Answer, Void, Exception>() {

                                    @Override
                                    protected Exception doInBackground(Answer... params) {
                                        try {
                                            mMessage.sendAnswer(params[0]);
                                        } catch (Exception e) {
                                            return e;
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Exception e) {
                                        if (e != null) {
                                            d.activeActivity.handleError(e);
                                        }
                                    }
                                }.execute(a);
                            }
                        });
                    }
                }, null);
    }
}
