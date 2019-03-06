package com.monitor.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.ResourceBundle;

public class Test2 {

	public static void main(String[] args) {
		// config为属性文件名，放在包com.test.config下，如果是放在src下，直接用config即可
		ResourceBundle resource = ResourceBundle.getBundle("./config.properties");
		String key = resource.getString("name");
		System.out.println(key);
	}

}
