package com.l.ChatRoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ChattRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChattRoomApplication.class, args);
	}

}
