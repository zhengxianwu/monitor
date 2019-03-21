package com.monitor.monitor.controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
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

	
	/**
	 * 获取最新数据 
	 * @param hostname    主机名称 (hostname)
	 * @param indexName   索引名字 metric
	 * @param beatName        插件名称 metricset
	 * @param module 插件数据模块名称  如：metricset
	 * @param name   插件数据名称 如：system
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	@RequestMapping(value = "/ESQuery/getNewData", method = RequestMethod.GET)
	public String getCPU(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "indexName", required = true) String indexName,
//			@RequestParam(value = "beatName", required = true) String beatName,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder) {
		TransportClient client = null;
		try {
			client = esClient.getClient();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String beatName = "";
		if (indexName.equals("fileset")) {
			 indexName = MyDataUtil.getIndexFormat(fileset_version);
			 beatName = "fileset";
		} else if(indexName.equals("metric")) {
			indexName = MyDataUtil.getIndexFormat(metric_version);
			 beatName = "metricset";
		}
	
		
		SortOrder Order;
		if (sortOrder.equals("desc")) {
			Order = SortOrder.DESC;
		} else {
			Order = SortOrder.ASC;
		}
		
		System.out.println("indexName: "+ indexName +"\r\nhostname  : " + hostname +"\r\n beatName :" + beatName + "\r\n module : " + module + "\r\n name : "+  name+ "\r\n order :"+ Order);
		List<String> newData = esOperate.getNewData(client, indexName,hostname, beatName, module, name, Order);
		JSONArray fromObject = JSONArray.fromObject(newData);
		return fromObject.toString();
	}

}
