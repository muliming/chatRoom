package com.l.ChatRoom.service;

import com.l.ChatRoom.pojo.Room;
import com.l.ChatRoom.result.CodeMsg;

import java.util.List;

public interface LoginService {

    public CodeMsg confirmRoom(Room room);

    public void entryRoom(Room room);

    public List<String> online(String roomId);
}
