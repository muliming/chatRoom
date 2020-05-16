package com.l.ChatRoom.controller;

//import com.l.imooc_muxin.ImoocMuxinApplication;
//import com.l.imooc_muxin.enums.OperatorFriendRequestTypeEnum;
//import com.l.imooc_muxin.enums.SearchFriendsStatusEnum;
//import com.l.imooc_muxin.pojo.ChatMessage;
//import com.l.imooc_muxin.pojo.Users;
//import com.l.imooc_muxin.pojo.bo.UsersBO;
//import com.l.imooc_muxin.pojo.vo.MyFriendsVO;
//import com.l.imooc_muxin.pojo.vo.UsersVO;
//import com.l.imooc_muxin.service.UserService;
//import com.l.imooc_muxin.utils.FastDFSClient;
//import com.l.imooc_muxin.utils.MyFileUtils;
//import com.l.imooc_muxin.utils.IMoocJSONResult;
//import com.l.imooc_muxin.utils.MD5Utils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("u")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private FastDFSClient fastDFSClient;
//    @PostMapping("/registOrLogin")
//    public IMoocJSONResult registOrLogin(@RequestBody Users user) throws Exception {
//        //判断用户名和密码不能为空
//        if(StringUtils.isBlank(user.getUsername())
//                || StringUtils.isBlank(user.getPassword())){
//            return IMoocJSONResult.errorMsg("用户名或密码不能为空...");
//        }
//        //判断用户名是否存在，如果存在就登陆，如果不存在就注册
//        boolean usernameIsExit=userService.queryUsernameIsExist(user.getUsername());
//        Users userResult=null;
//        if(usernameIsExit){
//            //登陆
//            userResult=userService.queryUserForLogin(user.getUsername(),
//                    MD5Utils.getMD5Str(user.getPassword()));
//            if(userResult==null){
//                return IMoocJSONResult.errorMsg("用户名或密码不正确...");
//            }
//        }else{
//            //注册
//            user.setNickname(user.getUsername());
//            user.setFaceImage("");
//            user.setFaceImageBig("");
//            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
//            userResult=userService.saveUser(user);
//        }
//        UsersVO userVO=new UsersVO();
//        BeanUtils.copyProperties(userResult,userVO);
//        return IMoocJSONResult.ok(userVO);
//    }
//
//    /*
//    *
//     */
//    @PostMapping("/uploadFaceBase64")
//    public IMoocJSONResult uploadFaceBase64(@RequestBody UsersBO userBO)throws Exception{
//        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
//        String base64Data = userBO.getFaceData();
//        String userFacePath = "D:\\" + userBO.getUserId() + "userface64.png";
//        MyFileUtils.base64ToFile(userFacePath, base64Data);
//
//        // 上传文件到fastdfs
//        MultipartFile faceFile = MyFileUtils.fileToMultipart(userFacePath);
//        String url = fastDFSClient.uploadBase64(faceFile);
//        //System.out.println(url);
//
////		"dhawuidhwaiuh3u89u98432.png"
////		"dhawuidhwaiuh3u89u98432_80x80.png"
//
//        // 获取缩略图的url
//        String thump = "_80x80.";
//        String arr[] = url.split("\\.");
//        String thumpImgUrl = arr[0] + thump + arr[1];
//
//        // 更细用户头像
//        Users user = new Users();
//        user.setId(userBO.getUserId());
//        user.setFaceImage(thumpImgUrl);
//        user.setFaceImageBig(url);
//
//        Users result = userService.updateUserInfo(user);
//
//        return IMoocJSONResult.ok(result);
//    }
//
//    @PostMapping("/setNickname")
//    public IMoocJSONResult setNickname(@RequestBody UsersBO userBO) throws Exception{
//        Users user = new Users();
//        user.setId(userBO.getUserId());
//        user.setNickname(userBO.getNickname());
//        Users result = userService.updateUserInfo(user);
//        return IMoocJSONResult.ok(result);
//    }
//
//    //搜索好友接口，根据账户做匹配查询而不是模糊查询
//    @PostMapping("/search")
//    public IMoocJSONResult searchUser(String myUserId,String friendUsername) throws Exception{
//        //判断myUserId friendUsername不能为空
//        if(StringUtils.isBlank(myUserId)||StringUtils.isBlank(friendUsername)){
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        //前置条件--1，搜索用户如果不存在，返回 无此用户
//        //前置条件--2，搜索账号是你自己，返回  不能添加自己
//        //前置条件--3，搜索的朋友已经是你的好友，返回 该用户已经是你的好友
//        Integer status=userService.preconditionSearchFriend(myUserId,friendUsername);
//        if(status== SearchFriendsStatusEnum.SUCCESS.status){
//            Users users=userService.queryUserInfoByUsername(friendUsername);
//            UsersVO userVO=new UsersVO();
//            BeanUtils.copyProperties(users,userVO);
//            return IMoocJSONResult.ok(userVO);
//
//        }else{
//            String errorMsg=SearchFriendsStatusEnum.getMsgByKey(status);
//            return IMoocJSONResult.errorMsg(errorMsg);
//        }
//    }
//
//    //添加好友
//    @PostMapping("/addFriendRequest")
//    public IMoocJSONResult addFriendRequest(String myUserId,String friendUsername) throws Exception{
//        //判断myUserId friendUsername不能为空
//        if(StringUtils.isBlank(myUserId)||StringUtils.isBlank(friendUsername)){
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        //前置条件--1，搜索用户如果不存在，返回 无此用户
//        //前置条件--2，搜索账号是你自己，返回  不能添加自己
//        //前置条件--3，搜索的朋友已经是你的好友，返回 该用户已经是你的好友
//        Integer status=userService.preconditionSearchFriend(myUserId,friendUsername);
//        if(status== SearchFriendsStatusEnum.SUCCESS.status){
//            userService.sendFriendRequest(myUserId,friendUsername);
//        }else{
//            String errorMsg=SearchFriendsStatusEnum.getMsgByKey(status);
//            return IMoocJSONResult.errorMsg(errorMsg);
//        }
//        return IMoocJSONResult.ok();
//    }
//
//    //查询用户接收到的朋友申请
//    @PostMapping("/queryFriendRequest")
//    public IMoocJSONResult queryFriendRequest(String userId) throws Exception{
//        //判断不能为空
//        if(StringUtils.isBlank(userId)){
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        //查询用户接收到的朋友申请
//        return IMoocJSONResult.ok(userService.queryFriendRequestList(userId));
//    }
//
//    //处理用户收到的朋友申请
//    @PostMapping("/operFriendRequest")
//    public IMoocJSONResult operFriendRequest(String acceptUserId,String sendUserId,
//                                             Integer operType) throws Exception{
//        //acceptUserId   sendUserId不能为空
//        if(StringUtils.isBlank(acceptUserId)
//                ||StringUtils.isBlank(sendUserId)
//                ||operType==null){
//            return IMoocJSONResult.errorMsg("");
//        }
//        //如果operType 没有对应的枚举值，则直接抛出空错误
//        if(operType== OperatorFriendRequestTypeEnum.IGNORE.type){
//            //如果判断忽略好友请求，则直接删除好友请求数据
//            userService.deleFriendRequest(sendUserId,acceptUserId);
//        }else if(operType==OperatorFriendRequestTypeEnum.PASS.type){
//            //判断如果是通过好友请求，则互相增加好友记录到数据库
//            //删除好友请求的数据库表记录
//            userService.passFriendRequest(sendUserId,acceptUserId);
//        }
//        //数据库查询好友列表
//        List<MyFriendsVO> myFriendsVOList=userService.queryMyFriends(acceptUserId);
//        return IMoocJSONResult.ok(myFriendsVOList);
//    }
//
//    //查询好友列表
//    @PostMapping("/myFriends")
//    public IMoocJSONResult myFriends(String userId) throws Exception{
//        //userId  判断不能为空
//        if(StringUtils.isBlank(userId)){
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        //数据库查询好友列表
//        List<MyFriendsVO> myFriendsVOList=userService.queryMyFriends(userId);
//        return IMoocJSONResult.ok(myFriendsVOList);
//    }
//
//    //用户手机端获取未签收的消息队列
//    @PostMapping("/getUnReadMsgList")
//    public IMoocJSONResult getUnReadMsgList(String acceptUserId) throws Exception{
//        //userId  判断不能为空
//        if(StringUtils.isBlank(acceptUserId)){
//            return IMoocJSONResult.errorMsg("");
//        }
//
//        //数据库查询好友列表
//        List<ChatMessage> unReadMsgList=userService.getUnReadMsgList(acceptUserId);
//        return IMoocJSONResult.ok(unReadMsgList);
//    }
//
//}
