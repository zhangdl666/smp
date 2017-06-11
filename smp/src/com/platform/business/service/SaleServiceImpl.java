package com.platform.business.service;

import java.util.List;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.bo.SaleStat;
import com.platform.business.dao.SaleDao;
import com.platform.business.pojo.Sale;
import com.platform.core.bo.Page;

public class SaleServiceImpl implements SaleService{

	private SaleDao saleDao;
	
	
	public SaleDao getSaleDao() {
		return saleDao;
	}

	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}

	@Override
	public Sale saveSale(Sale sale) {
		return saleDao.saveSale(sale);
	}

	@Override
	public Sale getSale(String id) {
		return saleDao.getSale(id);
	}

	@Override
	public Page querySale(SaleQueryBo bo, Page page) {
		return saleDao.querySale(bo, page);
	}

	@Override
	public List<SaleStat> statSale(SaleQueryBo bo) {
		return saleDao.statSale(bo);
	}

	@Override
	public boolean deleteSale(String id) {
		return saleDao.deleteSale(id);
	}

}
