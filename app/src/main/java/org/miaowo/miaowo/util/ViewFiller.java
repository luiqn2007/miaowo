package org.miaowo.miaowo.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Answer;
import org.miaowo.miaowo.set.windows.MessageWindows;

/**
 * 用以填充视图
 * Created by luqin on 17-1-25.
 */

public class ViewFiller {
    private static MessageWindows mMessageWindows;

    public static View fillAnswer(View view, Answer answer) {
        if (mMessageWindows == null) {
            mMessageWindows = new MessageWindows();
        }
        if (view == null) {
            view = View.inflate(D.getInstance().activeActivity, R.layout.list_answers, null);
            AnswerHolder holder = new AnswerHolder();
            holder.iv_user = (ImageView) view.findViewById(R.id.iv_user);
            holder.tv_answer = (TextView) view.findViewById(R.id.tv_answer);
            holder.tv_user = (TextView) view.findViewById(R.id.tv_user);
            view.setTag(holder);
        }
        AnswerHolder holder = (AnswerHolder) view.getTag();
        holder.tv_answer.setText(answer.getMessage());
        holder.tv_answer.setOnClickListener(v -> {
            try {
                mMessageWindows.showNewReply(answer);
            } catch (Exception e) {
                D.getInstance().activeActivity.handleError(e);
            }
        });
        holder.tv_user.setText(answer.getUser().getName());
        ImageUtil.fillImage(holder.iv_user, answer.getUser());
        return view;
    }
    private static class AnswerHolder {
        TextView tv_answer;
        TextView tv_user;
        ImageView iv_user;
    }
}
