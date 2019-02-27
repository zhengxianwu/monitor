package com.monitor.monitor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.been.TestDemo;
import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.MetricSystemType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyTimeUtil;

import net.sf.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorApplicationTests {

	private String ip = "192.168.126.1";
	private String cluster_name = "home";
	private int port = 9300;
	private String index_home = "metricbeat-6.4.3";
	private String hostname_1 = "zhengxian";
	private String hostname_2 = "elastic-128";

	@Autowired
	private TestDemo test;

	@Autowired
	private Metircbeat metricbeat;

	@Autowired
//	@Qualifier("client")
	private ESClient esClient;

	
	
	
	
	
	@Test
	public void newdate() {
		TransportClient client = null;
		try {
			client = esClient.getClient();
			client = esClient.getClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (client != null) {

			String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(new Date())); // 当天index
//			List<String> rangeSearch = metricbeat.RangeSearch(client, indexName, hostname_2, ESType.cpu);
			List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, hostname_2, MetricSystemType.cpu);
			for (String s : metricNewData) {
				System.out.println(s);
			}
		}
	}

//	@Test
//	public void home() throws UnknownHostException {
//
//		TransportClient client = esClient.getClient(cluster_name, ip, port, true);
//		
//		// 嗅探功能("client.transport.sniff", true)
//		if (client != null) {
//			System.out.println("链接成功");
//			System.out.println(client.toString());
//		}
//		String indexName = String.format(index_home + "-%s", new SimpleDateFormat("yyyy.MM.dd").format(new Date())); // 当天index
//		SearchRequestBuilder bulider = client.prepareSearch(indexName).setTypes("doc");
//		SearchResponse actionGet = bulider
//				.setQuery(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("beat.hostname", "elastic-128"))
//						.filter(QueryBuilders.termQuery("metricset.name", ESType.cpu.toString())))
//				.addSort("@timestamp", SortOrder.DESC).setExplain(true).execute().actionGet();
//		for (SearchHit hit : actionGet.getHits().getHits()) {
//			System.out.println(hit.getSourceAsString());
//
//		}
//
//	}

//	@Test
//	public void Cpu() throws UnknownHostException {
//		TransportClient client = metricbeat.getClient(cluster_name, ip, port);
//		Date date = new Date();
//		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
//		date = null;
//		String[] localToUTC = MyTimeUtil.getLocalToUTC();
//		System.out.println(localToUTC[0] + "---" + localToUTC[1]);
//		List<String> rangeSearch = new Metircbeat().RangeSearch(client, indexName, localToUTC[0], localToUTC[1], ESType.process.toString());
////		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, ESType.process);
//		for(String s : rangeSearch) {
//			System.out.println("---------------------");
//			System.out.println(s);
//		}
//		
//	}

//	@Test
//	public void contextLoads() {
//		System.out.println(test.test());
//		for(ESType e : ESType.values()) {
//			System.out.println(e);
//		}
//	}

//	@Test
//	public void MetricTest() throws UnknownHostException {
//		TransportClient client = metricbeat.getClient(cluster_name, ip, port);
//		Date date = new Date();
////		String indexName = String.format("metricbeat-6.5.0-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
//		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
//		date = null;
//		for(ESType e : ESType.values()) {
//			List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, e);
//			System.out.println(e.toString());
//			for(String s : metricNewData) {
//				System.out.println(s);
//			}
//			System.out.println("---------------------");
//		}
//		client.close();
//	}

//	@Test
//	public void Cpu() throws UnknownHostException {
//		TransportClient client = metricbeat.getClient(cluster_name, ip, port);
//		Date date = new Date();
////		String indexName = String.format("metricbeat-6.5.0-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
//		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
//		date = null;
////		system.cpu.system.pct
////		system.cpu.user.pct
//		String[] localToUTC = MyTimeUtil.getLocalToUTC();
//		System.out.println(localToUTC[0] + "---" + localToUTC[1]);
//		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, ESType.cpu);
//		JSONObject json = JSONObject.fromObject(metricNewData.get(0));
//		JSONObject system = json.getJSONObject("system");
//		JSONObject cpu = system.getJSONObject("cpu");
//		JSONObject system_cpu = cpu.getJSONObject("system");
//		JSONObject system_user = cpu.getJSONObject("user");
//		System.out.println(system_cpu.getString("pct"));
//		System.out.println(system_user.getString("pct"));
//		System.out.println(cpu.getString("cores"));
//		double cores = Double.valueOf(cpu.getString("cores"));
//		double system_cpu_value =  Double.valueOf(system_cpu.getString("pct"));
//		double system_user_value = Double.valueOf(system_user.getString("pct"));
//		double cpu_usage =  (system_cpu_value+system_user_value)/cores;
//		String formatDouble = MyDataUtil.formatDouble(cpu_usage*100);
//		System.out.println(formatDouble);
//		
//		
//	}
//	

}
