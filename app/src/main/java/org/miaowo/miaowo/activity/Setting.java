package org.miaowo.miaowo.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.CircleTransformation;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class Setting extends BaseActivity
        implements PopupMenu.OnMenuItemClickListener {
    final private int IMG_CAMERA = 1;
    final private int IMG_ALBUM = 2;

    private Uri photo, pDst;
    private File mDir;

    @BindView(R.id.et_user) EditText et_name;
    @BindView(R.id.et_password) EditText et_pwd;
    @BindView(R.id.et_email) EditText et_email;
    @BindView(R.id.iv_user) ImageView iv_head;
    @BindView(R.id.iv_bg) ImageView iv_bg;
    private PopupMenu mChoose;
    private User mUser;
    private HashMap<String, String> mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initActivity() {
        mUpdate = new HashMap<>();

        mChoose = new PopupMenu(this, iv_head);
        mChoose.inflate(R.menu.img_choose);
        mChoose.setOnMenuItemClickListener(this);

        mDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "miaowo");
        if (!mDir.isDirectory()) mDir.mkdirs();

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
        String pwd = et_pwd.getText().toString();
        mUpdate.put("username", et_email.getText().toString());
        mUpdate.put("email", et_name.getText().toString());
    }

    private void loadUser() {
        mUser = API.loginUser;
        et_name.setText(mUser.getUsername());
        et_pwd.setText(mUser.getPassword());
        et_email.setText(mUser.getEmail());
        ImageUtil.utils().setUser(iv_head, mUser, false);
        Picasso.with(this).load(String.format(getString(R.string.url_home), mUser.getCoverUrl())).into(iv_bg);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_camera:
                runWithPermission(() -> {
                    try {
                        int uid = mUser.getUid();
                        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            toast(R.string.err_sdcard, TastyToast.ERROR);
                            return;
                        }
                        File photoPath = new File(mDir, Integer.toString(uid));
                        if (photoPath.exists()) {
                            photoPath.delete();
                            photoPath.createNewFile();
                        }
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
                if (photo == null) {
                    toast(getString(R.string.err_no_pic), TastyToast.ERROR);
                    break;
                }
                File file = new File(mDir, "finalHead");
                if (file.exists()) file.delete();
                if (pDst == null)
                    pDst = FileProvider.getUriForFile(this, getString(R.string.file_provider), file);
                UCrop.of(photo, pDst).withAspectRatio(1, 1).start(this);
                break;
            case UCrop.REQUEST_CROP:
                Throwable error = UCrop.getError(data);
                if (resultCode == UCrop.RESULT_ERROR) {
                    toast(error.getMessage(), TastyToast.ERROR);
                    LogUtil.e(error);
                } else {
                    pDst = UCrop.getOutput(data);
                    LogUtil.i(pDst);
                    runOnUiThread(() -> Picasso.with(this)
                            .load(UCrop.getOutput(data))
                            .fit().transform(new CircleTransformation())
                            .into(iv_head));
                }
                break;
        }
        loadUser();
    }
}
