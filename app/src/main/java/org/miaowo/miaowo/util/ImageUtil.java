package org.miaowo.miaowo.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

public class ImageUtil {

    public static void fillImage(ImageView iv, User u) {
        String urlHead = "imgUrl>>";
        if (iv == null) {
            return;
        }
        String path = u == null ? null : u.getHeadImg();
        if (path == null || !path.startsWith(urlHead)) {
            Picasso.with(D.getInstance().activeActivity).load(R.drawable.def_user)
                    .transform(new CropCircleTransformation()).fit()
                    .into(iv);
        } else {
            String url = path.substring(urlHead.length());
            Picasso.with(D.getInstance().activeActivity).load(url)
                    .transform(new CropCircleTransformation()).fit()
                    .into(iv);
        }
    }
}
