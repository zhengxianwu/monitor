package com.monitor.monitor.been;

public class Schedule {
	public String Id;
	public String hostname;// 主机名字
	public String type; //监控类型
	public String threshold; // 阈值
	public String taskId; // 定时任务Id
	public String taskType;// 定时类型（秒，分钟，小时，天）
	public String taskValue;// 任务时间
	public String taskState;// 任务状态（运行，暂停）
	
	public Schedule(String id, String hostname, String type, String threshold, String taskId, String taskType,
			String taskValue, String taskState) {
		super();
		Id = id;
		this.hostname = hostname;
		this.type = type;
		this.threshold = threshold;
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskValue = taskValue;
		this.taskState = taskState;
	}
	
	public Schedule() {
		super();
	}

	@Override
	public String toString() {
		return "Schedule [Id=" + Id + ", hostname=" + hostname + ", type=" + type + ", threshold=" + threshold
				+ ", taskId=" + taskId + ", taskType=" + taskType + ", taskValue=" + taskValue + ", taskState="
				+ taskState + "]";
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskValue() {
		return taskValue;
	}
	public void setTaskValue(String taskValue) {
		this.taskValue = taskValue;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
}
