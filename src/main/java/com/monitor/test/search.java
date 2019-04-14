package com.monitor.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.been.Test;
import com.monitor.monitor.dao.ScheduleTaskDb;
import com.monitor.monitor.es.type.ScheduleTaskType;
import com.monitor.monitor.service.util.MyMD5;

public class search {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		List<Test> list = new ArrayList();
//		for (int i = 0; i < 10; i++) {
//			list.add(new Test(i, "a " + i));
//		}
//        List<Test> t = list.stream().filter(a -> a.getId() == 2).collect(Collectors.toList());
//        Test test = t.get(0);
//        System.out.println(test);
		
		
//		md5
//		String hostname = "zhengxian";
//		int id = 1;
//		String md5Hex = DigestUtils.md5Hex(hostname + id);
//		System.out.println(md5Hex);
		String md5 = MyMD5.Md5("zhengxian", String.valueOf(new Date().getTime()));
		System.out.println(md5);
	}

}
