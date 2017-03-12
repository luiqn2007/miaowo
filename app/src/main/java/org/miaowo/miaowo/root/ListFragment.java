package org.miaowo.miaowo.root;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.interfaces.Messages;
import org.miaowo.miaowo.set.windows.ListWindows;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.LoadMoreList;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment extends Fragment {

    public static String TAG_NAME = "name";

    private LoadMoreList mList;
    private ItemRecyclerAdapter<Question> mAdapter;
    private ArrayList<Question> mItems;
    private Messages mMessages;
    private MessageWindows mMessageWindows;

    private String name;
    private int sort = ListWindows.SORT_NONE;

    public ListFragment() {}

    public static ListFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString(TAG_NAME, name);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(TAG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mList = (LoadMoreList) v.findViewById(R.id.list_item);
        mMessages = new MsgImpl();
        mItems = new ArrayList<>();
        mMessageWindows = MessageWindows.windows();
        initList();

        return v;
    }

    private void initList() {
        mAdapter = new ItemRecyclerAdapter<>(
                mItems, new ItemRecyclerAdapter.ViewLoader<Question>() {
            @Override
            public ItemRecyclerAdapter.ViewHolder createHolder(ViewGroup parent, int viewType) {
                // 不能用 View.inflate
                // 详见 http://blog.csdn.net/sinat_26710701/article/details/52514387
                return new ItemRecyclerAdapter.ViewHolder(
                        LayoutInflater.from(getContext()).inflate(R.layout.list_question, parent, false)
                );
            }

            @Override
            public void bindView(final Question item, ItemRecyclerAdapter.ViewHolder holder) {

                // 用户
                final User u = item.user;
                holder.setOnClickListener((v) -> mMessageWindows.showQuestion(item), R.id.rl_item);
                ImageUtil.utils().setUser(holder.getImageView(R.id.iv_user), u, true);
                holder.getTextView(R.id.tv_user).setText(u.username);
                holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.timestamp));
                holder.getTextView(R.id.tv_title).setText(item.titleRaw);
                holder.getTextView(R.id.tv_count).setText(item.postcount + " 帖子, " + item.viewcount + " 浏览");
            }

            @Override
            public int setType(Question item, int position) {
                return 0;
            }

        });
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(this::refresh);
        mList.setPushRefresher(() -> checkUpdate(Messages.SEARCH_POSITION_DOWN));
    }

    // 检查更新
    private void checkUpdate(int position) {
        mMessages.loadQuestions(name, position);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        refresh();
        D.getInstance().shownFragment = this;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onDestroyView() {
        if (this.equals(D.getInstance().shownFragment)) {
            D.getInstance().shownFragment = null;
        }
        super.onDestroyView();
    }

    public void refresh() {
        checkUpdate(Messages.SEARCH_POSITION_UP);
        mAdapter.updateDate(mItems);
        mList.loadOver();
    }

    public void sort(int type) {
        switch (type) {
            case ListWindows.SORT_NONE:
                break;
            case ListWindows.SORT_HOT:
                Collections.sort(mItems, (o1, o2) -> o2.postcount - o1.postcount);
                break;
            case ListWindows.SORT_NEW:
                Collections.sort(mItems, (o1, o2) -> o1.timestamp - o2.timestamp > 0 ? 1 : -1);
                break;
        }
        this.sort = type;
        mAdapter.updateDate(mItems);
    }

    public void update(ArrayList<Question> items) {
        mItems = items;
        sort(sort);
    }
}
