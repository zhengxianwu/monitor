package com.monitor.monitor.been;

public class Schedule {
	private String Id;
	private String hostname;
	private String type;
	private String threshold;
	private String taskId;
	private String taskType;
	private String taskValue;
	private String 	taskState;
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
