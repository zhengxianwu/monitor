package com.monitor.monitor.schedule;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.jmx.support.MetricType;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.es.Beat;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.ESOperate;
import com.monitor.monitor.es.module.MetricModule;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.es.type.OperationType;
import com.monitor.monitor.es.type.ScheduleTaskType;
import com.monitor.monitor.es.type.TaskMonitorType;
import com.monitor.monitor.es.type.TaskStateType;
import com.monitor.monitor.reminder.DingtalkRobotUtil;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;
import com.monitor.monitor.service.util.MyTimeUtil;
import com.monitor.monitor.service.util.TaskUtil;

import lombok.val;
import net.sf.json.JSONObject;

//@Lazy(false)
//@Component
//@EnableScheduling
public class SpringDynamicCronTask {
//implements SchedulingConfigurer

	private String token = "2dced5c5d02e3a8b2769e61236bba976ab3dce7eb6de7883b14c2617145f7006";

	private DingtalkRobotUtil dingtalk;
	private ESOperate esOperate;
	private String metric_version;
	private ESClient esClient;

	private String cron;
	private Schedule schedule;
	ScheduledFuture<?> scheduledFuture = null;
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	public SpringDynamicCronTask(Schedule schedule, ThreadPoolTaskScheduler threadPoolTaskScheduler,
			DingtalkRobotUtil dingtalk, ESOperate esOperate, String metric_version, ESClient esClient) {
		super();
		this.schedule = schedule;
		this.cron = TaskUtil.formatCron(schedule.getTaskValue(), schedule.getTaskType());
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;

		this.dingtalk = dingtalk;
		this.esOperate = esOperate;
		this.metric_version = metric_version;
		this.esClient = esClient;
		startTask();// 启动任务
	}

	public void startTask() {
		scheduledFuture = threadPoolTaskScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				// 业务逻辑
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				System.out.println(schedule.getHostname() + " : " + schedule.getTaskId() + " is running..." + ",时间为:"
//						+ simpleDateFormat.format(new Date()));

				TransportClient client = null;
				try {
					client = esClient.getClient();

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 1、处理数据
				String hostname = schedule.getHostname();// 主机名
				String type = schedule.getType();// 监控类型
				String taskValue = schedule.getTaskValue();
				String threshold = schedule.getThreshold();// 阈值
				String operationType = schedule.getOperationType();

				String[] taskRange = MyTimeUtil.getTaskRange(schedule.getTaskType(), taskValue);
				String startTime = taskRange[0];
				String endTime = taskRange[1];
				String indexName;

				List<String> metricNewData = null;

				// 操作符号
				OperationType ot = null;
				if (operationType.equals(OperationType.Gte.toString())) {
					ot = OperationType.Gte; // 大于等于
				} else if (operationType.equals(OperationType.Lte.toString())) {
					ot = OperationType.Lte; // 小于等于
				} else if (operationType.equals(OperationType.Gt.toString())) {
					ot = OperationType.Gt; // 大于
				} else if (operationType.equals(OperationType.Lt.toString())) {
					ot = OperationType.Lt; // 小于
				}

				// 2、判断时间是否超过临界值（按日判断），超过则用全索引
				if (MyTimeUtil.isGteDay(taskRange)) {
					indexName = MyDataUtil.getIndexALL(metric_version);
				} else {
					Date date = new Date();// 当天
					indexName = String.format(metric_version + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
					date = null;
				}

				// 3、查询(Metricbeat),判断是否超过设定(应按定时范围查询)(集合查询)
				String systemType = null;
				if (type.equals(TaskMonitorType.Cpu.toString())) {
					systemType = MetricSystemType.cpu.toString();
				} else if (type.equals(TaskMonitorType.Memory.toString())) {
					systemType = MetricSystemType.memory.toString();
				} else if (type.equals(TaskMonitorType.Filesystem.toString())) {
					systemType = MetricSystemType.filesystem.toString();
				}
				metricNewData = esOperate.rangeTime(client, indexName, hostname, startTime, endTime,
						Beat.metricset.toString(), MetricModule.system.toString(), systemType, SortOrder.DESC);

				// 4、再判断含有超过阈值的数据，再判断是否发送软件
				if (metricNewData != null) {
					if (metricNewData.size() > 0) {// 含有数据
						ArrayList<String> list = new ArrayList<>();
						// 判断是否有大于阈值的
						for (String str : metricNewData) {
							String value = null;
							if (type.equals(TaskMonitorType.Cpu.toString())) {
								value = getCpuValue(str);
							} else if (type.equals(TaskMonitorType.Memory.toString())) {

							} else if (type.equals(TaskMonitorType.Filesystem.toString())) {

							}

							// 判断值操作
							switch (ot) {
							case Gte:
								if (Double.parseDouble(value) >= Double.parseDouble(threshold)) {
									System.out.println("value" + value + ">= threshold" + threshold);
									list.add(value);
								}
								break;
							case Lte:
								if (Double.parseDouble(value) <= Double.parseDouble(threshold)) {
									System.out.println("value" + value + "<= threshold" + threshold);
									list.add(value);
								}
								break;
							case Gt:
								if (Double.parseDouble(value) > Double.parseDouble(threshold)) {
									System.out.println("value" + value + "> threshold" + threshold);
									list.add(value);
								}
								break;
							case Lt:
								if (Double.parseDouble(value) < Double.parseDouble(threshold)) {
									System.out.println("value" + value + "< threshold" + threshold);
									list.add(value);
								}
								break;
							}
						}

						String text = "hostname :" + hostname + "，监控类型：" + schedule.getType() + "，阈值 ：" + threshold
								+ ",超过阈值监控记录：" + list.size() + ",监控值分别为：";
						for (String s : list) {
							text += s + ",";
						}
						String messageText = dingtalk.messageText(text, null, false, token);
						System.out.println(messageText);
						
						
					} else {// 没有数据

					}
				}

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
		if (scheduledFuture != null) {
			System.err.println(schedule.getTaskId() + ": is stop");
			scheduledFuture.cancel(true);
		}
	}

	public Schedule getSchedule() {
		return this.schedule;
	}

	/**
	 * cpu单个值
	 * 
	 * @param str 需要过滤数据
	 * @return 返回单条纪录得cpu值
	 */
	public String getCpuValue(String str) {
		JSONObject json = JSONObject.fromObject(str);
		JSONObject system = json.getJSONObject("system");
		JSONObject cpu = system.getJSONObject("cpu");
		JSONObject system_cpu = cpu.getJSONObject("system");
		JSONObject system_user = cpu.getJSONObject("user");
		double cores = Double.valueOf(cpu.getString("cores"));
		double system_cpu_value = Double.valueOf(system_cpu.getString("pct"));
		double system_user_value = Double.valueOf(system_user.getString("pct"));
		double cpu_usage = (system_cpu_value + system_user_value) / cores;
		return MyDataUtil.formatDouble(cpu_usage * 100);
	}
}
