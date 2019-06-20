package com.monitor.monitor.service.util;

import java.util.List;

import com.monitor.monitor.been.ScheduleBean;

public class CustomTemplateUtil {

	private static String hostname = "\\{主机名称\\}";
	private static String type = "\\{监控类型\\}";
	private static String thresholdNumber = "\\{超过阈值数量\\}";
	private static String overcount = "\\{超过记录数据\\}";
	// 明细

	public static String Expression(String customExpression, ScheduleBean schedule, List<String> matchingList) {

		String str = ""; //超过记录数据
		for (String b : matchingList) {
			str += b + " ; ";
		}

		String replaceAll = customExpression.replaceAll(hostname, schedule.getHostname());
		replaceAll = replaceAll.replaceAll(type, schedule.getType());
		replaceAll = replaceAll.replaceAll(thresholdNumber, String.valueOf(matchingList.size()));
		replaceAll = replaceAll.replaceAll(overcount, str);

		return replaceAll;
	}
}

//主机名称 ： {主机名称}
//监控类型 ：{监控类型}
//超过阈值数量 :{超过阈值数量}
//超过记录数据 ：{超过记录数据}
