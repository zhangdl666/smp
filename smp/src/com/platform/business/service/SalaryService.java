package com.platform.business.service;

import java.util.List;

import com.platform.business.pojo.Salary;

public interface SalaryService {

	public Salary saveSalary(Salary salary);

	public Salary getSalary(String id);
	
	public List<Salary> calcuSalary(String yearMonth);

	public List<Salary> querySalary(String yearMonth);

	public int deleteSalaryByYearMonth(String yearMonth);
}
