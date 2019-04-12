package com.monitor.monitor.service.util;

import com.monitor.monitor.es.type.ScheduleTaskType;

public class TaskUtil {

	
	public static String formatCron(String time,String stt) {
		String result = null;
		if(stt.equals(ScheduleTaskType.Second.toString())) {
			result =  String.format("0/%s * * * * *", time);
		}
		return result;
	}
}
