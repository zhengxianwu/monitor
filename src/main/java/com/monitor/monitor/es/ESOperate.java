package com.monitor.monitor.es;

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

import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.service.util.MyTimeUtil;

import net.sf.json.JSONObject;

@Service
@Scope("singleton")
public class ESOperate {

	// @param type_module 插件数据模块名称 如：MetricModule.system(插件+module)
	// @param type_name 插件数据名称 如：MtricSystemType.cpu(插件+module+Type)

	// ----------------------------------------集群----------------------------------------------

	/***
	 * ES数据查询(只过滤：module&name)
	 * 
	 * @param client      客户端
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param type_name   插件数据名称 如：MtricSystemType.cpu(插件+module+Type)
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String hostname, String beat,
			String type_module, String type_name, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery(beat.toString() + ".name", type_name.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname)))
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

	/***
	 * ES数据查询(只过滤：module)
	 * 
	 * @param client      客户端
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String hostname, String beat,
			String type_module, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname)))
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

	/***
	 * ES数据查询(只过滤：module&size)
	 * 
	 * @param client      客户端
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String hostname, String beat,
			String type_module, int from, int size, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname)))
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
	 * /*** ES数据查询(只过滤：module&name&size)
	 * 
	 * @param client      客户端
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @param type_name
	 * @param from
	 * @param size
	 * @return 默认返回最新几条数据
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String hostname, String beat,
			String type_module, String type_name, int from, int size, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery(beat.toString() + ".name", type_name.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname)))
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

	/***
	 * ES数据查询(只过滤：indexName&hostname) 用于查询索引得数据
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param hostname  主机名称
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, String hostname, int from, int size,
			SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("beat.hostname", hostname)))
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

	/***
	 * ES数据查询(只过滤：indexName) 用于查询索引得数据
	 * 
	 * @param client    客户端
	 * @param indexName 索引名字
	 * @param sortOrder 排序方法 SortOrder.DESC降序
	 * @return 默认返回最新几条数据
	 */
	public List<String> RangeSearch(TransportClient client, String indexName, int from, int size, SortOrder sortOrder) {
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b.setQuery(QueryBuilders.boolQuery()).setFrom(from).setSize(size)
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

	// --------------------------------------------------------------------------------------
	/**
	 * 获取最新一条数据（相同时间戳）(module&name)
	 * 
	 * @param client
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param type_name   插件数据名称
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return
	 */
	public List<String> getNewData(TransportClient client, String indexName, String hostname, String beat,
			String type_module, String type_name, SortOrder sortOrder) {
		// 1、获取最新一条数据
		List<String> rangeSearch = RangeSearch(client, indexName, hostname, beat, type_module, type_name, sortOrder);
		// 2、根据最新数据的timestamp获取批量数据
		String data = rangeSearch.get(0).toString(); // 这里会出错，因为时间问题，可能没有数据，考虑查询取第一条数据，exception：null
		JSONObject json = JSONObject.fromObject(data);
		List<String> sameTimeData = getSameTimeData(client, indexName, hostname, beat, type_module, type_name,
				json.getString("@timestamp"), sortOrder);
		return sameTimeData;
	}

	/**
	 * 获取最新一条数据（相同时间戳）(module)
	 * 
	 * @param client
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return
	 */
	public List<String> getNewData(TransportClient client, String indexName, String hostname, String beat,
			String type_module, SortOrder sortOrder) {
		// 1、获取最新一条数据
		List<String> rangeSearch = RangeSearch(client, indexName, hostname, beat, type_module, sortOrder);
		// 2、根据最新数据的timestamp获取批量数据
		String data = rangeSearch.get(0).toString(); // 这里会出错，因为时间问题，可能没有数据，考虑查询取第一条数据，exception：null
		JSONObject json = JSONObject.fromObject(data);
		List<String> sameTimeData = getSameTimeData(client, indexName, hostname, beat, type_module,
				json.getString("@timestamp"), sortOrder);
		return sameTimeData;
	}

	// --------------------------------------------------------------------------------------
	/**
	 * 获取相同时间的数据(集群)(module&name)
	 * 
	 * @author wuzhe
	 * @param client      客户端
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param type_name   插件数据名称
	 * @param timestamp   时间戳
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return 返回相同时间的数据
	 */
	public List<String> getSameTimeData(TransportClient client, String indexName, String hostname, String beat,
			String type_module, String type_name, String timestamp, SortOrder sortOrder) {
		/**
		 * 过滤不固定数值的思路 1、获取时间段的数据，时间排序，然后取第一条 2、再根据第一条的时间，取筛选数据
		 */
		// 尝试匹配id
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery(beat.toString() + ".name", type_name.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(timestamp).gte(timestamp)))
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
	 * 获取相同时间的数据(集群)(module)
	 * 
	 * @author wuzhe
	 * @param client      客户端
	 * @param indexName   索引名字
	 * @param hostname    主机名称
	 * @param beat        插件名称
	 * @param type_module 插件数据模块名称
	 * @param timestamp   时间戳
	 * @param sortOrder   排序方法 SortOrder.DESC降序
	 * @return 返回相同时间的数据
	 */
	public List<String> getSameTimeData(TransportClient client, String indexName, String hostname, String beat,
			String type_module, String timestamp, SortOrder sortOrder) {
		/**
		 * 过滤不固定数值的思路 1、获取时间段的数据，时间排序，然后取第一条 2、再根据第一条的时间，取筛选数据
		 */
		// 尝试匹配id
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(timestamp).gte(timestamp)))
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

