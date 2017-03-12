package org.miaowo.miaowo.set.windows;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.Messages;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseSet;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.ViewFiller;
import org.miaowo.miaowo.view.FloatView;

import java.util.ArrayList;

/**
 * 有关消息，回复的弹窗集合
 * Created by luqin on 17-1-17.
 */

public class MessageWindows extends BaseSet {

    private Messages mMessages;
    private State mState;
    private D d;

    private BaseAdapter mAdapter;

    private MessageWindows() {
        d = D.getInstance();
        mMessages = new MsgImpl();
        mState = new StateImpl();
    }
    public static MessageWindows windows() { return new MessageWindows(); }

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

        ImageUtil.utils().setUser(iv_user, question.user, true);
        tv_user.setText(question.user.username);
        tv_title.setText(question.titleRaw);
        tv_question.setText(question.posts[0].content);
        tv_count.setText(question.postcount + " 回复, " + question.viewcount + " 浏览");
        btn_reply.setText("回答(" + question.postcount + ")");
        btn_reply.setOnClickListener(v1 -> showAnswers(question));
        btn_answer.setOnClickListener(v1 -> showNewAnswer(question, null));

        return view.defaultBar().show();
    }
    public FloatView showTopic(final Question question) {
        getArguments().put("answer_" + question.cid, new ArrayList<Answer>());
        FloatView view = new FloatView(R.layout.window_topic);
        View v = view.getView();

        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        Button btn_answer = (Button) v.findViewById(R.id.btn_send);
        ListView lv_answer = (ListView) v.findViewById(R.id.lv_answer);

        tv_title.setText(question.titleRaw);
        btn_answer.setOnClickListener(v1 -> showNewAnswer(question, null));
        lv_answer.setAdapter(new BaseAdapter() {
            ArrayList<Answer> answers;

            @Override
            public int getCount() {
                answers = (ArrayList<Answer>) getArguments().get("answer_" + question.cid);
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
        mMessages.loadTopics(Messages.SEARCH_POSITION_UP);

        return view.defaultBar().show();
    }
    private FloatView showAnswers(Question question) {
        getArguments().put("answer_" + question.cid, new ArrayList<Answer>());
        FloatView view = new FloatView(R.layout.window_normal_list);
        View v = view.getView();

        ListView list = (ListView) v.findViewById(R.id.list);
        mAdapter = new BaseAdapter() {
            ArrayList<Answer> answers;

            @Override
            public int getCount() {
                answers = (ArrayList<Answer>) getArguments().get("answer_" + question.cid);
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
        getArguments().put("answer_" + question.cid, question.posts);
        mAdapter.notifyDataSetChanged();
        return view.defaultBar().show();
    }

    public FloatView showNewQuestion() {
        if (!mState.isLogin()) {
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
            Question q = new Question();
            Answer a = new Answer();
            a.content = et_content.getText().toString();
            q.title = et_title.getText().toString();
            q.posts = new Answer[] { a };
            try {
                mMessages.sendQuestion(q);
                view.dismiss();
            } catch (Exception e) {
                d.activeActivity.handleError(e);
            }
        });

        return view.defaultBar().show();
    }
    private FloatView showNewAnswer(Question question, User reply) {
        if (!mState.isLogin()) {
            d.activeActivity.handleError(Exceptions.E_NON_LOGIN);
        }

        final FloatView view = new FloatView(R.layout.window_ask);
        View v = view.getView();

        final TextInputEditText et_title = (TextInputEditText) v.findViewById(R.id.et_title);
        final TextInputEditText et_content = (TextInputEditText) v.findViewById(R.id.et_content);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);
        v.findViewById(R.id.rg_type).setVisibility(View.GONE);
        if (reply != null) {
            et_title.setText("正在回复: " + reply.username);
        } else {
            et_title.setText("正在回答: " + question.title);
        }
        et_title.setEnabled(false);

        btn_send.setOnClickListener(vv -> {
            Answer a = new Answer();
            a.content = et_content.getText().toString();
            a.uid = D.getInstance().thisUser.uid;
            a.pid = question.cid;
            a.timestamp = System.currentTimeMillis();
            try {
                view.dismiss();
            } catch (Exception e) {
                d.activeActivity.handleError(e);
            }
        });
        return view.defaultBar().show();
    }
}
