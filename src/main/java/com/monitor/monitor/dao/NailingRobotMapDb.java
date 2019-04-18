package com.monitor.monitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.monitor.monitor.been.NailingRobotMap;
import com.monitor.monitor.been.Schedule;
import com.monitor.monitor.been.NailingRobotMap;
import com.mysql.jdbc.Statement;

@Repository
@Singleton
public class NailingRobotMapDb {

	@Autowired
	private Databases db;

	private String table = "nailing_robot";

	/**
	 * 获取全部钉钉机器人映射
	 * 
	 * @return
	 */
	public List<NailingRobotMap> getAll() {
		List<NailingRobotMap> list = new ArrayList<>();

		ResultSet rs;
		try {
			rs = db.select("select * from " + table);
			while (rs.next()) {
				list.add(new NailingRobotMap(rs.getInt("Id"), rs.getString("rootId"), rs.getString("rootName"),
						rs.getString("rootToken")));
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
	 * 添加钉钉机器人映射
	 * 
	 * @param rootId    机器人id
	 * @param rootName  机器人名字
	 * @param rootToken 机器人token
	 * @return
	 */
	public boolean addMap(String rootId, String rootName, String rootToken) {
		String sql = String.format("insert into " + table + "(rootId,rootName,rootToken) values('%s','%s','%s')",
				rootId, rootName, rootToken);
		return db.insert(sql);
	}

	/**
	 * 更新映射
	 * 
	 * @param rootName  机器人名字(更新)
	 * @param rootToken 机器人token(更新)
	 * @param rootId    机器人id
	 * @return
	 */
	public boolean updateMap(String rootName, String rootToken, String rootId) {
		String sql = String.format("update " + table + " set  rootName = '%s' ,  rootToken = '%s' where rootId='%s' ",
				rootName, rootToken, rootId);
		System.out.println(sql);
		return db.update(sql);
	}

	/**
	 * 删除映射
	 * 
	 * @param rootId 机器人id
	 * @return
	 */
	public boolean deleteMap(String rootId) {
		String sql = String.format("delete from " + table + " where rootId = '%s'", rootId);
		return db.delete(sql);
	}

	/**
	 * 获取指定映射
	 * 
	 * @param rootId 机器人Id
	 * @return
	 */
	public NailingRobotMap getNailingRobotMap(String rootId) {
		ResultSet rs;
		NailingRobotMap nailingRobotMap =null;
		try {

			rs = db.select(String.format("select * from " + table + " where rootId = '%s'", rootId));
			while (rs.next()) {
				 nailingRobotMap = new NailingRobotMap(rs.getInt("Id"), rs.getString("rootId"), rs.getString("rootName"),
						rs.getString("rootToken"));
			}
			rs.close();
			db.close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nailingRobotMap;
	}

}
