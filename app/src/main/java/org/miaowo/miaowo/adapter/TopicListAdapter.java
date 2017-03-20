package org.miaowo.miaowo.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.web.TopicPage;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题列表的适配器,
 * Created by luqin on 17-3-15.
 */

public class TopicListAdapter
        extends ItemRecyclerAdapter<TopicPage.TopicsBean> {

    public TopicListAdapter(BaseActivity context) {
        super(new ArrayList<>(), new ViewLoader<TopicPage.TopicsBean>() {

            private MessageWindows messageWindows;

            @Override
            public ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new ItemRecyclerAdapter.ViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.list_question_title, parent, false)
                );
            }

            @Override
            public void bindView(TopicPage.TopicsBean item, ViewHolder holder) {
                messageWindows = MessageWindows.windows(context);
                User u = item.getUser();
                holder.setOnClickListener((v) -> messageWindows.showQuestion(item.getSlug()), R.id.rl_item);
                ImageUtil.utils(context).setUser(holder.getImageView(R.id.iv_user), u, true);
                holder.getTextView(R.id.tv_user).setText(u.getUsername());
                holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.getLastposttime()));
                holder.getTextView(R.id.tv_title).setText(item.getTitleRaw());
                holder.getTextView(R.id.tv_count).setText(item.getPostcount() + " 帖子, " + item.getViewcount() + " 浏览");
            }

            @Override
            public int setType(TopicPage.TopicsBean item, int position) {
                return 0;
            }
        });
        setSort(new DataSort<TopicPage.TopicsBean>() {
            @Override
            public int sortByHot(TopicPage.TopicsBean o1, TopicPage.TopicsBean o2) {
                return (o2.getLastposttime() - o1.getLastposttime()) >= 0 ? 1 : -1;
            }

            @Override
            public int sortByNew(TopicPage.TopicsBean o1, TopicPage.TopicsBean o2) {
                return (o2.getTimestamp() - o1.getTimestamp()) >= 0 ? 1 : -1;
            }
        });
    }

    public void update(TopicPage data) {
        List<TopicPage.TopicsBean> items = getItems();
        items.clear();
        items.addAll(data.getTopics());
        notifyDataSetChanged();
    }
}
