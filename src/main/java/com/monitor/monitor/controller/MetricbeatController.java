package com.monitor.monitor.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.metrics.MeanMetric;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.monitor.monitor.been.TestDemo;
import com.monitor.monitor.service.metricbeat.ESType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class MetricbeatController {

//	private String index = "metricbeat-6.5.0";
	private String index_home = "metricbeat-6.4.3";
	@Autowired
	private Metircbeat metricbeat;
	
	//@RestController返回是数据，不是页面
	//@Controller 才是返回页面
	
	@RequestMapping("/metricbeat/getMemory")
	public String getMemory() throws UnknownHostException{
//		system.memory.actual.used.pct
//		类型：scaled_float
//		格式：百分比
//		实际使用内存的百分比。
		TransportClient client = metricbeat.getClient();
		Date date = new Date();
		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, ESType.memory);
		JSONObject json = JSONObject.fromObject(metricNewData.get(0));
		JSONObject system = json.getJSONObject("system");
		JSONObject memory = system.getJSONObject("memory");
		JSONObject actual = memory.getJSONObject("actual");
		JSONObject used = actual.getJSONObject("used");
		String pct = used.getString("pct");
		pct = MyDataUtil.formatDouble(Double.parseDouble(pct)*100);
		System.out.println(used.getString("pct"));
		return pct;
		
	}
	
	@RequestMapping("/metricbeat/getFilesystem")
	public String getFilesystem() throws UnknownHostException{
//		system.filesystem.used.pct
//		类型：scaled_float
//		格式：百分比
//		已用磁盘空间的百分比。
		TransportClient client = metricbeat.getClient();
		Date date = new Date();
		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> filesystemNewData = metricbeat.getMetricNewData(client, indexName, ESType.filesystem);
		JSONArray fromObject = JSONArray.fromObject(filesystemNewData);
		System.out.println(fromObject.toString());
		return fromObject.toString();
	}
	
	@RequestMapping("/metricbeat/getCPU")
	public String getCPU() throws UnknownHostException{
//		system.cpu.total.pct
//		类型：scaled_float
//		格式：百分比
//		在Idle和IOWait以外的状态下花费的CPU时间百分比。
		TransportClient client = metricbeat.getClient();
		Date date = new Date();
		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> NewData = metricbeat.getMetricNewData(client, indexName, ESType.cpu);
		JSONObject json = JSONObject.fromObject(NewData.get(0));
		JSONObject system = json.getJSONObject("system");
		JSONObject cpu = system.getJSONObject("cpu");
		JSONObject system_cpu = cpu.getJSONObject("system");
		JSONObject system_user = cpu.getJSONObject("user");
		double cores = Double.valueOf(cpu.getString("cores"));
		double system_cpu_value =  Double.valueOf(system_cpu.getString("pct"));
		double system_user_value = Double.valueOf(system_user.getString("pct"));
		double cpu_usage =  (system_cpu_value+system_user_value)/cores;
		String value = MyDataUtil.formatDouble(cpu_usage*100);
		return value;
		
	}
	

}
