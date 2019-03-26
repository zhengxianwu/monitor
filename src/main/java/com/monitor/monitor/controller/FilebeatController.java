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

import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.service.metricbeat.Filebeat;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@CrossOrigin
@RestController
public class FilebeatController {

	@Value("${es.fileset_version}")
	private String fileset_version;

	@Autowired
	private Filebeat filebeat;

	@Autowired
//	@Qualifier("client")
	private ESClient esClient;

	/**
	 * 读取日志文件
	 * 
	 * @author wuzhe
	 * @param hostname  主机名称 节点名称
	 * @param filename  文件名，如zhengxian.log 或 路径全称 ：/var/log/zhengxian.log
	 * 
	 * @param sortOrder 数据排序
	 * @return json数组
	 */
	@RequestMapping(value = "/Filebeat/getFileLog", method = RequestMethod.GET)
	public String getFileLog(@RequestParam(value = "hostname", required = false) String hostname,
			@RequestParam(value = "filename", required = true) String filename,
			@RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
			@RequestParam(value = "endTime", required = false, defaultValue = "") String endTime,
			@RequestParam(value = "from", required = false, defaultValue = "") String from,
			@RequestParam(value = "size", required = false, defaultValue = "") String size,
			@RequestParam(value = "regContext", required = false, defaultValue = "") String regContext,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder) {
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

		SortOrder Order;
		if (sortOrder.equals("desc")) {
			Order = SortOrder.DESC;
		} else {
			Order = SortOrder.ASC;
		}

		String indexName = MyDataUtil.getIndexFormat(fileset_version);

		List<String> newData = null;
		if (startTime.equals("") && endTime.equals("") && from.equals("") && size.equals("")) {
			newData = filebeat.getFileLog(client, indexName, hostname, filename, Order);
		} else if ((!startTime.equals("") && !endTime.equals("")) && (from.equals("") && size.equals(""))) {
			newData = filebeat.getFileLog(client, indexName, hostname, filename, startTime, endTime, Order);
		} else if ((startTime.equals("") && endTime.equals("")) && (!from.equals("") && !size.equals(""))) {
			newData = filebeat.getFileLog(client, indexName, hostname, filename, Integer.parseInt(from),
					Integer.parseInt(size), Order);
		} else if (!startTime.equals("") && !endTime.equals("") && !from.equals("") && !size.equals("")) {
			newData = filebeat.getFileLog(client, indexName, hostname, filename, startTime, endTime,
					Integer.parseInt(from), Integer.parseInt(size), Order);
		}

		if (newData == null)
			return null;

		List<String> data = filebeat.getFileLog(client, indexName, hostname, filename, Order);
		JSONArray fromObject = JSONArray.fromObject(data);
		return fromObject.toString();
	}

}
