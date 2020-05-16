package com.l.ChatRoom.service.impl;

import com.l.ChatRoom.Redis.RedisUtils;
import com.l.ChatRoom.pojo.Room;
import com.l.ChatRoom.pojo.User;
import com.l.ChatRoom.result.CodeMsg;
import com.l.ChatRoom.service.LoginService;
import com.l.ChatRoom.utils.KeyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查看房间是否存在，名称是否重复
     * 1    名称重复
     * 2    房间存在，但是密码为空
     * 3    房间不存在
     *
     * @param room
     * @return
     */
    @Override
    public CodeMsg confirmRoom(Room room) {
        String name = KeyProfile.editRoomKey(room.getRoomname());
        if (redisUtils.hasKey(name)) {
            if (redisUtils.get(name).equals(room.getPassword())) {
                String numName = KeyProfile.editNumberKey(room.getRoomname());
                List<Object> users = redisUtils.lGet(numName, 0, -1);
                for (Object user : users) {
                    User u= (User) user;
                    if ((u.getName()).equals(room.getNickname())) {
                        return CodeMsg.Name_ERROR;
                    }
                }
            } else {
                return CodeMsg.PASSWORD_ERROR;
            }
        } else {
            return CodeMsg.NO_ROOM;
        }
        return CodeMsg.SUCCESS;
    }


    /**
     * 进入房间，如果房间存在，就将人数加一
     * 否则 创建房间，加入人数
     *
     * @param room
     */
    @Override
    public void entryRoom(Room room) {
        String roomName = KeyProfile.editRoomKey(room.getRoomname());
        if (!(redisUtils.hasKey(roomName))) {
            redisUtils.set(roomName, room.getPassword());

        }
    }

    @Override
    public List<String> online(String roomId) {
        List<String> result = new ArrayList<>();
        roomId = KeyProfile.editNumberKey(roomId);
        List<Object> list = redisUtils.lGet(roomId, 0, -1);
        if (list!=null&&list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                User u= (User) list.get(i);
                result.add(u.getName());
            }
        }
        return result;
    }
}
