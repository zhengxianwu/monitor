package com.monitor.monitor.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.MetricSystemType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class MetricbeatController {

	private String index_home = "metricbeat-6.4.3"; // 索引名称（后期改成读取配置文件）
	@Autowired
	private Metircbeat metricbeat;
	@Autowired
	private ESClient esClient;
	// hostname节点名称是根据ip映射电脑名称

	/**
	 * 读取系统内存信息
	 * 
	 * @author wuzhe
	 * @param hostname 节点名称
	 * @return 返回系统内存使用的百分比 @
	 */
	@RequestMapping(value = "/metricbeat/getMemory", method = RequestMethod.GET)
	public String getMemory(@RequestParam(value = "hostname", required = false) String hostname) {
//		system.memory.actual.used.pct
//		类型：scaled_float
//		格式：百分比
//		实际使用内存的百分比。
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.memory);
		String pct = JSONObject.fromObject(metricNewData.get(0)).getJSONObject("system").getJSONObject("memory")
				.getJSONObject("actual").getJSONObject("used").getString("pct");
		pct = MyDataUtil.formatDouble(Double.parseDouble(pct) * 100);
		return pct;

	}

	/**
	 * 读取系统Cpu信息
	 * 
	 * @author wuzhe
	 * @param hostname hostname 节点名称
	 * @return 返回系统cpu使用百分比 @
	 */
	@RequestMapping(value = "/metricbeat/getCPU", method = RequestMethod.GET)
	public String getCPU(@RequestParam(value = "hostname", required = false) String hostname) {
//		system.cpu.total.pct
//		类型：scaled_float
//		格式：百分比
//		在Idle和IOWait以外的状态下花费的CPU时间百分比。
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> NewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.cpu);
		JSONObject json = JSONObject.fromObject(NewData.get(0));
		JSONObject system = json.getJSONObject("system");
		JSONObject cpu = system.getJSONObject("cpu");
		JSONObject system_cpu = cpu.getJSONObject("system");
		JSONObject system_user = cpu.getJSONObject("user");
		double cores = Double.valueOf(cpu.getString("cores"));
		double system_cpu_value = Double.valueOf(system_cpu.getString("pct"));
		double system_user_value = Double.valueOf(system_user.getString("pct"));
		double cpu_usage = (system_cpu_value + system_user_value) / cores;
		String value = MyDataUtil.formatDouble(cpu_usage * 100);
		return value;
	}

	/**
	 * 读取磁盘信息
	 * 
	 * @author wuzhe
	 * @param hostname
	 * @return 返回Json数组的磁盘信息 @
	 */
	@RequestMapping(value = "/metricbeat/getFilesystem", method = RequestMethod.GET)
	public String getFilesystem(@RequestParam(value = "hostname", required = false) String hostname) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> filesystemNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.filesystem);
		JSONArray fromObject = JSONArray.fromObject(filesystemNewData);
		return fromObject.toString();
	}

	/**
	 * 读取系统进程使用信息（包含cpu和memory）
	 * 
	 * @author wuzhe
	 * @param hostname
	 * @return 返回Json数组的系统进程使用信息（包含cpu和memory） @
	 */
	@RequestMapping("/metricbeat/getProcess")
	public String getProcess(@RequestParam(value = "hostname", required = false) String hostname) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.process);
		JSONArray fromObject = JSONArray.fromObject(metricNewData);
		return fromObject.toString();
	}

	/**
	 * 读取系统网络信息
	 * 
	 * @author wuzhe
	 * @param hostname
	 * @return 返回Json数组的系统网络信息
	 */
	@RequestMapping("/metricbeat/getNetwork")
	public String getNetwork(@RequestParam(value = "hostname", required = false) String hostname) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.network);
		JSONArray fromObject = JSONArray.fromObject(metricNewData);
		return fromObject.toString();
	}

	/**
	 * 主机上运行的进程的摘要度量标准。
	 * 
	 * @author wuzhe
	 * @param hostname
	 * @return 返回Json数组的主机上运行的进程的摘要度量标准。
	 */
	@RequestMapping("/metricbeat/getNetwork")
	public String getProcess_summary(@RequestParam(value = "hostname", required = false) String hostname) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.process_summary);
		JSONArray fromObject = JSONArray.fromObject(metricNewData);
		return fromObject.toString();
	}

	/**
	 * Metricbeat模块状态
	 * 
	 * @author wuzhe
	 * @param hostname
	 * @return Metricbeat模块状态。
	 */
	@RequestMapping("/metricbeat/getStatus")
	public String getStatus(@RequestParam(value = "hostname", required = false) String hostname) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.status);
		JSONArray fromObject = JSONArray.fromObject(metricNewData);
		return fromObject.toString();
	}

	/**
	 * system.fsstat 包含从所有已安装的文件系统聚合的文件系统指标。
	 * 
	 * @author wuzhe
	 * @param hostname
	 * @return system.fsstat 包含从所有已安装的文件系统聚合的文件系统指标。
	 */
	@RequestMapping("/metricbeat/getFsstat")
	public String getFsstat(@RequestParam(value = "hostname", required = false) String hostname) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date = new Date();
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname, MetricSystemType.fsstat);
		JSONArray fromObject = JSONArray.fromObject(metricNewData);
		return fromObject.toString();
	}

}
