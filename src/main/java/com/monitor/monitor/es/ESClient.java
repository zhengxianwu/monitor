package com.monitor.monitor.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.inject.Singleton;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Singleton
public class ESClient {

	@Value("${es.master}")
	private String master;

	@Value("${es.cluster_name}")
	private String cluster_name;

	@Value("${es.port}")
	private int port;

	private TransportClient client = null;

	/**
	 * 获取ES客户端
	 * 
	 * @param cluster_name 集群名字
	 * @param ip           链接地址
	 * @param port         传输接口***
	 * @param bool         是否启动嗅探（启动），自动嗅探集群
	 * @return es传输客户端
	 * @throws UnknownHostException
	 */
	public TransportClient getClient(String cluster_name, String ip, int port, boolean bool)
			throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", cluster_name).put("client.transport.sniff", bool)
				.build();
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), port));
		return client;
	}

	public TransportClient getClient() throws UnknownHostException {
		if (this.client == null) {
			Settings settings = Settings.builder().put("cluster.name", this.cluster_name)
					.put("client.transport.sniff", true).build();
			this.client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(this.master), this.port));
		}
		return this.client;
	}

}
