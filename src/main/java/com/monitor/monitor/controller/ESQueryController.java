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
	 * 
	 * @param hostname  主机名称 (hostname)
	 * @param indexName 索引名字 metric
	 * @param module    插件数据模块名称 如：metricset
	 * @param name      (可选)插件数据名称 如：system
	 * @param from      分页从第几行开始(可选)
	 * @param size      分页长度(可选)
	 * @param sortOrder 排序(可选)
	 * @param sortOrder 排序方法 SortOrder.DESC降序(可选)
	 * @param indexTime 索引日期如： 2019-3-3（默认查询单天数据，如果要查询指定日期，则填写)
	 * @category 配对使用（startTime和endTime） ，（ from和size)必须两个一起
	 * @return 默认返回最新几条数据
	 */
	@RequestMapping(value = "/ESQuery/getNewData", method = RequestMethod.GET)
	public String getNewData(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "indexName", required = true) String indexName,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "from", required = false, defaultValue = "") String from,
			@RequestParam(value = "size", required = false, defaultValue = "") String size,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
			@RequestParam(value = "indexTime", required = false, defaultValue = "") String indexTime) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String beatName = "";
		if (indexName.equals("fileset")) {
			if (indexTime.equals("")) {
				indexName = MyDataUtil.getIndexFormat(fileset_version);
			} else {
				indexName = MyDataUtil.getIndexFormat(fileset_version);
			}
			beatName = "fileset";
		} else if (indexName.equals("metric")) {
			if (indexTime.equals("")) {
				indexName = MyDataUtil.getIndexFormat(metric_version);
			} else {
				indexName = MyDataUtil.getIndexFormat(metric_version);
			}
			beatName = "metricset";
		}

		SortOrder Order;
		if (sortOrder.equals("desc")) {
			Order = SortOrder.DESC;
		} else {
			Order = SortOrder.ASC;
		}

		List<String> newData = null;

		System.out.println("indexName: " + indexName + "\r\nhostname  : " + hostname + "\r\n beatName :" + beatName
				+ "\r\n module : " + module + "\r\n name : " + name + "\r\n order :" + Order);

		if (name.equals("")) {
			newData = esOperate.getNewData(client, indexName, hostname, beatName, module, Order);
		} else {
			newData = esOperate.getNewData(client, indexName, hostname, beatName, module, name, Order);
		}
		JSONArray fromObject = JSONArray.fromObject(newData);
		if (newData == null)
			return null;
		return fromObject.toString();
	}

	/**
	 * 根据时间来获取数据（以时间范围来获取）(含size,from分页) 如：获取像要获取某一分钟数据
	 * 
	 * @param indexName 索引名
	 * @param hostname  主机名称
	 * @param startTime 开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime   结束时间
	 * @param module    插件模块名称
	 * @param name      插件名称(可选)
	 * @param from      分页从第几行开始(可选)
	 * @param size      分页长度(可选)
	 * @param sortOrder 排序(可选)
	 * @param indexTime 索引日期如： 2019-3-3（默认查询单天数据，如果要查询指定日期，则填写)
	 * @category 配对使用（startTime和endTime） ，（ from和size)必须两个一起
	 * @return json数组
	 */
	@RequestMapping(value = "/ESQuery/getRangeTime", method = RequestMethod.GET)
	public String getRangeTime(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "indexName", required = true) String indexName,
			@RequestParam(value = "startTime", required = true) String startTime,
			@RequestParam(value = "endTime", required = true) String endTime,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "from", required = false, defaultValue = "") String from,
			@RequestParam(value = "size", required = false, defaultValue = "") String size,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
			@RequestParam(value = "indexTime", required = false, defaultValue = "") String indexTime) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String beatName = "";
		if (indexName.equals("fileset")) {
			if (indexTime.equals("")) {
				indexName = MyDataUtil.getIndexFormat(fileset_version);
			} else {
				indexName = MyDataUtil.getIndexFormat(fileset_version);
			}
			beatName = "fileset";
		} else if (indexName.equals("metric")) {
			if (indexTime.equals("")) {
				indexName = MyDataUtil.getIndexFormat(metric_version);
			} else {
				indexName = MyDataUtil.getIndexFormat(metric_version);
			}
			beatName = "metricset";
		}

		SortOrder Order;
		if (sortOrder.equals("desc")) {
			Order = SortOrder.DESC;
		} else {
			Order = SortOrder.ASC;
		}

		List<String> newData = null;
		if ((from.equals("") || size.equals("")) && name.equals("")) {
			newData = esOperate.rangeTime(client, indexName, hostname, startTime, endTime, beatName, module, Order);
		} else if ((from.equals("") || size.equals("")) && !name.equals("")) {
			newData = esOperate.rangeTime(client, indexName, hostname, startTime, endTime, beatName, module, name,
					Order);
		} else if ((!from.equals("") && !size.equals("")) && name.equals("")) {
			newData = esOperate.rangeTime(client, indexName, hostname, startTime, endTime, beatName, module,
					Integer.parseInt(from), Integer.parseInt(size), Order);
		} else if ((!from.equals("") && !size.equals("")) && !name.equals("")) {
			newData = esOperate.rangeTime(client, indexName, hostname, startTime, endTime, beatName, module, name,
					Integer.parseInt(from), Integer.parseInt(size), Order);
		}

		if (newData == null)
			return null;
		System.out.println("indexName: " + indexName + "\r\nhostname  : " + hostname + "\r\n beatName :" + beatName
				+ "\r\n module : " + module + "\r\n name : " + name + "\r\n order :" + Order);
		JSONArray fromObject = JSONArray.fromObject(newData);
		return fromObject.toString();
	}

	/**
	 * 查询接口(含size,from分页) 如：获取像要获取某一分钟数据
	 * 
	 * @param indexName 索引名
	 * @param hostname  主机名称
	 * @param module    插件模块名称
	 * @param name      插件名称(可选)
	 * @param from      分页从第几行开始(可选)
	 * @param size      分页长度(可选)
	 * @param sortOrder 排序(可选)
	 * @param indexTime  索引日期如： 2019-3-3（默认查询单天数据，如果要查询指定日期，则填写)
	 * @category 配对使用（startTime和endTime） ，（ from和size)必须两个一起
	 * @return json数组
	 */
	@RequestMapping(value = "/ESQuery/getRangeSearch", method = RequestMethod.GET)
	public String getRangeSearch(@RequestParam(value = "hostname", required = true) String hostname,
			@RequestParam(value = "indexName", required = true) String indexName,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "from", required = false, defaultValue = "") String from,
			@RequestParam(value = "size", required = false, defaultValue = "") String size,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "desc") String sortOrder,
			@RequestParam(value = "indexTime", required = false, defaultValue = "")String indexTime) {
		TransportClient client = null;
		try {
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String beatName = "";
		if (indexName.equals("fileset")) {
			if (indexTime.equals("")) {
				indexName = MyDataUtil.getIndexFormat(fileset_version);
			} else {
				indexName = MyDataUtil.getIndexFormat(fileset_version);
			}
			beatName = "fileset";
		} else if (indexName.equals("metric")) {
			if (indexTime.equals("")) {
				indexName = MyDataUtil.getIndexFormat(metric_version);
			} else {
				indexName = MyDataUtil.getIndexFormat(metric_version);
			}
			beatName = "metricset";
		}

		SortOrder Order;
		if (sortOrder.equals("desc")) {
			Order = SortOrder.DESC;
		} else {
			Order = SortOrder.ASC;
		}

		List<String> newData = null;
		if ((from.equals("") || size.equals("")) && name.equals("")) {
			newData = esOperate.RangeSearch(client, indexName, hostname, beatName, module, Order);
		} else if ((from.equals("") || size.equals("")) && !name.equals("")) {
			newData = esOperate.RangeSearch(client, indexName, hostname, beatName, module, name, Order);
		} else if ((!from.equals("") && !size.equals("")) && name.equals("")) {
			newData = esOperate.RangeSearch(client, indexName, hostname, beatName, module, Integer.parseInt(from),
					Integer.parseInt(size), Order);
		} else if ((!from.equals("") && !size.equals("")) && !name.equals("")) {
			newData = esOperate.RangeSearch(client, indexName, hostname, beatName, module, name, Integer.parseInt(from),
					Integer.parseInt(size), Order);
		}

		if (newData == null)
			return null;
		System.out.println("indexName: " + indexName + "\r\nhostname  : " + hostname + "\r\n beatName :" + beatName
				+ "\r\n module : " + module + "\r\n name : " + name + "\r\n order :" + Order);
		JSONArray fromObject = JSONArray.fromObject(newData);
		return fromObject.toString();
	}

}
