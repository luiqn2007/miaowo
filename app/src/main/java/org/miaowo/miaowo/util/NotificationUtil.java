package org.miaowo.miaowo.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.activity.Miao;
import org.miaowo.miaowo.bean.data.web.ChatMessage;
import org.miaowo.miaowo.root.BaseApp;

/**
 * 向系统状态栏发送消息
 * Created by luqin on 17-1-15.
 */

public class NotificationUtil {
    public static Notification sendChatNotification(Context context, ChatMessage message) {
        final int PW_CHAT = 1;

        Notification chatMsgNoti, chatLockNoti;
        PendingIntent chatDialog;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, Miao.class);
        intent.putExtra(BaseApp.EXTRA_ITEM, PW_CHAT);
        chatDialog = PendingIntent.getActivity(context, 0, intent, 0);

        chatLockNoti = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.cat_ghost)
                .setContentTitle("喵有新消息啦")
                .setContentText(message.getFrom().getUsername() + "给喵发来一条信息")
                .setContentIntent(chatDialog)
                .build();

        chatMsgNoti = new NotificationCompat.Builder(context)
                .setContentTitle(message.getFrom().getUsername() + "给喵发来一条信息")
                .setContentText(message.getMessage().length() > 10
                        ? message.getMessage().substring(0, 6) + "..."
                        : message.getMessage())
                .setSmallIcon(R.mipmap.cat_ghost)
                .setContentIntent(chatDialog)
                .setAutoCancel(true)
                .setPublicVersion(chatLockNoti)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.getMessage()))
                .build();

        manager.notify(PW_CHAT, chatMsgNoti);
        return chatMsgNoti;
    }
}
