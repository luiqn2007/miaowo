package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.TitleListAdapter;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.TitleList;
import org.miaowo.miaowo.custom.load_more_list.LMLAdapter;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

public class UnreadFragment extends BaseFragment {
    private LoadMoreList mList;
    private LMLAdapter<Title> mAdapter;

    private String url;
    private int mPageIndex;

    public UnreadFragment() {}

    public static UnreadFragment newInstance() {
        Bundle args = new Bundle();

        UnreadFragment fragment = new UnreadFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getString(R.string.url_unread);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return new LoadMoreList(getContext());
    }

    @Override
    public void initView(View view) {
        mList = (LoadMoreList) view;
        mAdapter = new TitleListAdapter();
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(() -> {
            mPageIndex = 0;
            loadPage();
        });
        mList.setPushRefresher(this::loadPage);
        mList.load();
    }

    private void loadPage() {
        mList.loadMoreControl(false, false);
        HttpUtil.utils().post(url + "?page=" + ++mPageIndex
                , (call, response) -> {
                    mList.loadMoreControl(true, true);
                    TitleList page =
                            JsonUtil.utils().buildFromAPI(response, TitleList.class);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        mList.loadOver();
                        if (page == null || page.getTitles().size() == 0) {
                            mPageIndex--;
                            TastyToast.makeText(getContext(), getString(R.string.err_page_end), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                        } else {
                            mAdapter.appendData(page.getTitles(), false);
                        }
                    });
                }
                , (call, e) -> {
                    mList.loadMoreControl(true, true);
                    BaseActivity.get.toast(e.getMessage(), TastyToast.ERROR);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> mList.loadOver());
                });
    }
}
