package org.miaowo.miaowo.bean;

import java.util.ArrayList;

/**
 * 聊天室
 * Created by luqin on 16-12-31.
 */
public class ChatRoom {
    int roomId;
    String name;
    ArrayList<User> users;

    public ChatRoom(int roomId, String name, ArrayList<User> users) {
        this.roomId = roomId;
        this.name = name;
        this.users = users;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        if (roomId != chatRoom.roomId) return false;
        if (!name.equals(chatRoom.name)) return false;
        return users != null ? users.equals(chatRoom.users) : chatRoom.users == null;

    }

    @Override
    public int hashCode() {
        int result = roomId;
        result = 31 * result + name.hashCode();
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}
