package com.monitor.monitor.been.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.been.HostnameMapBean;
import com.monitor.monitor.dao.DbTools;
import com.monitor.monitor.service.util.MyMD5;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc // 注入MockMvc
public class HostnameMapBeanMapperTest {

	@Autowired
	private DbTools tool;

	@Test
	public void test() {

		SqlSession sqlSeesion = tool.getSqlSeesion();
		System.out.println(sqlSeesion);
		
		HostnameMapBeanMapper mapper = sqlSeesion.getMapper(HostnameMapBeanMapper.class);

		String hostId = MyMD5.Md5("127.0.0.1");
//		boolean insertHostname = mapper.insertHostname(new HostnameMapBean("test", "127.0.0.1", "测试", hostId));
		
		
		
		mapper.updateHostname(new HostnameMapBean("1231232222", "127.0.0.1", "测asdasd试", "60377703ded403f326d71f2799b0c511"));
//		mapper.deleteHostname(hostId);
		
		List<HostnameMapBean> all = mapper.getAll();

		for (HostnameMapBean u : all) {
			System.out.println(u);
		}
	}

}
