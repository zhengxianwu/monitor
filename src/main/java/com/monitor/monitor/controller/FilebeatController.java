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
	 * @param hostname   主机名称 节点名称
	 * @param filename   文件名，如zhengxian.log 或 路径全称 ：/var/log/zhengxian.log
	 * @param startTime  开始时间 开始时间（本地）格式："2019-3-7 10:00:27"(可选)
	 * @param endTime    结束时间(可选)
	 * @param from       分页从第几行开始(可选)
	 * @param size       分页长度(可选)
	 * @param regContext 查询记录含有内容（可选)
	 * @param sortOrder  数据排序
	 * @param indexTime  索引日期如： 2019-3-3（默认查询单天数据，如果要查询指定日期，则填写)
	 * @param            ps:配对使用（startTime和endTime） ，（ from和size)必须两个一起
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
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
			@RequestParam(value = "indexTime", required = false, defaultValue = "") String indexTime) {
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
		
		if(from.equals("")) {
			from= "0";
		}

		String indexName;
		if (indexTime.equals("")) {
			indexName = MyDataUtil.getIndexFormat(fileset_version);
		} else {
			indexName = MyDataUtil.getIndexFormat(fileset_version, indexTime);
		}

		List<String> newData = null;

		if (regContext.equals("")) {
			if (startTime.equals("") && size.equals("")) { // 不做过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, Order);
			} else if (!startTime.equals("") && size.equals("")) { // 时间过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, startTime, endTime, Order);
			} else if (startTime.equals("") && !size.equals("")) { // 分页过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, Integer.parseInt(from),
						Integer.parseInt(size), Order);
			} else if (!startTime.equals("") && !size.equals("")) { // 分页时间过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, startTime, endTime,
						Integer.parseInt(from), Integer.parseInt(size), Order);
			}
		} else {
			if (startTime.equals("") && size.equals("")) { // 不做过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, regContext, Order);
			} else if (!startTime.equals("") && size.equals("")) { // 时间过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, startTime, endTime, regContext,
						Order);
			} else if (startTime.equals("") && !size.equals("")) { // 分页过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, Integer.parseInt(from),
						Integer.parseInt(size), regContext, Order);
			} else if (!startTime.equals("") && !size.equals("")) { // 分页时间过滤
				newData = filebeat.getFileLog(client, indexName, hostname, filename, startTime, endTime,
						Integer.parseInt(from), Integer.parseInt(size), regContext, Order);
			}
		}

		if (newData == null)
			return null;
		JSONArray fromObject = JSONArray.fromObject(newData);
		return fromObject.toString();
	}

}
