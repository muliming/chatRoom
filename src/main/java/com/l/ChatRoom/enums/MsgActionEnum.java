package com.l.ChatRoom.enums;

/**
 * 
 * @Description: 发送消息的动作 枚举
 */
public enum MsgActionEnum {
	
	CONNECT(1, "第一次(或重连)初始化连接"),
	CHAT(2, "聊天消息"),
	KEEPALIVE(3, "客户端保持心跳"),
	BREAK(4,"断开连接"),
	IMAGE(5,"聊天内容为图片");


	public final Integer type;
	public final String content;
	
	MsgActionEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}  
}
