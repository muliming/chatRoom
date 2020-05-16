package com.l.ChatRoom.Netty;

import com.l.ChatRoom.enums.MsgActionEnum;
import com.l.ChatRoom.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//处理消息的handler
//在netty中，是用于为websocket专门处理文本的对象，frame是消息载体
//public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//
//    //用于记录和管理所有客户端的channel
//    public static ChannelGroup users=
//            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//
//    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
//        //1获取客户端传输过来的消息
//        String content=msg.text();
//
//        Channel currentChannel=ctx.channel();
//
//        //2判断消息的类型，根据不同类型处理不同业务
//        DataContent dataContent= JsonUtils.jsonToPojo(content,DataContent.class);
//        Integer action=dataContent.getAction();
//        if(action== MsgActionEnum.CONNECT.type){
//            // 2.1当websocket，第一次open的时候，初始化channel,把用户的channel和用户id 关联
//            String senderId=dataContent.getChatMsg().getSenderId();
//            UserChannelRel.put(senderId,currentChannel);
//        }else if(action==MsgActionEnum.CHAT.type){
//            // 2.2 聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
//            ChatMsg chatMsg=dataContent.getChatMsg();
//            String msgText=chatMsg.getMsg();
//            String receiveId=chatMsg.getReceiverId();
//            String senderId=chatMsg.getSenderId();
//
//            //保存信息到数据库，并且标记为未签收
//            //spring在托管类的时候，如果没有给定名字，则按照类名来取名，并且首先字母小写
//            UserService userService=(UserService) SpringUtil.getBean("userServiceImpl");
//            String msgId=userService.saveMsg(chatMsg);
//            chatMsg.setMsgId(msgId);
//            //发送消息
//            //从全局用户channel中获取接收方的channel
//            Channel channel=UserChannelRel.get(receiveId);
//            if(channel==null){
//                //为空代表用户离线，推送消息（JPush,个推，小米推送）
//            }else{
//                //当reveiverChannel不为空的时候，从CHannelGroup去查找对应的channel是否为空
//                Channel findChannel=users.find(channel.id());
//                if(findChannel!=null){
//                    //用户在线
//                    channel.writeAndFlush(
//                            new TextWebSocketFrame(
//                                    JsonUtils.objectToJson(chatMsg)
//                            )
//                    );
//                }else{
//                    //使用推送消息
//
//                }
//            }
//
//        }else if(action==MsgActionEnum.SIGNED.type){
//            // 2.3签收消息，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
//            UserService userService=(UserService) SpringUtil.getBean("userServiceImpl");
//            //扩展字段在signed类型的消息中，代表需要签收的消息id，逗号间隔
//            String msgIdStr=dataContent.getExtand();
//            String msgIdS[] =msgIdStr.split(",");
//
//            List<String> msgIdList=new ArrayList<>();
//            for(String mid:msgIdS){
//                if(StringUtils.isNotBlank(mid)){
//                    msgIdList.add(mid);
//                }
//            }
//
//            if(msgIdList!=null&&!msgIdList.isEmpty()&&msgIdList.size()>0){
//                userService.updateMsgSigned(msgIdList);
//            }
//
//
//        }else if(action==MsgActionEnum.KEEPALIVE.type){
//            // 2.4心跳类型的消息
//
//        }
//
//
//
//    }
//
//    /*
//    *当客户端连接服务器之后，（打开连接）
//    * 获取客户端的channel,并且放到ChannelGroup中进行管理
//     */
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        users.add(ctx.channel());
//
//    }
//
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        //当触发handlerRemove,ChannelGroup会自动移除对应客户端的channel
//        users.remove(ctx.channel());
//
////        System.out.println("客户端端口，channel对应的长id为："+
////                ctx.channel().id().asLongText());
////        System.out.println("客户端端口，channel对应的短id为："+
////                ctx.channel().id().asShortText());
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        //发生异常之后(关闭channel),随后从ChannelGroup中移除
//        ctx.channel().close();
//        users.remove(ctx.channel());
//    }
//}
