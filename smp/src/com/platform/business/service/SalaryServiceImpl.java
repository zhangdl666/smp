package com.platform.business.service;

import java.util.List;

import com.platform.business.dao.SalaryDao;
import com.platform.business.pojo.Salary;

public class SalaryServiceImpl implements SalaryService {

	private SalaryDao salaryDao;
	
	
	public SalaryDao getSalaryDao() {
		return salaryDao;
	}

	public void setSalaryDao(SalaryDao salaryDao) {
		this.salaryDao = salaryDao;
	}

	@Override
	public Salary saveSalary(Salary salary) {
		return salaryDao.saveSalary(salary);
	}

	@Override
	public Salary getSalary(String id) {
		return salaryDao.getSalary(id);
	}

	@Override
	public List<Salary> querySalary(String yearMonth) {
		return salaryDao.querySalary(yearMonth);
	}

	@Override
	public int deleteSalaryByYearMonth(String yearMonth) {
		return salaryDao.deleteSalaryByYearMonth(yearMonth);
	}

	@Override
	public List<Salary> calcuSalary(String yearMonth) {
		return salaryDao.calcuSalary(yearMonth);
	}

	@Override
	public List<Salary> queryBelowUserSalary(String yearMonth, String userId) {
		return salaryDao.queryBelowUserSalary(yearMonth, userId);
	}

	@Override
	public Salary getSalary(String yearMonth, String userId) {
		return salaryDao.getSalary(yearMonth, userId);
	}

}
