package com.monitor.monitor.service.util;

import java.util.regex.Pattern;

import com.monitor.monitor.es.type.ScheduleTaskType;

public class TaskUtil {

	public static String formatCron(String time, String stt) {
		String result = null;
		if (stt.equals(ScheduleTaskType.Second.toString())) {
			// 每秒
			result = String.format("0/%s * * * * *", time);
		} else if (stt.equals(ScheduleTaskType.Minute.toString())) {
			result = String.format("0 0/%s * * * *", time);
		} else if (stt.equals(ScheduleTaskType.Hour.toString())) {
			result = String.format("0 0 0/%s * *  *", time);
		} else if (stt.equals(ScheduleTaskType.H_M_S.toString())) {
			String[] split = time.split("_");
			result = String.format("%s %s 0/%s * * *", split[2], split[1], split[0]);
		}

		return result;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
}
