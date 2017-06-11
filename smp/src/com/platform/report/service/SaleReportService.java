package com.platform.report.service;

import java.util.List;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.pojo.Sale;
import com.platform.report.po.Report;

public interface SaleReportService {

	/**
	 * ���۶����
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public Report statSale(SaleQueryBo bo) throws Exception;
	
	/**
	 * ������ϸ
	 * @param params
	 * @return
	 */
	public List<Sale> viewSaleDetails(SaleQueryBo bo,String rowName,String columName);
	
	public Report statSaleForSpecifyUser(SaleQueryBo bo,String userId) throws Exception;
}
