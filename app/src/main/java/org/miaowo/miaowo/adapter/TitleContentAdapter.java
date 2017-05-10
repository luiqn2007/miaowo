package org.miaowo.miaowo.adapter;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Add;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseListAdapter;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.ArrayList;

public class TitleContentAdapter extends BaseListAdapter<Post> {

    private FormatUtil format;

    public TitleContentAdapter() {
        super(new ArrayList<>());
        format = FormatUtil.format();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(BaseActivity.get).inflate(R.layout.list_question, parent, false);
            BaseViewHolder holder = new BaseViewHolder(convertView);
            convertView.setTag(holder);
        }
        BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
        if (getItemViewType(position) == 1)
            ((CardView) holder.getView(R.id.rl_item)).setCardBackgroundColor(ResourcesCompat.getColor(BaseActivity.get.getResources(), R.color.md_lime_A400, null));
        Post item = (Post) getItem(position);
        User u = item.getUser();
        ImageUtil.utils().setUser((ImageView) holder.getView(R.id.iv_user), u, true);
        holder.setText(R.id.tv_user, u.getUsername());
        holder.setText(R.id.tv_time, format.time(item.getTimestamp()));
        holder.setText(R.id.tv_page, format.praseHtml(item.getContent()));
        holder.setClickListener(R.id.tv_reply, v -> {
            Intent intent = new Intent(BaseActivity.get, Add.class);
            intent.putExtra(Add.TAG, -1);
            intent.putExtra(Add.TITLE, item);
            BaseActivity.get.startActivityForResult(intent, 0);
        });
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
}
