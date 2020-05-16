package com.l.ChatRoom.Netty;

import java.io.Serializable;

public class ChatMsg implements Serializable {

	private static final long serialVersionUID = 3611169682695799175L;
	
	private String senderId;		// 发送者的用户id	
	private String roomID;		// 房间名称
	private String msg;				// 聊天内容

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
