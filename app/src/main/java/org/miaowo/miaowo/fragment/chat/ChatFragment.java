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
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.ChatMessageList;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.impl.ChatImpl;
import org.miaowo.miaowo.impl.StateImpl;
import org.miaowo.miaowo.impl.interfaces.State;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.io.IOException;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 聊天内容
 * Created by luqin on 17-4-23.
 */

public class ChatFragment extends BaseFragment {

    @BindView(R.id.list)
    LoadMoreList lv_chat;
    @BindView(R.id.et_msg)
    EditText et_msg;

    private ChatMsgAdapter mAdapter;
    public ChatRoom mRoom;

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
        State mState = new StateImpl();

        mAdapter = new ChatMsgAdapter();
        lv_chat.setAdapter(mAdapter);
        lv_chat.loadMoreControl(false, false);

        mHttp.post(String.format(BaseActivity.get.getString(R.string.url_chat_message),
                mState.loginUser().getUsername().toLowerCase(), mRoom.getRoomId()), (call, response) ->
                BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                    try {
                        mAdapter.updateDate(mJson.buildFromAPI(response, ChatMessageList.class).getMessages());
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
        new ChatImpl().send(mRoom, et_msg.getText().toString());
        et_msg.setText("");
    }

    public void newMessage(ChatMessage message) {
        mAdapter.appendData(Collections.singletonList(message), false);
        lv_chat.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public ChatRoom getRoom() {
        return mRoom;
    }
}
