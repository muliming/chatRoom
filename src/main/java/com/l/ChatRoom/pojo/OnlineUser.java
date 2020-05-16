package com.l.ChatRoom.pojo;

import java.util.List;

public class OnlineUser {
    private int onlineNum;
    List<String> users;

    public OnlineUser() {
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
