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
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.UserWindows;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

public class ImageUtil {
    private ImageUtil() {}
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
        UserWindows uv = UserWindows.windows();
        if (clickable) {
            if (user.uid <= 0) {
                iv.setOnClickListener(v -> D.getInstance().activeActivity.handleError(Exceptions.E_NON_LOGIN));
            } else {
                iv.setOnClickListener(v -> uv.showUserWindow(user));
            }
        }
    }
    private void fillUserImage(ImageView iv, User user) {
        if (TextUtils.isEmpty(user.picture)) {
            if (TextUtils.isEmpty(user.iconText)) return;
            iv.setImageDrawable(textIcon(user));
            return;
        }
        fill(iv, user.picture, null);
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
                creator = Picasso.with(D.getInstance().activeActivity).load(R.drawable.def_user);
            } else {
                creator = Picasso.with(D.getInstance().activeActivity).load(imgRes);
            }
            creator.transform(new CropCircleTransformation()).fit().into(iv);
        } else {
            D.getInstance().activeActivity.runOnUiThread(() -> iv.setImageDrawable(textIcon(imgRes, config)));
        }
    }

    /**
     * 转换文字图标
     * @param text 转换的文字
     * @param config 文字图标配置
     * @return Drawable
     */
    public Drawable textIcon(String text, TextIconConfig config) {
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
    /**
     * 获取用户对象文字头像
     * @param user 要转换的用户对象
     * @return Drawable
     */
    public Drawable textIcon(User user) {
        int bgColor = userBgColor(user);
        TextIconConfig config = bgColor < 0
                ? new TextIconConfig()
                : new TextIconConfig(bgColor);
        return textIcon(user.iconText, config);
    }
    private int userBgColor(User u) {
        return Color.BLUE;
    }

    /**
     * 文字图标配置对象, null 通常表示不是文字图标
     */
    public static class TextIconConfig {
        int bgColor = Color.BLUE;
        int textColor = Color.WHITE;

        public TextIconConfig() {}
        public TextIconConfig(@ColorInt int bgColor) {
            this.bgColor = bgColor;
        }
        public TextIconConfig(@ColorInt int bgColor, @ColorInt int textColor) {
            this.bgColor = bgColor;
            this.textColor = textColor;
        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }
        public int getBgColor() {
            return bgColor;
        }
        public int getTextColor() {
            return textColor;
        }
        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }
}
