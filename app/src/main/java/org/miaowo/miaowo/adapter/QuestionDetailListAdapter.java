package org.miaowo.miaowo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.QuestionDetail;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.List;

/**
 * 显示在 问题 对话框中的回答列表
 * Created by luqin on 17-3-17.
 */

public class QuestionDetailListAdapter extends BaseAdapter {
    private QuestionDetail mPage;
    private List<QuestionDetail.PostsBean> mItems;
    private BaseActivity mContext;

    public QuestionDetailListAdapter(BaseActivity context, QuestionDetail page) {
        mPage = page;
        mContext = context;
        mItems = mPage.getPosts();
    }

    @Override
    public int getCount() {
        return mPage == null ? 0 : mPage.getPosts().size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_answer, null);
            ViewHolder holder = new ViewHolder();
            holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
            holder.tv_context = (TextView) convertView.findViewById(R.id.tv_context);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_user = (TextView) convertView.findViewById(R.id.tv_user);
            convertView.setTag(holder);
        }
        ViewHolder mHolder = (ViewHolder) convertView.getTag();
        QuestionDetail.PostsBean question = (QuestionDetail.PostsBean) getItem(position);
        ImageUtil.utils(mContext).setUser(mHolder.iv_user, question.getUser(), true);
        mHolder.tv_user.setText(question.getUser().getUsername());
        mHolder.tv_time.setText(FormatUtil.format().time(question.getTimestamp()));
        mHolder.tv_context.setText(question.getContent());
        return convertView;
    }

    private class ViewHolder {
        TextView tv_time;
        TextView tv_user;
        TextView tv_context;
        ImageView iv_user;
    }
}
