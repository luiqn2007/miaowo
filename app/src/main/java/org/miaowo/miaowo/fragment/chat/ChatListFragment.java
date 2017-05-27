package org.miaowo.miaowo.fragment.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ChatRoomListAdapter;
import org.miaowo.miaowo.api.API;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.bean.data.ChatRoomList;
import org.miaowo.miaowo.custom.load_more_list.LMLPageAdapter;
import org.miaowo.miaowo.custom.load_more_list.LoadMoreList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.root.BaseFragment;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.util.JsonUtil;

import java.io.IOException;

import okhttp3.Request;

public class ChatListFragment extends BaseFragment {

    private OnChatListener mListener;
    private LoadMoreList mList;
    private LMLPageAdapter<ChatRoom> mAdapter;
    private HttpUtil mHttp;
    private JsonUtil mJson;
    public ChatListFragment() {}

    @SuppressWarnings("unused")
    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return new LoadMoreList(getContext());
    }

    @Override
    public void initView(View view) {
        if (view instanceof LoadMoreList) {
            mList = (LoadMoreList) view;
            mHttp = HttpUtil.utils();
            mJson = JsonUtil.utils();

            mAdapter = new ChatRoomListAdapter(getContext(), mListener);
            mList.setAdapter(mAdapter);
            mList.setPullRefresher(this::loadChatList, false);
            mList.load();
        }
    }

    private void loadChatList() {
        mList.loadMoreControl(false, false);
        Request request = new Request.Builder().url(getActivity().getString(R.string.url_chat_room, API.loginUser.getUsername().toLowerCase())).build();
        mHttp.post(request, (call, response) -> BaseActivity.get.runOnUiThreadIgnoreError(() -> {
                    try {
                        mAdapter.update(mJson.buildFromAPI(response, ChatRoomList.class).getRooms());
                        mList.loadMoreControl(true, true);
                        mList.loadOver();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChatListener) {
            mListener = (OnChatListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnChatListener {
        void toRoom(ChatRoom room);
    }
}
