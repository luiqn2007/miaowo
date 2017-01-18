package org.miaowo.miaowo.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.Question;
import org.miaowo.miaowo.bean.User;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.interfaces.Message;
import org.miaowo.miaowo.set.MessageWindows;
import org.miaowo.miaowo.set.UserWindows;
import org.miaowo.miaowo.ui.LoadMoreList;
import org.miaowo.miaowo.util.SpUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

abstract public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";

    private LoadMoreList mList;
    private ItemRecyclerAdapter<Question> mAdapter;
    private ArrayList<Question> mItems;
    private Message mMessage;
    private Exception e = null;
    private UserWindows mUserWindows;
    private MessageWindows mMessageWindows;

    private int type;

    public ListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(C.EXTRA_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mList = (LoadMoreList) v.findViewById(R.id.list_item);
        mMessage = new MsgImpl();
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
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserWindows.showUserWindow(u);
                    }
                }, R.id.iv_user, R.id.tv_user);
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToQuestion(item);
                    }
                }, R.id.rl_item);
                Picasso.with(getContext())
                        .load(u.getHeadImg())
                        .transform(new CropCircleTransformation()).fit()
                        .into(holder.getImageView(R.id.iv_user));
                holder.getTextView(R.id.tv_user).setText(u.getName());
                holder.getTextView(R.id.tv_user)
                        .setTextColor(SpUtil.getInt(getContext(), C.UI_LIST_USERNAME_COLOR, Color.rgb(255, 255, 255)));
                // 时间
                holder.getTextView(R.id.tv_time).setText(item.getTime());
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

            private void jumpToQuestion(Question i) {
                try {
                    mMessageWindows.showQuestion(i);
                } catch (Exception e1) {
                    D.getInstance().activeActivity.handleError(e1);
                }
            }

        });
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
                            Snackbar.make(getActivity().getWindow().getDecorView(), "更新完成", Snackbar.LENGTH_SHORT).show();
                        }
                        mList.loadOver();
                        mList.scrollToPosition(0);
                    }
                }.execute();
            }
        });
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
            list = mMessage.checkQuestions(type, position,SpUtil.getInt(getContext(), C.UI_LIST_QUESTION_COUNT, 20));
        } catch (final Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    D.getInstance().activeActivity.handleError(e);
                }
            });
        }
        return list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mItems = checkUpdate(C.LF_POSITION_UP);
        if (e == null) mAdapter.updateDate(mItems);
    }
}
