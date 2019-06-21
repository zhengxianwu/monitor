package com.monitor.monitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.monitor.monitor.been.HostnameMapBean;
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
	 * 
	 * @return
	 */
	public List<HostnameMapBean> getAll() {
		List<HostnameMapBean> list = new ArrayList<>();

		ResultSet rs;
		try {
			rs = db.select("select * from hostname_map");
			while (rs.next()) {
				list.add(new HostnameMapBean(rs.getInt("id"), rs.getString("hostname"), rs.getString("address"),
						rs.getString("remark"), rs.getString("hostId")));
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
	 * @param hostId
	 * @param hostname
	 * @param address
	 * @param remark
	 * @return
	 */
	public boolean addMap(String hostId, String hostname, String address, String remark) {
		String sql = String.format("insert into hostname_map(hostId,hostname,address,remark) values('%s','%s','%s','%s')",
				hostId, hostname, address, remark);
		return db.insert(sql);
	}

	/**
	 * 
	/**
	 * 更新映射
	 * 
	 * @param hostname     新主机名
	 * @param address      新ip地址
	 * @param remark       新备注
	 * @param hostId       唯一标识
	 * @return
	 */
	public boolean updateMap(String hostname, String address, String remark, String hostId) {
		String sql = String.format(
				"update hostname_map set  hostname = '%s' ,  address = '%s' ,remark = '%s' where hostId = '%s'",
				hostname, address, remark, hostId);
		System.out.println(sql);
		return db.update(sql);
	}

	/**
	 * 删除映射
	 * 
	 * @param id
	 * @param hostname
	 * @param address
	 * @return
	 */
	public boolean deleteMap(String hostId) {
		String sql = String.format("delete from hostname_map where hostId = '%s'", hostId);
		return db.delete(sql);
	}

}
