package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.bean.data.web.UserList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.fragment.BaseFragment;
import org.miaowo.miaowo.set.windows.UserWindows;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.load_more_list.ItemRecyclerAdapter;
import org.miaowo.miaowo.view.load_more_list.LoadMoreList;
import org.miaowo.miaowo.view.load_more_list.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserFragment extends BaseFragment {

    private LoadMoreList mView;
    private ItemRecyclerAdapter<User> mAdapter;
    private List<User> mList;
    private UserWindows mUserWindows;
    private int mPage = 1;

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
        mView = new LoadMoreList(getContext());
        mList = new ArrayList<>();
        mUserWindows = UserWindows.windows((BaseActivity) getActivity());

        mAdapter = new ItemRecyclerAdapter<>(mList, new ItemRecyclerAdapter.ViewLoader<User>() {
            @Override
            public ViewHolder createHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.list_user, parent, false);
                return new ViewHolder(v);
            }

            @Override
            public void bindView(User item, ViewHolder holder) {
                holder.getView().setOnClickListener(v -> mUserWindows.showUserWindow(item.getUsername()));
                ImageView iv_user = holder.getImageView(R.id.iv_user);
                iv_user.setContentDescription("用户：" + item.getUsername());
                ImageUtil.utils((BaseActivity) getActivity()).setUser(iv_user, item, true);
                holder.getTextView(R.id.tv_user).setText(item.getUsername());
            }

            @Override
            public int setType(User item, int position) {
                return 0;
            }
        });
        mView.setAdapter(mAdapter);
        mView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mView.setPushRefresher(this::loadNextPage);
        mView.setPullRefresher(this::reloadUser);
        reloadUser();
        return mView;
    }

    private void reloadUser() {
        if (mList == null) mList = new ArrayList<>();
        else mList.clear();

        mPage = 1;
        loadNextPage();
    }

    private void loadNextPage() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        HttpUtil.utils().post(getString(R.string.url_users) + mPage, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((BaseActivity) getActivity()).handleError(e);
                mView.loadOver();
                mPage--;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                UserList userList = BeanUtil.utils().buildFromLastJson(response, UserList.class);
                if (userList.getUsers().size() == 0) {
                    mPage--;
                    getActivity().runOnUiThread(() ->
                            TastyToast.makeText(getContext(), "就这些用户啦", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show());
                }
                mList.addAll(userList.getUsers());
                getActivity().runOnUiThread(() -> {
                    mAdapter.updateDate(mList);
                    mView.loadOver();
                });
            }
        });
        mPage++;
    }

    @Override
    protected AnimatorController setAnimatorController() {
        return null;
    }

    @Override
    protected ProcessController setProcessController() {
        return null;
    }
}
