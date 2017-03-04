package org.miaowo.miaowo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.windows.UserWindows;

import java.io.IOException;
import java.util.Random;

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
    public static void setUserImage(@NonNull ImageView iv, User u) {
        if (u == null) {
            return;
        }
        fillUserImage(iv, u);
        UserWindows uv = new UserWindows();
        if (u.getId() <= 0) {
            iv.setOnClickListener(v -> Snackbar.make(D.getInstance().activeActivity.getWindow().getDecorView(), "流浪喵 为虚拟用户", Snackbar.LENGTH_SHORT).show());
        } else {
            iv.setOnClickListener(v -> uv.showUserWindow(u));
        }
    }

    /**
     * 填充用户头像框
     * @param iv 头像 ImageView
     * @param u 填充用户
     */
    public static void fillUserImage(@NonNull ImageView iv, User u) {
        String imgRes = u.getHeadImg();
        if (TextUtils.isEmpty(imgRes)) {
            if (TextUtils.isEmpty(u.getName())) return;
            imgRes = u.getName().substring(0, 1);
        }
        fillImage(iv, imgRes);
    }

    /**
     * 填充图片框
     * @param iv 图片框
     * @param imgRes 默认，地址，或转换为图片的文字
     */
    public static void fillImage(@NonNull ImageView iv, @NonNull String imgRes) {
        if ("default".equals(imgRes)) {
            Picasso.with(D.getInstance().activeActivity).load(R.drawable.def_user)
                    .transform(new CropCircleTransformation()).fit()
                    .into(iv);
        } else if (imgRes.length() <= 2) {
            iv.setImageDrawable(getText(imgRes));
        } else {
            Picasso.with(D.getInstance().activeActivity).load(imgRes)
                    .transform(new CropCircleTransformation()).fit()
                    .into(iv);
        }
    }

    /**
     * 获取图片资源
     * @param imgRes 默认，或转换为图片的文字
     * @return 头像 Drawable，返回 null 则表示输入的是 Uri 地址，无法直接转换
     */
    public static Drawable getDrawable(@NonNull String imgRes) {
        if ("default".equals(imgRes)) {
            return D.getInstance().activeActivity.getResources().getDrawable(R.drawable.def_user);
        } else if (imgRes.length() <= 2) {
            return getText(imgRes);
        } else {
            return null;
        }
    }

    /**
     * 获取文本图标
     * @param text 文本
     * @return 图标
     */
    public static TextDrawable getText(String text) {
        return TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .toUpperCase()
                .endConfig()
                .buildRound(text, Color.BLACK);
    }
}
