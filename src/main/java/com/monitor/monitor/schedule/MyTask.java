package com.monitor.monitor.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {

//	@Scheduled(cron = "0/10 * * * * *")
//	public void work() {
//		System.out.println("this is schedule work!");
//	}
//	

	@Autowired
	private SpringDynamicCronTask scheduledUtil;
	
	
	@Scheduled(cron = "0/60 * * * * *")
	public void work2() {
		scheduledUtil.setCron("0/1 * * * * *");
	}
}
