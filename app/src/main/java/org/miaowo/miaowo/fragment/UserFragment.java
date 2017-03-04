package org.miaowo.miaowo.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ItemRecyclerAdapter;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.set.windows.UserWindows;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.ArrayList;
import java.util.Collections;

public class UserFragment extends Fragment {

    private RecyclerView mView;
    private ItemRecyclerAdapter<User> mAdapter;
    private ArrayList<User> mList;
    private UserWindows mUserWindows;
    private Users mUsers;

    public UserFragment() {
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = new RecyclerView(getContext());
        mList = new ArrayList<>();
        mUserWindows = new UserWindows();
        mUsers = new UsersImpl();

        initView();
        return mView;
    }

    private void initView() {
        mAdapter = new ItemRecyclerAdapter<>(mList, new ItemRecyclerAdapter.ViewLoader<User>() {
            @Override
            public ItemRecyclerAdapter.ViewHolder createHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.list_user, parent, false);
                return new ItemRecyclerAdapter.ViewHolder(v);
            }

            @Override
            public void bindView(User item, ItemRecyclerAdapter.ViewHolder holder) {
                holder.getView().setOnClickListener(v -> mUserWindows.showUserWindow(item));
                ImageView iv_user = holder.getImageView(R.id.iv_user);
                iv_user.setContentDescription("用户：" + item.getName());
                ImageUtil.setUserImage(iv_user, item);
                holder.getTextView(R.id.tv_user).setText(item.getName());
            }

            @Override
            public int setType(User item, int position) {
                return 0;
            }
        });
        mView.setAdapter(mAdapter);
        mView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    mList.clear();
                    Collections.addAll(mList, mUsers.searchUsers(""));
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e != null) D.getInstance().activeActivity.handleError(e);
                else mAdapter.updateDate(mList);
            }
        }.execute();
    }
}
