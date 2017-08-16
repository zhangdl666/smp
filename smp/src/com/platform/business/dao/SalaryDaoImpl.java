package com.platform.business.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.business.pojo.Salary;

public class SalaryDaoImpl implements SalaryDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Salary saveSalary(Salary salary) {
		sessionFactory.getCurrentSession().save(salary);
		return salary;
	}

	@Override
	public Salary getSalary(String id) {
		String hql = "select s from Salary s where s.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		
		return (Salary) query.uniqueResult();
	}

	@Override
	public List<Salary> querySalary(String yearMonth) {
		String sql = "select s from Salary s where s.yearMonth = ? order by s.salaryTotal desc";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter(0, yearMonth);
		List<Salary> list = query.list();
		return list;
	}

	@Override
	public int deleteSalaryByYearMonth(String yearMonth) {
		String hql = "delete Salary s where s.yearMonth = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, yearMonth);
		
		return query.executeUpdate();
		
	}

	@Override
	public List<Salary> calcuSalary(String yearMonth) {
		sessionFactory.getCurrentSession().createSQLQuery("{call pro_salary(?)}").setParameter(0, yearMonth).executeUpdate();
		return this.querySalary(yearMonth);
	}

	@Override
	public List<Salary> queryBelowUserSalary(String yearMonth, String userId) {
		String sql = "select s from Salary s,OrgUser u where s.userId = u.id and s.yearMonth = ? and u.managerId = ? order by s.salaryTotal desc";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter(0, yearMonth);
		query.setParameter(1, userId);
		List<Salary> list = query.list();
		return list;
	}

	@Override
	public Salary getSalary(String yearMonth, String userId) {
		String hql = "select s from Salary s where s.yearMonth = ? and s.userId = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, yearMonth);
		query.setString(1, userId);
		
		return (Salary) query.uniqueResult();
	}

}
