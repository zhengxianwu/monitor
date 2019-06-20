package com.monitor.monitor.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.monitor.been.HostnameMapBean;
import com.monitor.monitor.been.NailingRobotMapBean;
import com.monitor.monitor.been.ScheduleBean;
import com.monitor.monitor.been.mapper.HostnameMapBeanMapper;
import com.monitor.monitor.been.mapper.NailingRobotMapBeanMapper;
import com.monitor.monitor.been.mapper.ScheduleBeanMapper;
import com.monitor.monitor.dao.AddressMapDb;
import com.monitor.monitor.dao.DbTools;
import com.monitor.monitor.dao.NailingRobotMapDb;
import com.monitor.monitor.dao.ScheduleTaskDb;
import com.monitor.monitor.es.type.OperationType;
import com.monitor.monitor.es.type.ReminderType;
import com.monitor.monitor.es.type.ScheduleTaskType;
import com.monitor.monitor.es.type.TaskMonitorType;
import com.monitor.monitor.es.type.TaskStateType;
import com.monitor.monitor.schedule.TaskManagement;
import com.monitor.monitor.service.util.MyMD5;
import com.monitor.monitor.service.util.TaskUtil;

import net.sf.json.JSONArray;

@CrossOrigin
@RestController
public class GeneralController {

	@Autowired
	private ScheduleTaskDb std;

	@Autowired
	private NailingRobotMapDb nrm;

	@Autowired
	private TaskManagement taskManage; // 定时任务

	@Autowired
	private DbTools dbTool; // MyBatis对象

	// --------------------------------------主机映射--------------------------------------
	/**
	 * 获取所有映射
	 * 
	 * @author wuzhe
	 * @return 返回Json数组形式的所有映射数据
	 */
	@RequestMapping(value = "/hostmap/All", method = RequestMethod.GET)
	public String getHostmap() {
		List<HostnameMapBean> all = dbTool.getSqlSeesion().getMapper(HostnameMapBeanMapper.class).getAll();
		return JSONArray.fromObject(all).toString();
	}

