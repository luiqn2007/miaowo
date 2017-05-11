package org.miaowo.miaowo.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.LogUtil;
import org.miaowo.miaowo.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

public class AppSetting extends BaseFragment {
    public static final String SETTING_APP_TAB = "square_tab_mode";
    public static final String SETTING_APP_CLEAN = "auto_clean_token";

    @BindView(R.id.sw_clean) Switch swClean;
    @BindView(R.id.sw_tab) Switch swTab;
    @BindView(R.id.rv_tokens) RecyclerView rvTokens;

    private API mApi;
    private User mUser;
    private String mToken;

    public AppSetting() {
    }

    public static AppSetting newInstance() {
        Bundle args = new Bundle();
        AppSetting fragment = new AppSetting();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_app, container, false);
    }

    @Override
    public void initView(View view) {
        mApi = new API();
        mUser = API.loginUser;
        mToken = API.token;
        SpUtil mSp = SpUtil.defaultSp();

        swTab.setChecked(mSp.getBoolean(SETTING_APP_TAB, false));
        swClean.setChecked(mSp.getBoolean(SETTING_APP_CLEAN, true));
        swTab.setOnClickListener(v -> mSp.putBoolean(SETTING_APP_TAB, swTab.isChecked()));
        swClean.setOnClickListener(v -> mSp.putBoolean(SETTING_APP_CLEAN, swClean.isChecked()));

        rvTokens.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        TokenAdapter adapter = new TokenAdapter();
        rvTokens.setAdapter(adapter);

        mApi.useAPI(API.APIType.USERS, mUser.getUid() + "/tokens", API.Method.GET, true, null, (call, response) -> {
            try {
                JSONObject object = new JSONObject(response.body().string());
                if (!"ok".equals(object.getString("code"))) throw new Exception("获取失败");
                else {
                    JSONArray tokenArray = object.getJSONObject("payload").getJSONArray("tokens");
                    ArrayList<String> tokens = new ArrayList<>();
                    for (int i = 0; i < tokenArray.length(); i++) tokens.add(tokenArray.getString(i));
                    adapter.update(tokens);
                }
            } catch (Exception e) {
                BaseActivity.get.toast(e.getMessage(), TastyToast.ERROR);
            }
        });
    }

    private class TokenAdapter extends RecyclerView.Adapter {
        List<String> mTokens = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(BaseActivity.get).inflate(R.layout.list_token, parent, false)) {};
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            View v = holder.itemView;
            TextView tv_token = (TextView) v.findViewById(R.id.token);
            String token = mTokens.get(position);
            tv_token.setText(token);
            boolean equals = token.equals(mToken);
            v.findViewById(R.id.thisToken).setVisibility(equals ? View.VISIBLE : View.GONE);
            v.findViewById(R.id.remove).setVisibility(equals ? View.GONE : View.VISIBLE);
            v.findViewById(R.id.remove).setOnClickListener(v1 -> {
                if (equals) v1.setVisibility(View.GONE);
                else mApi.useAPI(API.APIType.USERS, mUser.getUid() + "/tokens/" + token, API.Method.DELETE, true, null, (call, response) -> {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if ("ok".equals(object.getString("code"))) {
                            mTokens.remove(position);
                            BaseActivity.get.runOnUiThreadIgnoreError(this::notifyDataSetChanged);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
        }

        @Override
        public int getItemCount() {
            return mTokens.size();
        }

        public void update(List<String> tokens) {
            mTokens = tokens;
            BaseActivity.get.runOnUiThreadIgnoreError(this::notifyDataSetChanged);
        }
    }
}
