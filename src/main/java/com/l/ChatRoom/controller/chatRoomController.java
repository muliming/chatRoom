package com.l.ChatRoom.controller;

import com.l.ChatRoom.pojo.OnlineUser;
import com.l.ChatRoom.pojo.Room;
import com.l.ChatRoom.result.CodeMsg;
import com.l.ChatRoom.result.Result;
import com.l.ChatRoom.service.LoginService;
import com.l.ChatRoom.utils.BingImageUtil;
import com.l.ChatRoom.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller()
@RequestMapping()
public class chatRoomController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @GetMapping
    public String index(){
        return "login";
    }

    @ResponseBody
    @GetMapping("/changeImg")
    public Result changeImg(){
        String url=BingImageUtil.download(0,1);
        return Result.success(url);
    }

    @ResponseBody
    @PostMapping("/login")
    public Result login(Room room){
        if(room.getPassword()==null){
            room.setPassword("");
        }
        CodeMsg codeMsg=loginService.confirmRoom(room);
        return Result.INFO(codeMsg,null);
    }


    @ResponseBody
    @PostMapping("/entryRoom")
    public Result entryRoom(Room room, Model model){
        if(room.getPassword()==null){
            room.setPassword("");
        }
        loginService.entryRoom(room);
        return Result.success(room);
    }

    @GetMapping("/chat/{roomName}/{nickName}")
    public String chatPage(@PathVariable String roomName
                           , @PathVariable String nickName, ModelAndView modelAndView){
        modelAndView.addObject("roomName",roomName);
        modelAndView.addObject("nickName",nickName);
        return "chat";
    }

    //查看在线人数
    @ResponseBody
    @PostMapping("/online")
    public OnlineUser online(String room){
        OnlineUser onlineUser=new OnlineUser();
        if(room==null||room.equals("")){
            onlineUser.setOnlineNum(0);
        }else{
            List<String> list=loginService.online(room);
            onlineUser.setOnlineNum(list.size());
            onlineUser.setUsers(list);
        }
        return onlineUser;
    }

    //上传图片
    @ResponseBody
    @PostMapping("/fileUpload")
    public Result fileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String url =null;
        try {
             url = fastDFSClient.uploadBase64(file);
            return new Result(url);
            // 获取缩略图的url
//            String thump = "_100x100.";
//            String arr[] = url.split("\\.");
//            String thumpImgUrl = arr[0] + thump + arr[1];
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传图片失败");
            return new Result(CodeMsg.IMG_ERROR);
        }

    }

}
