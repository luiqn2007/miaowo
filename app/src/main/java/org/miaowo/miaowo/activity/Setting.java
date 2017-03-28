package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseApp;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.view.FloatView;

import java.io.File;

public class Setting extends BaseActivity {
    final private int IMG_CAMERA = 1;
    final private int IMG_ALBUM = 2;

    private User user;
    private Users mUsers;
    private State mState;
    private Uri src, dst;

    private EditText et_name, et_pwd, et_email;
    private ImageView iv_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        mUsers = new UsersImpl(this);
        mState = new StateImpl(this);
        user = mState.loginedUser();

        iv_head = (ImageView) findViewById(R.id.iv_user);
        et_name = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);

        fill();

        findViewById(R.id.btn_send).setOnClickListener(v -> send());
        findViewById(R.id.btn_cancel).setOnClickListener(v -> finish());
        iv_head.setOnClickListener(v -> chooseType());
    }

    private void fill() {
        et_name.setText(user.getUsername());
        et_pwd.setText("");
        et_email.setText(user.getEmail());
        ImageUtil.utils(this).setUser(iv_head, user, false);
    }

    private void send() {
        String name = et_email.getText().toString();
        String pwd = et_pwd.getText().toString();
        String email = et_name.getText().toString();
        try {
            mUsers.updateUser(name, pwd, email);
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void setHeadImg() {
        File dir = new File(getFilesDir().getAbsolutePath() + File.separator + "headImg");
        if (!dir.isDirectory()) {
            if (!dir.mkdirs()) {
                return;
            }
        }
        File img = new File(dir, "head_" + mState.loginedUser().getUid());
        dst = FileProvider.getUriForFile(this, BaseApp.FILE_PROVIDER_URI, img);
    }

    private void chooseType() {
        FloatView view = new FloatView(this, "选择图片", R.layout.window_choose_image);
        View v = view.getView();

        v.findViewById(R.id.btn_camera).setOnClickListener(v1 -> {
            try {
                File srcFile = new File(getCacheDir(), Integer.toString(mState.loginedUser().getUid()));
                if (srcFile.isDirectory()) {
                    if (!srcFile.mkdirs()) {
                        return;
                    }
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                src = FileProvider.getUriForFile(this, BaseApp.FILE_PROVIDER_URI, srcFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, src);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, IMG_CAMERA);
            } catch (Exception e) {
                e.printStackTrace();
                handleError(Exceptions.E_BAD_CAMERA);
            }
        });
        v.findViewById(R.id.btn_album).setOnClickListener(v1 -> {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, IMG_ALBUM);
        });
        v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> view.dismiss(false));

        view.show(Gravity.BOTTOM, new Point(0, 200));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMG_ALBUM:
                src = data.getData();
                break;
            case IMG_CAMERA:
                break;
        }

        setHeadImg();
    }
}
