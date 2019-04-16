package com.monitor.monitor.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.monitor.monitor.es.type.ScheduleTaskType;

public class MyTimeUtil {

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String format = s.format(date);
		date = null;
		return format;
	}

	/**
	 * 获取UTC时间，比本地时间差8小时，迎合ES得时间,数组0是开始时间，数组1是结束时间
	 * 
	 * @param localTime
	 * @return o是startTime,1是endTime
	 */
	public static String[] getLocalToUTC() {
		String[] arr = new String[2];
		Date localDate = new Date();
		long localTimeInMillis = localDate.getTime();
		/** long时间转换成Calendar */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(localTimeInMillis);
		/** 取得时间偏移量 */
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		/** 取得夏令时差 */
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		/** 从本地时间里扣除这些差量，即可以取得UTC时间 */
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		/** 取得的时间就是UTC标准时间 */
		Date utcDate = new Date(calendar.getTimeInMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String end = dateFormat.format(utcDate);// 将本地日期格式化为UTC格式的 日期字符串
		arr[1] = end;

		// 当前时间减一分钟处理
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(utcDate);
		calendar2.add(Calendar.MINUTE, -1);// 当前时间前去一个月，即一个月前的时间
		String start = dateFormat.format(calendar2.getTime());
		arr[0] = start;
		return arr;
	}

	/**
	 * local转Utc
	 * 
	 * @param localTime
	 * @return Utc
	 */
	public static String getLocalToUTC(String localtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date localDate = null;
		try {
			localDate = sdf.parse(localtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long localTimeInMillis = localDate.getTime();
		/** long时间转换成Calendar */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(localTimeInMillis);
		/** 取得时间偏移量 */
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		/** 取得夏令时差 */
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		/** 从本地时间里扣除这些差量，即可以取得UTC时间 */
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		/** 取得的时间就是UTC标准时间 */
		Date utcDate = new Date(calendar.getTimeInMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String end = dateFormat.format(utcDate);// 将本地日期格式化为UTC格式的 日期字符串
		return end;
	}

	/**
	 * utc转local
	 * 
	 * @param utcTime
	 * @return
	 */
	public static String UtctoLoaclTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date UtcDate = null;
		try {
			UtcDate = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UtcDate.toLocaleString();
	}

	/**
	 * 返回分钟范围数，如12：30 ，返回12:30:00-12:30:59
	 * 
	 * @param time 传入时间：2019-04-16 14:33
	 * @return 0是开头，1是结尾；返回是utc时间
	 */
	public static String[] getMinuteRange(String time) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date localDate = null;
		try {
			localDate = s.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] arr = new String[2];
		long localTimeInMillis = localDate.getTime();
		/** long时间转换成Calendar */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(localTimeInMillis);
		/** 取得时间偏移量 */
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		/** 取得夏令时差 */
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		/** 从本地时间里扣除这些差量，即可以取得UTC时间 */
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		/** 取得的时间就是UTC标准时间 */
		Date utcDate = new Date(calendar.getTimeInMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String end = dateFormat.format(utcDate);// 将本地日期格式化为UTC格式的 日期字符串
		arr[0] = end;

		// 当前时间减一分钟处理
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(utcDate);
		calendar2.add(Calendar.SECOND, +59);// 当前时间前去一个月，即一个月前的时间
		String start = dateFormat.format(calendar2.getTime());
		arr[1] = start;
		return arr;
	}

	/**
	 * 获取UTC时间，比本地时间差8小时，迎合ES得时间,数组0是开始时间，数组1是结束时间(减去15分钟）
	 * 
	 * @param localTime
	 * @return o是startTime,1是endTime
	 */
	public static String[] getLocalToUTCSub15() {
		String[] arr = new String[2];
		Date localDate = new Date();
		long localTimeInMillis = localDate.getTime();
		/** long时间转换成Calendar */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(localTimeInMillis);
		/** 取得时间偏移量 */
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		/** 取得夏令时差 */
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		/** 从本地时间里扣除这些差量，即可以取得UTC时间 */
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		/** 取得的时间就是UTC标准时间 */
		Date utcDate = new Date(calendar.getTimeInMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String end = dateFormat.format(utcDate);// 将本地日期格式化为UTC格式的 日期字符串
		arr[1] = end;

		// 当前时间减一分钟处理
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(utcDate);
		calendar2.add(Calendar.MINUTE, -15);// 当前时间前去一个月，即一个月前的时间
		String start = dateFormat.format(calendar2.getTime());
		arr[0] = start;
		return arr;
	}

	/**
	 * 返回分钟范围数，如12：30 ，返回12:30:00-12:30:59
	 * 
	 * @param time 传入时间：12：30
	 * @return 0是开头，1是结尾；返回是utc时间
	 */
	public static String[] getTaskRange(String taskType, String taskValue) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] arr = new String[2];
		// 1、获取当前时间
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 2、时间过滤
		// end
		arr[1] = s.format(calendar.getTime());

		if (taskType.equals(ScheduleTaskType.Second.toString())) {
			calendar.add(Calendar.SECOND, -Integer.parseInt(taskValue));
		} else if (taskType.equals(ScheduleTaskType.Minute.toString())) {
			calendar.add(Calendar.MINUTE, -Integer.parseInt(taskValue));
		} else if (taskType.equals(ScheduleTaskType.Hour.toString())) {
			calendar.add(Calendar.HOUR, -Integer.parseInt(taskValue));
		} else if (taskType.equals(ScheduleTaskType.H_M_S.toString())) {
			String[] split = taskValue.split("_");
			calendar.add(Calendar.SECOND, -Integer.parseInt(split[2]));
			calendar.add(Calendar.MINUTE, -Integer.parseInt(split[1]));
			calendar.add(Calendar.HOUR, -Integer.parseInt(split[0]));
		}
		// start
		arr[0] = s.format(calendar.getTime());
		s = null;
		calendar = null;
		return arr;
	}

	/**
	 * 判断是否超过临界值，以天数为界限，算end-start的天数，大于0则用全索引
	 * 
	 * @param arr
	 * @return true 为 结束时间（日）大于开始时间（日)，false则反之,如：end 4-16>satrt 4-15
	 */
	public static boolean isGteDay(String[] arr) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = null;
		Date parse2 = null;
		try {
			parse = s.parse(arr[0]);
			parse2 = s.parse(arr[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar satrt = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		satrt.setTime(parse);
		end.setTime(parse2);
		int result = end.get(Calendar.DAY_OF_MONTH) - satrt.get(Calendar.DAY_OF_MONTH);
		System.out.println(end.get(Calendar.DAY_OF_MONTH) + ":" + satrt.get(Calendar.DAY_OF_MONTH));
		// 判断开始天数比结束天数小，则用全部索引
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

}
