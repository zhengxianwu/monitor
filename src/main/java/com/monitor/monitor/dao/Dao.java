package com.monitor.monitor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Dao {



	public static void main(String[] args) {
		try {
//			content select = select(1);
			update("diyici",1,1);
//			System.out.println(select);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static Connection connectionDataBase() {
		// 声明Connection对象
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/affairs";
			String user = "root";
			String password = "root";
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	// select
	public static content select(int id) throws SQLException {
		Connection con = connectionDataBase();
		ResultSet rs = null;
		con.setAutoCommit(false);
		content c = new content();
		if (!con.isClosed() && con != null) {

			// 2.创建statement类对象，用来执行SQL语句！！
			Statement statement = (Statement) con.createStatement();
			// 要执行的SQL语句
			String sql = "select * from content where id = '" + id + "'";
			// 3.ResultSet类，用来存放获取的结果集！！
			rs = statement.executeQuery(sql);
			rs.next();
			c.setId(rs.getInt("id"));
			c.setContent(rs.getString("content"));
			c.setVersion(rs.getInt("version"));
		}
		rs.close();
		con.close();
		return c;
	}

	// update
	public static boolean update(String content, int version, int id) {
		Connection con = connectionDataBase();
		boolean flag = true;
		ResultSet rs = null;
		Statement statement = null;
		try {
			con.setAutoCommit(false);
			if (!con.isClosed() && con != null) {
				statement = (Statement) con.createStatement();
				String sql = String.format("update content set content = '%s' where version = %d and id = %d",
						content, version, id);
				System.out.println(sql);
				int execute = statement.executeUpdate(sql);
				System.out.println(execute);
				con.commit();
				System.out.println("更新成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (con != null) {
					con.rollback();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	public String t() {

		return "";
	}
}