	/**
	 * 添加主机-ip映射
	 * 
	 * @author wuzhe
	 * @param hostname 主机名字
	 * @param address  主机Ip地址
	 * @return true为添加成功，false添加失败，失败可能是（Hostname或address已经存在）
	 */
	@RequestMapping(value = "/hostmap/add", method = RequestMethod.POST)
	public String addHostmap(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "address", required = true) String address) {

		String hostId = MyMD5.Md5(address);
		boolean insertHostname = dbTool.getSqlSeesion().getMapper(HostnameMapBeanMapper.class)
				.insertHostname(new HostnameMapBean(hostname, address, remark, hostId));
		return String.valueOf(insertHostname);
	}

	/**
	 * 更新主机-ip映射
	 * 
	 * @author wuzhe
	 * @param hostname     新主机名
	 * @param address      新ip地址
	 * @param id           id
	 * @param old_hostname 旧主机名
	 * @param old_address  旧ip地址
	 * @return true为更新成功，false更新失败，失败可能是参数有误
	 */
	@RequestMapping(value = "/hostmap/update", method = RequestMethod.POST)
	public String updateHostmap(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "remark", required = true) String remark,
			@RequestParam(value = "hostId", required = true) String hostId

	) {
		boolean updateHostname = dbTool.getSqlSeesion().getMapper(HostnameMapBeanMapper.class)
				.updateHostname(new HostnameMapBean(hostname, address, remark, hostId));
		return String.valueOf(updateHostname);
	}

	/**
	 * 删除主机-ip映射
	 * 
	 * @author wuzhe
	 * @param id       id
	 * @param hostname 新主机名
	 * @param address  新ip地址
	 * @return true为删除成功，false删除失败，失败可能是参数有误
	 */
	@RequestMapping(value = "/hostmap/delete", method = RequestMethod.POST)
	public String DelHostmap(@RequestParam(value = "hostId", required = true) String hostId) {
		boolean deleteMap = dbTool.getSqlSeesion().getMapper(HostnameMapBeanMapper.class).deleteHostname(hostId);
		return String.valueOf(deleteMap);
	}

	// --------------------------------------定时任务--------------------------------------
	/**
	 * 获取定时任务
	 * 
	 * @author wuzhe
	 * @return 返回Json数组形式的所有映射数据
	 */
	@RequestMapping(value = "/task/All", method = RequestMethod.GET)
	public String GetTask() {
		List<ScheduleBean> all = dbTool.getSqlSeesion().getMapper(ScheduleBeanMapper.class).getAll();
//		List<ScheduleBean> all = std.getAll();
		return JSONArray.fromObject(all).toString();
	}

	/**
	 * 添加定时任务
	 * 
	 * @author wuzhe
	 * @param hostname         主机名称
	 * @param type             监控类型[Memory("运行内存"), Cpu("Cpu"),
	 *                         Filesystem("磁盘");](TaskMonitorType)
	 * @param threshold        阈值
	 * @param taskName         任务名称
	 * @param taskType         任务定时类型[ 传值(英文) ： Second("秒"), Minute("分钟"),
	 *                         Hour("小时"),Day("天"),
	 *                         H_M_S("小时_分钟_秒");(ScheduleTaskType) ]
	 * @param taskValue        任务时间值(必须正整数)【秒，分钟，天：直接填写数字：如10】，H_M_S : 1_15_3
	 *                         (1小时，15分钟，3秒定时)
	 * @param taskState        任务状态[ 传值(英文) :(Run("运行"), Stop("暂停");)TaskStateType ]
	 * @param operationType    数值比较指令，传参{ Gte(大于等于) , Lte(小于等于) , Gt(大于) , Lt(小于)}
	 * @param reminderType     通知类型 ReminderType -> DingTalkRobot("钉钉机器人"),
	 *                         EMail("邮件通知"),SMS("手机短信");
	 * @param reminderId       通知Id ； 通过接口去获取
	 * @param customExpression 自定义表达式，可以为空，空的话就是默认模板发送
	 * @return true为添加成功，false添加失败，
	 */
	@RequestMapping(value = "/task/add", method = RequestMethod.POST)
	public String AddTask(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "threshold", required = true) String threshold,
			@RequestParam(value = "taskName", required = true) String taskName,
			@RequestParam(value = "taskType", required = true) String taskType,
			@RequestParam(value = "taskValue", required = true) String taskValue,
			@RequestParam(value = "taskState", required = true) String taskState,
			@RequestParam(value = "operationType", required = true) String operationType,
			@RequestParam(value = "reminderType", required = true) String reminderType,
			@RequestParam(value = "reminderId", required = true) String reminderId,
			@RequestParam(value = "customExpression", required = false, defaultValue = "") String customExpression) {

		String taskId = MyMD5.Md5(hostname + type, String.valueOf(new Date().getTime()));

		// 监控类型
		TaskMonitorType tmt = null;
		if (type.equals(TaskMonitorType.Cpu.toString())) {
			tmt = TaskMonitorType.Cpu;
		} else if (type.equals(TaskMonitorType.Memory.toString())) {
			tmt = TaskMonitorType.Memory;
		} else if (type.equals(TaskMonitorType.Filesystem.toString())) {
			tmt = TaskMonitorType.Filesystem;
		}

		// 定时类型
		ScheduleTaskType stt = null;
		if (taskType.equals(ScheduleTaskType.Second.toString())) {
			stt = ScheduleTaskType.Second;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.Minute.toString())) {
			stt = ScheduleTaskType.Minute;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.Day.toString())) {
			stt = ScheduleTaskType.Day;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.H_M_S.toString())) {
			stt = ScheduleTaskType.H_M_S;
			for (String s : taskValue.split("_")) {
				if (!TaskUtil.isInteger(s))
					return String.valueOf(false);
			}
		}

		// 运行状态
		TaskStateType tst = null;
		if (taskState.equals(TaskStateType.Run.toString())) {
			tst = TaskStateType.Run;
		} else if (taskState.equals(TaskStateType.Stop.toString())) {
			tst = TaskStateType.Stop;
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

		// 通知类型
		ReminderType rt = null;
		if (reminderType.equals(ReminderType.DingTalkRobot.toString())) {
			rt = ReminderType.DingTalkRobot; // 大于等于
		} else if (reminderType.equals(ReminderType.EMail.toString())) {
			rt = ReminderType.EMail; // 大于等于
		} else if (reminderType.equals(ReminderType.SMS.toString())) {
			rt = ReminderType.SMS; // 大于等于
		}

		ScheduleBean scheduleBean = new ScheduleBean(hostname, tmt.toString(), threshold, taskName, taskId,
				stt.toString(), taskValue, tst.toString(), ot.toString(), rt.toString(), reminderId, customExpression);
		boolean insertScheduleBean = dbTool.getSqlSeesion().getMapper(ScheduleBeanMapper.class).insertScheduleBean(scheduleBean);
		
		
//		boolean addMap = std.add(hostname, tmt.toString(), threshold, taskName, taskId, stt.toString(), taskValue,
//				tst.toString(), ot.toString(), rt.toString(), reminderId, customExpression);

		// 启动任务
		if (tst == TaskStateType.Run) {
			taskManage.addTask(std.getTaskId(taskId));
		}

		return String.valueOf(insertScheduleBean);
	}

	/**
	 * 更新定时任务
	 * 
	 * @author wuzhe
	 * @param hostname         主机名称
	 * @param type             监控类型TaskMonitorType : [Memory("运行内存"), Cpu("Cpu"),
	 *                         Filesystem("磁盘");](TaskMonitorType)
	 * @param threshold        阈值
	 * @param taskName         任务名称
	 * @param taskType         任务定时类型[ 传值(英文) ： Second("秒"), Minute("分钟"),
	 *                         Hour("小时"),Day("天"),
	 *                         H_M_S("小时_分钟_秒");(ScheduleTaskType) ]
	 * @param taskId           任务Id
	 * @param taskValue        任务时间值
	 * @param taskState        任务状态[ 传值(英文) :(Run("运行"), Stop("暂停");)TaskStateType ]
	 * @param operationType    数值比较指令，传参{ Gte(大于等于) , Lte(小于等于) , Gt(大于) , Lt(小于)}
	 * @param reminderType     通知类型 ReminderType -> DingTalkRobot("钉钉机器人"),
	 *                         EMail("邮件通知"),SMS("手机短信");
	 * @param reminderId       通知Id ； 通过接口去获取
	 * @param customExpression 自定义表达式，可以为空，空的话就是默认模板发送
	 * @return true为添加成功，false添加失败，
	 */
	@RequestMapping(value = "/task/update", method = RequestMethod.POST)
	public String updateTask(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "threshold", required = true) String threshold,
			@RequestParam(value = "taskName", required = true) String taskName,
			@RequestParam(value = "taskType", required = true) String taskType,
			@RequestParam(value = "taskId", required = true) String taskId,
			@RequestParam(value = "taskValue", required = true) String taskValue,
			@RequestParam(value = "taskState", required = true) String taskState,
			@RequestParam(value = "operationType", required = true) String operationType,
			@RequestParam(value = "reminderType", required = true) String reminderType,
			@RequestParam(value = "reminderId", required = true) String reminderId,
			@RequestParam(value = "customExpression", required = false, defaultValue = "") String customExpression) {
		boolean updateMap = true;
		ScheduleBean oldSchedule = std.getTaskId(taskId);

		// 监控类型
		TaskMonitorType tmt = null;
		if (type.equals(TaskMonitorType.Cpu.toString())) {
			tmt = TaskMonitorType.Cpu;
		} else if (type.equals(TaskMonitorType.Memory.toString())) {
			tmt = TaskMonitorType.Memory;
		} else if (type.equals(TaskMonitorType.Filesystem.toString())) {
			tmt = TaskMonitorType.Filesystem;
		}

		// 定时类型
		ScheduleTaskType stt = null;
		if (taskType.equals(ScheduleTaskType.Second.toString())) {
			stt = ScheduleTaskType.Second;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.Minute.toString())) {
			stt = ScheduleTaskType.Minute;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.Hour.toString())) {
			stt = ScheduleTaskType.Hour;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.Day.toString())) {
			stt = ScheduleTaskType.Day;
			if (!TaskUtil.isInteger(taskValue))
				return String.valueOf(false);
		} else if (taskType.equals(ScheduleTaskType.H_M_S.toString())) {
			stt = ScheduleTaskType.H_M_S;
			for (String s : taskValue.split("_")) {
				if (!TaskUtil.isInteger(s))
					return String.valueOf(false);
			}
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

		// 运行状态
		TaskStateType tst = null;
		if (taskState.equals(TaskStateType.Run.toString())) {
			tst = TaskStateType.Run;
		} else if (taskState.equals(TaskStateType.Stop.toString())) {
			tst = TaskStateType.Stop;
		}

		// 通知类型
		ReminderType rt = null;
		if (reminderType.equals(ReminderType.DingTalkRobot.toString())) {
			rt = ReminderType.DingTalkRobot; //
		} else if (reminderType.equals(ReminderType.EMail.toString())) {
			rt = ReminderType.EMail; //
		} else if (reminderType.equals(ReminderType.SMS.toString())) {
			rt = ReminderType.SMS; //
		}

		//// 新的与旧的都等于停止
		if (oldSchedule.getTaskState().equals(TaskStateType.Stop.toString())
				&& taskState.equals(TaskStateType.Stop.toString())) {
			
			ScheduleBean scheduleBean = new ScheduleBean(hostname, tmt.toString(), threshold, taskName, taskId,
					stt.toString(), taskValue, tst.toString(), ot.toString(), rt.toString(), reminderId, customExpression);
			updateMap = dbTool.getSqlSeesion().getMapper(ScheduleBeanMapper.class).updateScheduleBean(scheduleBean);
			
//			updateMap = std.updateMap(hostname, tmt.toString(), threshold, taskName, stt.toString(), taskValue,
//					tst.toString(), ot.toString(), rt.toString(), reminderId, customExpression, taskId);

		} else {
			// 任务判断
			// 更新
			ScheduleBean scheduleBean = new ScheduleBean(hostname, tmt.toString(), threshold, taskName, taskId,
					stt.toString(), taskValue, tst.toString(), ot.toString(), rt.toString(), reminderId, customExpression);
			updateMap = dbTool.getSqlSeesion().getMapper(ScheduleBeanMapper.class).updateScheduleBean(scheduleBean);
			
			
			if (updateMap) { // 更新成功执行
				// 启动 -> 停止
				if (oldSchedule.getTaskState().equals(TaskStateType.Run.toString())
						&& taskState.equals(TaskStateType.Stop.toString())) {
					taskManage.removeTask(taskId);
				}
				// 停止 -> 启动
				if (oldSchedule.getTaskState().equals(TaskStateType.Stop.toString())
						&& taskState.equals(TaskStateType.Run.toString())) {
					taskManage.removeTask(taskId);
					taskManage.addTask(std.getTaskId(taskId));
				}

				// Run -> 启动
				if (oldSchedule.getTaskState().equals(TaskStateType.Run.toString())
						&& taskState.equals(TaskStateType.Run.toString())) {
					taskManage.removeTask(taskId);
					taskManage.addTask(std.getTaskId(taskId));
				}

			}
		}

		return String.valueOf(updateMap);
	}

	/**
	 * 删除定时任务
	 * 
	 * @author wuzhe
	 * @param taskId 任务Id
	 * @return true为删除成功，false删除失败，
	 */
	@RequestMapping(value = "/task/delete", method = RequestMethod.POST)
	public String DelTask(@RequestParam(value = "taskId", required = true) String taskId) {
		boolean deleteMap = dbTool.getSqlSeesion().getMapper(ScheduleBeanMapper.class).deleteScheduleBean(taskId);
		
//		boolean deleteMap = std.deleteMap(taskId);
		taskManage.removeTask(taskId);
		
		return String.valueOf(deleteMap);
	}

	// --------------------------------------钉钉映射--------------------------------------

	/**
	 * 获取所有钉钉机器人映射
	 * 
	 * @author wuzhe
	 * @return 返回Json数组形式的所有映射数据
	 */
	@RequestMapping(value = "/DingTalk/All", method = RequestMethod.GET)
	public String getTalk() {
		List<NailingRobotMapBean> all = dbTool.getSqlSeesion().getMapper(NailingRobotMapBeanMapper.class).getAll();
		return JSONArray.fromObject(all).toString();
	}

	/**
	 * 添加钉钉机器人映射
	 * 
	 * @param rootName
	 * @param rootToken
	 * @return true为添加成功，false添加失败
	 */
	@RequestMapping(value = "/DingTalk/add", method = RequestMethod.POST)
	public String AddTalk(@RequestParam(value = "rootName", required = true) String rootName,
			@RequestParam(value = "rootToken", required = true) String rootToken) {
		String rootId = MyMD5.Md5(rootToken);
		boolean insertNailingRobotMapBean = dbTool.getSqlSeesion().getMapper(NailingRobotMapBeanMapper.class)
				.insertNailingRobotMapBean(new NailingRobotMapBean(rootId, rootName, rootToken));
		return String.valueOf(insertNailingRobotMapBean);
	}

	/**
	 * 添加钉钉机器人映射
	 * 
	 * @param rootName
	 * @param rootToken
	 * @return true为添加成功，false添加失败
	 */
	@RequestMapping(value = "/DingTalk/update", method = RequestMethod.POST)
	public String updateTalk(@RequestParam(value = "rootId", required = true) String rootId,
			@RequestParam(value = "rootName", required = true) String rootName,
			@RequestParam(value = "rootToken", required = true) String rootToken) {
		boolean updateNailingRobotMapBean = dbTool.getSqlSeesion().getMapper(NailingRobotMapBeanMapper.class)
				.updateNailingRobotMapBean(new NailingRobotMapBean(rootName, rootToken, rootId));
		return String.valueOf(updateNailingRobotMapBean);
	}

	/**
	 * 删除钉钉机器人映射
	 * 
	 * @param rootId 机器人id
	 * @return true为添加成功，false添加失败
	 */
	@RequestMapping(value = "/DingTalk/delete", method = RequestMethod.POST)
	public String DelTalk(@RequestParam(value = "rootId", required = true) String rootId) {
		boolean deleteNailingRobotMapBean = dbTool.getSqlSeesion().getMapper(NailingRobotMapBeanMapper.class)
				.deleteNailingRobotMapBean(rootId);
		return String.valueOf(deleteNailingRobotMapBean);
	}

}
