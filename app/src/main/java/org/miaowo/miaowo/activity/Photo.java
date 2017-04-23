package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;

import butterknife.BindView;

public class Photo extends BaseActivity {
    Uri photo;
    Intent intent;

    @BindView(R.id.photo) ImageView iv_photo;

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
}
