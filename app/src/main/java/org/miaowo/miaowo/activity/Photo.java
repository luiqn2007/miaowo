package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.custom.PicEditor;
import org.miaowo.miaowo.root.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class Photo extends BaseActivity {
    Uri photo;
    Intent intent;

    @BindView(R.id.photo) PicEditor iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }

    @Override
    public void initActivity() {
        photo = getIntent().getParcelableExtra("photo");
        Picasso.with(this).load(photo).into(iv_photo);
        intent = new Intent();
        setResult(0, intent);
    }

    @OnClick(R.id.reset)
    public void reset() {
        iv_photo.resetPicture();
    }

    @OnClick(R.id.send)
    public void send() {
        intent.putExtra("photo", iv_photo.getChoiceBmp());
        finish();
    }
}
