package org.miaowo.miaowo.adapter;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.List;

/**
 * 显示在 问题 对话框中的回答列表
 * Created by luqin on 17-3-17.
 */

public class QuestionDetailListAdapter extends BaseAdapter {
    private Question mPage;
    private List<Post> mItems;
    private BaseActivity mContext;

    public QuestionDetailListAdapter(BaseActivity context, Question page) {
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
            BaseViewHolder holder = new BaseViewHolder(convertView);
            convertView.setTag(holder);
        }
        BaseViewHolder mHolder = (BaseViewHolder) convertView.getTag();
        Post question = (Post) getItem(position);
        ImageUtil.utils().setUser((ImageView) mHolder.getView(R.id.iv_user), question.getUser(), true);
        mHolder.setText(R.id.tv_user, question.getUser().getUsername());
        mHolder.setText(R.id.tv_time, FormatUtil.format().time(question.getTimestamp()));
        mHolder.setText(R.id.tv_context, Html.fromHtml(question.getContent()));
        ((TextView) mHolder.getView(R.id.tv_context)).setMovementMethod(LinkMovementMethod.getInstance());
        return convertView;
    }
}
