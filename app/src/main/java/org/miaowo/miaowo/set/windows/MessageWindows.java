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

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.impl.AnswersImpl;
import org.miaowo.miaowo.impl.QuestionsImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Answers;
import org.miaowo.miaowo.impl.interfaces.Questions;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.set.SetRoot;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.ui.FloatView;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.ViewFiller;

import java.util.ArrayList;
import java.util.HashMap;

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

        ImageUtil.fillImage(iv_user, question.getUser());
        tv_user.setText(question.getUser().getName());
        tv_title.setText(question.getTitle());
        tv_question.setText(question.getMessage());
        tv_count.setText(question.getReply() + " 回复, " + question.getView() + " 浏览");
        btn_reply.setText("回答(" + question.getReply() + ")");
        btn_reply.setOnClickListener(v1 -> showAnswers(question));
        btn_answer.setOnClickListener(v1 -> {
            try {
                showNewAnswer(question);
            } catch (Exception e) {
                d.activeActivity.handleError(e);
            }
        });

        return view.defaultCloseButton().show();
    }
    public FloatView showTopic(final Question question) {
        getArguments().putSerializable("answer_" + question.getId(), new HashMap<Answer, ArrayList<Answer>>());
        FloatView view = new FloatView(R.layout.window_topic);
        View v = view.getView();

        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        Button btn_answer = (Button) v.findViewById(R.id.btn_send);
        ListView lv_answer = (ListView) v.findViewById(R.id.lv_answer);

        tv_title.setText(question.getTitle());
        btn_answer.setOnClickListener(v1 -> {
            try {
                showNewAnswer(question);
            } catch (Exception e) {
                d.activeActivity.handleError(e);
            }
        });
        lv_answer.setAdapter(new BaseAdapter() {
            HashMap<Answer, ArrayList<Answer>> answers;

            @Override
            public int getCount() {
                answers = (HashMap<Answer, ArrayList<Answer>>) getArguments().getSerializable("answer_" + question.getId());
                return answers == null ? 0 : answers.size();
            }

            @Override
            public Object getItem(int position) {
                return answers.keySet().toArray()[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Answer answer = (Answer) getItem(position);
                ViewFiller.fillAnswer(convertView, answer, answers.get(answer));
                return convertView;
            }
        });

        new AsyncTask<Question, Void, Exception>() {

            @Override
            protected Exception doInBackground(Question... params) {
                try {
                    HashMap<Answer, ArrayList<Answer>> answers = mAnswers.getAnswers(params[0]);
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

        return view.defaultCloseButton().show();
    }
    private FloatView showAnswers(Question question) {
        getArguments().putSerializable("answer_" + question.getId(), new HashMap<Answer, ArrayList<Answer>>());
        FloatView view = new FloatView(R.layout.window_normal_list);
        View v = view.getView();

        ListView list = (ListView) v.findViewById(R.id.list);
        mAdapter = new BaseAdapter() {
            HashMap<Answer, ArrayList<Answer>> answers;
            BaseAdapter[] inAdapters;

            @Override
            public int getCount() {
                answers = (HashMap<Answer, ArrayList<Answer>>) getArguments().getSerializable("answer_" + question.getId());
                int count = answers == null ? 0 : answers.size();
                inAdapters = new BaseAdapter[count];
                return count;
            }

            @Override
            public Object getItem(int position) {
                return answers.keySet().toArray()[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Answer answer = (Answer) getItem(position);
                convertView = ViewFiller.fillAnswer(convertView, answer, answers.get(answer));
                inAdapters[position] = (BaseAdapter) ((ViewFiller.AnswerHolder) convertView.getTag()).lv_replies.getAdapter();
                return convertView;
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                for (BaseAdapter inAdapter : inAdapters) {
                    if (inAdapter != null) {
                        inAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        list.setAdapter(mAdapter);

        new AsyncTask<Question, Void, Exception>() {

            @Override
            protected Exception doInBackground(Question... params) {
                try {
                    HashMap<Answer, ArrayList<Answer>> answers = mAnswers.getAnswers(params[0]);
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

        return view.defaultCloseButton().show();
    }

    public FloatView showNewQuestion() throws Exception {
        if (mState.getLocalUser().getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
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
                    name = C.NAME_ANNOUNCEMENT;
                    break;
                case R.id.rb_ask:
                    name = C.NAME_QUESTION;
                    break;
                case R.id.rb_daily:
                    name = C.NAME_DAILY;
                    break;
                case R.id.rb_water:
                    name = C.NAME_WATER;
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

        return view.defaultCloseButton().show();
    }
    private FloatView showNewAnswer(final Question question) throws Exception {
        if (mState.getLocalUser().getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
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
        return view.defaultCloseButton().show();
    }
    public FloatView showNewReply(final Answer answer) throws Exception {
        if (mState.getLocalUser().getId() < 0) {
            throw Exceptions.E_NON_LOGIN;
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

        return view.defaultCloseButton().show();
    }
}
