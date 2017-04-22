package org.miaowo.miaowo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.miaowo.miaowo.bean.data.ChatMessage;
import org.miaowo.miaowo.bean.data.ChatRoom;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.fragment.chat.ChatFragment;
import org.miaowo.miaowo.fragment.chat.ChatListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager 的 Adapter
 * Created by luqin on 17-4-23.
 */

public class ChatPagerAdapter extends FragmentPagerAdapter {
    private List<ChatFragment> mFragments;
    private ChatListFragment mListFragment;

    public ChatPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mListFragment = ChatListFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) return mListFragment;
        else return mFragments.get(position - 1);
    }

    @Override
    public int getCount() {
        return mFragments.size() + 1;
    }

    public int addChat(ChatRoom room) {
        ChatFragment fragment = ChatFragment.newInstance(room);
        mFragments.add(fragment);
        return mFragments.indexOf(fragment) + 1;
    }

    public int getPosition(int roomId) {
        for (ChatFragment fragment : mFragments) {
            if (roomId == fragment.getRoom().getRoomId()) {
                return mFragments.indexOf(fragment) + 1;
            }
        }
        return -1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = getItem(position);
        if (fragment instanceof ChatListFragment) {
            return "所有聊天";
        } else if (fragment instanceof ChatFragment) {
            return ((ChatFragment) fragment).getRoom().getLastUser().getUsername();
        } else {
            return "你是谁? 从哪来?";
        }
    }

    public void sendMessage(int position, ChatMessage message) {
        Fragment fragment = getItem(position);
        if (fragment instanceof ChatFragment) {
            ((ChatFragment) fragment).newMessage(message);
        }
    }
}
