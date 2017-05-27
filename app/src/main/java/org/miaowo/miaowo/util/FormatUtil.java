package org.miaowo.miaowo.util;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.root.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 格式化工具类
 * Created by luqin on 17-1-23.
 */

public class FormatUtil {
    private FormatUtil() {}
    public static FormatUtil format() { return new FormatUtil(); }

    public String time(long time) {
        time = System.currentTimeMillis() - time;
        long second = time / 1000;
        if (second >= 60 * 60 * 24 * 365) {
            int n = (int) (second / (60 * 60 * 24 * 365));
            return "至少" + n + "年";
        } else if (second >= 60 * 60 * 24 * 30) {
            int n = (int) (second / (60 * 60 * 24 * 30));
            return "至少" + n + "个月";
        } else if (second >= 60 * 60 * 24) {
            int n = (int) (second / (60 * 60 * 24));
            return n + "天";
        } else if (second >= 60 * 60) {
            int n = (int) (second / (60 * 60));
            return n + "小时";
        } else if (second >= 60) {
            return (second / 60) + "分钟";
        } else if (second >= 3) {
            return second + "秒";
        } else {
            return "片刻";
        }
    }

    public void fillCount(View v, @IdRes int viewId, int count) {
        TextView tv = (TextView) v.findViewById(viewId);
        int n = tv.getText().length();
        if (n == 3) {
            if (count <= 99) {
                tv.setText(String.valueOf(count));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * 3 / 2);
            }
        } else {
            if (count > 99) {
                tv.setText(R.string.more99);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() / 3 * 2);
            } else {
                tv.setText(String.valueOf(count));
            }
        }
    }

    public void parseHtml(String html, Consumer<Spanned> apply) {
        List<String> imgs = new ArrayList<>();
        Spanned htmlRet = Html.fromHtml(html, source -> {
            imgs.add(source);
            return ResourcesCompat.getDrawable(BaseActivity.get.getResources(), R.drawable.ic_loading, null);
        }, (opening, tag, output, xmlReader) -> {});
        if (!imgs.isEmpty()) new Thread(() -> loadImgs(html, imgs, apply)).start();
        apply.accept(htmlRet);
    }
    private void loadImgs(String html, List<String> imgs, Consumer<Spanned> apply) {
        Map<String, Drawable> imgRets = new HashMap<>(imgs.size());
        for (String img : imgs) {
            try {
                String picImg = img.toLowerCase().startsWith("http") ? img : BaseActivity.get.getString(R.string.url_home, img);
                LogUtil.i(picImg);
                imgRets.put(img, new BitmapDrawable(BaseActivity.get.getResources(), Picasso.with(BaseActivity.get).load(picImg).error(R.drawable.ic_error).get()));
            } catch (IOException e) {
                imgRets.put(img, ResourcesCompat.getDrawable(BaseActivity.get.getResources(), R.drawable.ic_error, null));
                e.printStackTrace();
            } finally {
                BaseActivity.get.runOnUiThreadIgnoreError(() -> apply.accept(Html.fromHtml(html, imgRets::get, (opening, tag, output, xmlReader) ->
                        output.setSpan((AlignmentSpan) () -> Layout.Alignment.ALIGN_NORMAL, 0, output.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE))));
            }
        }
    }
}
