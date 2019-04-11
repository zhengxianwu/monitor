package com.monitor.monitor.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.monitor.monitor.been.HostnameMap;
import com.monitor.monitor.been.Schedule;
import com.mysql.jdbc.Statement;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc // 注入MockMvc
public class DatabasesTest {

	@Autowired
	private Databases db;

	@Autowired
	private AddressMapDb amd;

	
	@Test
	public void amd_test() throws Exception {
//		List<HostnameMap> all = amd.getALL();
//		for(HostnameMap m : all) {
//			System.out.println(m);
//		}
		
//		boolean addMap = amd.updateMap("yunwei_server", "39.108.227.22", 6, "heihei", "123");
		
		
//		amd.addMap("linux-129", "192.169.20.129");
		
		amd.deleteMap(12, "linux-129", "192.169.20.129");
	}

	
	
	
	
	
	
	
//	@Test
//	public void test() throws Exception {
//		Connection conn = db.getConnection();
//		Statement statement = (Statement) conn.createStatement();
//		ResultSet rs = statement.executeQuery("select * from hostname_map");
//		
//		rs.next();
//		int id= rs.getInt("id");
//		String hostname = rs.getString("hostname");
//		String address = rs.getString("address");
//		System.out.println("id :" + id + ", hsotname:" + hostname + ", address:" + address);
//		rs.close();
//		conn.close();
//		
//		boolean update = db.update("update hostname_map set address = 'localhost' where hostname = 'zhengxian'");
//		System.out.println("udpate :" + update);
//		
//		boolean insert = db.insert("insert into hostname_map(hostname,address) values('yunwei_server3','39.108.227.23')");
//		System.out.println(insert);
//		
//		boolean delete = db.delete("delete from hostname_map where hostname = 'yunwei_server3' and address ='39.108.227.23'");
//		System.out.println(delete);
//		
//	}

}
