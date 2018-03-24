package org.miaowo.miaowo.other;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.DrawableGetter;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.Miao;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.bean.data.Topic;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.interfaces.IMiaoListener;
import org.miaowo.miaowo.util.FormatUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 由于 Kotlin 的 @JvmStatic 无法被 DataBinding 正确识别，因此使用 Java 编写
 * Created by lq200 on 2018/2/20.
 */

public class DataBindingExtra {
    @BindingAdapter("visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("user_icon")
    public static void setUserIcon(View view, User user) {
        Context context = view.getContext();
        if (view instanceof ImageView) {
            if (user == null) Picasso.with(context).load(R.drawable.def_user);
            else if (!TextUtils.isEmpty(user.getPicture())) {
                String rImgRes = user.getPicture();
                if (!(rImgRes.startsWith("http") || rImgRes.startsWith("https")))
                    rImgRes = context.getString(R.string.url_home_head, rImgRes);
                Picasso.with(context)
                        .load(rImgRes)
                        .error(R.drawable.ic_error)
                        .transform(new CircleTransformation()).fit()
                        .into((ImageView) view);
            } else if (!user.getIconText().isEmpty()) {
                String colorStr = user.getIconBgColor();
                int color = Color.BLACK;
                if (colorStr.length() == 7) {
                    color = Color.rgb(
                            Integer.parseInt(colorStr.substring(1, 3), 16),
                            Integer.parseInt(colorStr.substring(3, 5), 16),
                            Integer.parseInt(colorStr.substring(5, 7), 16));
                } else if (colorStr.length() == 9) {
                    color = Color.argb(
                            Integer.parseInt(colorStr.substring(1, 3), 16),
                            Integer.parseInt(colorStr.substring(3, 5), 16),
                            Integer.parseInt(colorStr.substring(5, 7), 16),
                            Integer.parseInt(colorStr.substring(7, 9), 16));
                }
                String text = user.getIconText();
                Drawable drawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .toUpperCase()
                        .endConfig()
                        .buildRound(text, color);
                ((ImageView) view).setImageDrawable(drawable);
            } else Picasso.with(context).load(R.drawable.def_user);
        }
    }

    @BindingAdapter("user_bg")
    public static void setUserBackground(View view, String coverUrl) {
        Context context = view.getContext();
        if (view instanceof ImageView) {
            if (TextUtils.isEmpty(coverUrl))
                Picasso.with(context).load(R.drawable.def_user).fit().into((ImageView) view);
            else
                Picasso.with(context).load(context.getString(R.string.url_home_head, coverUrl)).fit().into((ImageView) view);
        }
    }

    @BindingAdapter("html")
    public static void setHTML(final View view, final String html) {
        if (view instanceof TextView) {
            if (TextUtils.isEmpty(html)) ((TextView) view).setText("");
            else {
                final Point screenSize = FormatUtil.INSTANCE.getScreenSize();
                int min = Math.min(screenSize.x, screenSize.y);
                RichText.from(removeEmptyEndLine(html))
                        // img size
                        .size(min, min)
                        // img click
                        .imageClick(new OnImageClickListener() {
                            @Override
                            public void imageClicked(List<String> imageUrls, int position) {
                                if (!imageUrls.isEmpty())
                                    Miao.Companion.getI().getHandler().jump(IMiaoListener.JumpFragment.Image, imageUrls.get(0));
                            }
                        })
                        // url click
                        .urlClick(new OnUrlClickListener() {
                            @Override
                            public boolean urlClicked(String uri) {
                                String homeUrl = view.getContext().getString(R.string.url_home_head, "");
                                // miaowo 内部(开头包含首页链接或非协议)
                                if (uri.startsWith(homeUrl) || !uri.contains("://")) {
                                    openMiaoPage(homeUrl, uri);
                                } else {
                                    Intent uriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    Miao.Companion.getI().startActivity(uriIntent);
                                }
                                return true;
                            }
                        })
                        // loading img
                        .placeHolder(new DrawableGetter() {
                            @Override
                            public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
                                return ResourcesCompat.getDrawable(view.getContext().getResources(), R.drawable.ic_loading, null);
                            }
                        })
                        // error img
                        .errorImage(new DrawableGetter() {
                            @Override
                            public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
                                return ResourcesCompat.getDrawable(view.getContext().getResources(), R.drawable.ic_error, null);
                            }
                        })
                        .cache(CacheType.all)
                        .into((TextView) view);
            }
        }
    }

    @BindingAdapter("bookmarks")
    public static void setBookMarks(View view, Post post) {
        if (view instanceof TextView)
            ((TextView) view).setText(post == null ? "null" : String.valueOf(post.getBookmarked()));
    }

    @BindingAdapter("username")
    public static void setUserName(View view, User user) {
        if (view instanceof TextView)
            ((TextView) view).setText(user == null ? "null" : user.getUsername());
    }

    @BindingAdapter("title")
    public static void setTitle(View view, Topic title) {
        if (view instanceof TextView) {
            if (title == null || title.getTitleRaw().isEmpty()) {
                ((TextView) view).setText("");
            } else {
                RichText.from(removeEmptyEndLine(title.getTitleRaw()
                )).into((TextView) view);
            }
        }
    }

    @BindingAdapter("time")
    public static void setTime(View view, Post post) {
        if (view instanceof TextView)
            ((TextView) view).setText(post == null ? "null" : FormatUtil.INSTANCE.time(post.getTimestamp()));
    }

    @BindingAdapter("fontIcon")
    public static void setFontIcon(View view, IIcon icon) {
        if (view instanceof ImageView && icon != null) {
            IconicsDrawable i = new IconicsDrawable(view.getContext(), icon);
            ((ImageView) view).setImageDrawable(i.actionBar());
        }
    }

    private static String removeEmptyEndLine(String html) {
        if (html == null || !html.endsWith("\n")) {
            return html;
        }
        String ret = html.substring(0, html.length() - 2);
        while (ret.endsWith("\n")) {
            ret = html.substring(0, html.length() - 2);
        }
        return ret;
    }

    private static void openMiaoPage(String homeUrl, String path) {
        String subPath = path;
        if (path.startsWith(homeUrl))
            subPath = subPath.replace(homeUrl, "");
        while (subPath.startsWith("/"))
            subPath = subPath.substring(1);
        String[] miaoSub = subPath.split("/");
        switch (miaoSub[0]) {
            case "post": {
                Miao.Companion.getI().jump(IMiaoListener.JumpFragment.Topic, "[int]" + miaoSub[1]);
                break;
            }
            case "uid": {
                Miao.Companion.getI().jump(IMiaoListener.JumpFragment.User, "[int]" + miaoSub[1]);
                break;
            }
            default: {
                String t = Arrays.toString(miaoSub);
                Log.i("DataBinding: miaoPage", t);
                Miao.Companion.getI().snackBar(t, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
