package org.miaowo.miaowo.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Add;
import org.miaowo.miaowo.adapter.TitleListAdapter;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.TitleList;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.root.BaseListFragment;
import org.miaowo.miaowo.util.JsonUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.Response;

public class SquareFragment extends BaseFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener {
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
        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, mFragments.get(item.getItemId()))
                .commit();
        if (!mFab.isEnabled()) {
            ObjectAnimator.ofPropertyValuesHolder(mFab
                    , PropertyValuesHolder.ofFloat("translationY", 500, 0)
                    , PropertyValuesHolder.ofFloat("alpha", 0, 1)).setDuration(300).start();
            mFab.setEnabled(true);
        }
        return true;
    }

    @Override
    public void initView(View view) {
        mApi = new API();
        mFragments = new SparseArray<>();
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.md_amber_100, null));
        mNavigation.setItemIconTintList(ResourcesCompat.getColorStateList(getResources(), R.color.selector_icon, null));
        Menu menu = mNavigation.getMenu();
        menu.add(0, 0, 0, getString(R.string.daily));
        menu.add(0, 1, 1, getString(R.string.announcement));
        menu.add(0, 2, 2, getString(R.string.ask));
        menu.add(0, 3, 3, getString(R.string.water));
        menu.getItem(0).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.daily, null));
        mFragments.put(menu.getItem(0).getItemId(), TitleListFragment.newInstance(getString(R.string.url_daily)));
        menu.getItem(1).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.announcement, null));
        mFragments.put(menu.getItem(1).getItemId(), TitleListFragment.newInstance(getString(R.string.url_announce)));
        menu.getItem(2).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ask, null));
        mFragments.put(menu.getItem(2).getItemId(), TitleListFragment.newInstance(getString(R.string.url_question)));
        menu.getItem(3).setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.water, null));
        mFragments.put(menu.getItem(3).getItemId(), TitleListFragment.newInstance(getString(R.string.url_water)));
        if (mFragmentId < 0) mFragmentId = menu.getItem(0).getItemId();
        mNavigation.setSelectedItemId(mFragmentId);
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
                    BaseActivity.get.toast(e.getMessage(), TastyToast.ERROR);
                    e.printStackTrace();
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
