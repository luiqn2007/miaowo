package org.miaowo.miaowo.root.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.fragment.ListFragment;

import java.util.ArrayList;

/**
 * 带下方选择的Fragment
 * Created by luqin on 17-1-24.
 */

public class ChooseFragment extends BaseFragment implements View.OnClickListener {
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

    private void loadFragment(int fragmentId) {
        int index = controls.indexOf(fragmentId);
        if (index >= 0) {
            for (int id : controls) {
                root.findViewById(id).setBackgroundColor(Color.rgb(255, 255, 255));
            }
            root.findViewById(fragmentId).setBackgroundColor(Color.rgb(238, 238, 238));
            getChildFragmentManager().beginTransaction().replace(container, fragments.get(index)).commit();
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

    private void initViews(View v) {
        root = v;

        for (int i = 0; i < controls.size(); i++) {
            int id = controls.get(i);
            View view = v.findViewById(id);
            view.setOnClickListener(this);
            view.setTag(i);
        }
    }

    @Override
    protected AnimatorController setAnimatorController() {
        return null;
    }

    @Override
    protected ProcessController setProcessController() {
        return null;
    }
}
