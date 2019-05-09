package com.monitor.monitor.es.type;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.controller.FilebeatControllerTest;
import com.monitor.monitor.dao.AddressMapDb;
import com.monitor.monitor.dao.Databases;
import com.monitor.monitor.dao.ScheduleTaskDb;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc // 注入MockMvc
public class ScheduleTaskTypeTest {
	@Autowired
	private ScheduleTaskDb std;

	private final static Logger logger = LoggerFactory.getLogger(ScheduleTaskTypeTest.class);

	@Test
	public void alltest() {
		List<Schedule> all = std.getAll();
		for (Schedule s : all) {
			logger.debug(s.toString());
			System.out.println(s);
		}
	}

	@Test
	public void printLogger() {
		logger.error("直接输出吧");
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addtest() {
//	  boolean add = std.add("zhengxian", "heihe", "asd", "asodko", "asdq", "asf", "adqdmqd","asd");
//	  System.out.println(add);
	}

	@Test
	public void updatetest() {
//	  boolean add = std.updateMap("测试更新", "heihe111", "asd222", "aso222dko", "asdq33", "as44f", "asodko","asd");
//	  System.out.println(add);
	}

}
