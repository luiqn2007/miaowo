package org.miaowo.miaowo.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Add;
import org.miaowo.miaowo.adapter.TitleListAdapter;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.TitleList;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.fragment.setting.AppSetting;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.root.BaseListFragment;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.Response;

public class SquareFragment extends BaseFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int ID_0;
    private int ID_1;
    private int ID_2;
    private int ID_3;

    @BindView(R.id.tab) TabLayout mTab;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.bottomNavigation) BottomNavigationView mNavigation;
    private SparseArray<TitleListFragment> mFragments;
    private int mFragmentId = -1;
    private API mApi;

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
        mFragmentId = item.getItemId();
        if (!mFab.isEnabled()) {
            ObjectAnimator.ofPropertyValuesHolder(mFab
                    , PropertyValuesHolder.ofFloat("translationY", 500, 0)
                    , PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(300).start();
            mFab.setEnabled(true);
        }
        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, mFragments.get(mFragmentId))
                .commit();
        TabLayout.Tab tab = mTab.getTabAt(mFragmentId);
        if (tab != null) tab.select();
        return true;
    }

    @Override
    public void initView(View view) {
        mApi = new API();
        initTabLayout();
        initNavigation();
        if (mFragmentId < 0) mNavigation.setSelectedItemId(ID_0);

        boolean modeTab = SpUtil.defaultSp().getBoolean(AppSetting.SETTING_APP_TAB, false);
        mTab.setVisibility(modeTab ? View.VISIBLE : View.GONE);
        mNavigation.setVisibility(modeTab ? View.GONE : View.VISIBLE);
    }

    private void initTabLayout() {
        TabLayout.Tab tab_daily = mTab.newTab().setText(getString(R.string.daily));
//        tab_daily.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.daily, null));
        TabLayout.Tab tab_announcement = mTab.newTab().setText(getString(R.string.announcement));
//        tab_daily.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.announcement, null));
        TabLayout.Tab tab_question = mTab.newTab().setText(getString(R.string.ask));
//        tab_daily.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ask, null));
        TabLayout.Tab tab_water = mTab.newTab().setText(getString(R.string.water));
//        tab_daily.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.water, null));
        mTab.addTab(tab_daily);
        mTab.addTab(tab_announcement);
        mTab.addTab(tab_question);
        mTab.addTab(tab_water);

        ID_0 = tab_daily.getPosition();
        ID_1 = tab_announcement.getPosition();
        ID_2 = tab_question.getPosition();
        ID_3 = tab_water.getPosition();

        mFragments = new SparseArray<>();
        mFragments.put(ID_0, TitleListFragment.newInstance(getString(R.string.url_daily)));
        mFragments.put(ID_1, TitleListFragment.newInstance(getString(R.string.url_announce)));
        mFragments.put(ID_2, TitleListFragment.newInstance(getString(R.string.url_question)));
        mFragments.put(ID_3, TitleListFragment.newInstance(getString(R.string.url_water)));

        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mFragmentId == tab.getPosition()) return;
                mNavigation.setSelectedItemId(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initNavigation() {
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.md_amber_100, null));
        mNavigation.setItemIconTintList(ResourcesCompat.getColorStateList(getResources(), R.color.selector_icon, null));
        Menu menu = mNavigation.getMenu();
        menu.add(0, ID_0, 0, getString(R.string.daily));
        menu.add(0, ID_1, 1, getString(R.string.announcement));
        menu.add(0, ID_2, 2, getString(R.string.ask));
        menu.add(0, ID_3, 3, getString(R.string.water));
        menu.getItem(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.daily, null));
        menu.getItem(1).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.announcement, null));
        menu.getItem(2).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ask, null));
        menu.getItem(3).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.water, null));
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Intent intent = new Intent(getContext(), Add.class);
        intent.putExtra(Add.TAG, mNavigation.getSelectedItemId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode > 0) {
            int pageId = data.getIntExtra(Add.TAG, mNavigation.getSelectedItemId());
            String title = data.getStringExtra(Add.TITLE);
            String content = data.getStringExtra(Add.CONTENT);
            FormBody body = new FormBody.Builder()
                    .add("cid", mFragments.get(pageId).getCid())
                    .add("title", title)
                    .add("content", content)
                    .build();
            mApi.useAPI(API.APIType.TOPICS, "", API.Method.POST, true, body, (call, response) -> {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    if (!"ok".equals(object.getString("code"))) throw new Exception(object.getString("message"));
                } catch (Exception e) {
                    BaseActivity.get.handleError(e);
                }
            });
        }
    }

    public static class TitleListFragment extends BaseListFragment<Title> {

        public TitleListFragment() {}
        public static TitleListFragment newInstance(String url) {
            Bundle args = new Bundle();
            TitleListFragment fragment = new TitleListFragment();
            fragment.mUrl = url;
            args.putString(URL, url);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public LMLPageAdapter<Title> setAdapter() {
            return new TitleListAdapter();
        }

        @Override
        public List<Title> getItems(Response response) throws IOException {
            TitleList list = JsonUtil.utils().buildFromAPI(response, TitleList.class);
            if (list != null) {
                return list.getTitles();
            }
            return null;
        }

        public String getCid() {
            return mUrl.split("/")[5];
        }
    }
}
