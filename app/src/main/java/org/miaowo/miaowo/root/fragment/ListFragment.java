package org.miaowo.miaowo.root.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.QuestionsImpl;
import org.miaowo.miaowo.impl.interfaces.Questions;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.set.windows.UserWindows;
import org.miaowo.miaowo.ui.LoadMoreList;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment extends Fragment implements Parcelable {

    public static String TAG_NAME = "name";

    private LoadMoreList mList;
    private ItemRecyclerAdapter<Question> mAdapter;
    private ArrayList<Question> mItems;
    private Questions mQuestions;
    private Exception e = null;
    private UserWindows mUserWindows;
    private MessageWindows mMessageWindows;

    private String name;

    public ListFragment() {}

    protected ListFragment(Parcel in) {
        mItems = in.createTypedArrayList(Question.CREATOR);
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mItems);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListFragment> CREATOR = new Creator<ListFragment>() {
        @Override
        public ListFragment createFromParcel(Parcel in) {
            return new ListFragment(in);
        }

        @Override
        public ListFragment[] newArray(int size) {
            return new ListFragment[size];
        }
    };

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
        mQuestions = new QuestionsImpl();
        mItems = new ArrayList<>();
        mUserWindows = new UserWindows();
        mMessageWindows = new MessageWindows();
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
                final User u = item.getUser();
                holder.setOnClickListener((v) -> mUserWindows.showUserWindow(u), R.id.iv_user, R.id.tv_user);
                holder.setOnClickListener((v) -> mMessageWindows.showQuestion(item), R.id.rl_item);
                ImageUtil.fillImage(holder.getImageView(R.id.iv_user), u);
                holder.getTextView(R.id.tv_user).setText(u.getName());
                holder.getTextView(R.id.tv_user)
                        .setTextColor(SpUtil.getInt(getContext(), C.UI_LIST_USERNAME_COLOR, Color.rgb(255, 255, 255)));
                // 时间
                holder.getTextView(R.id.tv_time).setText(FormatUtil.timeToString(item.getTime()));
                holder.getTextView(R.id.tv_time)
                        .setTextColor(SpUtil.getInt(getContext(), C.UI_LIST_TIME_COLOR, Color.rgb(255, 255, 255)));
                // 标题
                holder.getTextView(R.id.tv_title).setText(item.getTitle());
                holder.getTextView(R.id.tv_title)
                        .setTextColor(SpUtil.getInt(getContext(), C.UI_LIST_TITLE_COLOR, Color.rgb(255, 255, 255)));
                // 计数
                holder.getTextView(R.id.tv_count).setText(item.getReply() + " 帖子, " + item.getView() + " 浏览");
            }

            @Override
            public int setType(Question item, int position) {
                return 0;
            }

        });
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(this::refresh);
        mList.setPushRefresher(new SwipeRefreshLayout.OnRefreshListener() {
            int lastCount = 0;

            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        lastCount = mItems.size();
                        mItems.addAll(checkUpdate(C.LF_POSITION_DOWN));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if (e == null) {
                            mAdapter.updateDate(mItems);
                            Snackbar.make(getActivity().getWindow().getDecorView(),
                                    "更新了 " + (mItems.size() - lastCount) + " 条消息", Snackbar.LENGTH_SHORT).show();
                        }
                        mList.loadOver();
                    }
                }.execute();
            }
        });
    }

    // 检查更新
    private ArrayList<Question> checkUpdate(int position) {
        ArrayList<Question> list = new ArrayList<>();
        try {
            e = null;
            Question[] questions = mQuestions.checkQuestions(name, position, SpUtil.getInt(getContext(), C.UI_LIST_QUESTION_COUNT, 20),
                    mItems.size() == 0 ? 0 : mItems.get(mItems.size() - 1).getTime());
            Collections.addAll(list, questions);
        } catch (final Exception e) {
            getActivity().runOnUiThread(() -> D.getInstance().activeActivity.handleError(e));
        }
        return list;
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
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                mItems = checkUpdate(C.LF_POSITION_UP);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (e == null) {
                    mAdapter.updateDate(mItems);
                }
                mList.loadOver();
                mList.scrollToPosition(0);
            }
        }.execute();
    }

    public void sort(int type) {
        switch (type) {
            case C.SORT_HOT:
                Collections.sort(mItems, (o1, o2) -> o2.getReply() - o1.getReply());
                break;
            case C.SORT_NEW:
                Collections.sort(mItems, (o1, o2) -> o1.getTime() - o2.getTime() > 0 ? 1 : -1);
                break;
        }

        mAdapter.updateDate(mItems);
    }
}
