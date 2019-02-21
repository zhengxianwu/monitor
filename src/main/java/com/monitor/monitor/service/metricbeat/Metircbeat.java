package com.monitor.monitor.service.metricbeat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.monitor.monitor.es.ESType;
import com.monitor.monitor.service.util.MyTimeUtil;

import net.sf.json.JSONObject;

@Service
@Scope("singleton")
public class Metircbeat {

	/**
	 * 获取指定时间范围和类型数据，返回json数据(降序DESC)
	 * @author wuzhe
	 * @param client    es链接客户端
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param esTYPE    查询类型
	 * @param From      分页起始
	 * @param size      分页长度
	 * @return 返回json集合
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String startTime, String endTime,
			String esTYPE, int from, int size) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("metricset.name", esTYPE))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.addSort("@timestamp", SortOrder.DESC).setExplain(true).setFrom(from).setSize(size).execute()
				.actionGet();
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

	
	/***
	 * 获取Metricbeat 最新数据 （形参化）
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param e         MeticBeat类型
	 * @return 类型Json集合
	 */
	public List<String> getMetricNewData(TransportClient client, String indexName, ESType e) {
		// 1、获取最新一条数据
		String[] localToUTC = MyTimeUtil.getLocalToUTC();
		List<String> rangeSearch = RangeSearch(client, indexName, localToUTC[0], localToUTC[1], e.toString(), 0, 1);

		// 2、根据最新数据的timestamp获取批量数据
		String data = rangeSearch.get(0).toString(); // 这里会出错，因为时间问题，可能没有数据，考虑查询取第一条数据，exception：null
		JSONObject json = JSONObject.fromObject(data);
		List<String> sameTimeData = getSameTimeData(client, indexName, json.getString("@timestamp"), e);
		return sameTimeData;
	}
	
	/***
	 * 获取getProcessNewData 最新数据 （形参化）
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param e         MeticBeat类型
	 * @return 类型Json集合
	 */
	public List<String> getProcessNewData(TransportClient client, String indexName) {
		// 1、获取最新一条数据
		String[] localToUTC = MyTimeUtil.getLocalToUTC();
//		List<String> rangeSearch = RangeSearch(client, indexName, localToUTC[0], localToUTC[1],ESType.process);
		return null;
	}

	/**
	 * 获取相同时间的数据
	 * 
	 * @param client
	 * @param indexName
	 * @param timestamp
	 * @param e         获取的数据类型，如：process,network等不确定数量的数据
	 * @return 返回相同时间的数据
	 */
	public List<String> getSameTimeData(TransportClient client, String indexName, String timestamp, ESType e) {
		List<String> list = new ArrayList<String>();
		/**
		 * 过滤不固定数值的思路 1、获取时间段的数据，时间排序，然后取第一条 2、再根据第一条的时间，取筛选数据
		 */
		// 尝试匹配id
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("metricset.name", e.toString()))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(timestamp).gte(timestamp)))
				.addSort("@timestamp", SortOrder.DESC).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hits2 = hits.getHits();
		for (SearchHit s : hits2) {
			list.add(s.getSourceAsString());
		}
		return list;
	}

	
	//----------------------------------------集群----------------------------------------------
	
	
	/**
	 * 
	 * 获取类型数据，取最新数据，返回json数据(降序DESC)(集群)
	 * @author wuzhe
	 * @param client es链接客户端
	 * @param esTYPE 查询类型
	 * @param hostname 节点名字
	 * @return 返回集合（数据是json格式）
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String hostname, ESType esTYPE) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery("metricset.name", esTYPE.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname)))
				.addSort("@timestamp", SortOrder.DESC).setExplain(true).execute().actionGet();
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
	
	/***
	 * 获取Metricbeat 最新数据 (集群)
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param e         MeticBeat类型
	 * @return 类型Json集合
	 */
	public List<String> getMetricNewData(TransportClient client, String indexName,String hostname, ESType e) {
		// 1、获取最新一条数据
		List<String> rangeSearch = RangeSearch(client, indexName, hostname, e);

		// 2、根据最新数据的timestamp获取批量数据
		String data = rangeSearch.get(0).toString(); // 这里会出错，因为时间问题，可能没有数据，考虑查询取第一条数据，exception：null
		JSONObject json = JSONObject.fromObject(data);
		List<String> sameTimeData = getSameTimeData(client, indexName, hostname,json.getString("@timestamp"), e);
		return sameTimeData;
	}
	
	/**
	 * 获取相同时间的数据(集群)
	 * @author wuzhe
	 * @param client 客户端
	 * @param indexName 索引名字
	 * @param hostname 集群名字
	 * @param timestamp
	 * @param e         获取的数据类型，如：process,network等不确定数量的数据
	 * @return 返回相同时间的数据
	 */
	public List<String> getSameTimeData(TransportClient client, String indexName,String hostname, String timestamp, ESType e) {
		List<String> list = new ArrayList<String>();
		/**
		 * 过滤不固定数值的思路 1、获取时间段的数据，时间排序，然后取第一条 2、再根据第一条的时间，取筛选数据
		 */
		// 尝试匹配id
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("metricset.name", e.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(timestamp).gte(timestamp)))
				.addSort("@timestamp", SortOrder.DESC).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hits2 = hits.getHits();
		for (SearchHit s : hits2) {
			list.add(s.getSourceAsString());
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 根据时间来获取数据（以时间范围来获取） 如：获取像要获取某一分钟数据
	 * 1、先获取时间（分钟），再去这一分钟得范围，如取12：30得数据,就去12:30:00 - 12:30:59 2、只要做时间处理即可， 3、读取数据
	 */
	public List<String> rangeTime(){
			return null;
	}

}
