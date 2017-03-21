package org.miaowo.miaowo.adapter;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.Post;
import org.miaowo.miaowo.bean.data.web.Question;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.load_more_list.ViewHolder;

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
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder mHolder = (ViewHolder) convertView.getTag();
        Post question = (Post) getItem(position);
        ImageUtil.utils(mContext).setUser(mHolder.getImageView(R.id.iv_user), question.getUser(), true);
        mHolder.getTextView(R.id.tv_user).setText(question.getUser().getUsername());
        mHolder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(question.getTimestamp()));
        mHolder.getTextView(R.id.tv_context).setText(Html.fromHtml(question.getContent()));
        mHolder.getTextView(R.id.tv_context).setMovementMethod(LinkMovementMethod.getInstance());
        return convertView;
    }
}
