package com.l.ChatRoom.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WSServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workeGroup;
    private ServerBootstrap serverBootstrap;
    private static int PORT=8001;

    public void start(){
        try{
            bossGroup=new NioEventLoopGroup();
            workeGroup=new NioEventLoopGroup();
            serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workeGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitialzer());
            ChannelFuture future=serverBootstrap.bind(PORT).sync();

            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workeGroup.shutdownGracefully();
        }
    }

}
