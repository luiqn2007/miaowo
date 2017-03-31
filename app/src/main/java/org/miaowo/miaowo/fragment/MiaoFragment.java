package org.miaowo.miaowo.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.cleveroad.fanlayoutmanager.callbacks.FanChildDrawingOrderCallback;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.SpUtil;
import org.miaowo.miaowo.view.load_more_list.ViewHolder;

import java.util.List;
import java.util.Random;

public class MiaoFragment extends BaseFragment {
    private final String sp_save = "save_password";
    private final String sp_user = "username";
    private final String sp_pwd = "password";
    /* ================================================================================ */
    // 构造，创建，绑定及与 Activity 交互
    OnFragmentInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
        if (mState.isLogin()) {
            rv_page.setVisibility(View.VISIBLE);
            et_email.setEnabled(false);
            et_user.setEnabled(false);
            et_pwd.setEnabled(false);
            cb_save.setEnabled(false);
            btn_login.setEnabled(false);
        } else {
            prepareLogin();
        }
    }
    public MiaoFragment() {}
    public static MiaoFragment newInstance() {
        MiaoFragment fragment = new MiaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onChooserClick(int position, View sharedView);
        List<String> setItemNames();
        List<Drawable> setItemIcons();
    }
    /* ================================================================================ */
    private State mState;
    private SpUtil mSpUtil;

    // 进度条
    private ProgressBar pb_process;
    private TextView tv_message;
    // 欢迎图片
    private ImageView iv_eyes, iv_mouth;
    // 登录界面
    private Button btn_login;
    private TextView tv_user, tv_pwd, tv_email;
    private CheckBox cb_save;
    private EditText et_user, et_pwd, et_email;
    private RecyclerView rv_page;
    // 动画
    private AnimatorSet mShowLoginButton, mHideLoginButton;
    private AnimatorSet mShowLogin, mHideLogin;
    private AnimatorSet mShowProcess, mHideProcess;
    private AnimatorSet mShowImg, mHideImg;

    private void init(View v) {
        mSpUtil = SpUtil.defaultSp(getContext());
        mState = new StateImpl((BaseActivity) getActivity());

        pb_process = (ProgressBar) v.findViewById(R.id.pb_process);
        tv_message = (TextView) v.findViewById(R.id.tv_message);
        iv_eyes = (ImageView) v.findViewById(R.id.welcome_eyes);
        iv_mouth = (ImageView) v.findViewById(R.id.welcome_mouth);
        btn_login = (Button) v.findViewById(R.id.btn_login);
        tv_email = (TextView) v.findViewById(R.id.tv_email);
        tv_pwd = (TextView) v.findViewById(R.id.tv_password);
        tv_user = (TextView) v.findViewById(R.id.tv_user);
        cb_save = (CheckBox) v.findViewById(R.id.cb_save);
        et_email = (EditText) v.findViewById(R.id.et_email);
        et_pwd = (EditText) v.findViewById(R.id.et_password);
        et_user = (EditText) v.findViewById(R.id.et_user);
        rv_page = (RecyclerView) v.findViewById(R.id.rv_page);
        initPageChooser();
        initAnimators();
        cb_save.setChecked(mSpUtil.getBoolean(sp_save, false));
        et_pwd.setText(mSpUtil.getString(sp_pwd, ""));
        et_user.setText(mSpUtil.getString(sp_user, ""));
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    btn_login.setText(R.string.rlogin);
                    btn_login.setOnClickListener(v1 -> {
                        mState.login(
                                tv_user.getText().toString(),
                                tv_pwd.getText().toString());
                        mSpUtil.putString(sp_user, et_user.getText().toString());
                        mSpUtil.putString(sp_pwd, et_pwd.getText().toString());
                        mSpUtil.putBoolean(sp_save, cb_save.isChecked());
                    });
                } else {
                    btn_login.setText(R.string.rregister);
                    btn_login.setOnClickListener(v1 -> {
                        mState.register(
                                tv_user.getText().toString(),
                                tv_pwd.getText().toString(),
                                s.toString());
                        mSpUtil.putString(sp_user, et_user.getText().toString());
                        mSpUtil.putString(sp_pwd, et_pwd.getText().toString());
                        mSpUtil.putBoolean(sp_save, cb_save.isChecked());
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initPageChooser() {
        if (getActivity() instanceof Miao) {
            List<String> names = mListener.setItemNames();
            List<Drawable> icons = mListener.setItemIcons();
            FanLayoutManager layout = new FanLayoutManager(getContext(),
                    FanLayoutManagerSettings.newBuilder(getContext())
                    .withFanRadius(true)
                    .withAngleItemBounce(5)
                    .withViewHeightDp(130)
                    .withViewWidthDp(100)
                    .build());
            rv_page.setLayoutManager(layout);
            rv_page.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
                private int[] mColors = new int[]{
                        getResources().getColor(R.color.md_amber_300),
                        getResources().getColor(R.color.md_blue_300),
                        getResources().getColor(R.color.md_brown_300),
                        getResources().getColor(R.color.md_cyan_300),
                        getResources().getColor(R.color.md_green_300),
                        getResources().getColor(R.color.md_grey_300),
                        getResources().getColor(R.color.md_indigo_300),
                        getResources().getColor(R.color.md_lime_300),
                        getResources().getColor(R.color.md_orange_300),
                        getResources().getColor(R.color.md_pink_300),
                        getResources().getColor(R.color.md_purple_300),
                        getResources().getColor(R.color.md_red_300),
                        getResources().getColor(R.color.md_teal_300),
                        getResources().getColor(R.color.md_yellow_300),
                        getResources().getColor(R.color.md_blue_grey_300),
                        getResources().getColor(R.color.md_deep_orange_300),
                        getResources().getColor(R.color.md_deep_purple_300),
                        getResources().getColor(R.color.md_light_blue_300),
                        getResources().getColor(R.color.md_light_green_300)};
                private int mRndIndex;

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    mRndIndex = new Random(System.currentTimeMillis()).nextInt(mColors.length);
                    return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_page, parent, false));
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    holder.getTextView(R.id.tv_page).setText(names.get(position));
                    holder.getImageView(R.id.iv_page).setImageDrawable(icons.get(position));
                    holder.getView().setBackgroundColor(mColors[(mRndIndex * position) % mColors.length]);

                    ViewCompat.setTransitionName(holder.getImageView(R.id.iv_page), getString(R.string.fg_ani_page));

                    holder.getView().setOnClickListener(v -> {
                        if (layout.getSelectedItemPosition() == position) {
                            layout.straightenSelectedItem(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    mListener.onChooserClick(position, holder.getImageView(R.id.iv_page));
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                        } else {
                            layout.switchItem(rv_page, position);
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    return names.size();
                }
            });
            rv_page.setItemAnimator(new DefaultItemAnimator());
            rv_page.setChildDrawingOrderCallback(new FanChildDrawingOrderCallback(rv_page.getLayoutManager()));
        }
    }

    private void initAnimators() {
        ObjectAnimator processShow = ObjectAnimator.ofFloat(pb_process, "alpha", 0, 1);
        ObjectAnimator processHide = ObjectAnimator.ofFloat(pb_process, "alpha", 1, 0);
        ObjectAnimator processMsgShow = ObjectAnimator.ofFloat(tv_message, "alpha", 0, 1);
        ObjectAnimator processMsgHide = ObjectAnimator.ofFloat(tv_message, "alpha", 1, 0);
        ObjectAnimator loginButtonShow = ObjectAnimator.ofFloat(btn_login, "alpha", 0, 1);
        ObjectAnimator loginButtonHide = ObjectAnimator.ofFloat(btn_login, "alpha", 1, 0);
        ObjectAnimator userLabelShow = ObjectAnimator.ofFloat(tv_user, "alpha", 0, 1);
        ObjectAnimator userLabelHide = ObjectAnimator.ofFloat(tv_user, "alpha", 1, 0);
        ObjectAnimator userInputShow = ObjectAnimator.ofFloat(et_user, "alpha", 0, 1);
        ObjectAnimator userInputHide = ObjectAnimator.ofFloat(et_user, "alpha", 1, 0);
        ObjectAnimator pwdLabelShow = ObjectAnimator.ofFloat(tv_pwd, "alpha", 0, 1);
        ObjectAnimator pwdLabelHide = ObjectAnimator.ofFloat(tv_pwd, "alpha", 1, 0);
        ObjectAnimator pwdInputShow = ObjectAnimator.ofFloat(et_pwd, "alpha", 0, 1);
        ObjectAnimator pwdInputHide = ObjectAnimator.ofFloat(et_pwd, "alpha", 1, 0);
        ObjectAnimator emailLabelShow = ObjectAnimator.ofFloat(tv_email, "alpha", 0, 1);
        ObjectAnimator emailLabelHide = ObjectAnimator.ofFloat(tv_email, "alpha", 1, 0);
        ObjectAnimator emailInputShow = ObjectAnimator.ofFloat(et_email, "alpha", 0, 1);
        ObjectAnimator emailInputHide = ObjectAnimator.ofFloat(et_email, "alpha", 1, 0);
        ObjectAnimator saveShow = ObjectAnimator.ofFloat(cb_save, "alpha", 0, 1);
        ObjectAnimator saveHide = ObjectAnimator.ofFloat(cb_save, "alpha", 1, 0);
        ObjectAnimator imgEyeShow = ObjectAnimator.ofPropertyValuesHolder(iv_eyes,
                PropertyValuesHolder.ofFloat("alpha", 0, 1), PropertyValuesHolder.ofFloat("translationY", -500, -200));
        ObjectAnimator imgEyeHide = ObjectAnimator.ofPropertyValuesHolder(iv_eyes,
                PropertyValuesHolder.ofFloat("alpha", 1, 0), PropertyValuesHolder.ofFloat("translationY", -200, -500));
        ObjectAnimator imgEyeUp = ObjectAnimator.ofFloat(iv_eyes, "translationY", 0, -200);
        ObjectAnimator imgEyeDown = ObjectAnimator.ofFloat(iv_eyes, "translationY", -500, 0);
        ObjectAnimator imgMouthShow = ObjectAnimator.ofPropertyValuesHolder(iv_mouth,
                PropertyValuesHolder.ofFloat("alpha", 0, 1), PropertyValuesHolder.ofFloat("translationY", -500, -200));
        ObjectAnimator imgMouthHide = ObjectAnimator.ofPropertyValuesHolder(iv_mouth,
                PropertyValuesHolder.ofFloat("alpha", 1, 0), PropertyValuesHolder.ofFloat("translationY", -200, -500));
        ObjectAnimator imgMouthUp = ObjectAnimator.ofFloat(iv_mouth, "translationY", 0, -200);
        ObjectAnimator imgMouthDown = ObjectAnimator.ofFloat(iv_mouth, "translationY", -500, 0);
        
        mShowLoginButton = new AnimatorSet().setDuration(300);
        mShowLoginButton.playTogether(loginButtonShow, imgEyeUp, imgMouthUp);
        mHideLoginButton = new AnimatorSet().setDuration(300);
        mHideLoginButton.playTogether(loginButtonHide, imgEyeDown, imgMouthDown);
        mShowLogin = new AnimatorSet().setDuration(300);
        mShowLogin.playTogether(userLabelShow, userInputShow, pwdLabelShow, pwdInputShow, emailLabelShow, emailInputShow, saveShow);
        mHideLogin = new AnimatorSet().setDuration(300);
        mHideLogin.playTogether(userLabelHide, userInputHide, pwdLabelHide, pwdInputHide, emailLabelHide, emailInputHide, saveHide);
        mShowProcess = new AnimatorSet().setDuration(300);
        mShowProcess.playTogether(processShow, processMsgShow);
        mHideProcess = new AnimatorSet().setDuration(300);
        mHideProcess.playTogether(processHide, processMsgHide);
        mShowImg = new AnimatorSet().setDuration(300);
        mShowImg.playTogether(imgEyeShow, imgMouthShow);
        mHideImg = new AnimatorSet().setDuration(300);
        mHideImg.playTogether(imgEyeHide, imgMouthHide);
    }
    
    public void loginSucceed() {
        rv_page.setVisibility(View.VISIBLE);
        
        mHideLoginButton.start();
        mHideLogin.start();
        mShowImg.start();
        
        et_email.setEnabled(false);
        et_user.setEnabled(false);
        et_pwd.setEnabled(false);
        cb_save.setEnabled(false);
        btn_login.setEnabled(false);
        getProcessController().stopProcess();
    }

    public void prepareLogin() {
        rv_page.setVisibility(View.GONE);
        mShowLoginButton.start();
        btn_login.setText(R.string.login);
        btn_login.setEnabled(true);
        btn_login.setOnClickListener(v1 -> {
            et_email.setEnabled(true);
            et_user.setEnabled(true);
            et_pwd.setEnabled(true);
            cb_save.setEnabled(true);
            
            mHideImg.start();
            mShowLogin.start();
            
            btn_login.setText(R.string.rlogin);
            btn_login.setOnClickListener(v2 -> {
                mState.login(
                        et_user.getText().toString(),
                        et_pwd.getText().toString());
                mSpUtil.putString(sp_user, et_user.getText().toString());
                mSpUtil.putString(sp_pwd, et_pwd.getText().toString());
                mSpUtil.putBoolean(sp_save, cb_save.isChecked());
            });
        });
    }

    @Override
    protected ProcessController setProcessController() {
        return new ProcessController() {
            boolean isShow = false;

            @Override
            public void setProcess(int process, String message) {
                if (!isShow) {
                    mShowProcess.start();
                    pb_process.setMax(100);
                    isShow = true;
                }
                pb_process.setProgress(process > 100 ? 100 : process);
                tv_message.setTextColor(R.color.md_green_900);
                tv_message.setText(message);
            }

            @Override
            public void processError(Exception e) {
                if (!isShow) {
                    mShowProcess.start();
                    pb_process.setMax(100);
                    isShow = true;
                }
                tv_message.setTextColor(R.color.md_red_A700);
                tv_message.setText(e.getMessage());
            }

            @Override
            public void stopProcess() {
                if (isShow) {
                    mHideProcess.start();
                    isShow = false;
                }
            }
        };
    }
}
