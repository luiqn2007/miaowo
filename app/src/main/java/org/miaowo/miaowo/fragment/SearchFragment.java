package org.miaowo.miaowo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.miaowo.miaowo.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchView sv_search;
    private FragmentPagerAdapter mAdapter;

    private SearchDisplyFragment[] fragments;

    public SearchFragment() {}
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new SearchDisplyFragment[] {
                SearchDisplyFragment.newInstance(0, new ArrayList()),
                SearchDisplyFragment.newInstance(1, new ArrayList()),
                SearchDisplyFragment.newInstance(2, new ArrayList())
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        sv_search = (SearchView) v.findViewById(R.id.sv_search);
        ViewPager vp_result = (ViewPager) v.findViewById(R.id.vp_result);

        sv_search.setIconifiedByDefault(true);
        sv_search.setSubmitButtonEnabled(true);
        sv_search.onActionViewExpanded();
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);

                InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager != null) {
                    manager.hideSoftInputFromWindow(sv_search.getWindowToken(), 0);
                }
                sv_search.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        vp_result.setAdapter(mAdapter);
    }

    private void search(String key) {
        for (SearchDisplyFragment fragment : fragments) {
            fragment.search(key);
        }
        mAdapter.notifyDataSetChanged();
    }
}
