package com.l.ChatRoom.utils;

public class KeyProfile {

    //对房间的key进行加工
    public static String editRoomKey(String name){
        name="room-"+name;
        return name;
    }

    //对房间人员的key进行加工
    public static String editNumberKey(String name){
        name="number-"+name;
        return name;
    }

    //对channel的key进行加工
    public static String editChannelKey(String name){
        name="channel-"+name;
        return name;
    }
}
