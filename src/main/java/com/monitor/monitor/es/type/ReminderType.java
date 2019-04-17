package com.monitor.monitor.es.type;

public enum ReminderType {
	DingTalkRobot("钉钉机器人"), EMail("邮件通知"),SMS("手机短信");

	private final String explain;

	private ReminderType(String explain) {
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}
}
