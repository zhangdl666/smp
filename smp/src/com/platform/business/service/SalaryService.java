package com.platform.business.service;

import java.util.List;

import com.platform.business.pojo.Salary;

public interface SalaryService {

	public Salary saveSalary(Salary salary);

	public Salary getSalary(String id);
	
	public List<Salary> calcuSalary(String yearMonth);

	public List<Salary> querySalary(String yearMonth);

	public int deleteSalaryByYearMonth(String yearMonth);
	
	/**
	 * 查询用户直接下级网体工资数据
	 * @param yearMonth
	 * @param userId
	 * @return
	 */
	public List<Salary> queryBelowUserSalary(String yearMonth,String userId);
	
	/**
	 * 查询用户指定月份工资
	 * @param yearMonth
	 * @param userId
	 * @return
	 */
	public Salary getSalary(String yearMonth,String userId);
}
