package org.miaowo.miaowo.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.set.windows.UserWindows;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

public class ImageUtil {

    /**
     * 填充用户头像框
     * 附带设置点击查看详情
     * @param iv 头像 ImageView
     * @param u 填充用户
     */
    public static void fillUserImage(@NonNull ImageView iv, User u) {
        UserWindows uv = new UserWindows();
        fillImage(iv, u.getHeadImg());
        iv.setOnClickListener(v -> uv.showUserWindow(u));
    }

    /**
     * 填充图片框
     * @param iv 图片框
     * @param path 路径，以 "imgPath>>" 开头为图片Uri，否则加载默认头像图片
     */
    public static void fillImage(@NonNull ImageView iv, String path) {
        String urlHead = "imgPath>>";
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
