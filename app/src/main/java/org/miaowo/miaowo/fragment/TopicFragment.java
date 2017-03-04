package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.util.ArrayList;

public class TopicFragment extends Fragment {


    public TopicFragment() {
        // Required empty public constructor
    }
    public static TopicFragment newInstance() {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        initView(view);
        return view;
    }
    /* ================================================================ */

    private void initView(View view) {
        ImageView test = (ImageView) view.findViewById(R.id.test);
        test.setImageDrawable(ImageUtil.getText("试"));
        test.setOnClickListener(v -> LogUtil.i("Click!"));
    }
}
