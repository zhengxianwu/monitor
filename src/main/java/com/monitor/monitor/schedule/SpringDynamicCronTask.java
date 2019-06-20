package com.monitor.monitor.schedule;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.monitor.monitor.been.NailingRobotMap;
import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.dao.NailingRobotMapDb;
import com.monitor.monitor.es.Beat;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.ESOperate;
import com.monitor.monitor.es.module.MetricModule;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.es.type.OperationType;
import com.monitor.monitor.es.type.ReminderType;
import com.monitor.monitor.es.type.TaskMonitorType;
import com.monitor.monitor.reminder.DingtalkRobotUtil;
import com.monitor.monitor.service.util.CustomTemplateUtil;
import com.monitor.monitor.service.util.MyDataUtil;
import com.monitor.monitor.service.util.MyTimeUtil;
import com.monitor.monitor.service.util.TaskUtil;

import net.sf.json.JSONObject;

//@Lazy(false)
//@Component
//@EnableScheduling
public class SpringDynamicCronTask {
//implements SchedulingConfigurer

	private DingtalkRobotUtil dingtalk;
	private ESOperate esOperate;
	private String metric_version;
	private ESClient esClient;

	private String cron;
	private Schedule schedule;
	ScheduledFuture<?> scheduledFuture = null;
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	private NailingRobotMapDb nailingRobotMapDb;

	public SpringDynamicCronTask(Schedule schedule, ThreadPoolTaskScheduler threadPoolTaskScheduler,
			DingtalkRobotUtil dingtalk, ESOperate esOperate, String metric_version, ESClient esClient,
			NailingRobotMapDb nailingRobotMapDb) {
		super();
		this.schedule = schedule;
		this.cron = TaskUtil.formatCron(schedule.getTaskValue(), schedule.getTaskType());  //定时表达式
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;

		this.dingtalk = dingtalk;
		this.esOperate = esOperate;
		this.metric_version = metric_version;
		this.esClient = esClient;
		this.nailingRobotMapDb = nailingRobotMapDb;
		startTask();// 启动任务
	}

	public void startTask() {
		scheduledFuture = threadPoolTaskScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				// 业务逻辑
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(schedule.getHostname() + " : " + schedule.getTaskId() + " is running..." + ",时间为:"
						+ simpleDateFormat.format(new Date()));

				TransportClient client = null;
				try {
					client = esClient.getClient();

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// --------------------------------------1、处理数据--------------------------------------
				String hostname = schedule.getHostname();// 主机名
				String type = schedule.getType();// 监控类型
				String taskValue = schedule.getTaskValue();
				String threshold = schedule.getThreshold();// 阈值
				String operationType = schedule.getOperationType();
				String reminderType = schedule.getReminderType();
				String reminderId = schedule.getReminderId();
				String customExpression = schedule.getCustomExpression();

				String[] taskRange = MyTimeUtil.getTaskRange(schedule.getTaskType(), taskValue); // 根据定时时间来获取时间段的数据
				String startTime = taskRange[0];
				String endTime = taskRange[1];
				String indexName;

				List<String> metricNewData = null;

				// 发送类型
				ReminderType rt = null;
				if (reminderType.equals(ReminderType.DingTalkRobot.toString())) {
					rt = ReminderType.DingTalkRobot; // 大于等于
				} else if (reminderType.equals(ReminderType.EMail.toString())) {
					rt = ReminderType.EMail; // 小于等于
				} else if (reminderType.equals(ReminderType.SMS.toString())) {
					rt = ReminderType.EMail; // 大于
				}

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

				// --------------------------------------2、判断时间是否超过临界值（按日判断），超过则用全索引--------------------------------------
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
							String value = "";
							if (type.equals(TaskMonitorType.Cpu.toString())) {
								value = getCpuValue(str);
							} else if (type.equals(TaskMonitorType.Memory.toString())) {
								value = getMemoryValue(str);
							} else if (type.equals(TaskMonitorType.Filesystem.toString())) {

							}

							double parsethreshold = Double.parseDouble(threshold);
							double value_temp = Double.parseDouble(value);
							// 判断值操作
							switch (ot) {
							case Gte:
								if (value_temp >= parsethreshold) {
									System.out.println("value" + value + ">= threshold" + threshold);
									list.add(value);
								}
								break;
							case Lte:
								if (value_temp <= parsethreshold) {
									System.out.println("value" + value + "<= threshold" + threshold);
									list.add(value);
								}
								break;
							case Gt:
								if (value_temp > parsethreshold) {
									System.out.println("value" + value + "> threshold" + threshold);
									list.add(value);
								}
								break;
							case Lt:
								if (value_temp < parsethreshold) {
									System.out.println("value" + value + "< threshold" + threshold);
									list.add(value);
								}
								break;
							}
						}
						
						//自定义表达式
						String expression = CustomTemplateUtil.Expression(customExpression, schedule, list);
						for (String s : list) {
							System.out.println(s);
						}
						
						
						//判断有过滤出来的数据
						if(list.size() > 0) {
							// 判断发送类型
							String messageText = null;
							switch (rt) {
							case DingTalkRobot:
								// 获取token,根据Id
								NailingRobotMap nailingRobotMap = nailingRobotMapDb.getNailingRobotMap(reminderId);
								messageText = dingtalk.messageText(expression, null, false, nailingRobotMap.getRootToken());
								System.out.println(schedule);
								break;
							case EMail:
								break;
							case SMS:
								break;
							}
							System.out.println(messageText);
						}
						

					} else {// metricNewData没有数据

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

	/**
	 * 内存值
	 * 
	 * @param str 需要过滤数据
	 * @return 返回单条纪录得内存
	 */
	public String getMemoryValue(String str) {
//		system.memory.actual.used.pct
		JSONObject json = JSONObject.fromObject(str);
		JSONObject system = json.getJSONObject("system");
		JSONObject memory = system.getJSONObject("memory");
		JSONObject actual = memory.getJSONObject("actual");
		JSONObject used = actual.getJSONObject("used");
		return MyDataUtil.formatDouble(Double.parseDouble(used.getString("pct"))* 100);
	}

}
