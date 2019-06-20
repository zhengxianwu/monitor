package com.monitor.monitor.been;

import com.monitor.monitor.es.type.OperationType;

public class Schedule {
	private String Id;
	private String hostname;// 主机名字
	private String type; // 监控类型TaskMonitorType
	private String threshold; // 阈值
	private String taskName;//任务名称
	private String taskId; // 定时任务Id
	private String taskType;// 定时类型（秒，分钟，小时，天）
	private String taskValue;// 任务时间
	private String taskState;// 任务状态（运行，暂停）TaskStateType
	private String operationType;// 操作指令OperationType
	
	private String reminderType;// 通知类型 ReminderType
	private String reminderId;// 通知Id,根据Id去查找通知配置属性
	private String customExpression;//自定义通知表达式

	
	/**
	 * 含有任务名称
	 * @param id
	 * @param hostname
	 * @param type
	 * @param threshold
	 * @param taskName
	 * @param taskId
	 * @param taskType
	 * @param taskValue
	 * @param taskState
	 * @param operationType
	 * @param reminderType
	 * @param reminderId
	 * @param customExpression
	 */
	public Schedule(String id, String hostname, String type, String threshold, String taskName, String taskId,
			String taskType, String taskValue, String taskState, String operationType, String reminderType,
			String reminderId, String customExpression) {
		super();
		Id = id;
		this.hostname = hostname;
		this.type = type;
		this.threshold = threshold;
		this.taskName = taskName;
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskValue = taskValue;
		this.taskState = taskState;
		this.operationType = operationType;
		this.reminderType = reminderType;
		this.reminderId = reminderId;
		this.customExpression = customExpression;
	}

	public Schedule(String id, String hostname, String type, String threshold, String taskId, String taskType,
			String taskValue, String taskState, String operationType) {
		super();
		Id = id;
		this.hostname = hostname;
		this.type = type;
		this.threshold = threshold;
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskValue = taskValue;
		this.taskState = taskState;
		this.operationType = operationType;
	}

	public Schedule(String id, String hostname, String type, String threshold, String taskId, String taskType,
			String taskValue, String taskState, String operationType, String reminderType, String reminderId) {
		super();
		Id = id;
		this.hostname = hostname;
		this.type = type;
		this.threshold = threshold;
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskValue = taskValue;
		this.taskState = taskState;
		this.operationType = operationType;
		this.reminderType = reminderType;
		this.reminderId = reminderId;
	}
	
	

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Schedule(String id, String hostname, String type, String threshold, String taskId, String taskType,
			String taskValue, String taskState, String operationType, String reminderType, String reminderId,
			String customExpression) {
		super();
		Id = id;
		this.hostname = hostname;
		this.type = type;
		this.threshold = threshold;
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskValue = taskValue;
		this.taskState = taskState;
		this.operationType = operationType;
		this.reminderType = reminderType;
		this.reminderId = reminderId;
		this.customExpression = customExpression;
	}

	public String getCustomExpression() {
		return customExpression;
	}

	public void setCustomExpression(String customExpression) {
		this.customExpression = customExpression;
	}

	public Schedule() {
		super();
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}

	public String getReminderId() {
		return reminderId;
	}

	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
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
