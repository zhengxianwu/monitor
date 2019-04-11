package com.monitor.monitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.monitor.monitor.been.HostnameMap;
import com.mysql.jdbc.Statement;

//主机映射
//hostname_map表操作，映射主机-Ip_address
@Repository
@Singleton
public class AddressMapDb {

	
	@Autowired
	private Databases db;
	
	/**
	 * 获取全部映射
	 * @return
	 */
	public List<HostnameMap> getAll(){
		List<HostnameMap> list = new ArrayList<>();
		
		ResultSet rs;
		try {
			rs = db.select("select * from hostname_map");
			while(rs.next()) {
				list.add(new HostnameMap(rs.getInt("id"),  rs.getString("hostname"),  rs.getString("address")));
			}
			rs.close();
			db.close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 添加主机-ip映射
	 * @param hostname
	 * @param address
	 * @return
	 */
	public boolean addMap(String hostname,String address) {
		String sql = String.format("insert into hostname_map(hostname,address) values('%s','%s')", hostname,address);
		return db.insert(sql);
	}
	
	/**
	 * 更新映射
	 * @param hostname 新主机名
	 * @param address 新ip地址
	 * @param id id
	 * @param old_hostname  旧主机名
	 * @param old_address  旧ip地址
	 * @return
	 */
	public boolean updateMap(String hostname,String address,int id,String old_hostname,String old_address) {
		String sql = String.format("update hostname_map set  hostname = '%s' ,  address = '%s' where id=%d and hostname = '%s' and address = '%s' ", hostname,address,id,old_hostname,old_address);
		System.out.println(sql);
		return db.update(sql);
	}
	
	/**
	 * 删除映射
	 * @param id
	 * @param hostname
	 * @param address
	 * @return
	 */
	public boolean deleteMap(int id,String hostname,String address) {
		String sql = String.format("delete from hostname_map where id=%d and hostname = '%s' and address = '%s'",id,hostname,address);
		return db.delete(sql);
	}
	
}
