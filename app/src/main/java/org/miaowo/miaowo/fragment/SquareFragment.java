package org.miaowo.miaowo.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Add;
import org.miaowo.miaowo.adapter.TitleListAdapter;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.TitleList;
import org.miaowo.miaowo.custom.load_more_list.LMLAdapter;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SquareFragment extends BaseFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.list) LoadMoreList mList;
    @BindView(R.id.bottomNavigation) BottomNavigationView mNavigation;
    private LMLAdapter<Title> mAdapter;
    private int mPageIndex;
    private int mPageId = -1;
    private SparseArray<String> mUrls;

    public SquareFragment() {}
    public static SquareFragment newInstance() {
        SquareFragment fragment = new SquareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_square, container, false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mPageIndex = 0;
        mPageId = item.getItemId();
        if (!mFab.isEnabled()) {
            ObjectAnimator.ofPropertyValuesHolder(mFab
                    , PropertyValuesHolder.ofFloat("translationY", 500, 0)
                    , PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(300).start();
            mFab.setEnabled(true);
        }
        loadPage();
        return true;
    }

    @Override
    public void initView(View view) {
        mUrls = new SparseArray<>();
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.setBackgroundColor(getResources().getColor(R.color.md_amber_100));
        mNavigation.setItemIconTintList(getResources().getColorStateList(R.color.selector_icon));
        Menu menu = mNavigation.getMenu();
        menu.add(0, 0, 0, getString(R.string.daily));
        menu.add(0, 1, 1, getString(R.string.announcement));
        menu.add(0, 2, 2, getString(R.string.ask));
        menu.add(0, 3, 3, getString(R.string.water));
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.daily));
        mUrls.put(0, getString(R.string.url_daily));
        menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.announcement));
        mUrls.put(1, getString(R.string.url_announce));
        menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ask));
        mUrls.put(2, getString(R.string.url_question));
        menu.getItem(3).setIcon(getResources().getDrawable(R.drawable.water));
        mUrls.put(3, getString(R.string.url_water));
        if (mPageId < 0) mPageId = 0;

        mAdapter = new TitleListAdapter((BaseActivity) getActivity());
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(() -> {
            mPageIndex = 0;
            loadPage();
        });
        mList.setPushRefresher(this::loadPage);
        mList.load();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Intent intent = new Intent(getContext(), Add.class);
        intent.putExtra(Add.TAG, mPageId);
        startActivityForResult(intent, 0);
    }

    private void loadPage() {
        mList.loadMoreControl(false, false);
        String url = mUrls.get(mPageId);
        if (mPageIndex == 0) mAdapter.clear();
        HttpUtil.utils().post(url + "?page=" + ++mPageIndex, (call, response) -> {
                    mList.loadMoreControl(true, true);
                    TitleList page = JsonUtil.utils().buildFromAPI(response, TitleList.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i(requestCode, resultCode);
        if (data != null && resultCode > 0) {
            int pageId = data.getIntExtra(Add.TAG, mPageId);
            String title = data.getStringExtra(Add.TITLE);
            String content = data.getStringExtra(Add.CONTENT);
            LogUtil.i(pageId, title, content);
        } else {
            BaseActivity.get.toast("无", TastyToast.ERROR);
        }
    }
}
