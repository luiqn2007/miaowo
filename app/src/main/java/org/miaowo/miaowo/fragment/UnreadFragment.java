package org.miaowo.miaowo.fragment;

import android.os.Bundle;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.TitleListAdapter;
import org.miaowo.miaowo.bean.data.Title;
import org.miaowo.miaowo.bean.data.TitleList;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseListFragment;
import org.miaowo.miaowo.util.JsonUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class UnreadFragment extends BaseListFragment<Title> {

    public UnreadFragment() {}

    public static UnreadFragment newInstance() {
        Bundle args = new Bundle();

        UnreadFragment fragment = new UnreadFragment();
        args.putString(URL, BaseActivity.get.getString(R.string.url_unread));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public LMLPageAdapter<Title> setAdapter() {
        return new TitleListAdapter();
    }

    @Override
    public List<Title> getItems(Response response) throws IOException {
        TitleList list = JsonUtil.utils().buildFromAPI(response, TitleList.class);
        if (list != null) {
            return list.getTitles();
        }
        return null;
    }
}
