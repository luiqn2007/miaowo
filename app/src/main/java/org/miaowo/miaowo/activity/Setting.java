package org.miaowo.miaowo.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.web.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.io.File;

public class Setting extends BaseActivity
        implements PopupMenu.OnMenuItemClickListener {
    final private int IMG_CAMERA = 1;
    final private int IMG_ALBUM = 2;
    final private int IMG_EDIT = 3;

    private User user;
    private Users mUsers;
    private State mState;
    private Uri photo;

    private EditText et_name, et_pwd, et_email;
    private ImageView iv_head;
    private PopupMenu mChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        mUsers = new UsersImpl(this);
        mState = new StateImpl(this);

        iv_head = (ImageView) findViewById(R.id.iv_user);
        et_name = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);

        user = mState.loginedUser();
        et_name.setText(user.getUsername());
        et_pwd.setText("");
        et_email.setText(user.getEmail());
        ImageUtil.utils(this).setUser(iv_head, user, false);

        mChoose = new PopupMenu(this, iv_head);
        mChoose.inflate(R.menu.img_choose);
        mChoose.setOnMenuItemClickListener(this);

        findViewById(R.id.btn_send).setOnClickListener(v -> send());
        findViewById(R.id.btn_cancel).setOnClickListener(v -> finish());
        iv_head.setOnClickListener(v -> mChoose.show());
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_camera:
                runWithPermission(() -> {
                    try {
                        int uid = mState.isLogin() ? mState.loginedUser().getUid() : 1;
                        File photoPath = new File(getCacheDir(),
                                Integer.toString(uid));
                        if (photoPath.exists()) photoPath.delete();
                        photo = FileProvider.getUriForFile(this,
                                getString(R.string.file_provider), photoPath);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, IMG_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                        handleError(Exceptions.E_BAD_CAMERA);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.menu_file:
                runWithPermission(() -> {
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
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.menu_text:
                mUsers.updateUserHead(null);
                break;
        }
        mChoose.dismiss();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMG_ALBUM:
                photo = data.getData();
            case IMG_CAMERA:
                Intent intent = new Intent(this, Photo.class);
                LogUtil.i(photo);
                intent.putExtra("photo", photo);
                startActivityForResult(intent, IMG_EDIT);
                break;
            case IMG_EDIT:
                String photo = data.getStringExtra("photo");
                if (photo == null) {
                    handleError(Exceptions.E_NON_PICTURE);
                    break;
                }
                mUsers.updateUserHead(photo);
                break;
        }
    }
}
