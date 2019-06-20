package com.monitor.monitor.been;

import com.monitor.monitor.schedule.SpringDynamicCronTask;

public class TaskBean {
	private ScheduleBean schedule;
	private SpringDynamicCronTask springDynamicCronTask;

	public ScheduleBean getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleBean schedule) {
		this.schedule = schedule;
	}

	public TaskBean(ScheduleBean schedule, SpringDynamicCronTask springDynamicCronTask) {
		super();
		this.schedule = schedule;
		this.springDynamicCronTask = springDynamicCronTask;
	}

	public SpringDynamicCronTask getSpringDynamicCronTask() {
		return springDynamicCronTask;
	}

	public void setSpringDynamicCronTask(SpringDynamicCronTask springDynamicCronTask) {
		this.springDynamicCronTask = springDynamicCronTask;
	}

	public TaskBean() {
		super();
	}

}
