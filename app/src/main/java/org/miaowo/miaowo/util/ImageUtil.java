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
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.UserWindows;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * 图形处理类
 * Created by luqin on 17-1-27.
 */

public class ImageUtil {
    private ImageUtil(BaseActivity context) {
        mContext = context;
    }
    public static ImageUtil utils(BaseActivity context) {
        return new ImageUtil(context);
    }

    private BaseActivity mContext;

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
        UserWindows uv = UserWindows.windows(mContext);
        if (clickable) {
            if (user.getUid() <= 0) {
                iv.setOnClickListener(v -> mContext.handleError(Exceptions.E_NON_LOGIN));
            } else {
                iv.setOnClickListener(v -> uv.showUserWindow(user.getUsername()));
            }
        }
    }
    private void fillUserImage(ImageView iv, User user) {
        if (TextUtils.isEmpty(user.getPicture())) {
            if (TextUtils.isEmpty(user.getIconText())) return;
            iv.setImageDrawable(textIcon(user));
            return;
        }
        fill(iv, mContext.getString(R.string.url_home) + user.getPicture(), null);
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
                creator = Picasso.with(mContext).load(R.drawable.def_user);
            } else {
                creator = Picasso.with(mContext).load(imgRes);
            }
            creator.transform(new CropCircleTransformation()).fit().into(iv);
        } else {
            mContext.runOnUiThread(() -> iv.setImageDrawable(textIcon(imgRes, config)));
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
        int bgColor = fromUser(user.getIconBgColor());
        TextIconConfig config = bgColor < 0
                ? new TextIconConfig(Color.BLUE, Color.WHITE)
                : new TextIconConfig(bgColor, Color.WHITE);
        return textIcon(user.getIconText(), config);
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

    /**
     * 文字图标配置对象, null 通常表示不是文字图标
     */
    public static class TextIconConfig {
        int bgColor;
        int textColor;

        public TextIconConfig(@ColorInt int bgColor, @ColorInt int textColor) {
            this.bgColor = bgColor;
            this.textColor = textColor;
        }
        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
            this.textColor = Color.WHITE;
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
