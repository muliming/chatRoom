
window.Chat={



    /**
     * 图片服务器的url地址
     */
    imgServerUrl: 'http://39.96.56.217:80/imooc/',



    /**
     * 和后端的枚举对应
     */
    CONNECT: 1, 	 // 第一次(或重连)初始化连接
    CHAT: 2, 		 // 聊天消息
    KEEPALIVE: 3, 	 // 客户端保持心跳
    BREAK:4,         //断开连接
    IMAGE:5,           //聊天内容是图片

    /**
     * 和后端的 ChatMsg 聊天模型对象保持一致
     * @param {Object} senderId
     * @param {Object} receiverId
     * @param {Object} msg
     */
    ChatMsg: function(senderId, roomID, msg){
        this.senderId = senderId;
        this.roomID = roomID;
        this.msg = msg;
    },

    /**
     * 构建消息 DataContent 模型对象
     * @param {Object} action
     * @param {Object} chatMsg
     * @param {Object} extand
     */
    DataContent: function(action, chatMsg){
        this.action = action;
        this.chatMsg = chatMsg;
    },
}
