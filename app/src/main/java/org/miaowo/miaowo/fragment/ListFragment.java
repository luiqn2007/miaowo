package org.miaowo.miaowo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapters.ItemRecycleAdapter;
import org.miaowo.miaowo.beans.Question;
import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.impls.MsgImpl;
import org.miaowo.miaowo.impls.interfaces.Message;
import org.miaowo.miaowo.impls.interfaces.NotSingle.Handled;
import org.miaowo.miaowo.set.UserWindows;
import org.miaowo.miaowo.utils.SpUtil;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

abstract public class ListFragment extends Fragment implements Handled {
    private static final String TAG = "ListFragment";

    private PullLoadMoreRecyclerView mList;
    private ItemRecycleAdapter<Question> mAdapter;
    private ArrayList<Question> mItems;
    private Message mMessage;
    private Exception e = null;
    private UserWindows mUserWindows;

    private int type;

    public ListFragment() {}

    public int getType() { return C.LF_TYPE_DAILY; }

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
        mList = (PullLoadMoreRecyclerView) v.findViewById(R.id.list_item);
        mMessage = new MsgImpl();
        mItems = new ArrayList<>();
        mUserWindows = new UserWindows(getActivity());
        initList();

        return v;
    }

    private void initList() {
        mAdapter = new ItemRecycleAdapter<>(
                mItems, new ItemRecycleAdapter.ViewLoader<Question>() {
            @Override
            public ItemRecycleAdapter.ViewHolder createHolder(ViewGroup parent, int viewType) {
                // 不能用 View.inflate
                // 详见 http://blog.csdn.net/sinat_26710701/article/details/52514387
                return new ItemRecycleAdapter.ViewHolder(
                        LayoutInflater.from(getContext()).inflate(R.layout.list_question, parent, false)
                );
            }

            @Override
            public void bindView(final Question item, ItemRecycleAdapter.ViewHolder holder) {
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
            }

            private void jumpToQuestion(Question i) {
                Log.i(TAG, "jumpToQuestion: " + i.getTitle());
            }

        });
        mList.setLinearLayout();
        mList.setAdapter(mAdapter);
        mList.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mItems = checkUpdate(C.LF_POSITION_UP);
                if (e == null) mAdapter.updateDate(mItems);
                mList.setPullLoadMoreCompleted();
            }

            @Override
            public void onLoadMore() {
                mItems.addAll(checkUpdate(C.LF_POSITION_DOWN));
                if (e == null) mAdapter.updateDate(mItems);
                mList.setPullLoadMoreCompleted();
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
                    handleError(e);
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

    // 错误处理
    @Override
    public void handleError(Exception e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
