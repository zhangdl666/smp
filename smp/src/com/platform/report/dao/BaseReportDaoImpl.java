package com.platform.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.report.datadef.Data2D;
import com.platform.report.datadef.DefaultData2D;

public class BaseReportDaoImpl implements BaseReportDao  {
	
	private static Logger logger = Logger
			.getLogger(BaseReportDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	/** 
	 * ���ܣ�ͨ����ε����ݿ����Ӻ�SQL���飬��ѯSQL�����и���SQL�����ݼ�������Data2D������з��ء�
	 * 
	 * ������isNormalResult:true=��������в�ת����false=���������ת��;
	 * 		isNumberResult:true=���ֽ������false=�����ֽ������resultSet.getFloat(j+2)�Ƿ�Ϊ���֡�
	 * 		sql=Ҫִ�е�SQL����
	 * 
	 * sql�������ã�
	 * 		1.������SELECT��ѯ��䡣
	 * 		2.��������һ��Ϊ��key
	 * 		3.�ڶ�������ֶεı�����ΪData2D��������û�б�����ʹ��Ĭ��SQL������
	 * 
	 * @throws SQLException 
	 */
	
	public Data2D query(boolean isNormalResult,boolean isNumberResult,String... sql) throws Exception{
		Session session = sessionFactory.getCurrentSession();
		Connection con = ((SessionFactoryImplementor)session.getSessionFactory()).getConnectionProvider().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		DefaultData2D data = new DefaultData2D();
		try {
			statement = con.createStatement();
			for (int i = 0; i < sql.length; i++) {
				if(sql[i] == null || sql[i].trim().equals("")) {
					continue;
				}
				logger.info(sql[i]);
				resultSet = statement.executeQuery(sql[i]);
				ResultSetMetaData rsmd = resultSet.getMetaData() ;
				while (resultSet.next()) {
					for (int j = 0; j < rsmd.getColumnCount()-1; j++) {
						if(isNormalResult){//��������ת��
							if (isNumberResult) {//���ֽ����
								data.addValue(resultSet.getFloat(j+2), resultSet.getString(1), rsmd.getColumnName(j+2));
							}else{//�����ֽ����
								data.addValue(resultSet.getString(j+2), resultSet.getString(1), rsmd.getColumnName(j+2));
							}
						}else{
							if (isNumberResult) {//���ֽ����
								data.addValue(resultSet.getFloat(j+2), rsmd.getColumnName(j+2),resultSet.getString(1));
							}else{//�����ֽ����
								data.addValue(resultSet.getString(j+2), rsmd.getColumnName(j+2),resultSet.getString(1));
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			this.destroyCon(resultSet, statement, con);
		}
		return data;
	}
	
	
	public Data2D prepareStatementQuery(boolean isNormalResult,boolean isNumberResult,String sql,HashMap<String, String> paraMap) throws Exception{
		Session session = sessionFactory.getCurrentSession();
		Connection con = ((SessionFactoryImplementor)session.getSessionFactory()).getConnectionProvider().getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		DefaultData2D data = new DefaultData2D();
		try {
			statement = con.prepareStatement(sql);
			if(paraMap != null && !paraMap.isEmpty()) {
				Set<String> keys = paraMap.keySet();
				Iterator<String> it = keys.iterator();
				while(it.hasNext()) {
					String key = it.next();
					statement.setString(Integer.valueOf(key), paraMap.get(key));
				}
			}
			
			logger.info(sql);
			resultSet = statement.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData() ;
			while (resultSet.next()) {
				for (int j = 0; j < rsmd.getColumnCount()-1; j++) {
					if(isNormalResult){//��������ת��
						if (isNumberResult) {//���ֽ����
							data.addValue(resultSet.getFloat(j+2), resultSet.getString(1), rsmd.getColumnName(j+2));
						}else{//�����ֽ����
							data.addValue(resultSet.getString(j+2), resultSet.getString(1), rsmd.getColumnName(j+2));
						}
					}else{
						if (isNumberResult) {//���ֽ����
							data.addValue(resultSet.getFloat(j+2), rsmd.getColumnName(j+2),resultSet.getString(1));
						}else{//�����ֽ����
							data.addValue(resultSet.getString(j+2), rsmd.getColumnName(j+2),resultSet.getString(1));
						}
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			this.destroyCon(resultSet, statement, con);
		}
		return data;
	}
	
	public void destroyCon(ResultSet resultSet,Statement statement,Connection con){
		try {
			if(resultSet!=null){
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(statement!=null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public List createQuery(String hql) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	@Override
	public List createSQLQuery(String sql) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return query.list();
	}
}
