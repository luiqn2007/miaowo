package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.TitleList;
import org.miaowo.miaowo.bean.data.Topic;
import org.miaowo.miaowo.bean.data.TopicList;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.root.BaseListAdapter;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.set.MessageWindows;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Request;

public class TopicFragment extends BaseFragment {

    @BindView(R.id.list) ListView lv_list;
    @BindView(R.id.et_topic) TextView tv_topic;
    @BindView(R.id.ib_search) ImageButton ib_load;
    private PopupMenu mMenu;
    private FormatUtil format = FormatUtil.format();

    public TopicFragment() {}
    public static TopicFragment newInstance() {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    /* ================================================================ */
    private List<Topic> mTopics;
    private BaseListAdapter<Title> mAdapter;

    @Override
    public void initView(View view) {
        mMenu = new PopupMenu(getContext(), ib_load);
        ib_load.setOnClickListener(v -> mMenu.show());
        mAdapter = new BaseListAdapter<Title>(new ArrayList<>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.list_question_title, null);
                    BaseViewHolder holder = new BaseViewHolder(convertView);
                    convertView.setTag(holder);
                }
                BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
                MessageWindows messageWindows = MessageWindows.windows();
                Title item = (Title) getItem(position);
                User u =item.getUser();
                holder.setClickListener(R.id.rl_item, (v) -> messageWindows.showQuestion(item.getSlug()));
                ImageUtil.utils().setUser((ImageView) holder.getView(R.id.iv_user), u, true);
                holder.setText(R.id.tv_user, u.getUsername());
                holder.setText(R.id.tv_time, format.time(item.getLastposttime()));
                holder.setText(R.id.tv_page, format.praseHtml(item.getTitle()));
                return convertView;
            }
        };
        lv_list.setAdapter(mAdapter);
        loadTags();
    }

    private void initMenu() {
        Menu menu = mMenu.getMenu();
        tv_topic.setText("加载中...");
        menu.clear();
        menu.add("加载中");
        mMenu.setOnMenuItemClickListener(item -> false);
    }

    private boolean loadTags() {
        initMenu();
        Request request = new Request.Builder().url(String.format(getContext().getString(R.string.url_tags), "")).build();
        HttpUtil.utils().post(request, (call, response) -> {
                    TopicList topicList = JsonUtil.utils().buildFromAPI(response, TopicList.class);
                    mTopics = topicList.getTags();
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        tv_topic.setText("请选择话题");
                        Menu menu = mMenu.getMenu();
                        menu.clear();
                        for (Topic topic : mTopics) {
                            menu.add(topic.getValue());
                        }
                        menu.addSubMenu("其他").add("重新加载");
                        mMenu.setOnMenuItemClickListener(this::openTag);
                    });
                },
                (call, e) -> {
                    tv_topic.setText("加载失败");
                    mMenu.getMenu().clear();
                    mMenu.getMenu().add("重新加载");
                    mMenu.setOnMenuItemClickListener(item -> loadTags());
                });
        return true;
    }

    private boolean openTag(MenuItem item) {
        if (item.getSubMenu() != null) return loadTags();
        String title = item.getTitle().toString();
        tv_topic.setText(String.format(getString(R.string.data_loading_detail), title));
        Request request = new Request.Builder().url(String.format(getString(R.string.url_tags), title)).build();
        HttpUtil.utils().post(request, (call, response) -> {
                    TitleList topicPage = JsonUtil.utils().buildFromAPI(response, TitleList.class);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> tv_topic.setText(title));
                    mAdapter.update(topicPage.getTitles());
                },
                (call, e) -> {
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> tv_topic.setText("加载失败"));
                    BaseActivity.get.toast(e.getMessage(), TastyToast.ERROR);
                });
        return true;
    }
}
