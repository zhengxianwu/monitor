package com.monitor.monitor.been;

public class NailingRobotMapBean {

	private int id;// 数据库id
	private String rootId; // 机器人id
	private String rootName; // 机器人名字
	private String rootToken; // token

	public NailingRobotMapBean(int id, String rootId, String rootName, String rootToken) {
		super();
		this.id = id;
		this.rootId = rootId;
		this.rootName = rootName;
		this.rootToken = rootToken;
	}
	

	public NailingRobotMapBean(String rootId, String rootName, String rootToken) {
		super();
		this.rootId = rootId;
		this.rootName = rootName;
		this.rootToken = rootToken;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getRootToken() {
		return rootToken;
	}

	public void setRootToken(String rootToken) {
		this.rootToken = rootToken;
	}

	@Override
	public String toString() {
		return "HostnameMap [id=" + id + ", rootId=" + rootId + ", rootName=" + rootName + ", rootToken=" + rootToken
				+ "]";
	}

}
