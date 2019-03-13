package com.monitor.monitor.es;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.monitor.monitor.es.module.FilesetModule;
import com.monitor.monitor.es.module.MetricModule;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileModuleTest {

	private String hostname = "zhengxian";
	@Autowired
//	@Qualifier("client")
	private ESClient esClient;

	@Autowired
	private ESOperate esOperate;

	@Value("${es.metric_version}")
	private String metric_version;

	@Value("${es.fileset_version}")
	private String fileset_version;

	@Test
	public void Test() throws UnknownHostException {
		TransportClient client = esClient.getClient();
		String indexName = MyDataUtil.getIndexFormat(metric_version);

		List<String> newData = esOperate.getNewData(client, indexName, hostname, Beat.metricset.toString(), MetricModule.system.toString(), SortOrder.DESC);
		for (String s : newData) {
			System.out.println(s);
		}
	}
	
	@Test
	public void Test2() throws UnknownHostException {
		TransportClient client = esClient.getClient();
		String indexName = MyDataUtil.getIndexFormat(fileset_version);
//		List<String> rangeSearch = esOperate.RangeSearch(client, indexName, hostname, Beat.fileset, 
//				FilesetModule.mysql);

		String startTime = "2019-1-13 10:00:27";
		String endTime = "2019-3-13 12:00:27";
		List<String> newData = esOperate.rangeTime(client, "filebeat-*", hostname, startTime, endTime, Beat.fileset.toString(), FilesetModule.mysql.toString(), 0, 100,SortOrder.DESC);
		for (String s : newData) {
			System.out.println(s);
		}
	}
////				FilesetModule.mysql);
	
	@Test
	public void Test3() throws UnknownHostException {
		TransportClient client = esClient.getClient();
		SearchRequestBuilder b = client.prepareSearch("filebeat-*").setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.boolQuery()
						.filter(QueryBuilders.termQuery("fileset.module", "mysql"))
						.filter(QueryBuilders.termQuery("beat.hostname", hostname))
						.must(QueryBuilders.rangeQuery("@timestamp").lte("2019-03-13T04:00:27.000Z").gte("2019-01-13T02:00:27.000Z")))
				.addSort("@timestamp", SortOrder.DESC).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		SearchHit[] hits2 = hits.getHits();
		for(SearchHit s : hits2) {
			System.out.println(s.getSourceAsString());
		}
	}
	
	//查询索引所有数据
	@Test
	public void Test4() throws UnknownHostException {
		TransportClient client = esClient.getClient();
		SearchRequestBuilder b = client.prepareSearch("filebeat-*").setTypes("doc");
		SearchResponse actionGet = b
				.setQuery(QueryBuilders.matchAllQuery()).setSize(100).setExplain(true).execute().actionGet();
		SearchHits hits = actionGet.getHits();
		for(SearchHit s : hits) {
			System.out.println(s.getSourceAsString());
		}
	}

	
	@Test
	public void Test5() throws UnknownHostException {
		TransportClient client = esClient.getClient();
		String indexName = MyDataUtil.getIndexFormat(fileset_version);
		List<String> newData = esOperate.getNewData(client, indexName, hostname, "fileset", "mysql", SortOrder.DESC);
		for(String s : newData) {
			System.out.println(s);
		}
	}
	
}
