package org.miaowo.miaowo.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class Setting extends BaseActivity
        implements PopupMenu.OnMenuItemClickListener {
    final private int IMG_CAMERA = 1;
    final private int IMG_ALBUM = 2;
    final private int IMG_EDIT = 3;

    private Users mUsers;
    private State mState;
    private Uri photo;

    @BindView(R.id.et_user) EditText et_name;
    @BindView(R.id.et_password) EditText et_pwd;
    @BindView(R.id.et_email) EditText et_email;
    @BindView(R.id.iv_user) ImageView iv_head;
    @BindView(R.id.iv_bg) ImageView iv_bg;
    PopupMenu mChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initActivity() {
        mUsers = new UsersImpl();
        mState = new StateImpl();

        mChoose = new PopupMenu(this, iv_head);
        mChoose.inflate(R.menu.img_choose);
        mChoose.setOnMenuItemClickListener(this);

        loadUser();
    }

    @OnClick({R.id.btn_send, R.id.btn_cancel, R.id.iv_user})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_send: send();break;
            case R.id.btn_cancel: finish();break;
            case R.id.iv_user: mChoose.show();break;
        }
    }

    private void send() {
        String name = et_email.getText().toString();
        String pwd = et_pwd.getText().toString();
        String email = et_name.getText().toString();
        mUsers.updateUser(name, pwd, email);

        startActivity(new Intent(this, Test.class));
    }

    private void loadUser() {
        User user = mState.loginUser();
        et_name.setText(user.getUsername());
        et_pwd.setText("");
        et_email.setText(user.getEmail());
        ImageUtil.utils().setUser(iv_head, user, false);
        Picasso.with(this).load(String.format(getString(R.string.url_home), user.getCoverUrl())).into(iv_bg);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_camera:
                runWithPermission(() -> {
                    try {
                        int uid = mState.isLogin() ? mState.loginUser().getUid() : 1;
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
                        toast(getString(R.string.err_camera_app), TastyToast.ERROR);
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
                Bitmap photo = data.getParcelableExtra("photo");
                if (photo == null) {
                    toast(getString(R.string.err_no_pic), TastyToast.ERROR);
                    break;
                }
                iv_head.setImageDrawable(new BitmapDrawable(getResources(), photo));
                break;
        }
        loadUser();
    }
}
