package com.monitor.monitor.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.been.Task;
import com.monitor.monitor.dao.ScheduleTaskDb;
import com.monitor.monitor.es.type.TaskStateType;

@Service
@Singleton
@EnableScheduling
public class TaskManagement {

	/**
	 * 多线程定时任务执行. 可以设置执行线程池数（默认一个线程） 1. 使用前必须得先调用initialize()进行初始化 2.
	 * schedule(Runnable task, Trigger trigger)
	 * 指定一个触发器执行定时任务。可以使用CronTrigger来指定Cron表达式，执行定时任务 3. shutDown()方法，执行完后可以关闭线程
	 */
	private ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

	//存储运行任务
	private List<SpringDynamicCronTask> taskList = new ArrayList<>();

	@Autowired
	private ScheduleTaskDb std;

	public void init() {
		this.threadPoolTaskScheduler.initialize(); // 初始化线程池
		List<Schedule> allRun = std.getAllRun();
		for (Schedule schedule : allRun) {
			taskList.add(new SpringDynamicCronTask(schedule, threadPoolTaskScheduler));
		}
	}

	public List<SpringDynamicCronTask> getTaskList() {
		return taskList;
	}

	public void stopTaskManagement() {
		if (null != threadPoolTaskScheduler) {
			// 关闭线程
			threadPoolTaskScheduler.shutdown();
		}
	}

}