	// --------------------------------------------------------------------------------------
	/**
	 * 根据时间来获取数据（以时间范围来获取）(module&name) 如：获取像要获取某一分钟数据
	 * 
	 * @param client      客户端
	 * @param indexName   索引名
	 * @param hostname    主机名称
	 * @param startTime   开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime     结束时间
	 * @param beat        插件名称
	 * @param type_module 插件模块名称
	 * @param type_name   插件名称
	 * @param from        分页从第几行开始
	 * @param size        分页长度
	 * @return json数组
	 */
	public List<String> rangeTime(TransportClient client, String indexName, String hostname, String startTime,
			String endTime, String beat, String type_module, String type_name, int from, int size,
			SortOrder sortOrder) {
		List<String> list = new ArrayList<String>();
		// 尝试匹配id
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery(beat.toString() + ".name", type_name.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.setFrom(from).setSize(size).addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hits2 = hits.getHits();
		for (SearchHit s : hits2) {
			list.add(s.getSourceAsString());
		}
		return list;
	}

	/**
	 * 根据时间来获取数据（以时间范围来获取）(module) 如：获取像要获取某一分钟数据
	 * 
	 * @param client      客户端
	 * @param indexName   索引名
	 * @param hostname    主机名称
	 * @param startTime   开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime     结束时间
	 * @param beat        插件名称
	 * @param type_module 插件模块名称
	 * @param from        分页从第几行开始
	 * @param size        分页长度
	 * @param sortOrder   排序
	 * @return json数组
	 */
	public List<String> rangeTime(TransportClient client, String indexName, String hostname, String startTime,
			String endTime, String beat, String type_module, int from, int size, SortOrder sortOrder) {
		List<String> list = new ArrayList<String>();
		// 尝试匹配id
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.setFrom(from).setSize(size).addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		for (SearchHit s : hits) {
			list.add(s.getSourceAsString());
		}
		return list;
	}

	/**
	 * 根据时间来获取数据（以时间范围来获取）（不要size)(module&name) 如：获取像要获取某一分钟数据
	 * 
	 * @param client      客户端
	 * @param indexName   索引名
	 * @param hostname    主机名称
	 * @param startTime   开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime     结束时间
	 * @param beat        插件名称
	 * @param type_module 插件模块名称
	 * @param type_name   插件名称
	 * @return json数组
	 */
	public List<String> rangeTime(TransportClient client, String indexName, String hostname, String startTime,
			String endTime, String beat, String type_module, String type_name, SortOrder sortOrder) {
		List<String> list = new ArrayList<String>();
		// 尝试匹配id
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
//		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery(beat.toString() + ".name", type_name.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hits2 = hits.getHits();
		for (SearchHit s : hits2) {
			list.add(s.getSourceAsString());
		}
		return list;
	}

	/**
	 * 根据时间来获取数据（以时间范围来获取）（不要size)(module) 如：获取像要获取某一分钟数据
	 * 
	 * @param client      客户端
	 * @param indexName   索引名
	 * @param hostname    主机名称
	 * @param startTime   开始时间 开始时间（本地）格式："2019-3-7 10:00:27"
	 * @param endTime     结束时间
	 * @param beat        插件名称
	 * @param type_module 插件模块名称
	 * @return json数组
	 */
	public List<String> rangeTime(TransportClient client, String indexName, String hostname, String startTime,
			String endTime, String beat, String type_module, SortOrder sortOrder) {
		List<String> list = new ArrayList<String>();
		// 尝试匹配id
		startTime = MyTimeUtil.getLocalToUTC(startTime);
		endTime = MyTimeUtil.getLocalToUTC(endTime);
		System.out.println(startTime + "----" + endTime);
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery(beat.toString() + ".module", type_module.toString()))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte(endTime).gte(startTime)))
				.addSort("@timestamp", sortOrder).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hits2 = hits.getHits();
		for (SearchHit s : hits2) {
			list.add(s.getSourceAsString());
		}
		return list;
	}
	// --------------------------------------------------------------------------------------

}
