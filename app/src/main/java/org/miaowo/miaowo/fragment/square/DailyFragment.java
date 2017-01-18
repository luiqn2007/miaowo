package org.miaowo.miaowo.fragment.square;

import android.os.Bundle;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.fragment.ListFragment;

public class DailyFragment extends ListFragment {

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        args.putInt(C.EXTRA_ITEM, C.LF_TYPE_DAILY);
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
