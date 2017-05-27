package org.miaowo.miaowo.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Detail;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.custom.CircleTransformation;
import org.miaowo.miaowo.root.BaseActivity;

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

public class ImageUtil {
    private ImageUtil() {

    }
    public static ImageUtil utils() {
        return new ImageUtil();
    }


    /**
     * 填充用户头像框
     * 附带设置点击查看详情
     * @param iv 头像 ImageView
     * @param user 填充用户
     */
    public void setUser(ImageView iv, User user, boolean clickable) {
        if (user == null) {
            return;
        }
        fillUserImage(iv, user);

        if (clickable) {
            if (user.getUid() <= 0) {
                iv.setOnClickListener(v -> BaseActivity.get.handleError(R.string.err_not_login));
            } else {
                iv.setOnClickListener(v -> Detail.showUser(user.getUsername()));
            }
        }
    }
    private void fillUserImage(ImageView iv, User user) {
        if (TextUtils.isEmpty(user.getPicture())) {
            if (TextUtils.isEmpty(user.getIconText())) return;
            iv.setImageDrawable(textIcon(user.getIconText(),
                    new TextIconConfig(fromUser(user.getIconBgColor()), Color.WHITE)));
            return;
        }
        fill(iv, BaseActivity.get.getString(R.string.url_home, user.getPicture()), null);
    }

    /**
     * 填充图片框
     * @param iv 图片框
     * @param imgRes 默认，地址，或转换为图片的文字
     */
    public void fill(ImageView iv, String imgRes, TextIconConfig config) {
        if (config == null) {
            RequestCreator creator;
            if ("default".equals(imgRes)) {
                creator = Picasso.with(BaseActivity.get).load(R.drawable.def_user);
            } else {
                creator = Picasso.with(BaseActivity.get).load(imgRes);
            }
            BaseActivity.get.runOnUiThreadIgnoreError(() -> creator.transform(new CircleTransformation()).fit().into(iv));
        } else {
            BaseActivity.get.runOnUiThreadIgnoreError(() -> iv.setImageDrawable(textIcon(imgRes, config)));
        }
    }

    private Drawable textIcon(String text, TextIconConfig config) {
        if (TextUtils.isEmpty(text) || config == null) {
            return null;
        }
        return TextDrawable.builder()
                .beginConfig()
                .textColor(config.textColor)
                .toUpperCase()
                .endConfig()
                .buildRound(text, config.bgColor);
    }
    private int fromUser(String color) {
        if (TextUtils.isEmpty(color) || color.length() < 6) {
            return -1;
        }
        int r = Integer.parseInt(color.substring(color.length() - 6, color.length() - 4), 16);
        int g = Integer.parseInt(color.substring(color.length() - 4, color.length() - 2), 16);
        int b = Integer.parseInt(color.substring(color.length() - 2, color.length()), 16);
        return Color.rgb(r, g, b);
    }

    private static class TextIconConfig {
        int bgColor;
        int textColor;

        TextIconConfig(@ColorInt int bgColor, @ColorInt int textColor) {
            this.bgColor = bgColor;
            this.textColor = textColor;
        }
    }
}
