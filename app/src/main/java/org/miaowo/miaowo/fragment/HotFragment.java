package org.miaowo.miaowo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.fragment.square.AnnouncementFragment;
import org.miaowo.miaowo.fragment.square.AskFragment;
import org.miaowo.miaowo.fragment.square.DailyFragment;
import org.miaowo.miaowo.fragment.square.WaterFragment;
import org.miaowo.miaowo.util.FragmentUtil;
import org.miaowo.miaowo.util.SpUtil;

/**
 * 一个典型的Fragment {@link Fragment}.
 * Activities 想要包含此 Fragment，需要实现
 * SquareFragment.OnFragmentInteractionListener(已删) 接口来实现
 * 互相的信息传递
 * 使用 {@link HotFragment#newInstance} 工厂方法创建此 Fragment 的
 * 一个实例
 */
public class HotFragment extends Fragment implements View.OnClickListener {
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
        View v = inflater.inflate(R.layout.fragment_square, container, false);
        initViews(v);
        return v;
    }
    public HotFragment() {
        // 需要空参构造
    }
    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    /* ================================================================================ */

    private TextView tv_daily, tv_announcement, tv_ask, tv_water;
    private Fragment fg_daily, fg_announcement, fg_ask, fg_water;
    private FragmentManager fm;

    // 相当于 Activity 的 onPostCreate
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 显示 每日 页面
        onClick(tv_daily);
    }

    @Override
    public void onClick(View view) {
        // 处理点击事件， 跳转不同 碎片
        // 关闭所有 Fragment
        hideAllFragment();
        FragmentManager manager = getChildFragmentManager();
        // 加载相应的 Fragment
        switch (view.getId()) {
            case R.id.tv_daily:
                FragmentUtil.showFragment(manager, R.id.container, fg_daily);
                break;
            case R.id.tv_announcement:
                FragmentUtil.showFragment(manager, R.id.container, fg_announcement);
                break;
            case R.id.tv_ask:
                FragmentUtil.showFragment(manager, R.id.container, fg_ask);
                break;
            case R.id.tv_water:
                FragmentUtil.showFragment(manager, R.id.container, fg_water);
                break;
            default:
                break;
        }
        view.setBackgroundColor(SpUtil.getInt(getContext(), C.UI_BOTTOM_SELECTED_COLOR,
                Color.rgb(255, 255, 255)));
    }

    // 隐藏所有 Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        if (fg_daily != null) {
            transaction.hide(fg_daily);
        }
        if (fg_announcement != null) {
            transaction.hide(fg_announcement);
        }
        if (fg_ask != null) {
            transaction.hide(fg_ask);
        }
        if (fg_water != null) {
            transaction.hide(fg_water);
        }
        transaction.commit();

        tv_daily.setBackgroundColor(SpUtil.getInt(getContext(), C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        tv_announcement.setBackgroundColor(SpUtil.getInt(getContext(), C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        tv_ask.setBackgroundColor(SpUtil.getInt(getContext(), C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
        tv_water.setBackgroundColor(SpUtil.getInt(getContext(), C.UI_BOTTOM_DEFAULT_COLOR,
                Color.argb(255, 255, 255, 255)));
    }

    private void initViews(View v) {
        tv_daily = (TextView) v.findViewById(R.id.tv_daily);
        tv_water = (TextView) v.findViewById(R.id.tv_water);
        tv_ask = (TextView) v.findViewById(R.id.tv_ask);
        tv_announcement = (TextView) v.findViewById(R.id.tv_announcement);

        tv_daily.setOnClickListener(this);
        tv_announcement.setOnClickListener(this);
        tv_ask.setOnClickListener(this);
        tv_water.setOnClickListener(this);

        fg_announcement = AnnouncementFragment.newInstance();
        fg_ask = AskFragment.newInstance();
        fg_daily = DailyFragment.newInstance();
        fg_water = WaterFragment.newInstance();

        fm = getChildFragmentManager();
    }

}
