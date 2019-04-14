package com.monitor.monitor.es.type;

public enum TaskMonitorType {
	Memory("运行内存"), Cpu("Cpu"), Filesystem("磁盘");

	private final String explain;


	private TaskMonitorType(String explain) {
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}

}
