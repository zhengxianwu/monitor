package com.monitor.monitor.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:DatabasesConfig.properties")
@ConfigurationProperties(prefix = "dao")
public class DatabasesConfig {
	private String url;
	private String user;
	private String password;
	private String daobases;
	private String port;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDaobases() {
		return daobases;
	}
	public void setDaobases(String daobases) {
		this.daobases = daobases;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
}
