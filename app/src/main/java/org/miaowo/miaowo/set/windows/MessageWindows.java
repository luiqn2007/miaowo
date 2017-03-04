package org.miaowo.miaowo.set.windows;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.view.activity.Miao;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.impl.AnswersImpl;
import org.miaowo.miaowo.impl.QuestionsImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Answers;
import org.miaowo.miaowo.impl.interfaces.Questions;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.SetRoot;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.view.FloatView;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.ViewFiller;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 有关消息，回复的弹窗集合
 * Created by luqin on 17-1-17.
 */

public class MessageWindows extends SetRoot {

    private Questions mQuestions;
    private Answers mAnswers;
    private State mState;
    private D d;

    private BaseAdapter mAdapter;

    public MessageWindows() {
        d = D.getInstance();
        mQuestions = new QuestionsImpl();
        mAnswers = new AnswersImpl();
        mState = new StateImpl();
    }

    public FloatView showQuestion(final Question question) {
        FloatView view = new FloatView(R.layout.window_question);
        View v = view.getView();

        TextView tv_user = (TextView) v.findViewById(R.id.tv_user);
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) v.findViewById(R.id.tv_count);
        TextView tv_question = (TextView) v.findViewById(R.id.tv_question);
        ImageView iv_user = (ImageView) v.findViewById(R.id.iv_user);
        Button btn_answer = (Button) v.findViewById(R.id.btn_send);
        Button btn_reply = (Button) v.findViewById(R.id.btn_reply);

        ImageUtil.setUserImage(iv_user, question.getUser());
        tv_user.setText(question.getUser().getName());
        tv_title.setText(question.getTitle());
        tv_question.setText(question.getMessage());
        tv_count.setText(question.getReply() + " 回复, " + question.getView() + " 浏览");
        btn_reply.setText("回答(" + question.getReply() + ")");
        btn_reply.setOnClickListener(v1 -> showAnswers(question));
        btn_answer.setOnClickListener(v1 -> showNewAnswer(question));

