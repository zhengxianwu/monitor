package com.monitor.monitor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Singleton
@Repository
public class Databases {

	@Value("${dao.url}")
	private String url;

	@Value("${dao.port}")
	private String port;

	@Value("${dao.user}")
	private String user;

	@Value("${dao.password}")
	private String password;

	@Value("${dao.daobases}")
	private String daobases;

	// 声明Connection对象
	private Connection con = null;

	private Connection initConnection() {
		if (con != null)
			con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			String uri = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.daobases +"?characterEncoding=utf-8";//防止乱码
			String uri = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.daobases +"?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
			
			con = DriverManager.getConnection(uri, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	public void close_connection() throws SQLException {
		if (con != null && !con.isClosed()) {
			this.con.close();
		}
	}

	public Connection getConnection() throws SQLException {
		return initConnection();
	}

	// select
	public ResultSet select(String sql) throws SQLException {
		ResultSet rs = null;
		Connection conn = initConnection();
		Statement statement = (Statement) conn.createStatement();
		rs = statement.executeQuery(sql);
		return rs;
	}

	// update
	public boolean update(String sql) {
		Connection con = initConnection();
		boolean flag = true;
		Statement statement = null;
		try {
			con.setAutoCommit(false);
			if (!con.isClosed() && con != null) {
				statement = (Statement) con.createStatement();
				if (statement.executeUpdate(sql) == 1) {
					con.commit();
					System.out.println("更新成功");
				} else {
					con.rollback();
					System.out.println("更新失败");
					flag = !flag;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (con != null) {
					con.rollback();
					flag = !flag;
					System.out.println("更新失败");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			statement.close();
			con.close();
			close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	// insert
	public boolean insert(String sql) {
		Connection con = initConnection();
		boolean flag = true;
		Statement statement = null;
		try {
			con.setAutoCommit(false);
			if (!con.isClosed() && con != null) {
				statement = (Statement) con.createStatement();
				if (statement.executeUpdate(sql) == 1) {
					con.commit();
					System.out.println("插入成功");
				} else {
					con.rollback();
					System.out.println("插入失败");
					flag = !flag;
				}
			}
		}catch (MySQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			//Duplicate entry '39.108.227.23' for key 'address' 存在相同的数据，报错
			return false;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (con != null) {
					con.rollback();
					System.out.println("插入失败");
					flag = !flag;
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			statement.close();
			con.close();
			close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	//delete
	public boolean delete(String sql) {
		Connection con = initConnection();
		boolean flag = true;
		Statement statement = null;
		try {
			con.setAutoCommit(false);
			if (!con.isClosed() && con != null) {
				statement = (Statement) con.createStatement();
				if (statement.executeUpdate(sql) == 1) {
					con.commit();
					System.out.println("删除成功");
				} else {
					con.rollback();
					System.out.println("删除失败");
					flag = !flag;
				}
			}
		}catch (MySQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			//Duplicate entry '39.108.227.23' for key 'address' 存在相同的数据，报错
			return false;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (con != null) {
					con.rollback();
					System.out.println("删除失败");
					flag = !flag;
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			statement.close();
			con.close();
			close_connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}
}
