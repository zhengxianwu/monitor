package com.monitor.monitor.been.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.been.HostnameMapBean;
import com.monitor.monitor.been.NailingRobotMapBean;
import com.monitor.monitor.been.ScheduleBean;
import com.monitor.monitor.dao.DbTools;
import com.monitor.monitor.service.util.MyMD5;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc // 注入MockMvc
public class ScheduleBeanMapperTest {
	@Autowired
	private DbTools tool;

	@Test
	public void test() {

		SqlSession sqlSeesion = tool.getSqlSeesion();
		System.out.println(sqlSeesion);

		ScheduleBeanMapper mapper = sqlSeesion.getMapper(ScheduleBeanMapper.class);
		List<ScheduleBean> all = mapper.getAll();
		for(ScheduleBean s : all) {
			System.out.println(s);
		}
		
		//添加
		
		
	}
}
