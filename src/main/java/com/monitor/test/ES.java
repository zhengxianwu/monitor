package com.monitor.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.monitor.monitor.es.MetricSystemType;

public class ES {

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		String ip = "192.168.126.1";
		String cluster_name = "home";
		int port = 9300;
		String index_home = "metricbeat-6.4.3";

		//嗅探功能("client.transport.sniff", true)
		Settings settings = Settings.builder().put("cluster.name", cluster_name).put("client.transport.sniff", true)
				.build();
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), 9300));

		if (client != null) {
			System.out.println("链接成功");
			System.out.println(client.toString());
		}

		SearchResponse res = null;
		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(new Date())); // 当天index
		
		
//		QueryBuilder qb = QueryBuilders.matchAllQuery();
//
//		res = client.prepareSearch(indexName)
//
//				.setTypes("doc")
//
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//
//				.setQuery(qb)
//
//				.setFrom(0)
//
//				.setSize(10)
//
//				.execute().actionGet();
//
//		for (SearchHit hit : res.getHits().getHits()) {
//
//			System.out.println(hit.getSourceAsString());
//
//		}
		
		SearchRequestBuilder setTypes = client.prepareSearch(indexName).setTypes("doc");
		
		
		
		
		List<String> list = null;
		SearchRequestBuilder b = client.prepareSearch(indexName).setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("beat.hostname", "elastic-128"))
						.filter(QueryBuilders.termQuery("metricset.name", MetricSystemType.cpu.toString())))
				.addSort("@timestamp", SortOrder.DESC)
				.setExplain(true)
				.execute()
				.actionGet();
		for (SearchHit hit : actionGet.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());

		}

	}

}





























