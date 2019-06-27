package com.monitor.monitor.service.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtilTest {
	
	private static final Logger log = LoggerFactory.getLogger(TimeUtilTest.class);

	
//	@Test
//	public void test() {
//		String[] localToUTC = TimeUtil.getLocalToUTC();
//		for(String s : localToUTC) {
//			System.out.println(s);
//		}
//		String nowTime = MyTimeUtil.getNowTime();
//		System.out.println(nowTime);
//		String[] minuteRange = MyTimeUtil.getMinuteRange(nowTime);
//		for (String s : minuteRange) {
//			System.out.println(s);
//		}
//	}
//	2019-01-28T08:50:30Z
//	2019-01-28T08:51:30Z

	@Test
	public void test() {
		String[] arr = {"2019-3-6 10:00:27","2019-3-7 10:00:27"};
		boolean gteDay = MyTimeUtil.isGteDay(arr);
		System.out.println(gteDay);
		System.out.println(MyDataUtil.getIndexFormat("metric", "2019-3-6 10:00:27"));
	}
	
}