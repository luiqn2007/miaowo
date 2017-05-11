package org.miaowo.miaowo.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Detail;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.util.ArrayList;

/**
 * 问题列表的适配器,
 * Created by luqin on 17-3-15.
 */

public class TitleListAdapter
        extends LMLPageAdapter<Title> {

    public TitleListAdapter() {
        super(new ArrayList<>(), new ViewLoaderCreator<Title>() {
            FormatUtil format = FormatUtil.format();

            @Override
            public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(
                        LayoutInflater.from(BaseActivity.get).inflate(R.layout.list_question_title, parent, false)
                );
            }

            @Override
            public void bindView(Title item, RecyclerView.ViewHolder holder, int type) {
                User u = item.getUser();
                BaseViewHolder vh = (BaseViewHolder) holder;
                vh.setClickListener(R.id.rl_item, (v) -> LogUtil.i("click"));
                ImageUtil.utils().setUser((ImageView) vh.getView(R.id.iv_user), u, true);
                vh.setText(R.id.tv_user, u.getUsername());
                vh.setText(R.id.tv_time, format.time(item.getLastposttime()));
                vh.setText(R.id.tv_page, format.parseHtml(item.getTitle()));
                vh.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(BaseActivity.get, Detail.class);
                    intent.putExtra(Detail.TITLE, item);
                    BaseActivity.get.startActivity(intent);
                });
            }

            @Override
            public int setType(Title item, int position) {
                return 0;
            }
        });
    }
}
