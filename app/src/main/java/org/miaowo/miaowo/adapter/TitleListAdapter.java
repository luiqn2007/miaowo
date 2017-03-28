package org.miaowo.miaowo.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.Title;
import org.miaowo.miaowo.bean.data.web.TitleList;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.load_more_list.ItemRecyclerAdapter;
import org.miaowo.miaowo.view.load_more_list.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题列表的适配器,
 * Created by luqin on 17-3-15.
 */

public class TitleListAdapter
        extends ItemRecyclerAdapter<Title> {

    public TitleListAdapter(BaseActivity context) {
        super(new ArrayList<>(), new ViewLoader<Title>() {

            private MessageWindows messageWindows;

            @Override
            public ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.list_question_title, parent, false)
                );
            }

            @Override
            public void bindView(Title item, ViewHolder holder) {
                messageWindows = MessageWindows.windows(context);
                User u = item.getUser();
                holder.setOnClickListener((v) -> messageWindows.showQuestion(item.getSlug()), R.id.rl_item);
                ImageUtil.utils(context).setUser(holder.getImageView(R.id.iv_user), u, true);
                holder.getTextView(R.id.tv_user).setText(u.getUsername());
                holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.getLastposttime()));
                holder.getTextView(R.id.tv_page).setText(Html.fromHtml(item.getTitle()));
                holder.getTextView(R.id.tv_count).setText(item.getPostcount() + " 帖子, " + item.getViewcount() + " 浏览");
            }

            @Override
            public int setType(Title item, int position) {
                return 0;
            }
        });
        setSort(new DataSort<Title>() {
            @Override
            public int sortByHot(Title o1, Title o2) {
                return (o2.getLastposttime() - o1.getLastposttime()) >= 0 ? 1 : -1;
            }

            @Override
            public int sortByNew(Title o1, Title o2) {
                return (o2.getTimestamp() - o1.getTimestamp()) >= 0 ? 1 : -1;
            }
        });
    }

    public void update(TitleList data) {
        List<Title> items = getItems();
        items.clear();
        items.addAll(data.getTitles());
        notifyDataSetChanged();
    }
}
