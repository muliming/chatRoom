package com.l.ChatRoom;

import com.l.ChatRoom.Netty.WSServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            if(contextRefreshedEvent.getApplicationContext().getParent()==null){

                new Thread(()->{
                    WSServer wsServer=new WSServer();
                    wsServer.start();
                }).start();
                System.out.println("Netty服务器，启动完成");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
