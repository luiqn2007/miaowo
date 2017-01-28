package org.miaowo.miaowo.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.set.windows.MessageWindows;

import java.util.ArrayList;

/**
 * 用以填充视图
 * Created by luqin on 17-1-25.
 */

public class ViewFiller {
    private static MessageWindows mMessageWindows;

    public static View fillAnswer(View view, Answer answer, ArrayList<Answer> replies) {
        if (mMessageWindows == null) {
            mMessageWindows = new MessageWindows();
        }
        if (view == null) {
            view = View.inflate(D.getInstance().activeActivity, R.layout.list_answers, null);
            AnswerHolder holder = new AnswerHolder();
            holder.iv_user = (ImageView) view.findViewById(R.id.iv_user);
            holder.lv_replies = (ListView) view.findViewById(R.id.list);
            holder.tv_answer = (TextView) view.findViewById(R.id.tv_answer);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_count);
            holder.tv_user = (TextView) view.findViewById(R.id.tv_user);
            view.setTag(holder);
        }
        AnswerHolder holder = (AnswerHolder) view.getTag();
        holder.tv_answer.setText(answer.getMessage());
        holder.tv_user.setText(answer.getUser().getName());
        holder.tv_count.setText(replies.size() + " 回复");
        ImageUtil.fillImage(holder.iv_user, answer.getUser());
        holder.lv_replies.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return replies.size();
            }

            @Override
            public Object getItem(int position) {
                return replies.toArray()[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null || !(convertView instanceof TextView)) {
                    convertView = new TextView(D.getInstance().activeActivity);
                }
                TextView textView = (TextView) convertView;
                Answer reply = (Answer) getItem(position);
                if (reply.getReply().getQuestion() != null) {
                    textView.setText("回复 " + reply.getReply().getUser().getName() + ":" + reply.getMessage());
                } else {
                    textView.setText(reply.getMessage());
                }
                return convertView;
            }
        });
        holder.lv_replies.setOnItemClickListener((parent1, view1, position1, id) -> {
            try {
                mMessageWindows.showNewReply(answer);
            } catch (Exception e) {
                D.getInstance().activeActivity.handleError(e);
            }
        });
        return view;
    }
    public static class AnswerHolder {
        TextView tv_answer;
        TextView tv_count;
        public TextView tv_user;
        public ImageView iv_user;
        public ListView lv_replies;
    }
}