        return view.defaultBar().show();
    }
    public FloatView showTopic(final Question question) {
        getArguments().putSerializable("answer_" + question.getId(), new ArrayList<Answer>());
        FloatView view = new FloatView(R.layout.window_topic);
        View v = view.getView();

        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        Button btn_answer = (Button) v.findViewById(R.id.btn_send);
        ListView lv_answer = (ListView) v.findViewById(R.id.lv_answer);

        tv_title.setText(question.getTitle());
        btn_answer.setOnClickListener(v1 -> showNewAnswer(question));
        lv_answer.setAdapter(new BaseAdapter() {
            ArrayList<Answer> answers;

            @Override
            public int getCount() {
                answers = (ArrayList<Answer>) getArguments().getSerializable("answer_" + question.getId());
                return answers == null ? 0 : answers.size();
            }

            @Override
            public Object getItem(int position) {
                return answers.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Answer answer = (Answer) getItem(position);
                ViewFiller.fillAnswer(convertView, answer);
                return convertView;
            }
        });

        new AsyncTask<Question, Void, Exception>() {

            @Override
            protected Exception doInBackground(Question... params) {
                try {
                    ArrayList<Answer> answers = new ArrayList<>();
                    Collections.addAll(answers, mAnswers.getAnswers(params[0]));
                    getArguments().putSerializable("answer_" + question.getId(), answers);
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e != null) {
                    d.activeActivity.handleError(e);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }.execute(question);

        return view.defaultBar().show();
    }
    private FloatView showAnswers(Question question) {
        getArguments().putSerializable("answer_" + question.getId(), new ArrayList<Answer>());
        FloatView view = new FloatView(R.layout.window_normal_list);
        View v = view.getView();

        ListView list = (ListView) v.findViewById(R.id.list);
        mAdapter = new BaseAdapter() {
            ArrayList<Answer> answers;

            @Override
            public int getCount() {
                answers = (ArrayList<Answer>) getArguments().getSerializable("answer_" + question.getId());
                return answers == null ? 0 : answers.size();
            }

            @Override
            public Object getItem(int position) {
                return answers.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Answer answer = (Answer) getItem(position);
                convertView = ViewFiller.fillAnswer(convertView, answer);
                return convertView;
            }
        };
        list.setAdapter(mAdapter);

        new AsyncTask<Question, Void, Exception>() {

            @Override
            protected Exception doInBackground(Question... params) {
                try {
                    ArrayList<Answer> answers = new ArrayList<>();
                    Collections.addAll(answers, mAnswers.getAnswers(params[0]));
                    getArguments().putSerializable("answer_" + question.getId(), answers);
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e != null) {
                    d.activeActivity.handleError(e);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }.execute(question);

        return view.defaultBar().show();
    }

    public FloatView showNewQuestion() {
        if (mState.getLocalUser().getId() < 0) {
            d.activeActivity.handleError(Exceptions.E_NON_LOGIN);
        }

        final FloatView view = new FloatView(R.layout.window_ask);
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
            Question q = new Question(0, mState.getLocalUser(),
                    et_title.getText().toString(),
                    et_content.getText().toString(),
                    System.currentTimeMillis(), 0, 0, name);

            new AsyncTask<Question, Void, Exception>() {

                @Override
                protected Exception doInBackground(Question... params) {
                    try {
                        mQuestions.sendQuestion(params[0]);
                    } catch (Exception e) {
                        return e;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Exception e) {
                    if (e != null) {
                        d.activeActivity.handleError(e);
                    } else {
                        Snackbar.make(D.getInstance().activeActivity.getWindow().getDecorView(),
                                "发送成功", Snackbar.LENGTH_SHORT).show();
                        view.dismiss();
                    }
                }
            }.execute(q);
        });

        return view.defaultBar().show();
    }
    private FloatView showNewAnswer(final Question question) {
        if (mState.getLocalUser().getId() < 0) {
            d.activeActivity.handleError(Exceptions.E_NON_LOGIN);
        }

        final FloatView view = new FloatView(R.layout.window_ask);
        View v = view.getView();

        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        v.findViewById(R.id.rg_type).setVisibility(View.GONE);
        et_title.setText("正在回答: " + question.getTitle());
        et_title.setEnabled(false);

        btn_send.setOnClickListener(vv -> {

            Answer a = new Answer(0, question, null, et_content.getText().toString(), mState.getLocalUser(), System.currentTimeMillis());

            new AsyncTask<Answer, Void, Exception>() {

                @Override
                protected Exception doInBackground(Answer... params) {
                    try {
                        mAnswers.sendAnswer(params[0]);
                    } catch (Exception e) {
                        return e;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Exception e) {
                    if (e != null) {
                        d.activeActivity.handleError(e);
                    } else {
                        Snackbar.make(D.getInstance().activeActivity.getWindow().getDecorView(),
                                "发送成功", Snackbar.LENGTH_SHORT).show();
                        view.dismiss();
                    }
                }
            }.execute(a);
        });
        return view.defaultBar().show();
    }
    public FloatView showNewReply(final Answer answer) {
        if (mState.getLocalUser().getId() < 0) {
            d.activeActivity.handleError(Exceptions.E_NON_LOGIN);
        }

        final FloatView view = new FloatView(R.layout.window_ask);
        View v = view.getView();

        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        v.findViewById(R.id.rg_type).setVisibility(View.GONE);
        et_title.setText("正在回复: " + answer.getUser().getName());
        et_title.setEnabled(false);
        btn_send.setOnClickListener(v1 -> {
            Answer a = new Answer(0, mAnswers.getFinalAnswer(answer).getQuestion(), answer, et_content.getText().toString(), mState.getLocalUser(), System.currentTimeMillis());
            new AsyncTask<Answer, Void, Exception>() {
                @Override
                protected Exception doInBackground(Answer... params) {
                    try {
                        mAnswers.sendAnswer(params[0]);
                    } catch (Exception e) {
                        return e;
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Exception e) {
                    if (e != null) {
                        d.activeActivity.handleError(e);
                    } else {
                        Snackbar.make(d.activeActivity.getWindow().getDecorView(), "发送成功", Snackbar.LENGTH_SHORT).show();
                        view.dismiss();
                    }
                }
            }.execute(a);
        });

        return view.defaultBar().show();
    }
}
