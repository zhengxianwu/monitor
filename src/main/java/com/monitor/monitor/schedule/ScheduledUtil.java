package com.monitor.monitor.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

//@Component
public class ScheduledUtil implements SchedulingConfigurer {

	private static Logger log = LoggerFactory.getLogger(ScheduledUtil.class);

	private String cron = "0/1 * * * * ?";

	private String name = "测试";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.addTriggerTask(doTask(), getTrigger());
	}

	/**
	 * 业务执行方法
	 * 
	 * @return
	 */
	private Runnable doTask() {
		return new Runnable() {
			@Override
			public void run() {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 业务逻辑
//				log.info(name + ",时间为:" + simpleDateFormat.format(new Date()));
				System.out.println(name + ",时间为:" + simpleDateFormat.format(new Date()));
			}
		};
	}

	/**
	 * 业务触发器
	 * 
	 * @return
	 */
	private Trigger getTrigger() {
		return new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 触发器
				CronTrigger trigger = new CronTrigger(cron);
				return trigger.nextExecutionTime(triggerContext);
			}
		};
	}
}