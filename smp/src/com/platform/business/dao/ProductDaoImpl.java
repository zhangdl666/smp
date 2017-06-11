package com.platform.business.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.business.pojo.Product;
import com.platform.core.bo.Page;

public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	// 设置query参数
	private void setQueryParameter(Query query, Map<Integer, Object> paraMap) {
		if (paraMap == null || paraMap.isEmpty()) {
			return;
		}

		Set<Integer> keys = paraMap.keySet();
		Iterator<Integer> it = keys.iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			query.setParameter(key, paraMap.get(key));
		}
	}

	@Override
	public Product saveProduct(Product product) {
		sessionFactory.getCurrentSession().save(product);
		return product;
	}

	@Override
	public Product getProduct(String id) {
		String hql = "select p from Product p where p.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		
		return (Product) query.uniqueResult();
	}

	@Override
	public Page queryProduct(String name, Page page) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p from Product p ");
		sb.append(" where p.validstatus = '1' ");
		HashMap<Integer, Object> params = new HashMap<Integer, Object>();
		int paramIndex = 0;
		
		if(name!=null && !name.equals("")){
			sb.append(" and p.name like ?");
			params.put(paramIndex, "%" + name + "%");
			paramIndex = paramIndex + 1;
		}
		
		sb.append(" order by p.name desc ");
		
		String sql = sb.toString();
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		setQueryParameter(query,params);
		query.setFirstResult(page.getCurrentPageOffset());
		query.setMaxResults(page.getPageSize());
		List<Product> list = query.list();
		
		if(list == null || list.size() ==0) {
			return page;
		}
		
		page.setResult(list);
		
		//取记录总数
		String countSql = "select count(p) " + sql.substring(sql.indexOf("from"),sql.indexOf("order"));
		Query countQuery = sessionFactory.getCurrentSession().createQuery(countSql);
		setQueryParameter(countQuery, params);
		Long count = (Long) countQuery.uniqueResult();
		page.setTotalRowSize(count.intValue());
		return page;
	}

	@Override
	public List<Product> queryProduct(String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p from Product p ");
		sb.append(" where p.validstatus = '1' ");
		HashMap<Integer, Object> params = new HashMap<Integer, Object>();
		int paramIndex = 0;
		
		if(name!=null && !name.equals("")){
			sb.append(" and p.name like ?");
			params.put(paramIndex, "%" + name + "%");
			paramIndex = paramIndex + 1;
		}
		
		sb.append(" order by p.name desc ");
		
		String sql = sb.toString();
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		setQueryParameter(query,params);
		List<Product> list = query.list();
		
		if(list == null || list.size() ==0) {
			return null;
		}
		
		return list;
	}
}
