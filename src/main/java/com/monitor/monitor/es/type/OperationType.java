package com.monitor.monitor.es.type;

public enum OperationType {
	Gte("大于等于"), Lte("小于等于"), Gt("大于"), Lt("小于");

	private final String explain;

	private OperationType(String explain) {
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}

}
