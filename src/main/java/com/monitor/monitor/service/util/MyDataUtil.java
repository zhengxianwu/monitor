package com.monitor.monitor.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDataUtil {
	/**
	    * 四舍五入保留两位
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d) {
        return String.format("%.2f", d);
    }
	
	
	/**
	 * 获取指定日期得索引
	 * @param indexVersion  索引
	 * @param dateTime 日期如： 2019-3-3
	 * @return
	 */
	public static String getIndexFormat(String indexVersion,String dateTime) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = s.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String indexName = String.format(indexVersion + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(parse)); // 当天index
		return indexName;
	}
	
	
	
	
	
	
	
	
	/**
	 * 获取当天索引名字
	 * @param indexVersion  索引
	 * @return
	 */
	public static String getIndexFormat(String indexVersion) {
		Date date = new Date();
		String indexName = String.format(indexVersion + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		return indexName;
	}
	
	
	/**
	 * 获取全部索引（如：全天filebeat-*)
	 * @param indexVersion  索引
	 * @return
	 */
	public static String getIndexALL(String indexVersion) {
		String[] split = indexVersion.split("-");
		String indexName = split[0]+ "-*";
		return indexName;
	}


	

}
