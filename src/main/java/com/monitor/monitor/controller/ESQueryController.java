package com.monitor.monitor.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.monitor.es.Beat;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.ESOperate;
import com.monitor.monitor.es.type.FilesetType;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class ESQueryController {

	// hostname节点名称是根据ip映射电脑名称

	@Value("${es.metric_version}")
	private String metric_version;

	@Value("${es.fileset_version}")
	private String fileset_version;
	
	@Autowired
	private ESClient esClient;

	@Autowired
	private ESOperate esOperate;

	@RequestMapping(value = "/ESQuery/getNewData", method = RequestMethod.GET)
	public String getCPU(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "indexName", required = true) String indexName,
			@RequestParam(value = "beatName", required = true) String beatName,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
//			@RequestParam(value = "indexTime", required = false, defaultValue = "") String indexTime,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder) {
		TransportClient client = null;
		try {
			client = esClient.getClient();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(indexName == "fileset") {
			indexName = MyDataUtil.getIndexFormat(fileset_version);
		}else {
			indexName = MyDataUtil.getIndexFormat(metric_version);
		}
		System.out.println(indexName);
		
		SortOrder Order;
		if (sortOrder == "desc") {
			Order = SortOrder.DESC;
		} else {
			Order = SortOrder.ASC;
		}
		
		System.out.println("name : " + name);
		
		
		List<String> newData = esOperate.getNewData(client, indexName,hostname, beatName, module, "cpu", Order);
		JSONArray fromObject = JSONArray.fromObject(newData);
		return fromObject.toString();
	}

}
