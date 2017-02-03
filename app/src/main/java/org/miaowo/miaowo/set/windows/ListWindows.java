package org.miaowo.miaowo.set.windows;

import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.fragment.ListFragment;
import org.miaowo.miaowo.root.view.BaseActivity;
import org.miaowo.miaowo.ui.FloatView;
import org.miaowo.miaowo.util.SpUtil;

/**
 * 排序等对列表的操作
 * Created by luqin on 17-1-21.
 */

public class ListWindows {
    // 显示
    final public static int SORT_NEW = 1;
    final public static int SORT_HOT= 2;

    private static ListWindows windows;

    private BaseActivity context;

    public ListWindows() {
        context = D.getInstance().activeActivity;
    }

    public static ListWindows getInstance() {
        if (windows == null) {
            windows = new ListWindows();
        }
        return windows;
    }

    public FloatView showListSortChooser() {
        final ListFragment lf = D.getInstance().shownFragment;
        if (lf == null) {
            return null;
        }
        final String name = lf.getName();

        final FloatView view = new FloatView(R.layout.window_list_sort);
        View v = view.getView();


        Button btn_ok = (Button) v.findViewById(R.id.btn_send);
        Button btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        final CheckBox cb_refresh = (CheckBox) v.findViewById(R.id.cb_refresh);
        cb_refresh.setChecked(true);
        final RadioGroup rg_type = (RadioGroup) v.findViewById(R.id.rg_type);
        int t = SpUtil.getInt(context, name, SORT_NEW);
        rg_type.check(t == SORT_HOT ? R.id.rb_hotter : R.id.rb_newer);

        btn_cancel.setOnClickListener(v1 -> view.dismiss());
        btn_ok.setOnClickListener(v1 -> {
            switch (rg_type.getCheckedRadioButtonId()) {
                case R.id.rb_newer:
                    SpUtil.putInt(context, name, SORT_NEW);
                    break;
                case R.id.rb_hotter:
                    SpUtil.putInt(context, name, SORT_HOT);
                    break;
            }

            if (cb_refresh.isChecked()) {
                lf.refresh();
            } else {
                lf.sort(SpUtil.getInt(context, name, SORT_NEW));
            }

            view.dismiss();
        });

        return view.show(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, new Point(0, 500));
    }
}
