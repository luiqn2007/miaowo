package org.miaowo.miaowo.set.windows;

import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.fragment.ListFragment;
import org.miaowo.miaowo.util.SpUtil;
import org.miaowo.miaowo.view.FloatView;

import static org.miaowo.miaowo.adapter.ItemRecyclerAdapter.SORT_HOT;
import static org.miaowo.miaowo.adapter.ItemRecyclerAdapter.SORT_NEW;

/**
 * 排序等对列表的操作
 * Created by luqin on 17-1-21.
 */

public class ListWindows {
    private BaseActivity mContext;
    private SpUtil mDefaultSp;

    private ListWindows(BaseActivity context) {
        mContext = context;
        mDefaultSp = SpUtil.defaultSp(context);
    }
    public static ListWindows windows(BaseActivity context) { return new ListWindows(context); }

    public FloatView showListSortChooser(ListFragment lf) {
        if (lf == null) {
            return null;
        }
        final String name = lf.getName();

        final FloatView view = new FloatView(mContext, "", R.layout.window_list_sort);
        View v = view.getView();

        Button btn_ok = (Button) v.findViewById(R.id.btn_send);
        Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        final CheckBox cb_refresh = (CheckBox) v.findViewById(R.id.cb_refresh);
        cb_refresh.setChecked(true);
        final RadioGroup rg_type = (RadioGroup) v.findViewById(R.id.rg_type);
        int t = mDefaultSp.getInt(name, SORT_NEW);
        rg_type.check(t == SORT_HOT ? R.id.rb_hotter : R.id.rb_newer);

        btn_cancel.setOnClickListener(v1 -> view.dismiss(false));
        btn_ok.setOnClickListener(v1 -> {
            switch (rg_type.getCheckedRadioButtonId()) {
                case R.id.rb_newer:
                    mDefaultSp.putInt(name, SORT_NEW);
                    break;
                case R.id.rb_hotter:
                    mDefaultSp.putInt(name, SORT_HOT);
                    break;
            }

            if (cb_refresh.isChecked()) {
                lf.checkNew();
            } else {
                lf.sort(mDefaultSp.getInt(name, SORT_NEW));
            }

            view.dismiss(false);
        });

        return view.show(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, new Point(0, 500));
    }
}
