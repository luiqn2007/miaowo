package org.miaowo.miaowo.root;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.set.windows.ListWindows;
import org.miaowo.miaowo.util.FragmentUtil;
import org.miaowo.miaowo.util.SpUtil;
import org.miaowo.miaowo.util.ThemeUtil;

import java.util.ArrayList;

/**
 * 带下方选择的Fragment
 * Created by luqin on 17-1-24.
 */

public class ChooseFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    public static String TAG_CONTAINER = "container";
    public static String TAG_DEFAULT = "default";
    public static String TAG_LAYOUT = "layout";
    public static String TAG_FRAGMENTS = "fragments";
    public static String TAG_CONTROLS = "controls";

    /* ================================================================================ */
    // 构造，创建，绑定及与 Activity 交互
    private int layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            container = getArguments().getInt(TAG_CONTAINER);
            defaultFg = getArguments().getInt(TAG_DEFAULT);
            layout = getArguments().getInt(TAG_LAYOUT);
            controls = (ArrayList<Integer>) getArguments().getSerializable(TAG_CONTROLS);
            fragments = (ArrayList<ListFragment>) getArguments().getSerializable(TAG_FRAGMENTS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 为这个 Fragment 填充布局
        View v = inflater.inflate(layout, container, false);
        initViews(v);
        return v;
    }
    public ChooseFragment() { }
    /* ================================================================================ */

    private ArrayList<ListFragment> fragments;
    private ArrayList<Integer> controls;
    private int container, defaultFg;

    private View root;
    private ListWindows mListWindows;

    private void loadFragment(int fragmentId) {
        int index = controls.indexOf(fragmentId);
        if (index >= 0) {
            hideAllFragment();
            FragmentUtil.showFragment(getChildFragmentManager(), container, fragments.get(index));
            root.findViewById(fragmentId).setBackgroundColor(SpUtil.getInt(getContext(), ThemeUtil.UI_BOTTOM_SELECTED_COLOR,
                    Color.rgb(255, 255, 255)));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadFragment(defaultFg);
    }

    @Override
    public void onClick(View v) {
        loadFragment(v.getId());
    }

    @Override
    public boolean onLongClick(View v) {
        mListWindows.showListSortChooser();
        return true;
    }

    private void initViews(View v) {
        root = v;

        for (Integer id : controls) {
            v.findViewById(id).setOnClickListener(this);
            v.findViewById(id).setOnLongClickListener(this);
        }

        mListWindows = new ListWindows();
    }

    // 隐藏所有 Fragment
    private void hideAllFragment() {
        FragmentUtil.hideAllFragment(getChildFragmentManager());

        for (int id : controls) {
            root.findViewById(id).setBackgroundColor(SpUtil.getInt(getContext(), ThemeUtil.UI_BOTTOM_DEFAULT_COLOR,
                    Color.argb(255, 255, 255, 255)));
        }
    }
}
