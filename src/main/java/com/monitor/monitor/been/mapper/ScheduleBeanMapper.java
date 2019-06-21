package com.monitor.monitor.been.mapper;

import java.util.List;

import com.monitor.monitor.been.ScheduleBean;

public interface ScheduleBeanMapper {

	/**
	 * 获取全部任务
	 */
	public List<ScheduleBean> getAll();

	/**
	 * 添加任务
	 */
	public boolean insertScheduleBean(ScheduleBean s);

	/**
	 * 更新任务(根据任务id更新)
	 */
	public boolean updateScheduleBean(ScheduleBean s);

	/**
	 * 删除任务
	 * @param taskId 任务Id
	 * @return
	 */
	public boolean deleteScheduleBean(String taskId);

	/**
	 * 获取全部运行任务
	 * 
	 * @return list
	 */
	public List<ScheduleBean> getAllRun();

	/**
	 * 获取指定任务
	 * 
	 * @param taskId 任务Id
	 * @return
	 */
	public ScheduleBean getTaskId(String taskId);
}
