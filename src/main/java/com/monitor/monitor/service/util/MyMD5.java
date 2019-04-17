package com.monitor.monitor.service.util;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class MyMD5 {
	
	public static String Md5(String key,String time) {
		return DigestUtils.md5Hex((key+time).getBytes());
	}
	
	public static String Md5(String key) {
		return DigestUtils.md5Hex((key+new Date().getTime()).getBytes());
	}
}
