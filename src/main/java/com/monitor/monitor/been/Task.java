package com.monitor.monitor.been;

import com.monitor.monitor.schedule.SpringDynamicCronTask;

public class Task {
	private Schedule schedule;
	private SpringDynamicCronTask springDynamicCronTask;
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public Task(Schedule schedule, SpringDynamicCronTask springDynamicCronTask) {
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
	public Task() {
		super();
	}
	

	
}
