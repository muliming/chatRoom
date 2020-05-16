package com.l.ChatRoom.utils;

import java.util.Random;
import java.util.UUID;

public class UUIDUtil {

	private static Random random=new Random(1);

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 989-932-443
	 * @return   9位数字
	 */
	public static String roomNumber(){
		String number="";
		for(int i=0;i<3;i++){
			int a=random.nextInt(900)+100;
			number+=a;
			if(i<=1){
				number+='-';
			}
		}
		return number;
	}
}
