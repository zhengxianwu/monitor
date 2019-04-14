package com.monitor.monitor.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.es.type.TaskStateType;
import com.monitor.monitor.service.util.TaskUtil;

//@Lazy(false)
//@Component
//@EnableScheduling

public class SpringDynamicCronTask {
//implements SchedulingConfigurer
	private String cron;
	private Schedule schedule;
	ScheduledFuture<?> scheduledFuture = null;
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	public SpringDynamicCronTask(Schedule schedule,ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		super();
		this.schedule = schedule;
		this.cron = TaskUtil.formatCron(schedule.getThreshold(),schedule.getTaskType());
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
		
		startTask();//启动任务
	}

	

	public void startTask() {
		scheduledFuture = threadPoolTaskScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				// 任务逻辑
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 业务逻辑
				System.out.println(schedule.getHostname() + " : " +schedule.getTaskId() + " is running..." + ",时间为:"
						+ simpleDateFormat.format(new Date()));
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 任务触发，可修改任务的执行周期
				CronTrigger trigger = new CronTrigger(cron);
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
	}

	public void stopTask() {
		if(scheduledFuture != null) {
			System.err.println(schedule.getTaskId() +": is stop");
			scheduledFuture.cancel(true);
		}
	}



	public Schedule getSchedule() {
		return this.schedule;
	}

	

}
