package com.monitor.monitor.been;

public class HostnameMapBean {
	private int id;
	private String hostname; // 主机名
	private String address; // ip地址
	private String remark;// 备注
	private String hostId;// 唯一标识

	public HostnameMapBean(int id, String hostname, String address, String remark, String hostId) {
		super();
		this.id = id;
		this.hostname = hostname;
		this.address = address;
		this.remark = remark;
		this.hostId = hostId;
	}

	public HostnameMapBean(String hostname, String address, String remark, String hostId) {
		super();
		this.hostname = hostname;
		this.address = address;
		this.remark = remark;
		this.hostId = hostId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@Override
	public String toString() {
		return "HostnameMap [id=" + id + ", hostname=" + hostname + ", address=" + address + ", remark=" + remark
				+ ", hostId=" + hostId + "]";
	}

}
