package com.l.ChatRoom.pojo;

public class Room {
    private String roomname;
    private String nickname;//开启者的名字
    private String password;

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Room(String roomname, String nickname, String password) {
        this.roomname = roomname;
        this.nickname = nickname;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomname='" + roomname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
