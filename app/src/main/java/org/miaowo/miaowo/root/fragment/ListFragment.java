package org.miaowo.miaowo.root.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.adapter.TopicListAdapter;
import org.miaowo.miaowo.bean.data.web.TopicPage;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.view.LoadMoreList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListFragment extends Fragment {
    public static String TAG_NAME = "name";

    private LoadMoreList mList;
    private ItemRecyclerAdapter<TopicPage.TopicsBean> mAdapter;
    private List<TopicPage.TopicsBean> mItems;
    private BaseActivity mContext;

    private String name;
    private String url;

    public ListFragment() {}

    public static ListFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString(TAG_NAME, name);

        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(TAG_NAME);
        }
        assert name != null;
        switch (name) {
            case Miao.FRAGMENT_ANNOUNCEMENT:
                url = getString(R.string.url_announce);
                break;
            case Miao.FRAGMENT_DAILY:
                url = getString(R.string.url_daily);
                break;
            case Miao.FRAGMENT_QUESTION:
                url = getString(R.string.url_question);
                break;
            case Miao.FRAGMENT_WATER:
                url = getString(R.string.url_water);
                break;
            default:
                return;

        }
        mAdapter = new TopicListAdapter((BaseActivity) getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mList = (LoadMoreList) v.findViewById(R.id.list_item);
        mItems = new ArrayList<>();
        mContext = (BaseActivity) getActivity();
        MessageWindows mMessageWindows = MessageWindows.windows((BaseActivity) getActivity());
        initList();

        return v;
    }
    private void initList() {
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(() -> HttpUtil.utils().post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.handleError(Exceptions.E_WEB);
                mContext.runOnUiThread(() -> mList.loadOver());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TopicPage page = BeanUtil.utils().buildFromLastJson(response, TopicPage.class);
                if (page == null) {
                    mItems = new ArrayList<>();
                } else {
                    mItems = page.getTopics();
                }
                mContext.runOnUiThread(() -> {
                    mAdapter.updateDate(mItems);
                    mList.loadOver();
                });
            }
        }));
        mList.setPushRefresher(() -> mContext.handleError(Exceptions.E_NONE));
    }

    public String getName() {
        return name;
    }

    public void checkNew() {
        mList.pull();
    }

    public void sort(int type) {
        mAdapter.sortBy(type);
    }
}
