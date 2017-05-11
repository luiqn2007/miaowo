package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.bean.data.QuestionSearch;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.UserSearch;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.root.BaseListAdapter;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.set.MessageWindows;
import org.miaowo.miaowo.set.UserWindows;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.list) ListView lv_list;
    @BindView(R.id.et_topic) EditText et_topic;
    @BindView(R.id.ib_search) ImageButton ib_search;

    private boolean isUser = false;

    private FormatUtil mFormat = FormatUtil.format();

    public SearchFragment() {}

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    /* ================================================================ */
    private List<Post> mQuestions = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();
    private BaseListAdapter<Post> mTitleAdapter;
    private BaseListAdapter<User> mUserAdapter;

    private MessageWindows mMessageWindows;
    private UserWindows mUserWindows;

    @Override
    public void initView(View view) {
        mMessageWindows = MessageWindows.windows();
        mUserWindows = UserWindows.windows();
        mTitleAdapter = new BaseListAdapter<Post>(mQuestions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.list_answer, null);
                    BaseViewHolder holder = new BaseViewHolder(convertView);
                    convertView.setTag(holder);
                    Post question = (Post) getItem(position);
                    ImageUtil.utils().setUser((ImageView) holder.getView(R.id.iv_user), question.getUser(), true);
                    holder.setText(R.id.tv_user, question.getUser().getUsername());
                    holder.setText(R.id.tv_time, mFormat.time(question.getTimestamp()));
                    holder.setText(R.id.tv_context, mFormat.parseHtml(question.getContent()));
                    ((TextView) holder.getView(R.id.tv_context)).setMovementMethod(LinkMovementMethod.getInstance());
                }
                return convertView;
            }
        };
        mUserAdapter = new BaseListAdapter<User>(mUsers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.list_user_h, null);
                    BaseViewHolder holder = new BaseViewHolder(convertView);
                    convertView.setTag(holder);
                }
                BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
                User u = (User) getItem(position);
                holder.setText(R.id.tv_user, u.getUsername());
                ImageUtil.utils().setUser((ImageView) holder.getView(R.id.iv_user), u, false);
                return convertView;
            }
        };
        ib_search.setOnClickListener(v -> search(et_topic.getText().toString()));
        loadSearch();
    }

    private void loadSearch() {
        if (isUser) loadSearchUser();
        else loadSearchTopic();
        ib_search.setOnClickListener(v -> search(et_topic.getText().toString()));
    }

    private void loadSearchTopic() {
        ib_search.setImageDrawable(new IconicsDrawable(getContext(), FontAwesome.Icon.faw_calculator).actionBar());
        lv_list.setAdapter(mTitleAdapter);
        lv_list.setOnItemClickListener((parent, view, position, id) -> {
            Post item = (Post) mTitleAdapter.getItem(position);
            Title title = item.getTitle();
            mMessageWindows.showQuestion(title.getSlug());
        });
        mTitleAdapter.update(mQuestions);
    }

    private void loadSearchUser() {
        ib_search.setImageDrawable(new IconicsDrawable(getContext(), FontAwesome.Icon.faw_user).actionBar());
        lv_list.setAdapter(mUserAdapter);
        lv_list.setOnItemClickListener((parent, view, position, id) ->
                mUserWindows.showUserWindow(((User) mUserAdapter.getItem(position)).getUsername()));
        mUserAdapter.update(mUsers);
    }

    public void search(String key) {
        boolean lastUser = isUser;
        String url = String.format(getString(R.string.url_search),
                key, getString(isUser ? R.string.url_search_user : R.string.url_search_title));
        Request request = new Request.Builder().url(url).build();
        Call call = HttpUtil.utils().post(request, (call1, response) -> {
            if (lastUser) {
                UserSearch user = JsonUtil.utils().buildFromAPI(response, UserSearch.class);
                mUsers = user.getUsers();
            } else {
                QuestionSearch question = JsonUtil.utils().buildFromAPI(response, QuestionSearch.class);
                mQuestions = question.getPosts();
            }
            isUser = lastUser;
            BaseActivity.get.runOnUiThreadIgnoreError(this::loadSearch);
        });
        ib_search.setImageDrawable(new IconicsDrawable(getContext(), FontAwesome.Icon.faw_ban).actionBar());
        ib_search.setOnClickListener(v -> {
            call.cancel();
            loadSearch();
        });
    }
}
