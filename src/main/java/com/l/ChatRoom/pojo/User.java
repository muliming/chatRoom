package com.l.ChatRoom.pojo;

import io.netty.channel.ChannelId;

public class User {
   private String name;
   private String roomID;
   private ChannelId channelID;
   private int Identity; //0:主持人，1:听众

    public User() {
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChannelId getChannelID() {
        return channelID;
    }

    public void setChannelID(ChannelId channelID) {
        this.channelID = channelID;
    }

    public int getIdentity() {
        return Identity;
    }

    public void setIdentity(int identity) {
        Identity = identity;
    }

}
