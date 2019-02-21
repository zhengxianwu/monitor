package com.monitor.monitor.been;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component
@Singleton
public class TestDemo {
	
	

	public TestDemo() {
		super();
		System.out.println("构造一次");
		// TODO Auto-generated constructor stub
	}


	public String test() {
		return "test";
	}
	
	
	public String hello(String hello) {
		return hello;
	}
	
}
