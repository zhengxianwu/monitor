package com.monitor.test;

import java.util.Date;

import com.monitor.monitor.es.type.OperationType;
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
		String md5 = MyMD5.Md5("zhengxian", String.valueOf(new Date().getTime()));
		System.out.println(md5);
		
		
		//时间过滤
//		String taskValue = "0.1_1_1";
//		for(String s :taskValue.split("_")) {
//			if(!TaskUtil.isInteger(s))System.out.println(String.valueOf(false));
//		}
//		String[] minuteRange = MyTimeUtil.getMinuteRange("2019-04-16 14:33");
//		System.out.println(minuteRange[0]);
		
		
		//自定义句子
		String s = "{hostname}:123,${ok}";
		String replaceAll = s.replaceAll("hostname", "zhengxian");
		System.out.println(replaceAll);
		
		
		OperationType ot = OperationType.Gt;
		switch (ot) {
		case Gte:
			System.out.println(ot);
			break;
		case Lte:
			System.out.println(ot);
			break;
		case Gt:
			System.out.println(ot);
			break;
		case Lt:
			System.out.println(ot);
			break;
		default:
			break;
		}
		
		
		
	}

}
