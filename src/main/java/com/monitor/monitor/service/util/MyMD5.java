package com.monitor.monitor.service.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MyMD5 {
	
	public static String Md5(String key,String time) {
		return DigestUtils.md5Hex((key+time).getBytes());
	}
}
