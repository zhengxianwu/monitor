package com.monitor.monitor.been.mapper;

import java.util.List;

import com.monitor.monitor.been.HostnameMapBean;

public interface HostnameMapBeanMapper {

	public List<HostnameMapBean> getAll();

	public boolean insertHostname(HostnameMapBean h);
	
	public boolean updateHostname(HostnameMapBean h);
	
	public boolean deleteHostname(String hostId);
	
}
