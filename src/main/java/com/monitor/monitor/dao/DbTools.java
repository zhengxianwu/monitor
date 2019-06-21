package com.monitor.monitor.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Repository;

@Repository
@Singleton
public class DbTools {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private SqlSession session;

	static {
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public SqlSession getSqlSeesion() {
		if (session == null) {
			synchronized (DbTools.class) {
				session = sqlSessionFactory.openSession();
			}
		}
		return session;
	}

}
