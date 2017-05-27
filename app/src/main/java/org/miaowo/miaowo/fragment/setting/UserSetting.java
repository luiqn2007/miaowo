package org.miaowo.miaowo.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.PwdShowListener;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

public class UserSetting extends BaseFragment {
    @BindView(R.id.update_user) Button updateUser;
    @BindView(R.id.update_pwd) Button updatePwd;
    @BindView(R.id.btn_remove) Button btnRemove;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_user) EditText etUser;
    @BindView(R.id.et_location) EditText etLocation;
    @BindView(R.id.et_birthday) EditText etBirthday;
    @BindView(R.id.et_signature) EditText etSignature;
    @BindView(R.id.et_website) EditText etWebsite;
    @BindView(R.id.et_pwd_ori) EditText etPwdOri;
    @BindView(R.id.et_pwd_new) EditText etPwdNew;
    @BindView(R.id.show_ori) ImageButton showOri;
    @BindView(R.id.show_new) ImageButton showNew;

    private API mApi;
    private User mUser;

    public UserSetting() {}

    public static UserSetting newInstance() {
        Bundle args = new Bundle();
        UserSetting fragment = new UserSetting();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_user, container, false);
    }

    @Override
    public void initView(View view) {
        mApi = new API();
        mUser = API.loginUser;

        FormatUtil format = FormatUtil.format();
        etEmail.setText(mUser.getEmail());
        etUser.setText(mUser.getUsername());
        format.parseHtml(mUser.getLocation(), spanned -> etLocation.setText(spanned));
        format.parseHtml(mUser.getWebsite(), spanned -> etWebsite.setText(spanned));
        format.parseHtml(mUser.getBirthday(), spanned -> etBirthday.setText(spanned));
        format.parseHtml(mUser.getSignature(), spanned -> etSignature.setText(spanned));

        showOri.setOnTouchListener(new PwdShowListener(etPwdOri));
        showNew.setOnTouchListener(new PwdShowListener(etPwdNew));
    }

    @OnClick({R.id.update_user, R.id.update_pwd, R.id.btn_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_user:
                String user = etUser.getText().toString();
                String email = etEmail.getText().toString();
                String location = etLocation.getText().toString();
                String website = etWebsite.getText().toString();
                String birthday = etBirthday.getText().toString();
                String signature = etSignature.getText().toString();
                FormBody body1 = new FormBody.Builder()
                        .add("username", user)
                        .add("email", email)
                        .add("fullname", user)
                        .add("website", website)
                        .add("location", location)
                        .add("birthday", birthday)
                        .add("signature", signature)
                        .build();
                mApi.useAPI(API.APIType.USERS, String.valueOf(mUser.getUid()), API.Method.PUT, true, body1, (call, response) -> {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if ("ok".equals(object.getString("code")))
                            BaseActivity.get.toast("修改完成, 重新登录后将更新信息", TastyToast.SUCCESS);
                        else throw new Exception(object.getString("message"));
                    } catch (Exception e) {
                        BaseActivity.get.handleError(e);
                    }
                    LogUtil.i(response);
                });
                break;
            case R.id.update_pwd:
                String pwdNew = etPwdNew.getText().toString();
                String pwdOld = etPwdOri.getText().toString();
                FormBody body2 = new FormBody.Builder()
                        .add("uid", String.valueOf(mUser.getUid()))
                        .add("new", pwdNew)
                        .add("current", pwdOld)
                        .build();
                mApi.useAPI(API.APIType.USERS, mUser.getUid() + "/password", API.Method.POST, true, body2, (call, response) -> {
                    // TODO: API-更改密码
                    LogUtil.i(response);
                });
                break;
            case R.id.btn_remove:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("注销帐号");
                builder.setMessage("注意!!!\n\n注销帐号后，您的一切信息都将从服务器数据库中删除\n您以后也无法通过该帐号登录喵窝\n\n确认删除吗？");
                builder.setNegativeButton("算了", null);
                builder.setPositiveButton("删除", (dialog, which) -> {
                    mApi.useAPI(API.APIType.USERS, String.valueOf(mUser.getUid()), API.Method.DELETE, true, null, (call, response) -> {
                        try {
                            String json = response.body().string();
                            LogUtil.i(json);
                            JSONObject object = new JSONObject(json);
                            if ("ok".equals(object.getString("code"))) {
                                mApi.logout();
                                getActivity().finish();
                            } else throw new Exception(object.getString("message"));
                        } catch (Exception e) {
                            BaseActivity.get.handleError(e);
                        }
                    });
                    dialog.dismiss();
                });
                builder.show();
                break;
        }
    }
}
