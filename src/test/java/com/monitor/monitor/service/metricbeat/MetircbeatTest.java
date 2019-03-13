package com.monitor.monitor.service.metricbeat;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.es.ESClient;
import com.monitor.monitor.es.type.MetricSystemType;
import com.monitor.monitor.service.util.MyDataUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MetircbeatTest {

	private String hostname_1 = "zhengxian";
	private String hostname_2 = "elastic-128";

	@Autowired
	private Metircbeat metricbeat;

	@Value("${es.metric_version}")
	private String metric_version;

	@Autowired
	private ESClient esClient;

	@Test
	public void rangeTimeTest() throws UnknownHostException {
		String indexName = MyDataUtil.getIndexFormat(metric_version, "2019-03-06");
		System.out.println(indexName);
		List<String> rangeTime = metricbeat.rangeTime(esClient.getClient(), indexName, hostname_1, "2019-3-6 11:32:00",
				"2019-3-6 11:50:08", MetricSystemType.cpu);
		for (String s : rangeTime) {
			System.out.println(s);
		}
	}

}
