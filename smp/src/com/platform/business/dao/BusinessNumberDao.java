package com.platform.business.dao;


public interface BusinessNumberDao {

	/**
	 * 
	 * @param start ���ſ�ͷ��ĸ
	 * @param sequenceName ��������
	 * @return
	 */
	public String getNumber(String start,String sequenceName);
	
	/**
	 * 
	 * @param start ���ſ�ͷ��ĸ
	 * @return
	 */
	public String getNumber(String start);
}
