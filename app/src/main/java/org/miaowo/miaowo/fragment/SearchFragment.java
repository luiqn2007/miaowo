package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.Post;
import org.miaowo.miaowo.bean.data.web.QuestionSearch;
import org.miaowo.miaowo.bean.data.web.Title;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.bean.data.web.UserSearch;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseListAdapter;
import org.miaowo.miaowo.root.fragment.BaseFragment;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.set.windows.UserWindows;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.load_more_list.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchFragment extends BaseFragment {

    private ListView lv_list;
    private EditText et_topic;
    private ImageButton ib_search;
    private BaseActivity mContext;
    private boolean isUser = false;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        bind(view);
        return view;
    }

    private void bind(View view) {
        lv_list = (ListView) view.findViewById(R.id.list);
        et_topic = (EditText) view.findViewById(R.id.et_topic);
        ib_search = (ImageButton) view.findViewById(R.id.ib_search);
        mContext = (BaseActivity) getActivity();
        start();
    }

    /* ================================================================ */
    private List<Post> mQuestions = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();
    private BaseListAdapter<Post> mTitleAdapter;
    private BaseListAdapter<User> mUserAdapter;

    private MessageWindows mMessageWindows;
    private UserWindows mUserWindows;

    private void start() {
        mMessageWindows = MessageWindows.windows((BaseActivity) getActivity());
        mUserWindows = UserWindows.windows((BaseActivity) getActivity());
        mTitleAdapter = new BaseListAdapter<Post>(mQuestions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.list_answer, null);
                    ViewHolder holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                    Post question = (Post) getItem(position);
                    ImageUtil.utils(mContext).setUser(holder.getImageView(R.id.iv_user), question.getUser(), true);
                    holder.getTextView(R.id.tv_user).setText(question.getUser().getUsername());
                    holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(question.getTimestamp()));
                    holder.getTextView(R.id.tv_context).setText(Html.fromHtml(question.getContent()));
                    holder.getTextView(R.id.tv_context).setMovementMethod(LinkMovementMethod.getInstance());
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();

                return convertView;
            }
        };
        mUserAdapter = new BaseListAdapter<User>(mUsers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.list_user_h, null);
                    ViewHolder holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();
                User u = (User) getItem(position);
                holder.getTextView(R.id.tv_user).setText(u.getUsername());
                ImageUtil.utils((BaseActivity) getActivity()).setUser(holder.getImageView(R.id.iv_user), u, false);
                return convertView;
            }
        };
        ib_search.setOnClickListener(v -> search(et_topic.getText().toString()));
        loadSearch();
    }

    private void loadSearch() {
        if (isUser) {
            loadSearchUser();
        } else {
            loadSearchTopic();
        }
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
        lv_list.setOnItemClickListener((parent, view, position, id) -> {
            User item = (User) mUserAdapter.getItem(position);
            mUserWindows.showUserWindow(item.getUsername());
        });
        mUserAdapter.update(mUsers);
    }

    public void search(String key) {
        boolean lastUser = isUser;
        String url = getString(R.string.url_home)
                + getString(R.string.url_search) + key
                + getString(isUser ? R.string.url_search_user : R.string.url_search_title);
        Call call = HttpUtil.utils().post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((BaseActivity) getActivity()).handleError(Exceptions.E_WEB);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (lastUser) {
                    UserSearch user = BeanUtil.utils().buildFromLastJson(response, UserSearch.class);
                    mUsers = user.getUsers();
                } else {
                    QuestionSearch question = BeanUtil.utils().buildFromLastJson(response, QuestionSearch.class);
                    mQuestions = question.getPosts();
                }
                isUser = lastUser;
                getActivity().runOnUiThread(() -> loadSearch());
            }
        });
        ib_search.setImageDrawable(new IconicsDrawable(getContext(), FontAwesome.Icon.faw_ban).actionBar());
        ib_search.setOnClickListener(v -> {
            call.cancel();
            loadSearch();
        });
    }

    @Override
    protected AnimatorController setAnimatorController() {
        return null;
    }

    @Override
    protected ProcessController setProcessController() {
        return null;
    }
}
