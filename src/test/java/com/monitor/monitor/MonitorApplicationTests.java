package com.monitor.monitor;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.been.TestDemo;
import com.monitor.monitor.service.metricbeat.ESType;
import com.monitor.monitor.service.metricbeat.Metircbeat;
import com.monitor.monitor.service.util.MyDataUtil;
import com.monitor.monitor.service.util.MyTimeUtil;

import net.sf.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorApplicationTests {

	private String ip = "127.0.0.1";
	private String cluster_name = "elasticsearch";
	private int port = 9300;
	private String index_home = "metricbeat-6.4.3";
	@Autowired
	private TestDemo test;
	
	@Autowired
	private Metircbeat metricbeat;
	
	
	@Test
	public void Cpu() throws UnknownHostException {
		TransportClient client = metricbeat.getClient(cluster_name, ip, port);
		Date date = new Date();
		String indexName = String.format(index_home+"-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		String[] localToUTC = MyTimeUtil.getLocalToUTC();
		System.out.println(localToUTC[0] + "---" + localToUTC[1]);
		List<String> rangeSearch = new Metircbeat().RangeSearch(client, indexName, localToUTC[0], localToUTC[1], ESType.process.toString());
//		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, ESType.process);
		for(String s : rangeSearch) {
			System.out.println("---------------------");
			System.out.println(s);
		}
		
	}
	
	
	
	
	
	
	
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

