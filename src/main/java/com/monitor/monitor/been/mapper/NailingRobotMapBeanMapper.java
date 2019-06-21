package com.monitor.monitor.been.mapper;

import java.util.List;

import com.monitor.monitor.been.HostnameMapBean;
import com.monitor.monitor.been.NailingRobotMapBean;

public interface NailingRobotMapBeanMapper {

	
	public List<NailingRobotMapBean> getAll();

	public boolean insertNailingRobotMapBean(NailingRobotMapBean n);
	
	public boolean updateNailingRobotMapBean(NailingRobotMapBean n);
	
	public boolean deleteNailingRobotMapBean(String rootId);
}
