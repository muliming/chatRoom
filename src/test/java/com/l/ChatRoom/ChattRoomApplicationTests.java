package com.l.ChatRoom;

import com.l.ChatRoom.Redis.RedisUtils;
import com.l.ChatRoom.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ChattRoomApplicationTests {

	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private ApplicationContext applicationContext;


	/**
	 * 测试redis
	 **/
	@Test
	void contextLoads() {
		Object redisTemplate1 = applicationContext.getBean("redisTemplate");
		Object myRedisTemplate2 = applicationContext.getBean("myRedisTemplate");
	}

}
