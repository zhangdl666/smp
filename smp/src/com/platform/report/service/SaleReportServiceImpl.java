package com.platform.report.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.pojo.Sale;
import com.platform.report.dao.BaseReportDao;
import com.platform.report.datadef.Data2D;
import com.platform.report.po.Report;

public class SaleReportServiceImpl implements SaleReportService {

	private BaseReportDao baseReportDao;
	
	public BaseReportDao getBaseReportDao() {
		return baseReportDao;
	}

	public void setBaseReportDao(BaseReportDao baseReportDao) {
		this.baseReportDao = baseReportDao;
	}

	@Override
	public Report statSale(SaleQueryBo bo) throws Exception {
		String totalSql = "select sum(s.total) from sale s where validstatus = '1' and s.sale_user_id = u.id";
		String cumulativeTotalSql = "select sum(s.total) from sale s where validstatus = '1' and s.sale_user_id in (select u2.id" +
                  " from org_user u2" +
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
		
		String sql = "select u.id||'-'||u.username||'('||u.loginname||')',(" + totalSql + ") 销售额,(" + cumulativeTotalSql + ") 累计额度 from org_user u order by u.username";
		
		
		try {
			Data2D dataSet = baseReportDao.query(true, true, sql);
			Report report = new Report();
			report.setDataSet(dataSet);
			report.setViewDetail(true);
			return report;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 统计指定用户的销售额
	 * @param bo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Report statSaleForSpecifyUser(SaleQueryBo bo,String userId) throws Exception {
		String totalSql = "select sum(s.total) from sale s where validstatus = '1' and s.sale_user_id = u.id";
		String cumulativeTotalSql = "select sum(s.total) from sale s where validstatus = '1' and s.sale_user_id in (select u2.id" +
                  " from org_user u2" +
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
		
		String sql1 = "select u.id||'-'||u.username||'('||u.loginname||')',(" + totalSql + ") 销售额 from org_user u ";
		sql1 = sql1 + " where u.id = '" + userId + "'";
		
		String sql2 = "select u.id||'-'||u.username||'('||u.loginname||')',(" + cumulativeTotalSql + ") 销售额 from org_user u ";
		sql2 = sql2 + " where u.MANAGER_ID = '" + userId + "'";
		sql2 = sql2 + " order by u.username";
		
		try {
			Data2D dataSet = baseReportDao.query(true, true, new String[]{sql1,sql2});
			Report report = new Report();
			report.setDataSet(dataSet);
			report.setViewDetail(true);
			return report;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Sale> viewSaleDetails(SaleQueryBo bo,String rowName,String columName) {
		String userId = "";
		if(rowName!=null && !rowName.equals("")){
			String[] s = rowName.split("-");
			userId = s[0];
		}
		String sql = "";
		if("累计额度".equals(columName)){//暂未用
			sql = "select s.id,s.BUSINESS_NUMBER,s.SALE_TIME,s.SALE_USER_NAME,s.total from sale s where validstatus = '1' and s.sale_user_id in (select u2.id" +
	                  " from org_user u2" +
	                 " start with u2.id = '" + userId + "' " + 
	                " connect by prior id = manager_id)";
		}else if("销售额".equals(columName)) {
			sql = "select s.id,s.BUSINESS_NUMBER,s.SALE_TIME,s.SALE_USER_NAME,s.total from sale s where validstatus = '1' and s.sale_user_id = '" + userId + "' ";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(bo.getSaleTimeFrom()!=null){
			sql = sql + " and s.sale_Time >= to_date('" + sdf.format(bo.getSaleTimeFrom()) + " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		if(bo.getSaleTimeEnd()!=null){
			sql = sql + " and s.sale_Time <= to_date('" + sdf.format(bo.getSaleTimeEnd()) + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		
		
		sql = sql + " order by s.sale_time";
		
		List<Object[]> list = baseReportDao.createSQLQuery(sql);
		if(list == null || list.size() == 0) {
			return null;
		}
		
		List<Sale> result = new ArrayList<Sale>();
		for(int i=0;i<list.size();i++) {
			Object[] obj = list.get(i);
			Sale sale = new Sale();
			sale.setId(obj[0]==null?null:(String)obj[0]);
			sale.setBusinessNumber(obj[1]==null?null:(String)obj[1]);
			sale.setSaleTime(obj[2]==null?null:(Date)obj[2]);
			sale.setSaleUserName(obj[3]==null?null:(String)obj[3]);
			sale.setTotal(obj[4]==null?null:((BigDecimal)obj[4]).doubleValue());
			result.add(sale);
		}
		
		return result;
		
	}

}
