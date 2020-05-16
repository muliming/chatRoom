package com.l.ChatRoom.Netty;

import com.l.ChatRoom.Redis.RedisUtils;
import com.l.ChatRoom.SpringUtil;
import com.l.ChatRoom.enums.MsgActionEnum;
import com.l.ChatRoom.pojo.User;
import com.l.ChatRoom.utils.JsonUtils;
import com.l.ChatRoom.utils.KeyProfile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel
    public static ChannelGroup users=
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private static  RedisUtils redisUtils;

    static {
        redisUtils=SpringUtil.getBean(RedisUtils.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg=textWebSocketFrame.text();
        DataContent dataContent= JsonUtils.jsonToPojo(msg,DataContent.class);
        int action=dataContent.getAction();
        System.out.println("信息动作是："+action);
        ChatMsg chatMsg=dataContent.getChatMsg();
        Channel chanel=ctx.channel();
        if(action== MsgActionEnum.CONNECT.type){
            String roomId=chatMsg.getRoomID();
            String name=chatMsg.getSenderId();
            User user=new User();
            user.setName(name);
            user.setChannelID(chanel.id());
            user.setIdentity(1);
            user.setRoomID(roomId);
            roomId= KeyProfile.editNumberKey(roomId);
            //将channel和用户对应的关系存入redis
            boolean l=redisUtils.lSet(roomId,user);
            System.out.println(l);
            redisUtils.set(chanel.id().asLongText(),user);
            chanel.writeAndFlush(new TextWebSocketFrame(
               msg
            ));
            //通知其他人你上线了
            System.out.println("我上线了");
            notifyOthers(user.getRoomID(),user.getName(),chatMsg);
        }else if(action==MsgActionEnum.CHAT.type){
            String roomId=chatMsg.getRoomID();
            String name=chatMsg.getSenderId();
            roomId= KeyProfile.editNumberKey(roomId);
            String Msg=chatMsg.getMsg();
            List<Object> list=redisUtils.lGet(roomId,0,-1);
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    User user= (User) list.get(i);
                    if(!(user.getName().equals(name))){
                        Channel writeChannel=users.find(user.getChannelID());
                        if(writeChannel!=null){
                            DataContent dataContent1=new DataContent();
                            dataContent1.setAction(MsgActionEnum.CHAT.type);
                            dataContent1.setChatMsg(chatMsg);
                            writeChannel.writeAndFlush(new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContent1)
                            ));
                        }
                    }
                }
            }
        }else if(action==MsgActionEnum.IMAGE.type){
            String roomId=chatMsg.getRoomID();
            String name=chatMsg.getSenderId();
            roomId= KeyProfile.editNumberKey(roomId);
            String Msg=chatMsg.getMsg();
            List<Object> list=redisUtils.lGet(roomId,0,-1);
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    User user= (User) list.get(i);
                    if(!(user.getName().equals(name))){
                        Channel writeChannel=users.find(user.getChannelID());
                        if(writeChannel!=null){
                            DataContent dataContent1=new DataContent();
                            dataContent1.setAction(MsgActionEnum.IMAGE.type);
                            dataContent1.setChatMsg(chatMsg);
                            writeChannel.writeAndFlush(new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContent1)
                            ));
                        }
                    }
                }
            }

        }else if(action==MsgActionEnum.KEEPALIVE.type){

        }else {

        }

    }

    //通知其他人
    private void notifyOthers(String roomId,String name,ChatMsg chatMsg){
        List<Object> list=redisUtils.lGet(KeyProfile.editNumberKey(roomId),0,-1);
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                User u= (User) list.get(i);
                //判断是否是自己
                if(!((u.getName()).equals(name))){
                    Channel channel=users.find(u.getChannelID());
                    if(channel!=null){
                        DataContent dataContent1=new DataContent();
                        dataContent1.setAction(MsgActionEnum.CONNECT.type);
                        dataContent1.setChatMsg(chatMsg);
                        channel.writeAndFlush(new TextWebSocketFrame(
                                JsonUtils.objectToJson(dataContent1)
                        ));
                    }
                }
            }

        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
       users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("下线通知");
        User user= (User) redisUtils.get(ctx.channel().id().asLongText());
        Channel channel=users.find(user.getChannelID());
        String roomId=user.getRoomID();
        String name=user.getName();
        String numRoomId=KeyProfile.editNumberKey(roomId);
        //删除redis中缓存的channel信息
        redisUtils.del(ctx.channel().id().asLongText());
        redisUtils.lRemove(numRoomId,1,user);
        if(redisUtils.lGet(numRoomId,0,-1).size()==0){
            redisUtils.del(numRoomId);
            redisUtils.del(KeyProfile.editRoomKey(roomId));
        }
        //通知其他人你下线了
        ChatMsg chatMsg=new ChatMsg();
        chatMsg.setRoomID(roomId);
        chatMsg.setSenderId(name);
        notifyOthers(user.getRoomID(),user.getName(),chatMsg);
        //group中删除channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
        handlerRemoved(ctx);
    }

}
