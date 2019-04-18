package com.monitor.monitor.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.been.Task;
import com.monitor.monitor.dao.NailingRobotMapDb;
import com.monitor.monitor.dao.ScheduleTaskDb;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.ESOperate;
import com.monitor.monitor.es.type.TaskStateType;
import com.monitor.monitor.reminder.DingtalkRobotUtil;

@Service
@Singleton
@EnableScheduling
public class TaskManagement {

	@Autowired
	private DingtalkRobotUtil dingtalk; //钉钉对象

	@Autowired
	private ESOperate esOperate; //es操作对象

	@Value("${es.metric_version}")
	private String metric_version;

	@Autowired
	private ESClient esClient; //es客户端
	
	@Autowired
	private NailingRobotMapDb nailingRobotMapDb; //钉钉映射机器人对象

	@Autowired
	private ScheduleTaskDb std; //任务数据对象
	
	/**
	 * 多线程定时任务执行. 可以设置执行线程池数（默认一个线程） 1. 使用前必须得先调用initialize()进行初始化 2.
	 * schedule(Runnable task, Trigger trigger)
	 * 指定一个触发器执行定时任务。可以使用CronTrigger来指定Cron表达式，执行定时任务 3. shutDown()方法，执行完后可以关闭线程
	 */
	private ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

	// 存储运行任务
	private List<SpringDynamicCronTask> taskList = new ArrayList<>();

	

	/**
	 * 1、初始化任务管理 2、读取数据库运行状态数据 3、添加到任务列表
	 */
	public void init() {
		this.threadPoolTaskScheduler.initialize(); // 初始化线程池
		List<Schedule> allRun = std.getAllRun();
		// 启动任务
		for (Schedule schedule : allRun) {
			taskList.add(new SpringDynamicCronTask(schedule, this.threadPoolTaskScheduler, this.dingtalk,
					this.esOperate, this.metric_version, this.esClient,this.nailingRobotMapDb));
		}
	}

	/**
	 * 获取任务列表
	 * 
	 * @return
	 */
	public List<SpringDynamicCronTask> getTaskList() {
		return taskList;
	}

	/**
	 * 启动,添加任务
	 * 
	 * @param schedule 任务对象
	 * @return
	 */
	public boolean addTask(Schedule schedule) {
		return taskList.add(new SpringDynamicCronTask(schedule, this.threadPoolTaskScheduler, this.dingtalk,
				this.esOperate, this.metric_version, this.esClient,this.nailingRobotMapDb));
	}

	/**
	 * 暂停,删除任务
	 * 
	 * @param schedule
	 * @return
	 */
	public boolean removeTask(Schedule schedule) {
		// 1、找出任务
		List<SpringDynamicCronTask> filterObj = taskList.stream()
				.filter(a -> a.getSchedule().getTaskId().equals(schedule.getTaskId())).collect(Collectors.toList());
		if (filterObj.size() == 0)
			return true;
		SpringDynamicCronTask filteTask = filterObj.get(0);
		// 2、移除列表
		boolean remove = taskList.remove(filteTask);
		System.err.println(filteTask.getSchedule().getTaskId() + " -> 移除成功 ：" + filteTask);
		// 3、停止任务，销毁对象
		filteTask.stopTask();
		filteTask = null;
		return remove;
	}

	/**
	 * 暂停,删除任务(依据任务Id)
	 * 
	 * @param taskId 任务Id
	 * @return
	 */
	public boolean removeTask(String taskId) {
		// 1、找出任务
		List<SpringDynamicCronTask> filterObj = taskList.stream()
				.filter(a -> a.getSchedule().getTaskId().equals(taskId)).collect(Collectors.toList());
		if (filterObj.size() == 0)
			return true;
		SpringDynamicCronTask filteTask = filterObj.get(0);
		// 2、移除列表
		boolean remove = taskList.remove(filteTask);
		System.err.println(filteTask.getSchedule().getTaskId() + " -> 移除成功 ：" + filteTask);
		// 3、停止任务，销毁对象
		filteTask.stopTask();
		filteTask = null;
		return remove;
	}

	/**
	 * 停止线程池
	 */
	private void stopTaskManagement() {
		if (null != threadPoolTaskScheduler) {
			// 关闭线程
			threadPoolTaskScheduler.shutdown();
		}
	}

}
