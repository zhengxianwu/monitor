package com.monitor.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import com.monitor.monitor.service.util.MyDataUtil;
import com.monitor.monitor.service.util.MyTimeUtil;

public class Test2 {

	public static void main(String[] args) throws ParseException {
		
		String utctoLoaclTime = MyTimeUtil.UtctoLoaclTime("2019-03-06T03:32:08.299Z");
		System.out.println(utctoLoaclTime);
		String localToUTC2 = MyTimeUtil.getLocalToUTC("2019-03-06 11:32:08");
		System.out.println( localToUTC2);
		
		
		
		String date = "2019-1-1";
		SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
		Date parse = s.parse(date);
		System.out.println(parse.getTime());
		
		String indexALL = MyDataUtil.getIndexALL("filebeat-6.4.3");
		System.out.println(indexALL);
	}

}
