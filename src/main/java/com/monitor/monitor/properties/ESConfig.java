package com.monitor.monitor.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ESConfig.properties")
@ConfigurationProperties(prefix = "es")
public class ESConfig {
	private String master;
	private String cluster_name;
	private String port;
	
	private String metric_version;
	


	public String getMetric_version() {
		return metric_version;
	}

	public void setMetric_version(String metric_version) {
		this.metric_version = metric_version;
	}

	public String getCluster_name() {
		return cluster_name;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
}
