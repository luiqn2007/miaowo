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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseApp;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.util.ImageUtil;
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
    Button btn_ok, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        mUsers = new UsersImpl();
        newUser = new User(D.getInstance().thisUser);

        iv_head = (ImageView) findViewById(R.id.iv_user);
        et_name = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_password);
        et_summary = (EditText) findViewById(R.id.et_summary);
        btn_ok = (Button) findViewById(R.id.btn_send);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        fill();

        iv_head.setOnClickListener(v -> chooseType());
        btn_cancel.setOnClickListener(v -> finish());
        btn_ok.setOnClickListener(v -> send());
    }

    private void fill() {
        et_name.setText(newUser.username);
        et_pwd.setText("");
        et_summary.setText(newUser.signature);
        ImageUtil.utils().setUser(iv_head, newUser, false);
    }

    private void send() {
        newUser.signature = et_summary.getText().toString();
        newUser.password = et_pwd.getText().toString();
        newUser.username = et_name.getText().toString();
        try {
            mUsers.updateUser(newUser);
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void setHeadImg() {
        File dir = new File(getFilesDir().getAbsolutePath() + File.separator + "headImg");
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        File img = new File(dir, "head_" + D.getInstance().thisUser.uid);
        dst = FileProvider.getUriForFile(this, BaseApp.FILE_PROVIDER_URI, img);
        UCrop.of(src, dst).withAspectRatio(1, 1).start(this);
    }

    private void chooseType() {
        FloatView view = new FloatView(R.layout.window_choose_image);
        View v = view.getView();

        v.findViewById(R.id.btn_camera).setOnClickListener(v1 -> {
            try {
                File srcFile = new File(getCacheDir(), Integer.toString(D.getInstance().thisUser.uid));
                if (srcFile.isDirectory()) {
                    srcFile.mkdirs();
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
        v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> view.dismiss());

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
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    dst = UCrop.getOutput(data);
                    ImageUtil.utils().fill(iv_head, dst.getPath(), null);
                } else {
                    handleError(new Exception(UCrop.getError(data)));
                }
        }

        setHeadImg();
    }
}
