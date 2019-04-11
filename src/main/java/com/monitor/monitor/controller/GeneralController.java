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
import com.monitor.monitor.dao.AddressMapDb;
import com.monitor.monitor.es.Beat;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.ESOperate;
import com.monitor.monitor.es.type.FilesetType;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@CrossOrigin
@RestController
public class GeneralController {

	@Autowired
	private AddressMapDb amd;

	//--------------------------------------主机映射--------------------------------------
	
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
	 * @param id           id
	 * @param hostname     新主机名
	 * @param address      新ip地址
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

	
	
	
}
