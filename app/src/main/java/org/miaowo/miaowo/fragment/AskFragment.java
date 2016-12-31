package org.miaowo.miaowo.fragment;

import android.os.Bundle;

import org.miaowo.miaowo.C;

public class AskFragment extends ListFragment {

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        args.putInt(C.EXTRA_ITEM, C.LF_TYPE_QUESTION);
        AskFragment fragment = new AskFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
