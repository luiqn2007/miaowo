package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.util.ImageUtil;

public class TopicFragment extends Fragment {

    private static TextView msg;
    private static StringBuffer sb;

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
        msg = (TextView) view.findViewById(R.id.msg);
        test.setImageDrawable(ImageUtil.utils().textIcon("试", new ImageUtil.TextIconConfig()));
    }

    public static void setText(CharSequence str) {
        if (msg != null) {
            if (sb == null) {
                sb = new StringBuffer(msg.getText());
            }
            sb.append('\n');
            sb.append(str);
            D.getInstance().activeActivity.runOnUiThread(() -> msg.setText(sb.toString()));
        }
    }

    public static void clearText() {
        if (msg != null) {
            if (sb != null) {
                sb.delete(0, sb.length());
                D.getInstance().activeActivity.runOnUiThread(() -> msg.setText(sb.toString()));
            } else {
                D.getInstance().activeActivity.runOnUiThread(() -> msg.setText(""));
            }
        }
    }
}
