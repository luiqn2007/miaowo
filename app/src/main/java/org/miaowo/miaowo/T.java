package org.miaowo.miaowo;

import org.miaowo.miaowo.beans.User;
import org.miaowo.miaowo.utils.MD5Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * T == Test
 * 用于测试的一些数据
 * Created by lq2007 on 16-12-7.
 */

public class T {
    private static int lastNum = 0;
    // 用于提供图片数据
    private static String[] imgUrls = new String[]{
            "http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg",
            "http://pic27.nipic.com/20130310/4499633_163759170000_2.jpg",
            "http://d.hiphotos.bdimg.com/album/whcrop%3D657%2C370%3Bq%3D90/sign=2c994e578a82b9013df895711cfd9441/09fa513d269759eede0805bbb2fb43166d22df62.jpg",
            "http://pic24.nipic.com/20120928/6062547_081856296000_2.jpg",
            "http://pic25.nipic.com/20121203/213291_135120242136_2.jpg",
            "http://pic.58pic.com/58pic/13/72/07/55Z58PICKka_1024.jpg",
            "http://pic15.nipic.com/20110731/8022110_162804602317_2.jpg",
            "http://pic29.nipic.com/20130515/1391526_115902145000_2.jpg",
            "http://pic19.nipic.com/20120215/6400281_142640002386_2.jpg",
            "http://pic27.nipic.com/20130222/11914137_165343253157_2.jpg",
            "http://pic41.nipic.com/20140519/18505720_094832590165_2.jpg",
            "http://img1.3lian.com/img013/v4/72/d/33.jpg",
            "http://tupian.enterdesk.com/2013/mxy/12/16/4/6.jpg",
            "http://img.taopic.com/uploads/allimg/140326/235113-1403260I05466.jpg"
    };
    public static String getRadomImgUrl() {
        int a = new Random(System.currentTimeMillis()).nextInt(imgUrls.length);
        while (a == lastNum) {
            a = (a + 1) % imgUrls.length;
        }
        lastNum = a;
        return imgUrls[a];
    }

    public static boolean isLogin = false;
    public static ArrayList<User> users;
    public static User localUser;

    static
    {
        users = new ArrayList<>();
        users.add(new User(-1, "流浪喵", "欢迎来到喵窝"));
        users.add(new User(0, "test", "测试账户", MD5Util.getMD5("test", "test"), null));
        localUser = users.get(0);
    }
}
