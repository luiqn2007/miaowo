package org.miaowo.miaowo.fragment.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ChatMsgAdapter;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.ChatMessageList;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.custom.ChatListAnimator;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;
import org.miaowo.miaowo.util.LogUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 聊天内容
 * Created by luqin on 17-4-23.
 */

public class ChatFragment extends BaseFragment {
    @BindView(R.id.list) LoadMoreList lv_chat;
    @BindView(R.id.et_msg) EditText et_msg;

    private ChatMsgAdapter mAdapter;
    private ChatRoom mRoom;
    private API mApi;

    public ChatFragment() {}
    public static ChatFragment newInstance(ChatRoom room) {
        ChatFragment fragment = new ChatFragment();
        fragment.mRoom = room;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
    @Override
    public void initView(View view) {
        HttpUtil mHttp = HttpUtil.utils();
        JsonUtil mJson = JsonUtil.utils();
        mApi = new API();

        mAdapter = new ChatMsgAdapter();
        lv_chat.setAdapter(mAdapter);
        lv_chat.loadMoreControl(false, false);
        lv_chat.setItemAnimator(new ChatListAnimator());

        Request request = new Request.Builder().url(BaseActivity.get.getString(R.string.url_chat_message,
                API.loginUser.getUsername().toLowerCase(), mRoom.getRoomId())).build();
        mHttp.post(request, (call, response) ->
                BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                    try {
                        mAdapter.update(mJson.buildFromAPI(response, ChatMessageList.class).getMessages());
                        lv_chat.scrollToPosition(mAdapter.getItemCount() - 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    @OnClick(R.id.btn_send)
    public void onSend() {
        String message = et_msg.getText().toString();
        if (TextUtils.isEmpty(message)) {
            BaseActivity.get.toast(R.string.err_msg_empty, TastyToast.ERROR);
            return;
        }
        FormBody body = new FormBody.Builder()
                .add("message", message)
                .add("timestamp", String.valueOf(System.currentTimeMillis()))
                .add("roomId", String.valueOf(mRoom.getRoomId()))
                .build();
        mApi.useAPI(API.APIType.USERS, mRoom.getLastUser().getUid() + "/chats", API.Method.POST, true, body, (call, response) -> {
            // TODO: API-发送聊天信息
            LogUtil.i(response);
        });
        et_msg.setText("");
    }

    public void newMessage(ChatMessage message) {
        mAdapter.insert(message, false);
        lv_chat.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public ChatRoom getRoom() {
        return mRoom;
    }
}
