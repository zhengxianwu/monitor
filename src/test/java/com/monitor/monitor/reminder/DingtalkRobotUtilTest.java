package com.monitor.monitor.reminder;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc // 注入MockMvc
public class DingtalkRobotUtilTest {

	@Autowired
	private DingtalkRobotUtil dru;

	@Test
	public void test() {
		Long begin = System.currentTimeMillis();
		String[] at = new String[]{""};
		dru.messageText("测试:@全部人",at,true);
		Long end = System.currentTimeMillis();
		System.out.println((end - begin) + "ms");
	}
	
	@Test
	public void testLink() {
		Long begin = System.currentTimeMillis();
		String[] at = new String[]{""};
		dru.messageLink("测试链接型：", "titile:这是百度地址", "https://www.baidu.com/img/bd_logo1.png", "https://www.baidu.com/", at, true);
		Long end = System.currentTimeMillis();
		System.out.println((end - begin) + "ms");
	}


}
