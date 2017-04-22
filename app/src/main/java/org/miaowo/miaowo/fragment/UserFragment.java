package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.UserList;
import org.miaowo.miaowo.custom.load_more_list.LMLAdapter;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.set.UserWindows;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.util.ArrayList;

public class UserFragment extends BaseFragment {

    private LoadMoreList mList;
    private LMLAdapter<User> mAdapter; 
    private UserWindows mUserWindows;
    private int mPage;

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
        return new LoadMoreList(getContext());
    }

    @Override
    public void initView(View view) {
        mList = (LoadMoreList) view;

        mUserWindows = UserWindows.windows();
        mAdapter = new LMLAdapter<>(new ArrayList<>(), new LMLAdapter.ViewLoaderCreator<User>() {
            @Override
            public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.list_user, parent, false);
                return new BaseViewHolder(v);
            }

            @Override
            public void bindView(User item, RecyclerView.ViewHolder holder, int type) {
                holder.itemView.setOnClickListener(v -> mUserWindows.showUserWindow(item.getUsername()));
                ImageView iv_user = (ImageView) holder.itemView.findViewById(R.id.iv_user);
                iv_user.setContentDescription("用户：" + item.getUsername());
                ImageUtil.utils().setUser(iv_user, item, true);
                ((TextView) holder.itemView.findViewById(R.id.tv_user)).setText(item.getUsername());
            }

            @Override
            public int setType(User item, int position) {
                return 0;
            }
        });
        mList.setAdapter(mAdapter);
        mList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mList.setPushRefresher(this::loadNextPage);
        mList.setPullRefresher(() -> {
            mPage = 0;
            loadNextPage();
        });
        mList.load();
    }

    private void loadNextPage() {
        mList.loadMoreControl(false, false);
        HttpUtil.utils().post(String.format(getString(R.string.url_users), ++mPage),
                (call, response) -> {
                    mList.loadMoreControl(true, true);
                    UserList userList = JsonUtil.utils().buildFromAPI(response, UserList.class);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                        mList.loadOver();
                        if (userList == null || userList.getUsers().size() == 0) {
                            mPage--;
                            TastyToast.makeText(getContext(), getString(R.string.err_page_end), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                        } else {
                            mAdapter.appendData(userList.getUsers(), false);
                        }
                    });
                },
                (call, e) -> {
                    BaseActivity.get.toast(e.getMessage(), TastyToast.ERROR);
                    BaseActivity.get.runOnUiThreadIgnoreError(() -> mList.loadOver());
                    mList.loadMoreControl(true, true);
                    mPage--;
                });
    }
}
