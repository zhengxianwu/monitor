package com.monitor.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.monitor.monitor.service.util.MyTimeUtil;

public class ES {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] localToUTCSub15 = MyTimeUtil.getLocalToUTCSub15();
		System.out.println(localToUTCSub15[0]+"---"+localToUTCSub15[1]);
	}

}
