package org.miaowo.miaowo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import org.miaowo.miaowo.C;
import org.miaowo.miaowo.D;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.set.windows.ChatWindows;
import org.miaowo.miaowo.util.NotificationUtil;
import org.miaowo.miaowo.root.view.BaseActivity;

/**
 * 用于接收信息，分发信息
 * Created by luqin on 17-1-16.
 */

public class MessageReceiver extends BroadcastReceiver {
    Context context;

    public MessageReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case C.BC_CHAT:
                setChatMsg(intent.getParcelableExtra(C.EXTRA_ITEM));
                break;
            case C.BC_MSG:
                break;
        }
    }

    private void setChatMsg(ChatMessage msg) {
        // 当前聊天(添加至弹窗)->非当前(SnackBar提醒)->APP未运行(状态栏提醒)
        BaseActivity activity = D.getInstance().activeActivity;
        if (activity == null) {
            NotificationUtil.sendChatNotification(context, msg);
        }
        if (!ChatWindows.addChatMsg(msg)) {
            Snackbar.make(activity.getWindow().getDecorView(), "您有一条来自 " + msg.getFrom().getName() + " 的新消息", Snackbar.LENGTH_SHORT).show();
        }
    }
}
