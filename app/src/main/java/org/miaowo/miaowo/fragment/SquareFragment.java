package org.miaowo.miaowo.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.interfaces.Message;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SquareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SquareFragment extends BaseFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigation;
    private ListFragment lf_daily, lf_announce, lf_ask, lf_water;
    private ImageView iv_logo;
    private Message mMessage;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMessage = new MsgImpl((BaseActivity) getActivity());
        bottomNavigation = (BottomNavigationView) view.findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setBackgroundColor(getResources().getColor(R.color.md_amber_100));
        bottomNavigation.setItemIconTintList(getResources().getColorStateList(R.color.selector_icon));
        Menu menu = bottomNavigation.getMenu();
        menu.add(0, 0, 0, getString(R.string.daily));
        menu.add(0, 1, 1, getString(R.string.announcement));
        menu.add(0, 2, 2, getString(R.string.ask));
        menu.add(0, 3, 3, getString(R.string.water));
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.daily));
        menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.announcement));
        menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ask));
        menu.getItem(3).setIcon(getResources().getDrawable(R.drawable.water));
        lf_daily = ListFragment.FragmentGetter.DAILY.get();
        lf_announce = ListFragment.FragmentGetter.ANNOUNCEMENT.get();
        lf_ask = ListFragment.FragmentGetter.QUESTION.get();
        lf_water = ListFragment.FragmentGetter.WATER.get();
        bottomNavigation.setSelectedItemId(0);
        iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
        iv_logo.setImageDrawable(new IconicsDrawable(getContext(),
                FontAwesome.Icon.faw_calendar_check_o).actionBar());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                getChildFragmentManager().beginTransaction().replace(R.id.container, lf_daily).commit();
                break;
            case 1:
                getChildFragmentManager().beginTransaction().replace(R.id.container, lf_announce).commit();
                break;
            case 2:
                getChildFragmentManager().beginTransaction().replace(R.id.container, lf_ask).commit();
                break;
            case 3:
                getChildFragmentManager().beginTransaction().replace(R.id.container, lf_water).commit();
                break;
        }
        return true;
    }
}
