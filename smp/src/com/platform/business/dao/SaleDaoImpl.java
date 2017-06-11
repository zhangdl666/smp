package com.platform.business.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.bo.SaleStat;
import com.platform.business.pojo.Sale;
import com.platform.core.bo.Page;

public class SaleDaoImpl implements SaleDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Sale saveSale(Sale sale) {
		sessionFactory.getCurrentSession().save(sale);
		return sale;
	}

	@Override
	public Sale getSale(String id) {
		String hql = "select s from Sale s where s.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		
		return (Sale) query.uniqueResult();
	}

	@Override
	public Page querySale(SaleQueryBo bo, Page page) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select s from Sale s ");
		sb.append(" where validstatus = '1' ");
		HashMap<Integer, Object> params = new HashMap<Integer, Object>();
		int paramIndex = 0;
		
		if(bo.getSaleTimeFrom()!=null && !bo.getSaleTimeFrom().equals("")){
			sb.append(" and s.saleTime >= ?");
			params.put(paramIndex, bo.getSaleTimeFrom());
			paramIndex = paramIndex + 1;
		}
		
		if(bo.getSaleTimeEnd()!=null && !bo.getSaleTimeEnd().equals("")){
			sb.append(" and s.saleTime <= ?");
			params.put(paramIndex, bo.getSaleTimeEnd());
			paramIndex = paramIndex + 1;
		}
		
		if(bo.getSaleUserId()!=null && !bo.getSaleUserId().equals("")){
			sb.append(" and s.saleUserId = ?");
			params.put(paramIndex, bo.getSaleUserId());
			paramIndex = paramIndex + 1;
		}
		
		if(bo.getSaleUserName()!=null && !bo.getSaleUserName().equals("")){
			sb.append(" and s.saleUserName like ?");
			params.put(paramIndex, "%" + bo.getSaleUserName() + "%");
			paramIndex = paramIndex + 1;
		}
		
		if(bo.getProductId()!=null && !bo.getProductId().equals("")){
			sb.append(" and s.productId = ?");
			params.put(paramIndex, bo.getProductId());
			paramIndex = paramIndex + 1;
		}
				
		sb.append(" order by s.saleTime desc ");
		
		String sql = sb.toString();
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		setQueryParameter(query,params);
		query.setFirstResult(page.getCurrentPageOffset());
		query.setMaxResults(page.getPageSize());
		List<Sale> list = query.list();
		
		if(list == null || list.size() ==0) {
			return page;
		}
		
		page.setResult(list);
		
		//取记录总数
		String countSql = "select count(s) " + sql.substring(sql.indexOf("from"),sql.indexOf("order"));
		Query countQuery = sessionFactory.getCurrentSession().createQuery(countSql);
		setQueryParameter(countQuery, params);
		Long count = (Long) countQuery.uniqueResult();
		page.setTotalRowSize(count.intValue());
		return page;
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
	public List<SaleStat> statSale(SaleQueryBo bo) {
		String totalSql = "select sum(s.total) from sale s where validstatus = '1' and s.sale_user_id = u.id";
		String cumulativeTotalSql = "select sum(s.total) from tt.sale s where validstatus = '1' and s.sale_user_id in (select u2.id" +
                  " from tt.org_user u2" +
                 " start with u2.id = u.id" +
                " connect by prior id = manager_id)";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(bo.getSaleTimeFrom()!=null){
			totalSql = totalSql + " and s.sale_Time >= to_date('" + sdf.format(bo.getSaleTimeFrom()) + " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
			cumulativeTotalSql = cumulativeTotalSql + " and s.sale_Time >= to_date('" + sdf.format(bo.getSaleTimeFrom()) + " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		if(bo.getSaleTimeEnd()!=null){
			totalSql = totalSql + " and s.sale_Time <= to_date('" + sdf.format(bo.getSaleTimeEnd()) + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			cumulativeTotalSql = cumulativeTotalSql + " and s.sale_Time <= to_date('" + sdf.format(bo.getSaleTimeEnd()) + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		String sql = "select u.id,u.loginname,u.username,u.manager_id,(" + totalSql + ") total,(" + cumulativeTotalSql + ") cumulativeTotal from org_user u";
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Object[]> list = query.list();
		if(list == null || list.size()==0) {
			return null;
		}
		
		List<SaleStat> result = new ArrayList<SaleStat>();
		for(int i=0; i<list.size(); i++) {
			Object[] obj = list.get(i);
			SaleStat stat = new SaleStat();
			stat.setUserId(obj[0]==null?null:(String)obj[0]);
			stat.setUserName(obj[2]==null?null:(String)obj[2]);
			stat.set_parentId(obj[3]==null?null:(String)obj[3]);
			if(obj[4] != null) {
				BigDecimal big = (BigDecimal)obj[4];
				stat.setTotal(big.doubleValue());
			}
			if(obj[5] != null) {
				BigDecimal big = (BigDecimal)obj[5];
				stat.setCumulativeTotal(((BigDecimal)obj[5]).doubleValue());
			}
			result.add(stat);
		}
		return result;
	}

	@Override
	public boolean deleteSale(String id) {
		String hql = "update Sale s set s.validstatus = 0 where s.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		
		return (query.executeUpdate() > 0);
	}
}
