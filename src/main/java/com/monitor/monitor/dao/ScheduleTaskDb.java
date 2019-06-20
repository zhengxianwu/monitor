package com.monitor.monitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.monitor.monitor.been.HostnameMap;
import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.schedule.TaskManagement;
import com.mysql.jdbc.Statement;

//定时任务

@Repository
@Singleton
public class ScheduleTaskDb {

	@Autowired
	private Databases db;

	private String table = "schedule";

	/**
	 * 获取全部任务
	 * 
	 * @return list
	 */
	public List<Schedule> getAll() {
		List<Schedule> list = new ArrayList<>();
		ResultSet rs;
		try {
			rs = db.select("select * from " + table);
			while (rs.next()) {
				list.add(new Schedule(rs.getString("Id"), rs.getString("hostname"), rs.getString("type"),
						rs.getString("threshold"), rs.getString("taskName"),rs.getString("taskId"), rs.getString("taskType"),
						rs.getString("taskValue"), rs.getString("taskState"), rs.getString("operationType"),
						rs.getString("reminderType"), rs.getString("reminderId"), rs.getString("customExpression")));
			}
			rs.close();
			db.close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 添加任务
	 * 
	 * @param hostname  主机名称
	 * @param type      监控类型(ScheduleTaskType)
	 * @param threshold 阈值
	 * @param taskName  任务名称
	 * @param taskId    定时任务Id
	 * @param taskType  定时类型（秒，分钟，小时，天）(TaskStateType)
	 * @param taskValue 任务时间值
	 * @param taskState (Run("运行"), Stop("暂停");)TaskStateType
	 * @return
	 */
	public boolean add(String hostname, String type, String threshold,String taskName,String taskId, String taskType, String taskValue,
			String taskState, String operationType, String reminderType, String reminderId, String customExpression) {
		String sql = String.format(
				"insert into "+ table +"(hostname,type,threshold,taskName,taskId,taskType,taskValue,taskState,operationType,reminderType,reminderId,customExpression) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
				hostname, type, threshold,taskName, taskId, taskType, taskValue, taskState, operationType, reminderType,
				reminderId, customExpression);
		return db.insert(sql);
	}

	/**
	 * 更新任务(根据任务id更新)
	 * 
	 * @param hostname  主机名称
	 * @param type      监控类型(TaskMonitorType)
	 * @param threshold 阈值
	 * @param taskName  任务名称
	 * @param taskId    定时任务Id
	 * @param taskType  定时类型（秒，分钟，小时，天）(ScheduleTaskType)
	 * @param taskValue 任务时间值
	 * @param taskState (Run("运行"), Stop("暂停");)TaskStateType
	 * @return
	 */
	public boolean updateMap(String hostname, String type, String threshold, String taskName,String taskType, String taskValue,
			String taskState, String operationType, String reminderType, String reminderId, String customExpression,
			String taskId) {
		String sql = String.format(
				"update schedule set  " + " taskName  = '%s'," + " hostname  = '%s'," + " type  = '%s'," + " threshold  = '%s',"
						+ " taskType  = '%s'," + " taskValue  = '%s'," + " taskState  = '%s',"
						+ " operationType  = '%s'," + " reminderType  = '%s'," + " reminderId  = '%s',"
						+ " customExpression  = '%s'" + " where   taskId  = '%s'",
						taskName,hostname, type, threshold, taskType, taskValue, taskState, operationType, reminderType, reminderId,
				customExpression, taskId);
		System.out.println(sql);
		return db.update(sql);
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId 任务Id
	 * @return
	 */
	public boolean deleteMap(String taskId) {
		String sql = String.format("delete from schedule where taskId = '%s'", taskId);
		return db.delete(sql);
	}

	/**
	 * 获取全部运行任务
	 * 
	 * @return list
	 */
	public List<Schedule> getAllRun() {
		List<Schedule> list = new ArrayList<>();
		ResultSet rs;
		try {
			rs = db.select("select * from " + table + " where taskState = 'Run'");
			while (rs.next()) {
				list.add(new Schedule(rs.getString("Id"), rs.getString("hostname"), rs.getString("type"),
						rs.getString("threshold"), rs.getString("taskId"), rs.getString("taskType"),
						rs.getString("taskValue"), rs.getString("taskState"), rs.getString("operationType"),
						rs.getString("reminderType"), rs.getString("reminderId"), rs.getString("customExpression")));
			}
			rs.close();
			db.close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取指定任务
	 * 
	 * @param taskId 任务Id
	 * @return
	 */
	public Schedule getTaskId(String taskId) {
		Schedule schedule = null;
		ResultSet rs;
		try {
			rs = db.select(String.format("select * from " + table + " where taskId = '%s'", taskId));
			while (rs.next()) {
				schedule = new Schedule(rs.getString("Id"), rs.getString("hostname"), rs.getString("type"),
						rs.getString("threshold"), rs.getString("taskId"), rs.getString("taskType"),
						rs.getString("taskValue"), rs.getString("taskState"), rs.getString("operationType"),
						rs.getString("reminderType"), rs.getString("reminderId"), rs.getString("customExpression"));
			}
			rs.close();
			db.close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return schedule;
	}

}
