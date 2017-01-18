package org.miaowo.miaowo.fragment.square;

import android.os.Bundle;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.fragment.ListFragment;

public class AnnouncementFragment extends ListFragment {

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        args.putInt(C.EXTRA_ITEM, C.LF_TYPE_ANNOUNCEMENT);
        AnnouncementFragment fragment = new AnnouncementFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
