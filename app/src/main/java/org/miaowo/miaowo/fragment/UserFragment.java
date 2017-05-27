package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Detail;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.bean.data.UserList;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseListFragment;
import org.miaowo.miaowo.root.BaseViewHolder;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class UserFragment extends BaseListFragment<User> {

    public UserFragment() {}
    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(URL, BaseActivity.get.getString(R.string.url_users));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mList.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public LMLPageAdapter<User> setAdapter() {
        return new LMLPageAdapter<>(new ArrayList<>(), new LMLPageAdapter.ViewLoaderCreator<User>() {
            @Override
            public RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.list_user, parent, false);
                return new BaseViewHolder(v);
            }

            @Override
            public void bindView(User item, RecyclerView.ViewHolder holder, int type) {
                holder.itemView.setOnClickListener(v -> Detail.showUser(item.getUsername()));
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
    }

    @Override
    public List<User> getItems(Response response) throws IOException {
        UserList userList = JsonUtil.utils().buildFromAPI(response, UserList.class);
        if (userList != null) {
            return userList.getUsers();
        }
        return null;
    }
}
