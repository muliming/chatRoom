package com.l.ChatRoom.result;

public class CodeMsg {
    private int code;
    private String msg;

    //注册时异常
    public static CodeMsg SUCCESS=new CodeMsg(0,"success");
    public static CodeMsg Name_ERROR=new CodeMsg(1,"昵称已存在，请重新输入");
    public static CodeMsg PASSWORD_ERROR=new CodeMsg(2,"密码错误，请重新输入");
   // public static CodeMsg NO_PASSWORD=new CodeMsg(3,"房间无密码");
    public static CodeMsg NO_ROOM=new CodeMsg(3,"房间不存在");

    public static CodeMsg IMG_ERROR=new CodeMsg(4,"图片上传失败");


    //聊天时异常
    public static CodeMsg COME_ROOM=new CodeMsg(100,"成功加入房间");
    public static CodeMsg LEAVE_ROOM=new CodeMsg(101,"离开房间");


    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
