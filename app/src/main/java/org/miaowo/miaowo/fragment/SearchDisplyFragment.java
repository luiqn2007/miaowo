package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.web.QuestionSearchPage;
import org.miaowo.miaowo.bean.data.web.UserSearchPage;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 显示搜索结果的 Fragment
 */
public class SearchDisplyFragment extends Fragment {
    final private static String TAG_TYPE = "layout";
    final private static String TAG_RESULT = "result";

    final public static int TYPE_USER = 0;
    final public static int TYPE_QUESTION = 1;

    private List result;
    private int type;

    private MessageWindows mMessageWindows;

    public SearchDisplyFragment() {
        // Required empty public constructor
    }

    public static SearchDisplyFragment newInstance(int type, ArrayList result) {
        SearchDisplyFragment fragment = new SearchDisplyFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_TYPE, type);
        args.putSerializable(TAG_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TAG_TYPE);
            result = (ArrayList) getArguments().getSerializable(TAG_RESULT);
        }

        mMessageWindows = MessageWindows.windows((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        ListView mList = (ListView) v.findViewById(R.id.lv_result);

        switch (type) {
            case TYPE_QUESTION:
                tv_title.setText("问题");
                break;
            case TYPE_USER:
                tv_title.setText("用户");
                break;
        }

        mList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return result == null ? 0 : result.size();
            }

            @Override
            public Object getItem(int position) {
                return result.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                switch (type) {
                    case TYPE_QUESTION:
                        convertView = setQuestion((QuestionSearchPage.PostsBean) getItem(position), convertView);
                        break;
                    case TYPE_USER:
                        convertView = setUser((UserSearchPage.UsersBean) getItem(position), convertView);
                        break;
                    default:
                        convertView = null;
                        break;
                }
                return convertView;
            }
        });
    }

    private View setQuestion(QuestionSearchPage.PostsBean item, View view) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.list_question_title, null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        User u = item.getUser();
        holder.setOnClickListener((v) -> mMessageWindows.showQuestion(item.getTopic().getSlug()), R.id.rl_item);
        ImageUtil.utils((BaseActivity) getActivity()).setUser(holder.getImageView(R.id.iv_user), u, true);
        holder.getTextView(R.id.tv_user).setText(u.getUsername());
        holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.getTimestamp()));
        holder.getTextView(R.id.tv_title).setText(item.getTopic().getTitle());
        holder.getTextView(R.id.tv_count).setText(item.getTopic().getPostcount()+ " 帖子");

        return view;
    }

    private View setUser(UserSearchPage.UsersBean item, View view) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.list_chat, null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.getTextView(R.id.tv_user).setText(item.getUsername());
        ImageUtil.utils((BaseActivity) getActivity()).setUser(holder.getImageView(R.id.iv_user), item, true);
        return view;
    }

    private static class ViewHolder {
        private SparseArray<View> mViews;
        private View itemView;

        ViewHolder(@NonNull View view) {
            itemView = view;
            mViews = new SparseArray<>();
        }

        private View findViewById(int viewId) {
            View view;
            view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }

        TextView getTextView(int viewId) {
            return (TextView) findViewById(viewId);
        }

        ImageView getImageView(int viewId) {
            return (ImageView) findViewById(viewId);
        }

        public ViewHolder setOnClickListener(View.OnClickListener listener, int... viewId) {
            for (int i : viewId) {
                View view = findViewById(i);
                view.setOnClickListener(listener);
            }
            return this;
        }
    }

    public void search(String key) {
        String url = getString(R.string.url_home)
                + getString(R.string.url_search) + key
                + getString(type == TYPE_USER ? R.string.url_search_user : R.string.url_search_title);
        HttpUtil.utils().post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((BaseActivity) getActivity()).handleError(Exceptions.E_WEB);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                switch (type) {
                    case TYPE_QUESTION:
                        QuestionSearchPage question = BeanUtil.utils().buildFromLastJson(response, QuestionSearchPage.class);
                        result = question.getPosts();
                        break;
                    case TYPE_USER:
                        UserSearchPage user = BeanUtil.utils().buildFromLastJson(response, UserSearchPage.class);
                        result = user.getUsers();
                        break;
                }
            }
        });
    }
}
