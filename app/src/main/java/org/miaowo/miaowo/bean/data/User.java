package org.miaowo.miaowo.bean.data;

/**
 * 用户信息类的抽象接口
 * Created by luqin on 17-3-15.
 */

public interface User {
    String getUsername();
    String getUserslug();
    String getEmail();
    String getPicture();
    int getReputation();
    String getStatus();
    int getUid();
    boolean emailConfirmed();
    String getIconText();
    String getIconBgColor();
    boolean isAdmin();
    String getPassword();
    int getPostcount();
}
