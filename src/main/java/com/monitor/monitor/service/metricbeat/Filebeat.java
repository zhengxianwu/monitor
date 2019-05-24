package com.monitor.monitor.service.metricbeat;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.monitor.monitor.service.util.MyTimeUtil;

@Service
@Scope("singleton")
public class Filebeat {

	/**
	 * Filebeat文件监控查询(不做过滤)
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param hostname  主机名称
	 * @param filename  日志文件名 必须是文件名字+类型，如mysql.log
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename,
			SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", "*" + filename))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log")))
				.addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

	/**
	 * Filebeat文件监控查询（分页)
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param hostname  主机名称
	 * @param filename  日志文件名
	 * @param from      分页从第几行开始(可选)
	 * @param size      分页长度(可选)
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename, int from,
			int size, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log")))
				.setFrom(from).setSize(size).addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

	/**
	 * Filebeat文件监控查询（时间过滤)
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param hostname  主机名称
	 * @param filename  日志文件名
	 * @param startTime 开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime   结束时间
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename,
			String startTime, String endTime, SortOrder sortOrder) {
		List<String> list = null;
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log"))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

	/**
	 * Filebeat文件监控查询（时间过滤+分页)
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param hostname  主机名称
	 * @param filename  日志文件名
	 * @param startTime 开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime   结束时间
	 * @param from      分页从第几行开始(可选)
	 * @param size      分页长度(可选)
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename,
			String startTime, String endTime, int from, int size, SortOrder sortOrder) {
		List<String> list = null;
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log"))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.setFrom(from).setSize(size).addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

	// -------------------------------------------------------日志内容含有过滤--------------------------------------------------------------------

	/**
	 * Filebeat文件内容查询（不做过滤)
	 * 
	 * @param client     客户端
	 * @param indexName  索引名字
	 * @param hostname   主机名称
	 * @param filename   日志文件名
	 * @param regContext 查询记录含有得内容
	 * @param sortOrder  排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename,
			String regContext, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.regexpQuery("message", ".*" + regContext + ".*"))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log")))
				.addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

	/**
	 * Filebeat文件内容查询（分页)
	 * 
	 * @param client     客户端
	 * @param indexName  索引名字
	 * @param hostname   主机名称
	 * @param filename   日志文件名
	 * @param from       分页从第几行开始(可选)
	 * @param size       分页长度(可选)
	 * @param regContext 查询记录含有得内容
	 * @param sortOrder  排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename, int from,
			int size, String regContext, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.regexpQuery("message", ".*" + regContext + ".*"))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log")))
				.setFrom(from).setSize(size).addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

	/**
	 * Filebeat文件内容查询（时间过滤)
	 * 
	 * @param client     客户端
	 * @param indexName  索引名字
	 * @param hostname   主机名称
	 * @param filename   日志文件名
	 * @param startTime  开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime    结束时间
	 * @param regContext 查询记录含有得内容
	 * @param sortOrder  排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename,
			String startTime, String endTime, String regContext, SortOrder sortOrder) {
		List<String> list = null;
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.regexpQuery("message", ".*" + regContext + ".*"))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log"))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}
	
	/**
	 * Filebeat文件内容查询（时间过滤+分页)
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param hostname  主机名称
	 * @param filename  日志文件名 
	 * @param startTime 开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime   结束时间
	 * @param from      分页从第几行开始(可选)
	 * @param size      分页长度(可选)
	 * @param regContext 查询记录含有得内容
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> getFileLog(TransportClient client, String indexName, String hostname, String filename,
			String startTime, String endTime, int from, int size, String regContext, SortOrder sortOrder) {
		List<String> list = null;
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("source", ".*" + filename))
						.filter(QueryBuilders.regexpQuery("message", ".*" + regContext + ".*"))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.filter(QueryBuilders.termQuery("prospector.type", "log"))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.setFrom(from).setSize(size).addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hitsCount = hits.getHits();
		if (hitsCount.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < hitsCount.length; i++) {
				list.add(hitsCount[i].getSourceAsString());
			}
		}
		return list;
	}

}
