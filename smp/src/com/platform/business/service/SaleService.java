package com.platform.business.service;

import java.util.List;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.bo.SaleStat;
import com.platform.business.pojo.Sale;
import com.platform.core.bo.Page;

public interface SaleService {

	public Sale saveSale(Sale sale);

	public Sale getSale(String id);
	
	public boolean deleteSale(String id);
	
	public Page querySale(SaleQueryBo bo, Page page);
	
	public List<SaleStat> statSale(SaleQueryBo bo);
}
