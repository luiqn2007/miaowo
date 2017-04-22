package org.miaowo.miaowo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.ChatPagerAdapter;
import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.fragment.chat.ChatListFragment;
import org.miaowo.miaowo.root.BaseActivity;

import butterknife.BindView;

public class Chat extends BaseActivity implements ChatListFragment.OnChatListener {

    @BindView(R.id.tab) TabLayout mTab;
    @BindView(R.id.container) ViewPager mPager;
    private ChatPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
    @Override
    public void initActivity() {
        mAdapter = new ChatPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTab.setupWithViewPager(mPager);
    }

    @Override
    public void toRoom(ChatRoom room) {
        int position = mAdapter.getPosition(room.getRoomId());
        if (position <= 0) position = mAdapter.addChat(room);
        mAdapter.notifyDataSetChanged();
        mPager.setCurrentItem(position, true);
    }

    public void sendMessage(ChatMessage message) {
        int position = mAdapter.getPosition(message.getRoomId());
        if (position > 0) {
            mAdapter.sendMessage(position, message);
        }
    }
}
