package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.root.fragment.ChooseFragment;

import java.util.ArrayList;

/**
 * 一个典型的Fragment.
 * Activities 想要包含此 Fragment，需要实现
 * SquareFragment.OnFragmentInteractionListener(已删) 接口来实现
 * 互相的信息传递
 * 使用 {@link SquareFragment#newInstance} 工厂方法创建此 Fragment 的
 * 一个实例
 */
public class SquareFragment extends ChooseFragment {

    public static SquareFragment newInstance() {
        ArrayList<Integer> controls;
        ArrayList<Fragment> fragments;
        int container, fragmentFg, layout;

        Bundle args = new Bundle();
        SquareFragment fragment = new SquareFragment();

        fragments = new ArrayList<>();
        fragments.add(ListFragment.FragmentGetter.DAILY.get());
        fragments.add(ListFragment.FragmentGetter.ANNOUNCEMENT.get());
        fragments.add(ListFragment.FragmentGetter.QUESTION.get());
        fragments.add(ListFragment.FragmentGetter.WATER.get());
        controls = new ArrayList<>();
        controls.add(R.id.tv_daily);
        controls.add(R.id.tv_announcement);
        controls.add(R.id.tv_ask);
        controls.add(R.id.tv_water);

        container = R.id.container;
        fragmentFg = R.id.tv_daily;
        layout = R.layout.fragment_square;

        args.putSerializable(ChooseFragment.TAG_FRAGMENTS, fragments);
        args.putSerializable(ChooseFragment.TAG_CONTROLS, controls);
        args.putInt(ChooseFragment.TAG_CONTAINER, container);
        args.putInt(ChooseFragment.TAG_DEFAULT, fragmentFg);
        args.putInt(ChooseFragment.TAG_LAYOUT, layout);

        fragment.setArguments(args);
        return fragment;
    }
}
