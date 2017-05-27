package org.miaowo.miaowo.root;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.util.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 加载的 Fragment
 * 必须向 Arguments 传入一个 URL
 * Created by luqin on 17-5-4.
 */

public abstract class BaseListFragment<ITEM> extends BaseFragment {
    final public static String URL = "url";

    protected LoadMoreList mList;
    protected LMLPageAdapter<ITEM> mAdapter;
    protected String mUrl;
    protected int mPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new LoadMoreList(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView(View view) {
        mUrl = getArguments().getString("url");
        mPage = 1;
        mList = (LoadMoreList) view;
        mAdapter = setAdapter();
        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setPullRefresher(this::loadBackPage, true);
        mList.setPushRefresher(this::loadPage, true);
        mList.load();
    }

    private void loadPage() {
        mList.loadMoreControl(false, false);
        Request request = new Request.Builder()
                .url(mUrl + "?page=" + ++mPage)
                .build();
        HttpUtil.utils().post(request, (call, response) -> {
            mList.loadMoreControl(true, true);
            List<ITEM> items = getItems(response);
            BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                mList.loadOver();
                if (items == null || items.size() == 0) {
                    mPage--;
                    TastyToast.makeText(getContext(), getString(R.string.err_page_end), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                } else {
                    mAdapter.addPage(items, mPage);
                    mAdapter.removePage(mPage - 2);
                }
            });
        }, (call, e) -> {
            mList.loadMoreControl(true, true);
            BaseActivity.get.handleError(e);
            BaseActivity.get.runOnUiThreadIgnoreError(() -> mList.loadOver());
        });
    }
    private void loadBackPage() {
        mList.loadMoreControl(false, false);

        Request request = new Request.Builder()
                .url(mUrl + "?page=" + (mPage == 1 ? mPage : --mPage))
                .build();
        HttpUtil.utils().post(request, (call, response) -> {
                    mList.loadMoreControl(true, true);
                    List<ITEM> items = getItems(response);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        mList.loadOver();
                        if (items == null || items.size() == 0) {
                            TastyToast.makeText(getContext(), getString(R.string.err_page_end), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                        } else {
                            if (mPage == 1) mAdapter.clear();
                            mAdapter.addPage(items, mPage, true);
                            if (mPage == 1) mAdapter.getPage(mPage).index = true;
                            mAdapter.removePage(mPage + 2);
                        }
                    });
                }
                , (call, e) -> {
                    mList.loadMoreControl(true, true);
                    BaseActivity.get.handleError(e);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> mList.loadOver());
                });
    }

    abstract public LMLPageAdapter<ITEM> setAdapter();
    abstract public List<ITEM> getItems(Response response) throws IOException;
}
