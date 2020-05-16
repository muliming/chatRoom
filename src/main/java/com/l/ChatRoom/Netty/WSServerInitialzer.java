package com.l.ChatRoom.Netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http2.InboundHttpToHttp2Adapter;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;


public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline=socketChannel.pipeline();

        //websocket基于http协议，所以有http解码器
        pipeline.addLast(new HttpServerCodec());
        ///对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        //几乎在http中的编程，都会使用到此handle
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //针对客户端，如果在1分钟时没有向服务器发送读写心跳，则主动断开链接
        //如果是读写空闲，不处理
        pipeline.addLast(new IdleStateHandler(20,400,600));
        //websocket服务器处理的协议，用于指定给客户端连接协议的支持
        /*
         *本handler会处理一些繁重的复杂的事
         * 会帮你处理握手动作：handshaking(close,ping,pong) ping+pong=心跳
         * 对于websocket来讲，都是frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
        pipeline.addLast(new ChatHandler());

    }
}
