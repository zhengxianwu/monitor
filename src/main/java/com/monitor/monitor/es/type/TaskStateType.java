package com.monitor.monitor.es.type;

public enum TaskStateType {
	Run("运行"), Stop("暂停");
	
	private final String explain;

	private TaskStateType(String explain) {
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}
	
}
