package org.miaowo.miaowo.view.activity;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.root.MyApp;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.ImageUtil;
import org.miaowo.miaowo.util.PwdUtil;
import org.miaowo.miaowo.view.FloatView;

import java.io.File;

public class Setting extends BaseActivity {
    final private int IMG_CAMERA = 1;
    final private int IMG_ALBUM = 2;

    User newUser;
    Users mUsers;
    Uri src, dst;

    EditText et_name, et_pwd, et_summary;
    ImageView iv_head;
    Button btn_ok, btn_cancel, btn_reset, btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        mUsers = new UsersImpl();

        iv_head = (ImageView) findViewById(R.id.iv_user);
        et_name = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_password);
        et_summary = (EditText) findViewById(R.id.et_summary);
        btn_ok = (Button) findViewById(R.id.btn_send);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_clear = (Button) findViewById(R.id.btn_clear);

        fillDefault();

        iv_head.setOnClickListener(v -> chooseType());
        btn_cancel.setOnClickListener(v -> finish());
        btn_clear.setOnClickListener(v -> fillEmpty());
        btn_ok.setOnClickListener(v -> send());
        btn_reset.setOnClickListener(v -> fillDefault());
    }

    private void fillEmpty() {
        clearUser();
        fill();
    }

    private void fillDefault() {
        reloadUser();
        fill();
    }

    private void reloadUser() {
        User localUser = D.getInstance().thisUser;
        if (newUser == null) {
            newUser = new User(localUser.getName(), localUser.getSummary(), localUser.getPwd(), localUser.getHeadImg());
        } else {
            newUser.setHeadImg(localUser.getHeadImg());
            newUser.setName(localUser.getName());
            newUser.setPwd(localUser.getPwd());
            newUser.setSummary(localUser.getSummary());
        }
    }

    private void clearUser() {
        if (newUser == null) {
            newUser = new User("", "", "", "");
        } else {
            newUser.setHeadImg("");
            newUser.setName("");
            newUser.setPwd("");
            newUser.setSummary("");
        }
    }

    private void fill() {
        et_name.setText(newUser.getName());
        et_pwd.setText("");
        et_summary.setText(newUser.getSummary());
        ImageUtil.fillImage(iv_head, newUser.getHeadImg());
    }

    private void send() {
        newUser.setSummary(et_summary.getText().toString());
        newUser.setPwd(PwdUtil.toPwd(et_pwd.getText().toString(), et_name.getText().toString()));
        newUser.setName(et_name.getText().toString());
        new AsyncTask<User, Void, Exception>() {

            @Override
            protected Exception doInBackground(User... params) {
                try {
                    mUsers.updateUser(params[0]);
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e != null) {
                    handleError(e);
                    return;
                }
                finish();
            }
        }.execute(newUser);
    }

    private void setHeadImg() {
        File dir = new File(getFilesDir().getAbsolutePath() + File.separator + "headImg");
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        File img = new File(dir, "head_" + D.getInstance().thisUser.getId());
        dst = FileProvider.getUriForFile(this, MyApp.FILE_PROVIDER_URI, img);
        UCrop.of(src, dst).withAspectRatio(1, 1).start(this);
    }

    private void chooseType() {
        FloatView view = new FloatView(R.layout.window_choose_image);
        View v = view.getView();

        v.findViewById(R.id.btn_camera).setOnClickListener(v1 -> {
            try {
                File srcFile = new File(getCacheDir(), Integer.toString(D.getInstance().thisUser.getId()));
                if (srcFile.isDirectory()) {
                    srcFile.mkdirs();
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                src = FileProvider.getUriForFile(this, MyApp.FILE_PROVIDER_URI, srcFile);
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
        v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> view.dismiss());

        view.show(Gravity.BOTTOM, new Point(0, 200));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case IMG_ALBUM:
                src = data.getData();
                break;
            case IMG_CAMERA:
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    dst = UCrop.getOutput(data);
                    ImageUtil.fillImage(iv_head, "imgPath>>" + dst.getPath());
                } else {
                    handleError(new Exception(UCrop.getError(data)));
                }
        }

        setHeadImg();
    }
}
