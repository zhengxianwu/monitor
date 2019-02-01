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

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorApplicationTests {

	private String ip = "127.0.0.1";
	private String cluster_name = "elasticsearch";
	private int port = 9300;
	private String index = "metricbeat-6.5.0";
	
	@Autowired
	private TestDemo test;
	
	@Autowired
	private Metircbeat metricbeat;
	
	@Test
	public void contextLoads() {
		System.out.println(test.test());
	}
	
	@Test
	public void MetricTest() throws UnknownHostException {
		TransportClient client = metricbeat.getClient(cluster_name, ip, port);
		Date date = new Date();
		String indexName = String.format("metricbeat-6.5.0-%s", new SimpleDateFormat("yyyy.MM.dd").format(date)); // 当天index
		date = null;
		List<String> metricNewData = metricbeat.getMetricNewData(client, indexName, ESType.filesystem);
		for(String s : metricNewData) {
			System.out.println(s);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

