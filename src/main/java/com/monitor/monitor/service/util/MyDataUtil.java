package com.monitor.monitor.service.util;

public class MyDataUtil {
	/**
	    * 四舍五入保留两位
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d) {
        return String.format("%.2f", d);
    }
}
