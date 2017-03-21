package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.Title;
import org.miaowo.miaowo.bean.data.web.TitleList;
import org.miaowo.miaowo.bean.data.web.Topic;
import org.miaowo.miaowo.bean.data.web.TopicList;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseListAdapter;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.load_more_list.ViewHolder;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TopicFragment extends Fragment {

    private ListView lv_list;
    private PopupMenu mMenu;
    private TextView tv_topic;
    private ImageButton ib_load;
    private BaseActivity mContext;

    public TopicFragment() {
        // Required empty public constructor
    }
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
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        bind(view);
        return view;
    }
    private void bind(View view) {
        lv_list = (ListView) view.findViewById(R.id.list);
        tv_topic = (TextView) view.findViewById(R.id.tv_topic);
        ib_load = (ImageButton) view.findViewById(R.id.ib_choose);
        mContext = (BaseActivity) getActivity();
        mMenu = new PopupMenu(getContext(), ib_load);
        ib_load.setOnClickListener(v -> mMenu.show());
        start();
    }
    /* ================================================================ */
    private List<Topic> mTopics;
    private List<Title> mQuestions;
    private BaseListAdapter<Title> mAdapter;

    private void start() {
        mAdapter = new BaseListAdapter<Title>(mQuestions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.list_question_title, null);
                    ViewHolder holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();
                MessageWindows messageWindows = MessageWindows.windows(mContext);
                Title item = (Title) getItem(position);
                User u =item.getUser();
                holder.setOnClickListener((v) -> messageWindows.showQuestion(item.getSlug()), R.id.rl_item);
                ImageUtil.utils(mContext).setUser(holder.getImageView(R.id.iv_user), u, true);
                holder.getTextView(R.id.tv_user).setText(u.getUsername());
                holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.getLastposttime()));
                holder.getTextView(R.id.tv_title).setText(Html.fromHtml(item.getTitle()));
                holder.getTextView(R.id.tv_count).setText(item.getPostcount() + " 帖子, " + item.getViewcount() + " 浏览");
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
        mMenu.setOnMenuItemClickListener(null);
    }

    private boolean loadTags() {
        initMenu();
        HttpUtil.utils().post(getContext().getString(R.string.url_tags), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> {
                    tv_topic.setText("加载失败");
                    mMenu.getMenu().clear();
                    mMenu.getMenu().add("重新加载");
                    mMenu.setOnMenuItemClickListener(item -> loadTags());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TopicList topicList = BeanUtil.utils().buildFromLastJson(response, TopicList.class);
                mTopics = topicList.getTags();
                getActivity().runOnUiThread(() -> {
                    tv_topic.setText("请选择话题");
                    Menu menu = mMenu.getMenu();
                    menu.clear();
                    for (Topic topic : mTopics) {
                        menu.add(topic.getValue());
                    }
                    menu.addSubMenu("其他").add("重新加载");
                    mMenu.setOnMenuItemClickListener(item -> openTag(item));
                });
            }
        });
        return true;
    }

    private boolean openTag(MenuItem item) {
        if (item.getSubMenu() != null) return loadTags();
        String title = item.getTitle().toString();
        tv_topic.setText("正在加载: " + title);
        HttpUtil.utils().post(mContext.getString(R.string.url_tags) + title, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> tv_topic.setText("加载失败"));
                mContext.handleError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TitleList topicPage = BeanUtil.utils().buildFromLastJson(response, TitleList.class);
                mQuestions = topicPage.getTitles();
                getActivity().runOnUiThread(() -> {
                    tv_topic.setText(title);
                    mAdapter.update(mQuestions);
                });
            }
        });
        return true;
    }

}
