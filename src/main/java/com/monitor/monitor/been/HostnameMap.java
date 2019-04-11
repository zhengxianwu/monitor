package com.monitor.monitor.been;

public class HostnameMap {
	private int id;
	private String hostname; //主机名
	private String address; //ip地址
	public HostnameMap(int id, String hostname, String address) {
		super();
		this.id = id;
		this.hostname = hostname;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "HostnameMap [id=" + id + ", hostname=" + hostname + ", address=" + address + "]";
	}
	
	
}
