package org.miaowo.miaowo.fragment;

import android.os.Bundle;

import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.ChooseFragment;
import org.miaowo.miaowo.root.ListFragment;

import java.util.ArrayList;

/**
 * 一个典型的Fragment.
 * Activities 想要包含此 Fragment，需要实现
 * SquareFragment.OnFragmentInteractionListener(已删) 接口来实现
 * 互相的信息传递
 * 使用 {@link UnreadFragment#newInstance} 工厂方法创建此 Fragment 的
 * 一个实例
 */
public class UnreadFragment extends ChooseFragment {

    public static UnreadFragment newInstance() {
        ArrayList<ListFragment> fragments;
        ArrayList<Integer> controls;
        int container, fragmentFg, layout;

        Bundle args = new Bundle();
        UnreadFragment fragment = new UnreadFragment();

        fragments = new ArrayList<>();
        fragments.add(ListFragment.newInstance(Miao.FRAGMENT_U_QUESTION));
        fragments.add(ListFragment.newInstance(Miao.FRAGMENT_U_ANSWER));
        fragments.add(ListFragment.newInstance(Miao.FRAGMENT_U_REPLY));
        controls = new ArrayList<>();
        controls.add(R.id.tv_question);
        controls.add(R.id.tv_answer);
        controls.add(R.id.tv_reply);

        container = R.id.container;
        fragmentFg = R.id.tv_question;
        layout = R.layout.fragment_unread;

        args.putSerializable(ChooseFragment.TAG_FRAGMENTS, fragments);
        args.putSerializable(ChooseFragment.TAG_CONTROLS, controls);
        args.putInt(ChooseFragment.TAG_CONTAINER, container);
        args.putInt(ChooseFragment.TAG_DEFAULT, fragmentFg);
        args.putInt(ChooseFragment.TAG_LAYOUT, layout);

        fragment.setArguments(args);
        return fragment;
    }
}
