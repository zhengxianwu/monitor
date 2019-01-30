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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.monitor.monitor.service.metricbeat.ESType;
import com.monitor.monitor.service.metricbeat.Metircbeat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class indexController {

	
	//@RestController返回是数据，不是页面
	//@Controller 才是返回页面
	@RequestMapping("/ES")
	public String ES() {

//		TransportClient client = null;
//		try {
//			client = Metircbeat.getClient(cluster_name, ip, port);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Date date = new Date();
//		String indexName = String.format("metricbeat-6.5.0-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
//		date = null;
//		List<String> metricNewData = Metircbeat.getMetricNewData(client, indexName, ESType.cpu);
//		JSONArray fromObject = JSONArray.fromObject(metricNewData);
//		if(client != null) client.close();
//		return fromObject.toString();
		return "";
	}
	

	

}
