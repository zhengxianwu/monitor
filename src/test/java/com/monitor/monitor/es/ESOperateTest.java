package com.monitor.monitor.es;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.es.module.MetricModule;
import com.monitor.monitor.service.util.MyDataUtil;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESOperateTest {

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
		String indexName = MyDataUtil.getIndexALL(fileset_version);

		List<String> newData = esOperate.RangeSearch(client, indexName, hostname, 0, 1000, SortOrder.DESC);
		for (String s : newData) {
			System.out.println(s);
		}
	}

}
