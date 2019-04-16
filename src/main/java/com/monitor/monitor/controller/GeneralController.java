package com.monitor.monitor.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.monitor.been.HostnameMap;
import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.dao.AddressMapDb;
import com.monitor.monitor.dao.ScheduleTaskDb;
import com.monitor.monitor.es.Beat;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.ESOperate;
import com.monitor.monitor.es.type.FilesetType;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.es.type.ScheduleTaskType;
import com.monitor.monitor.es.type.TaskMonitorType;
import com.monitor.monitor.es.type.TaskStateType;
import com.monitor.monitor.schedule.TaskManagement;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;
import com.monitor.monitor.service.util.MyMD5;
import com.monitor.monitor.service.util.TaskUtil;
import com.sun.javafx.image.IntPixelGetter;
import com.sun.javafx.scene.control.skin.TextAreaSkin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@CrossOrigin
@RestController
public class GeneralController {

	@Autowired
	private AddressMapDb amd;

	@Autowired
	private ScheduleTaskDb std;

	@Autowired
	private TaskManagement taskManage;

	// --------------------------------------主机映射--------------------------------------

	/**
	 * 获取所有映射
	 * 
	 * @author wuzhe
	 * @return 返回Json数组形式的所有映射数据
	 */
	@RequestMapping(value = "/hostmap/All", method = RequestMethod.GET)
	public String hostmap() {
		List<HostnameMap> all = amd.getAll();
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
	public String hostmap(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "address", required = true) String address) {
		boolean addMap = amd.addMap(hostname, address);
		return String.valueOf(addMap);
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
	public String hostmap(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "old_hostname", required = true) String old_hostname,
			@RequestParam(value = "old_address", required = true) String old_address

	) {
		boolean updateMap = amd.updateMap(hostname, address, Integer.parseInt(id), old_hostname, old_address);
		return String.valueOf(updateMap);
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
	public String hostmap(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "address", required = true) String address

	) {
		boolean deleteMap = amd.deleteMap(Integer.parseInt(id), hostname, address);
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
	public String task() {
		List<Schedule> all = std.getAll();
		return JSONArray.fromObject(all).toString();
	}

	/**
	 * 添加定时任务
	 * 
	 * @author wuzhe
	 * @param hostname  主机名称
	 * @param type      监控类型[Memory("运行内存"), Cpu("Cpu"),
	 *                  Filesystem("磁盘");](TaskMonitorType)
	 * @param threshold 阈值
	 * @param taskType  任务定时类型[ 传值(英文) ： Second("秒"), Minute("分钟"),
	 *                  Hour("小时"),Day("天"), H_M_S("小时_分钟_秒");(ScheduleTaskType) ]
	 * @param taskValue 任务时间值(必须正整数)【秒，分钟，天：直接填写数字：如10】，H_M_S : 1_15_3
	 *                  (1小时，15分钟，3秒定时)
	 * @param taskState 任务状态[ 传值(英文) :(Run("运行"), Stop("暂停");)TaskStateType ]
	 * @return true为添加成功，false添加失败，
	 */
	@RequestMapping(value = "/task/add", method = RequestMethod.POST)
	public String task(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "threshold", required = true) String threshold,
			@RequestParam(value = "taskType", required = true) String taskType,
			@RequestParam(value = "taskValue", required = true) String taskValue,
			@RequestParam(value = "taskState", required = true) String taskState) {

		String taskId = MyMD5.Md5(hostname + type, String.valueOf(new Date().getTime()));

		// 监控类型
		TaskMonitorType tmt = null;
		if (type.equals(TaskMonitorType.Cpu.toString())) {
			tmt = TaskMonitorType.Cpu;
		} else if (taskType.equals(TaskMonitorType.Memory.toString())) {
			tmt = TaskMonitorType.Memory;
		} else if (taskType.equals(TaskMonitorType.Filesystem.toString())) {
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

		boolean addMap = std.add(hostname, tmt.toString(), threshold, taskId, stt.toString(), taskValue,
				tst.toString());

		// 启动任务
		if (tst == TaskStateType.Run) {
			taskManage.addTask(std.getTaskId(taskId));
		}

		return String.valueOf(addMap);
	}

	/**
	 * 更新定时任务
	 * 
	 * @author wuzhe
	 * @param hostname  主机名称
	 * @param type      监控类型[Memory("运行内存"), Cpu("Cpu"),
	 *                  Filesystem("磁盘");](TaskMonitorType)
	 * @param threshold 阈值
	 * @param taskType  任务定时类型[ 传值(英文) ： Second("秒"), Minute("分钟"),
	 *                  Hour("小时"),Day("天"), H_M_S("小时_分钟_秒");(ScheduleTaskType) ]
	 * @param taskId    任务Id
	 * @param taskValue 任务时间值
	 * @param taskState 任务状态[ 传值(英文) :(Run("运行"), Stop("暂停");)TaskStateType ]
	 * @return true为添加成功，false添加失败，
	 */
	@RequestMapping(value = "/task/update", method = RequestMethod.POST)
	public String task(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "threshold", required = true) String threshold,
			@RequestParam(value = "taskType", required = true) String taskType,
			@RequestParam(value = "taskId", required = true) String taskId,
			@RequestParam(value = "taskValue", required = true) String taskValue,
			@RequestParam(value = "taskState", required = true) String taskState) {
		boolean updateMap = true;
		Schedule oldSchedule = std.getTaskId(taskId);

		// 监控类型
		TaskMonitorType tmt = null;
		if (type.equals(TaskMonitorType.Cpu.toString())) {
			tmt = TaskMonitorType.Cpu;
		} else if (taskType.equals(TaskMonitorType.Memory.toString())) {
			tmt = TaskMonitorType.Memory;
		} else if (taskType.equals(TaskMonitorType.Filesystem.toString())) {
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

		//// 新的与旧的都等于停止
		if (oldSchedule.getTaskState().equals(TaskStateType.Stop.toString())
				&& taskState.equals(TaskStateType.Stop.toString())) {
			updateMap = std.updateMap(hostname, tmt.toString(), threshold, stt.toString(), taskValue, tst.toString(),
					taskId);
		} else {
			// 任务判断
			// 更新
			updateMap = std.updateMap(hostname, tmt.toString(), threshold, stt.toString(), taskValue, tst.toString(),
					taskId);
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
	public String task(@RequestParam(value = "taskId", required = true) String taskId) {
		boolean deleteMap = std.deleteMap(taskId);
		taskManage.removeTask(taskId);
		return String.valueOf(deleteMap);
	}
}
