package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.fragment.ChooseFragment;

import java.util.HashMap;

/**
 * 一个典型的Fragment {@link Fragment}.
 * Activities 想要包含此 Fragment，需要实现
 * SquareFragment.OnFragmentInteractionListener(已删) 接口来实现
 * 互相的信息传递
 * 使用 {@link UnreadFragment#newInstance} 工厂方法创建此 Fragment 的
 * 一个实例
 */
public class UnreadFragment extends ChooseFragment {

    public static UnreadFragment newInstance() {
        HashMap<Integer, Fragment> fragments;
        int container, fragmentFg, layout;

        Bundle args = new Bundle();
        UnreadFragment fragment = new UnreadFragment();

        fragments = new HashMap<>();
        fragments.put(R.id.tv_question, ListFragment.newInstance(C.NAME_U_QUESTION));
        fragments.put(R.id.tv_answer, ListFragment.newInstance(C.NAME_U_ANSWER));
        fragments.put(R.id.tv_reply, ListFragment.newInstance(C.NAME_U_REPLY));
        container = R.id.container;
        fragmentFg = R.id.tv_question;
        layout = R.layout.fragment_unread;

        args.putSerializable(ChooseFragment.TAG_FRAGMENTS, fragments);
        args.putInt(ChooseFragment.TAG_CONTAINER, container);
        args.putInt(ChooseFragment.TAG_DEFAULT, fragmentFg);
        args.putInt(ChooseFragment.TAG_LAYOUT, layout);

        fragment.setArguments(args);
        return fragment;
    }
}
