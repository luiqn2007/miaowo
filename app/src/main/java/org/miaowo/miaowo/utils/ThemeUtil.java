package org.miaowo.miaowo.utils;

import android.content.Context;
import android.graphics.Color;

import org.miaowo.miaowo.C;

/**
 * 界面设置的辅助类
 * 时间紧急，就不写自定义主题的功能了，这个工具类是为以后主题创建的
 * 喵喜欢各种自定义，所以就也带到程序里来了。
 * Created by luqin on 16-12-28.
 */

public class ThemeUtil {

    /**
     * 加载默认主题配置
     * @param context 上下文，用于获取参数
     */
    public static void loadDefaultTheme(Context context) {
        /*
        自定义内容
        格式：
            C类：
                我写的一个常量储存类，防止手动输入错误
            约定：
                <...>       需具体内容替代
            颜色：
                alpha, red, green, blue 参数范围 0-255 整数
                推荐去 https://material.io/guidelines/style/color.html#color-color-palette 找
                可能要翻墙。查到的是类似 #AABBCCDD 的格式，AA为alpha，BB为red，CC为green，DD为blue
                用计算器把他们从十六进制转换成十进制就好了，Windows计算器->程序员
                SpUtil.putInt(this, <C.UI_*>, Color.argb(<alpha>, <red>, <green>, <blue>));
                SpUtil.putInt(this, <C.UI_*>, Color.rgb(<red>, <green>, <blue>)); -->alpha为255
            开关：
                value 范围：true 或 false
                SpUtil.putBoolean(this, <C.UI_*>, <value>);
         */
        // 侧边栏
        SpUtil.putInt(context, C.UI_SLIDE_USERNAME_COLOR, Color.rgb(0, 0, 0));
        SpUtil.putInt(context, C.UI_SLIDE_USERSUMMARY_COLOR, Color.rgb(0, 0, 0));
        // 广场 底部
        SpUtil.putInt(context, C.UI_BOTTOM_DEFAULT_COLOR, Color.rgb(255, 255, 255));
        SpUtil.putInt(context, C.UI_BOTTOM_SELECTED_COLOR, Color.rgb(238, 238, 238));
        // 问题列表
        SpUtil.putInt(context, C.UI_LIST_USERNAME_COLOR, Color.rgb(0, 0, 0));
        SpUtil.putInt(context, C.UI_LIST_TIME_COLOR, Color.rgb(117, 117, 117));
        SpUtil.putInt(context, C.UI_LIST_TITLE_COLOR, Color.rgb(121, 85, 72));
        SpUtil.putInt(context, C.UI_LIST_QUESTION_COUNT, 20);
    }

//    // 导入用户配置
//    public static void importTheme(Context context, Theme theme) {
//
//    }
//
//    // 导出当前配置
//    public static Theme exportTheme(Context context) {
//        return null;
//    }
//
//    // 保存配置
//    public static File saveTheme(Theme theme, File file) {
//        return null;
//    }
//
//    // 读取配置
//    public static Theme readTheme(File file) {
//        return null;
//    }
}
