package org.miaowo.miaowo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.util.LogUtil;

public class Photo extends BaseActivity {
    Uri photo;
    ImageView iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photo = getIntent().getParcelableExtra("photo");
        LogUtil.i(photo);
        iv_photo = (ImageView) findViewById(R.id.photo);
        Picasso.with(this).load(photo).into(iv_photo);
    }
}
