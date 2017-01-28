package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;

/**
 * 一个典型的Fragment {@link Fragment}.
 * Activities 想要包含此 Fragment，需要实现
 * SquareFragment.OnFragmentInteractionListener(已删) 接口来实现
 * 互相的信息传递
 * 使用 {@link MiaoFragment#newInstance} 工厂方法创建此 Fragment 的
 * 一个实例
 */
public class MiaoFragment extends Fragment {
    /* ================================================================================ */
    // 构造，创建，绑定及与 Activity 交互
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 为这个 Fragment 填充布局
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
    public MiaoFragment() {
        // 需要空参构造
    }
    public static MiaoFragment newInstance() {
        MiaoFragment fragment = new MiaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    /* ================================================================================ */
}
