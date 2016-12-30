package org.miaowo.miaowo.fragment;

import android.os.Bundle;

import org.miaowo.miaowo.C;

public class DailyFragment extends ListFragment {

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        args.putInt(C.EXTRA_ITEM, C.LF_TYPE_DAILY);
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
