package com.l.ChatRoom.result;

public class Result<T> {
    private int code;
    private String msg;
    private T data;



    //成功时调用
    public static <T> Result<T> success(T data){
        return  new Result<T>(data);
    }
    public Result(T data) {
        this.code=0;
        this.msg="success";
        this.data=data;
    }

    //失败时调用
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }

    private Result(CodeMsg cm){
        if(cm==null){
            return;
        }
        this.code=cm.getCode();
        this.msg=cm.getMsg();
    }

    //通知类消息
    public static <T> Result<T> INFO(CodeMsg cm,T data){
        return new Result<T>(cm,data);
    }
    private Result(CodeMsg cm, T data){
        if(cm==null){
            return;
        }
        this.data=data;
        this.code=cm.getCode();
        this.msg=cm.getMsg();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
